package com.fdctech.gisconn.api.model.iterator;/**
 * Was created: 05 06 2021, 19:02:29
 * Version: 3.0
 */

import com.fdctech.gisconn.api.model.entity.OrderState;
import com.fdctech.sprint.Type;
import com.fdctech.sprint.entry.SprintEntry;
import com.fdctech.sprint.iterator.SprintIterator;

public class OrderStateIterator extends SprintIterator<OrderState> {
    public OrderStateIterator(OrderState entity) {
        addEntry(new SprintEntry<>("orderId", Type.Long, entity::getOrderId, entity::setOrderId, "order_id", "order_id"));
        addEntry(new SprintEntry<>("id", Type.Long, entity::getId, entity::setId, "id", "id"));
        addEntry(new SprintEntry<>("status", Type.String, entity::getStatus, entity::setStatus, "status", "status"));
    }
}