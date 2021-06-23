package com.fdctech.gisconn.api.model.entity;

import com.fdctech.gisconn.api.model.entity.support.GisIsCurrentEntity;
import com.fdctech.gisconn.api.model.iterator.MessageFileIterator;
import com.fdctech.sprint.annotation.SprintEntity;
import com.fdctech.sprint.annotation.SprintField;
import com.fdctech.sprint.iterator.SprintIterator;

@SprintEntity
public class MessageFile extends GisIsCurrentEntity<MessageFile> {
    @SprintField("body")
    private String rawTextContent;
    @SprintField("guid")
    private String GUID;
    @SprintField("code")
    private String statusCode;
    @SprintField("description")
    private String statusDescription;
    @SprintField("order_id")
    private Long orderId;

    public String getRawTextContent() {
        return rawTextContent;
    }

    public void setRawTextContent(String rawTextContent) {
        this.rawTextContent = new String(rawTextContent.getBytes());
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Override
    public SprintIterator getSprintIterator() {
        return new MessageFileIterator(this);
    }

    @Override
    public String getEntityName() {
        return "AnswerMessage";
    }

    @Override
    public Integer getEntityLockId() {
        return 1;
    }

    @Override
    public Long getCommonId() {
        return orderId;
    }

    @Override
    public void setCommonId(Long commonId) {
        this.orderId = commonId;
    }



    public Long getOrderId() {
        return getCommonId();
    }

    public void setOrderId(Long orderId) {
        setCommonId(orderId);
    }
}
