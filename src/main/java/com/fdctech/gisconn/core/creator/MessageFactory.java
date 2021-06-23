package com.fdctech.gisconn.core.creator;

import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.core.business.TemplateFileBusinessService;
import com.fdctech.gisconn.core.file.FileManager;
import com.fdctech.nautils.JSON.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.UUID;

@Component
public class MessageFactory {
    @Value("#{${gis.default.fields}}")
    private HashMap<String, String> gisDefaults;
    @Value("${order_id_key}")
    private String orderIdKey;
    @Value("${order_type_key}")
    private String orderTypeKey;
    @Resource
    private TemplateConverter templateConverter;
    @Resource
    private TemplateFileBusinessService templateFileBusinessService;
    @Resource
    private FileManager fileManager;

    public MessageFile createMessage(JSONObject requestData) throws Exception {
        MessageFile messageFile = new MessageFile();
        String orderType = requestData.getString(orderTypeKey);
        TemplateFile templateFile = templateFileBusinessService.getByName(orderType);
        if (templateFile == null)
            throw new Exception("No template file for " + orderType);
        mergeWithDefaultFields(requestData);

        String guid = UUID.randomUUID().toString();
        LocalDateTime requestTime = LocalDateTime.now(ZoneId.of("+05:00"));
        requestData.put("guid", guid);
        requestData.put("request_time", getRepresentStringOfTime(requestTime));

        messageFile.setGUID(guid);
        messageFile.setCtime(requestTime);
        messageFile.setCommonId(requestData.optLong(orderIdKey));
        messageFile.setStatusDescription("Created by gisconn");
        messageFile.setStatusCode("0");

        templateConverter.setTextContent(messageFile, templateFile, requestData);
        return messageFile;
    }

    public String getRepresentStringOfTime(LocalDateTime instant) throws DatatypeConfigurationException {
        XMLGregorianCalendar time = DatatypeFactory.newInstance().newXMLGregorianCalendar();

        time.setTimezone(300);
        time.setYear(instant.getYear());
        time.setMonth(instant.getMonthValue());
        time.setDay(instant.getDayOfMonth());
        time.setHour(instant.getHour());
        time.setMinute(instant.getMinute());
        time.setSecond(instant.getSecond());
        time.setFractionalSecond(new BigDecimal("0." + instant.getNano()));

        return  time.toXMLFormat();
    }

    private void mergeWithDefaultFields(JSONObject jsonObject) {
        gisDefaults.forEach((key, value) -> {
            if (!jsonObject.has(key))
                jsonObject.put(key, value);
        });
    }

    public void saveMessage(MessageFile messageFile){
        fileManager.saveMessageFile(messageFile);
    }
}
