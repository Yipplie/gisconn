package com.fdctech.gisconn.dao;

import com.fdctech.coreandr.model.creator.ICreator;
import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.dao.support.GisconnIsCurrentDAO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MessageFileDAO extends GisconnIsCurrentDAO<MessageFile> {
    @Override
    protected ICreator<MessageFile> getCreator() {
        return MessageFile::new;
    }

    @Override
    public String getTableName() {
        return "message";
    }

    @Override
    public MessageFile getCurrentEntityByBusinessFields(MessageFile item) {
        String query = "select * from " + this.getTableName() + " where guid = :guid and is_current=:is_current";
        SqlParameterSource parameters = (new MapSqlParameterSource())
                .addValue("guid", item.getGUID())
                .addValue("is_current", true);
        return getnJdbcTemplate().queryForObject(query, parameters, this.getMapper());
    }

    @Override
    public String getSequenceName() {
        return null;
    }

    @Override
    public String getCommonIDFieldName() {
        return "order_id";
    }

    public List<MessageFile> getAllInPeriod(LocalDateTime from, LocalDateTime to) {
        String QUERY = "select * from message where ctime > :from and ctime < :to AND is_current = 1";
        SqlParameterSource parameters = (new MapSqlParameterSource())
                .addValue("from", from)
                .addValue("to",to);
        return this.getnJdbcTemplate().query(QUERY, parameters, this.getMapper());
    }

}
