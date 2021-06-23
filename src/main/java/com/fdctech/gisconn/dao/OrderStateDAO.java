package com.fdctech.gisconn.dao;

import com.fdctech.coreandr.datasource.dao.GenericDao;
import com.fdctech.coreandr.model.creator.ICreator;
import com.fdctech.gisconn.api.model.entity.OrderState;
import com.fdctech.gisconn.api.model.entity.support.State;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderStateDAO extends GenericDao<OrderState> {
    @Override
    protected ICreator<OrderState> getCreator() {
        return OrderState::new;
    }

    @Override
    public OrderState getEntityFromDB(OrderState item) {
        return getEntityByOrderId(item.getOrderId());
    }

    @Override
    public String getTableName() {
        return "state";
    }

    public OrderState getEntityByGUID(String guid) {
        String QUERY = "select * from state where guid = :guid";
        SqlParameterSource parameters = (new MapSqlParameterSource())
                .addValue("guid", guid);
        return this.getnJdbcTemplate().queryForObject(QUERY, parameters, this.getMapper());
    }

    public OrderState getEntityByOrderId(Long orderId) {
        String QUERY = "select * from state where order_id = :order_id";
        SqlParameterSource parameters = (new MapSqlParameterSource())
                .addValue("order_id", orderId);
        return this.getnJdbcTemplate().queryForObject(QUERY, parameters, this.getMapper());
    }

    public List<OrderState> getAllUnfinished() {
        String QUERY = "select * from state where (" +
                "state.status <> :accepted AND " +
                "state.status <> :failed AND " +
                "state.status <> :expired)";
        SqlParameterSource parameters = (new MapSqlParameterSource())
                .addValue("failed", State.FAILED.toString())
                .addValue("expired", State.EXPIRED.toString())
                .addValue("accepted", State.ACCEPTED.toString());
        return this.getnJdbcTemplate().query(QUERY, parameters, this.getMapper());
    }

    public List<OrderState> getAllWithStatus(State status) {
        String QUERY = "select * from state where state.status = :status";
        SqlParameterSource parameters = (new MapSqlParameterSource())
                .addValue("status", status.toString());
        return this.getnJdbcTemplate().query(QUERY, parameters, this.getMapper());
    }


}
