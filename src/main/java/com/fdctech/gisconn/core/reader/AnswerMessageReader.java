package com.fdctech.gisconn.core.reader;

import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.core.file.FileUtil;
import com.fdctech.gisconn.gen.smev.AdapterMessage;
import com.fdctech.gisconn.gen.smev.ResponseMessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class AnswerMessageReader implements IReader<MessageFile> {
    private static Logger logger = LogManager.getLogger(AnswerMessageReader.class);
    private static final String STATUS_NODE_NAME = "com:ImportProtocol";
    private static final String STATUS_CODE_ATTRIBUTE_NAME = "code";
    private static final String STATUS_DESCRIPTION_ATTRIBUTE_NAME = "description";
    @Resource
    private Unmarshaller unmarshaller;
    @Value("${gisconn.path.failed.input}")
    private String errorPath;
    @Value("${gisconn.path.handled.input}")
    private String handledPath;

    @PostConstruct
    @Override
    public void initFolders() {
        File errorFolder = new File(errorPath);
        initFolder(errorFolder);
        File handledFolder = new File(handledPath);
        initFolder(handledFolder);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public MessageFile readMessage(File file) throws Exception {
        try {
            ResponseMessageType adapterMessage = unmarshallMessage(file);
            String type = adapterMessage.getMessageType();
            MessageFile messageFile = new MessageFile();
            messageFile.setGUID(adapterMessage.getResponseMetadata().getReplyToClientId());
            messageFile.setRawTextContent(FileUtil.getStringContent(file));

            if (type.equals("StatusMessage") || type.equals("RejectMessage")) {
                messageFile.setStatusCode(getStatusCode(adapterMessage));
                messageFile.setStatusDescription(getStatusDescription(adapterMessage));
            } else if (type.equals("PrimaryMessage")) {
                Node statusNode = getStatusNode(adapterMessage);
                messageFile.setStatusCode(getStatusCode(statusNode));
                messageFile.setStatusDescription(getStatusDescription(statusNode));
            }
            else throw new Exception(String.format("Unexpected type of message %s", type));

            moveToFolder(file, handledPath);
            return messageFile;
        } catch (Exception err) {
            moveToFolder(file, errorPath);
            throw err;
        }
    }

    @SuppressWarnings("unchecked")
    private ResponseMessageType unmarshallMessage(File file) throws JAXBException {
        JAXBElement<AdapterMessage> adapterMessage = (JAXBElement<AdapterMessage>) unmarshaller.unmarshal(file);
        return ((ResponseMessageType) adapterMessage.getValue().getMessage());
    }

    private String getStatusCode(ResponseMessageType message) {
        String stringCode = message
                .getResponseContent()
                .getStatus()
                .getCode();
        return stringCode;
    }

    private String getStatusDescription(ResponseMessageType message) {
        return message
                .getResponseContent()
                .getStatus()
                .getDescription();
    }

    private String getStatusCode(Node node) {
        String stringCode = node.getAttributes().getNamedItem(STATUS_CODE_ATTRIBUTE_NAME).getTextContent();
        return stringCode;
    }

    private String getStatusDescription(Node node) {
        return node.getAttributes().getNamedItem(STATUS_DESCRIPTION_ATTRIBUTE_NAME).getTextContent();
    }

    private Node getStatusNode(ResponseMessageType responseMessageType) {
        return responseMessageType
                .getResponseContent()
                .getContent()
                .getMessagePrimaryContent()
                .getAny()
                .getElementsByTagName(STATUS_NODE_NAME)
                .item(0);
    }

}
