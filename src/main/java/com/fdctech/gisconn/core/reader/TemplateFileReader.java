package com.fdctech.gisconn.core.reader;

import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.core.file.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

@Component
public class TemplateFileReader implements IReader<TemplateFile> {
    private static Logger logger = LogManager.getLogger(TemplateFileReader.class);
    @Value("${gisconn.path.template.failed}")
    private String templateErrorPath;
    @Value("${gisconn.path.template.handled}")
    private String templateLoadedPath;
    @Value("${gisconn.path.template.input}")
    private String templateInputPath;

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public TemplateFile readMessage(File file) throws JAXBException, IOException {
        try {

            TemplateFile templateFile = new TemplateFile();
            templateFile.setBody(FileUtil.getStringContent(file));
            templateFile.setName(file.getName().replace(".xml", ""));
            moveToFolder(file, templateLoadedPath);

            return templateFile;
        } catch (Exception err) {
            moveToFolder(file, templateErrorPath);
            throw err;
        }
    }

    @PostConstruct
    @Override
    public void initFolders() {
        File errorFolder = new File(templateErrorPath);
        initFolder(errorFolder);
        File loadedFolder = new File(templateLoadedPath);
        initFolder(loadedFolder);
        File inputFolder = new File(templateInputPath);
        initFolder(inputFolder);
    }


    @SuppressWarnings({"DuplicatedCode", "ResultOfMethodCallIgnored"})
    @Override
    public void moveToFolder(File file, String destinationFolder) {
        File directoryToMove = new File(destinationFolder);
        if (!directoryToMove.exists())
            if (directoryToMove.mkdirs())
                getLogger().info(format("Create path %s", destinationFolder));
            else {
                getLogger().error(format("Failed to created necessary path %s", destinationFolder));
                return;
            }
        File fileToReplace = new File(String.format("%s\\%s", directoryToMove.getAbsolutePath(), file.getName()));
        if (fileToReplace.exists())
            fileToReplace.delete();
        if (file.renameTo(fileToReplace))
            getLogger().info(format("Template file %s moved to folder %s", file.getName(), destinationFolder));
        else
            getLogger().error(format("Can't move file %s to folder %s", file.getName(), destinationFolder));
    }
}
