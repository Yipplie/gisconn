package com.fdctech.gisconn.api.model.iterator;/**
 * Was created: 05 06 2021, 19:02:29
 * Version: 3.0
 */

import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.sprint.Type;
import com.fdctech.sprint.entry.SprintEntry;
import com.fdctech.sprint.iterator.SprintIterator;

public class MessageFileIterator extends SprintIterator<MessageFile> {
    public MessageFileIterator(MessageFile entity) {
        addEntry(new SprintEntry<>("statusDescription", Type.String, entity::getStatusDescription, entity::setStatusDescription, "description", "description"));
        addEntry(new SprintEntry<>("isCurrent", Type.Boolean, entity::isCurrent, entity::setCurrent, "is_current", "is_current"));
        addEntry(new SprintEntry<>("orderId", Type.Long, entity::getOrderId, entity::setOrderId, "order_id", "order_id"));
        addEntry(new SprintEntry<>("GUID", Type.String, entity::getGUID, entity::setGUID, "guid", "guid"));
        addEntry(new SprintEntry<>("rawTextContent", Type.String, entity::getRawTextContent, entity::setRawTextContent, "body", "body"));
        addEntry(new SprintEntry<>("ctime", Type.LocalDateTime, entity::getCtime, entity::setCtime, "ctime", "ctime"));
        addEntry(new SprintEntry<>("id", Type.Long, entity::getId, entity::setId, "id", "id"));
        addEntry(new SprintEntry<>("statusCode", Type.String, entity::getStatusCode, entity::setStatusCode, "code", "code"));
    }
}