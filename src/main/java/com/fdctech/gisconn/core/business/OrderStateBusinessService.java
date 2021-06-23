package com.fdctech.gisconn.core.business;

import com.fdctech.coreandr.business.GenericBusiness;
import com.fdctech.gisconn.api.model.entity.OrderState;
import com.fdctech.gisconn.api.model.entity.support.State;
import com.fdctech.gisconn.dao.OrderStateDAO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderStateBusinessService extends GenericBusiness<OrderState> {
    @Resource
    private OrderStateDAO dao;

    @Override
    protected OrderStateDAO getEntityDao() {
        return dao;
    }

    public List<OrderState> getAllUnfinished() {
        return dao.getAllUnfinished();
    }

    public OrderState getEntityByGUID(String guid) {
        return dao.getEntityByGUID(guid);
    }

    public OrderState getEntityByOrderId(Long orderId) {
        return dao.getEntityByOrderId(orderId);
    }

    public List<OrderState> getAllWithState(State status){
        return dao.getAllWithStatus(status);
    }
}
