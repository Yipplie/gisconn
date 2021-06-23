package com.fdctech.gisconn.dao;

import com.fdctech.coreandr.datasource.dao.GenericDao;
import com.fdctech.coreandr.model.creator.ICreator;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateFileDAO extends GenericDao<TemplateFile> {
    @Override
    protected ICreator<TemplateFile> getCreator() {
        return TemplateFile::new;
    }

    @Override
    public String getTableName() {
        return "template";
    }

    @Override
    public TemplateFile getEntityFromDB(TemplateFile item) {
        return getByName(item.getName());
    }

    public TemplateFile getByName(String order_type) {
        String query = String.format("select * from %s where order_type=:order_type", getTableName());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("order_type", order_type);
        return getnJdbcTemplate().queryForObject(query, parameterSource, getMapper());
    }

    public List<TemplateFile> getAll() {
        String query = String.format("select * from %s where", getTableName());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        return getnJdbcTemplate().query(query, parameterSource, getMapper());
    }
}
