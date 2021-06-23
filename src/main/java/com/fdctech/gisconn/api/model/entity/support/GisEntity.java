package com.fdctech.gisconn.api.model.entity.support;

import com.fdctech.coreandr.model.IGenericEntity;
import com.fdctech.sprint.Type;
import com.fdctech.sprint.annotation.SprintField;
import com.fdctech.sprint.entry.SprintEntry;
import com.fdctech.sprint.iterator.SprintIterator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class GisEntity<T extends GisEntity<T>> implements IGenericEntity<T> {
    @SprintField("id")
    private Long id;
    @SprintField("ctime")
    private LocalDateTime ctime;
    private LocalDateTime mtime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getcUserId() {
        return null;
    }

    @Override
    public void setcUserId(Long cUserId) {
    }

    @Override
    public Long getmUserId() {
        return null;
    }

    @Override
    public void setmUserId(Long mUserId) {
    }

    @Override
    public LocalDateTime getCtime() {
        return ctime;
    }

    @Override
    public LocalDateTime getMtime() {
        return mtime;
    }

    @Override
    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    @Override
    public void setMtime(LocalDateTime mtime) {
        this.mtime = mtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
            sb.append("id=").append(this.id);
        SprintIterator<?> iterator = getSprintIterator();
        for (SprintEntry<?> entry : iterator) {
            if (entry.get() != null && entry.get().toString().length() > 100)
                continue;
            if (!entry.getType().equals(Type.ByteArray) && entry.get() != null && entry.getFieldName() != "id")
                sb.append(", ").append(entry.getFieldName()).append("=").append(entry.get());
        }
        return sb.append("}").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GisEntity<?> gisEntity = (GisEntity<?>) o;

        SprintIterator<?> thisIterator = getSprintIterator();
        Iterator<?> anotherIterator = gisEntity.getSprintIterator().iterator();
        for (SprintEntry<?> entry : thisIterator){
            if (!Objects.equals(entry.get(), anotherIterator.next()))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        SprintIterator<?> iterator = getSprintIterator();
        List<Object> hashs = new ArrayList<>();
        for (SprintEntry<?> entry : iterator)
            hashs.add(entry.get());
        return Objects.hash(hashs.toArray());
    }


    @Override
    public Boolean isDeleted() {
        return false;
    }

    @Override
    public void setDeleted(Boolean deleted) {

    }
}
