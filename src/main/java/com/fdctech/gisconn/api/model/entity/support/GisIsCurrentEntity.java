package com.fdctech.gisconn.api.model.entity.support;

import com.fdctech.coreandr.model.IGenericIsCurrentEntity;
import com.fdctech.sprint.annotation.SprintField;

public abstract class GisIsCurrentEntity<T extends GisIsCurrentEntity<T>> extends GisEntity<T> implements IGenericIsCurrentEntity<T> {
    @SprintField("is_current")
    private Boolean isCurrent;

    @Override
    public Boolean isCurrent() {
        return isCurrent;
    }

    @Override
    public void setCurrent(Boolean current) {
        isCurrent = current;
    }
}
