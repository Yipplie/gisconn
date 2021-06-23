package com.fdctech.gisconn.dao.support;

import com.fdctech.coreandr.datasource.dao.GenericIsCurrentDao;
import com.fdctech.coreandr.exception.DAOException;
import com.fdctech.gisconn.api.model.entity.support.GisIsCurrentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GisconnIsCurrentDAO<T extends GisIsCurrentEntity<T>> extends GenericIsCurrentDao<T> {
    private static Logger logger = LogManager.getLogger(GisconnIsCurrentDAO.class);
    protected static final ConcurrentHashMap<Integer, Object> lockMap = new ConcurrentHashMap<>();

    @Override
    protected T update(T item, T oldItem) throws DAOException {
        if (!isNewRecordNeeded(item, oldItem)){
            logger.info("New record not needed");
            return super.update(item);
        } else {
            lockMap.putIfAbsent(item.getEntityLockId(), new Object());
            synchronized (lockMap.get(item.getEntityLockId())) {
                return updateWithNewRecord(item, oldItem);
            }
        }
    }

    private void cleanIsCurrentFlag(Long commonId) throws DAOException {
        final String UPDATE_QUERY = String.format("update %s set is_current = 0 where %s = :commonId and is_current=1",
                getTableName(), getCommonIDFieldName());

        SqlParameterSource parameters = new MapSqlParameterSource().addValue("commonId", commonId);
        int count = getnJdbcTemplate().update(UPDATE_QUERY, parameters);

        if (count == 0)
            throw new DAOException(String.format("0 rows updated for common_id: %s", commonId));
        else {
            logger.info(String.format("%s rows updated for common_id: %s", count, commonId));
        }
    }

    protected T updateWithNewRecord(T item, T oldItem) throws DAOException  {

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
            public void afterCompletion(int status){

                logger.info(String.format("transaction ended with %s status",
                        (status == STATUS_COMMITTED ? "COMMITED" :
                                status == STATUS_ROLLED_BACK ? "ROLLED_BACK" :
                                        status == STATUS_UNKNOWN ? "UNKNOWN" : "VERY_UNKNOWN"))
                );

                //do stuff right after commit
            }
        });

        logger.info("New record needed");

        cleanIsCurrentFlag(item.getCommonId());

        if (item.getMtime() == null) item.setMtime(LocalDateTime.now());

        if (item.getcUserId() == null || item.getmUserId() == null) {
            //TODO set user later when we'll start to support it
        }
        Number newID = getInsertActor().executeAndReturnKey(mergeParametersWithoutPK(oldItem, item));

        item.setId(newID.longValue());
        oldItem.setId(item.getId());

        return oldItem;
    }

    @Override
    public Long count() {
        final String query = String.format("select count(*) from %s where is_current = 1", getTableName());
        return getJdbcTemplateObject().queryForObject(query, Long.class);
    }
}
