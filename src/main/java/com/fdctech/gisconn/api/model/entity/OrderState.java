package com.fdctech.gisconn.api.model.entity;

import com.fdctech.gisconn.api.model.entity.support.GisEntity;
import com.fdctech.gisconn.api.model.entity.support.State;
import com.fdctech.gisconn.api.model.iterator.OrderStateIterator;
import com.fdctech.sprint.annotation.SprintEntity;
import com.fdctech.sprint.annotation.SprintField;
import com.fdctech.sprint.iterator.SprintIterator;

@SprintEntity(extendParent = false)
public class OrderState extends GisEntity<OrderState> {
    @SprintField("order_id")
    private Long orderId;
    @SprintField("status")
    private String status;

    public OrderState(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public OrderState() {
    }

    public void updateStatus(MessageFile messageFile) {
        State state = State.valueOf(status);
        if (!messageFile.getStatusCode().equals("0"))
            status = State.FAILED.toString();
        switch (state) {
            case STARTED:
                status = State.SENT.toString();
                return;
            case SENT:
                status = State.WAITING.toString();
                return;
            case WAITING:
                status = State.ACCEPTED.toString();
                return;
        }
    }

    @Override
    public SprintIterator<OrderState> getSprintIterator() {
        return new OrderStateIterator(this);
    }

    @Override
    public String getEntityName() {
        return "OrderState";
    }

    @Override
    @SprintField("id")
    public Long getId() {
        return super.getId();
    }

    @Override
    @SprintField("id")
    public void setId(Long id) {
        super.setId(id);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
