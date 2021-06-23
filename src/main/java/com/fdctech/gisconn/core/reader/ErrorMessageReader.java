package com.fdctech.gisconn.core.reader;

import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.core.file.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
public class ErrorMessageReader implements IReader<MessageFile> {
    private static Logger logger = LogManager.getLogger(ErrorMessageReader.class);
    @Value("${gisconn.path.handled.error}")
    private String errorHandledPath;
    @Value("${gisconn.path.failed.error}")
    private String errorFailedPath;

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public MessageFile readMessage(File file) throws Exception {
        try {
            MessageFile messageFile = new MessageFile();
            messageFile.setGUID(getGUIDFromFileName(file));
            messageFile.setStatusCode("GISCONN_SENT_ERROR");
            messageFile.setStatusDescription("Ошибка при отправке в СМЭВ");
            messageFile.setRawTextContent(FileUtil.getStringContent(file));

            moveToFolder(file, errorHandledPath);
            return messageFile;
        } catch (Exception err) {
            moveToFolder(file, errorFailedPath);
            throw err;
        }
    }

    @PostConstruct
    @Override
    public void initFolders() {
        File errorHandledFolder = new File(errorHandledPath);
        initFolder(errorHandledFolder);
        File errorFailedFolder = new File(errorFailedPath);
        initFolder(errorFailedFolder);
    }

    private String getGUIDFromFileName(File file) throws Exception {
        String suffix = ".xml}.error";
        if (!file.getName().endsWith(suffix) || !file.getName().startsWith("{"))
            throw new Exception(String.format("Can't get guid from file %s", file.getName()));
        return file.getName()
                .replace("{", "")
                .replace(suffix, "");
    }
}
