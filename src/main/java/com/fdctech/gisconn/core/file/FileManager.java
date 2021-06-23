package com.fdctech.gisconn.core.file;

import com.fdctech.gisconn.api.model.entity.MessageFile;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileManager {
    private Logger logger = LogManager.getLogger(FileManager.class);
    @Value("${smev.input.path}")
    private String inputDirectoryPath;
    @Value("${smev.output.path}")
    private String outputDirectoryPath;
    @Value("${smev.error.path}")
    private String errorDirectoryPath;
    @Value("${smev.sent.path}")
    private String sentDirectoryPath;

    @Value("${gisconn.path.template.input}")
    private String templateInputPath;
    @Value("${gisconn.path.template.handled}")
    private String templateHandledPath;
    @Value("${gisconn.path.template.failed}")
    private String templateErrorPath;

    @Value("${gisconn.path.handled.input}")
    private String inputHandledPath;
    @Value("${gisconn.path.failed.input}")
    private String inputErrorPath;

    @Value("${gisconn.path.handled.sent}")
    private String sentHandledPath;
    @Value("${gisconn.path.failed.sent}")
    private String sentErrorPath;

    @Value("${gisconn.path.handled.sent}")
    private String errorHandledPath;
    @Value("${gisconn.path.failed.error}")
    private String errorFailHandledPath;

    public void freeMemory() throws IOException {
        FileUtil.freeDirectory(templateErrorPath);
        FileUtil.freeDirectory(templateHandledPath);
        FileUtil.freeDirectory(templateInputPath);

        FileUtil.freeDirectory(sentErrorPath);
        FileUtil.freeDirectory(sentHandledPath);

        FileUtil.freeDirectory(inputErrorPath);
        FileUtil.freeDirectory(inputHandledPath);

        FileUtil.freeDirectory(errorFailHandledPath);
        FileUtil.freeDirectory(errorHandledPath);
    }

    public void saveMessageFile(MessageFile messageFile) {
        String fileName = String.format("%s\\%s.xml", outputDirectoryPath, messageFile.getGUID());
        tryToSave(fileName, messageFile.getRawTextContent());
    }

    public List<File> getFilesInInputFolder() {
        return getFilesInFolder(inputDirectoryPath);
    }

    public List<File> getFilesInSentFolder() {
        return getFilesInFolder(sentDirectoryPath);
    }


    public List<File> getFilesInErrorFolder() {
        return getFilesInFolder(errorDirectoryPath);
    }

    public List<File> getFilesInTemplateFolder() {
        return getFilesInFolder(templateInputPath);
    }

    private List<File> getFilesInFolder(String folderPath) {
        try {
            return FileUtil.getXmlFilesInDirectory(folderPath);
        } catch (IOException e) {
            logger.error(String.format("Search files in %s error", folderPath));
            logger.error(e);
            return new ArrayList<>();
        }
    }

    private void tryToSave(String fileName, String data) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            FileUtils.writeStringToFile(new File(fileName), data, Charset.forName("utf-8"));
        } catch (Exception e) {
            logger.error(String.format("File creation %s error\n", fileName) + e.getMessage());
        }
    }
}
