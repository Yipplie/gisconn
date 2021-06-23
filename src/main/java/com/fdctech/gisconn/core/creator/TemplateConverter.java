package com.fdctech.gisconn.core.creator;

import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.nautils.JSON.JSONObject;
import com.fdctech.tempura.util.TextRedactor;
import org.springframework.stereotype.Component;

@Component
public class TemplateConverter {
    public void setTextContent(MessageFile messageFile, TemplateFile templateFile, JSONObject data) throws Exception {
        if (templateFile == null)
            throw new Exception("Template file is null");

        fixSpecialSymbols(data);

        messageFile.setRawTextContent(TextRedactor.handleSimpleKeys(templateFile.getBody(), data.toMap()));
    }

    private void fixSpecialSymbols(JSONObject data){
        for (String key : data.keySet()) {
            String value = data.optString(key);
            if (value.contains("\""))
                data.put(key, value.replaceAll(value, "&quot;"));
        }
    }
}
