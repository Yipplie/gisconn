package com.fdctech.gisconn.core.reader;

import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.core.file.FileUtil;
import com.fdctech.gisconn.gen.smev.AdapterMessage;
import com.fdctech.gisconn.gen.smev.RequestMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Component
public class SentMessageReader implements IReader<MessageFile>{
    private static Logger logger = LogManager.getLogger(SentMessageReader.class);
    @Value("${gisconn.path.handled.sent}")
    private String handledPath;
    @Value("${gisconn.path.failed.sent}")
    private String errorPath;
    @Resource
    private Unmarshaller unmarshaller;
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public MessageFile readMessage(File file) throws JAXBException, IOException {
        try {
            RequestMessageType adapterMessage = unmarshallMessage(file);
            MessageFile messageFile = new MessageFile();
            messageFile.setGUID(getClientId(adapterMessage));
            messageFile.setRawTextContent(FileUtil.getStringContent(file));
            messageFile.setStatusCode("0");
            messageFile.setStatusDescription("Сообщение отправлено в СМЭВ Адаптер");

            moveToFolder(file, handledPath);
            return messageFile;
        } catch (Exception err) {
            moveToFolder(file, errorPath);
            throw err;
        }
    }

    @PostConstruct
    @Override
    public void initFolders() {
        File handledFolder = new File(handledPath);
        initFolder(handledFolder);
        File errorFolder = new File(errorPath);
        initFolder(errorFolder);
    }

    @SuppressWarnings("unchecked")
    private RequestMessageType unmarshallMessage(File file) throws JAXBException {
        JAXBElement<AdapterMessage> adapterMessage = (JAXBElement<AdapterMessage>) unmarshaller.unmarshal(file);
        return ((RequestMessageType) adapterMessage.getValue().getMessage());
    }

    private String getClientId(RequestMessageType message) {
        return message
                .getRequestMetadata()
                .getClientId();
    }
}
