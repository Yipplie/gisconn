package com.fdctech.gisconn.api.model.iterator;/**
 * Was created: 05 06 2021, 19:02:29
 * Version: 3.0
 */

import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.sprint.Type;
import com.fdctech.sprint.entry.SprintEntry;
import com.fdctech.sprint.iterator.SprintIterator;

public class TemplateFileIterator extends SprintIterator<TemplateFile> {
    public TemplateFileIterator(TemplateFile entity) {
        addEntry(new SprintEntry<>("name", Type.String, entity::getName, entity::setName, "order_type", "order_type"));
        addEntry(new SprintEntry<>("ctime", Type.LocalDateTime, entity::getCtime, entity::setCtime, "ctime", "ctime"));
        addEntry(new SprintEntry<>("id", Type.Long, entity::getId, entity::setId, "id", "id"));
        addEntry(new SprintEntry<>("body", Type.String, entity::getBody, entity::setBody, "body", "body"));
    }
}