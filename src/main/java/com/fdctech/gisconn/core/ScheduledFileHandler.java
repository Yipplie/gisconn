package com.fdctech.gisconn.core;

import com.fdctech.coreandr.exception.DAOException;
import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.api.model.entity.OrderState;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.api.model.entity.support.State;
import com.fdctech.gisconn.core.business.MessageFileBusinessService;
import com.fdctech.gisconn.core.business.OrderStateBusinessService;
import com.fdctech.gisconn.core.business.TemplateFileBusinessService;
import com.fdctech.gisconn.core.file.FileManager;
import com.fdctech.gisconn.core.reader.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledFileHandler {
    private static Logger logger = LogManager.getLogger(ScheduledFileHandler.class);
    @Value("${order_expire_time_in_days}")
    private Long expireTimeInDays;
    @Resource
    private FileManager fileManager;
    @Resource
    private TemplateFileReader templateFileReader;
    @Resource
    private TemplateFileBusinessService templateFileBusinessService;
    @Resource
    private AnswerMessageReader answerMessageReader;
    @Resource
    private SentMessageReader sentMessageReader;
    @Resource
    private ErrorMessageReader errorMessageReader;
    @Resource
    private MessageFileBusinessService messageFileBusinessService;
    @Resource
    private OrderStateBusinessService orderStateBusinessService;


    @Scheduled(fixedDelay = 10*1000L)
    public void updateRequests() {
        //logger.info("Start update requests task...");
        updateTemplatesFromFolder();
        handleErrorFolder();
        handleSentFolder();
        handleInputSmevFolder();
    }

    @Scheduled(fixedDelay = 60*60*1000L)
    public void checkForExpires() {
        logger.info("Start check for expired requests task...");
        LocalDateTime currentTime = LocalDateTime.now();
        List<OrderState> orders = orderStateBusinessService.getAllUnfinished();
        for (OrderState orderState : orders) {
            if (orderState.getStatus().equals(State.RECEIVED.toString()))
                continue;
            MessageFile message = messageFileBusinessService.getCurrentEntityByCommonID(orderState.getOrderId());
            if (message.getCtime().plusDays(expireTimeInDays).isBefore(currentTime)) {
                orderState.setStatus(State.EXPIRED.toString());
                try {
                    orderStateBusinessService.save(orderState);
                    message.setStatusCode("GISCONN_EXPIRED");
                    message.setStatusDescription(String.format("Marked as expired at %s", currentTime));
                    messageFileBusinessService.save(message);
                } catch (DAOException e) {
                    logger.error(e);
                }
            }
        }
    }

    private void updateTemplatesFromFolder() {
        List<File> templateFiles = fileManager.getFilesInTemplateFolder();

        for (File file : templateFiles) {
            try {
                TemplateFile templateFile = templateFileReader.readMessage(file);
                templateFileBusinessService.save(templateFile);
                logger.info(String.format("Template file %s successfully saved", templateFile.getName()));
            } catch (Exception err) {
                logger.error(String.format("Can't read template file %s", file.getName()));
                logger.error(err);
            }
        }
    }

    private void handleSentFolder() {
        List<File> files = fileManager.getFilesInSentFolder();
        for (File file : files)
            updateMessageFile(file, sentMessageReader);
    }

    private void handleInputSmevFolder() {
        List<File> files = fileManager.getFilesInInputFolder();
        for (File file : files)
            updateMessageFile(file, answerMessageReader);
    }

    private void handleErrorFolder() {
        List<File> files = fileManager.getFilesInErrorFolder();
        for (File file : files)
            updateMessageFile(file, errorMessageReader);
    }

    @Transactional
    public void updateMessageFile(File file, IReader<MessageFile> reader) {
        try {
            MessageFile messageFile = reader.readMessage(file);
            messageFileBusinessService.save(messageFile);
            updateOrderStatus(messageFile);
        } catch (Exception err) {
            logger.error(String.format("Can't read file %s", file.getName()));
            logger.error(err);
        }
    }

    private void updateOrderStatus(MessageFile messageFile) throws Exception {
        OrderState state = orderStateBusinessService.getEntityByOrderId(messageFile.getCommonId());
        if (state == null)
            throw new Exception(String.format("Can't find status for message with guid %s", messageFile.getGUID()));
        state.updateStatus(messageFile);
        orderStateBusinessService.save(state);
        logger.info(String.format("Update order state with order id %s to %s", state.getOrderId(), state.getStatus()));
    }
}
