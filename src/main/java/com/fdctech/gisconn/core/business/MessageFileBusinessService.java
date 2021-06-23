package com.fdctech.gisconn.core.business;

import com.fdctech.coreandr.business.GenericIsCurrentBusiness;
import com.fdctech.coreandr.datasource.dao.IsCurrentEntityDAO;
import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.api.model.entity.OrderState;
import com.fdctech.gisconn.api.model.entity.support.State;
import com.fdctech.gisconn.dao.MessageFileDAO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageFileBusinessService extends GenericIsCurrentBusiness<MessageFile> {
    @Resource
    private MessageFileDAO dao;
    @Resource
    OrderStateBusinessService orderStateBusinessService;

    @Override
    protected IsCurrentEntityDAO<MessageFile> getEntityDao() {
        return dao;
    }

    @Override
    public MessageFile getCurrentEntityByCommonID(long commonID) {
        return dao.getCurrentEntityByCommonID(commonID);
    }

    public List<MessageFile> getAllWithStateInPeriod(LocalDateTime from, LocalDateTime to, State state){
        List<MessageFile> messageFiles = dao.getAllInPeriod(from, to);
        List<MessageFile> result = new ArrayList<>();
        for(MessageFile file : messageFiles){
            OrderState orderState = orderStateBusinessService.getEntityByOrderId(file.getOrderId());
            if (orderState.getStatus().equals(state.toString()))
                result.add(file);

        }
        return result;
    }
}
