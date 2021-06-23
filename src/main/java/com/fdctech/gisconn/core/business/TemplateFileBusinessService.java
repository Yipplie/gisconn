package com.fdctech.gisconn.core.business;

import com.fdctech.coreandr.business.GenericBusiness;
import com.fdctech.coreandr.datasource.dao.IEntityDAO;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.core.file.FileManager;
import com.fdctech.gisconn.dao.TemplateFileDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TemplateFileBusinessService extends GenericBusiness<TemplateFile> {
    private static final Logger logger = LogManager.getLogger(TemplateFileBusinessService.class);
    @Resource
    private TemplateFileDAO dao;
    @Resource
    private FileManager fileManager;

    @Override
    protected IEntityDAO<TemplateFile> getEntityDao() {
        return dao;
    }

    public TemplateFile getByName(String name) {
        return dao.getByName(name);
    }

    public List<TemplateFile> getAll() {
        return dao.getAll();
    }

    public TemplateFile getTemplateByName(String name) {
        return dao.getByName(name);
    }
}
