package com.fdctech.gisconn.api.model.entity;

import com.fdctech.gisconn.api.model.entity.support.GisEntity;
import com.fdctech.gisconn.api.model.iterator.TemplateFileIterator;
import com.fdctech.sprint.annotation.SprintEntity;
import com.fdctech.sprint.annotation.SprintField;
import com.fdctech.sprint.iterator.SprintIterator;

@SprintEntity
public class TemplateFile extends GisEntity<TemplateFile> {
    @SprintField("body")
    private String body;
    @SprintField("order_type")
    private String name;

    @Override
    public SprintIterator getSprintIterator() {
        return new TemplateFileIterator(this);
    }

    @Override
    public String getEntityName() {
        return "TemplateFile";
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
