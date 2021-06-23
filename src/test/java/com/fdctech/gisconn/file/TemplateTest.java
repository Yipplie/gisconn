package com.fdctech.gisconn.file;

import com.fdctech.gisconn.GenericTest;
import com.fdctech.gisconn.api.controller.InputController;
import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.core.business.MessageFileBusinessService;
import com.fdctech.gisconn.core.business.TemplateFileBusinessService;
import com.fdctech.gisconn.core.creator.MessageFactory;
import com.fdctech.gisconn.core.creator.TemplateConverter;
import com.fdctech.nautils.JSON.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Files;

public class TemplateTest extends GenericTest {

    @Resource
    private TemplateConverter templateConverter;
    @Resource
    private MessageFactory messageFactory;
    @Resource
    private InputController inputController;
    @Resource
    private MessageFileBusinessService messageFileBusinessService;
    @Resource
    private TemplateFileBusinessService templateFileBusinessService;

    @Test
    public void testTemplate() throws Exception {
        TemplateFile templateFile = new TemplateFile();
        templateFile.setName("import");
        File file = new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\import.xml");
        templateFile.setBody(new String(Files.readAllBytes(file.toPath())));
        templateFileBusinessService.save(templateFile);

        MessageFile messageFile = new MessageFile();
        templateConverter.setTextContent(
                messageFile,
                templateFile,
                new JSONObject()
                        .put("payer_name", "YOLO")
                        .put("payer_id", "YOLO"));

        System.out.println(messageFile.getRawTextContent());
    }

    @Test
    public void testCreateMessageFromTemplateWithController() throws Exception {
        testTemplate();
        JSONObject jsonObject = new JSONObject("{\n" +
                "   \"order_id\":\"1\", \n" +
                "   \"order_type\":\"import\",\n" +
                "   \"is_test\":\"true\",\n" +
//                "   // business fields\n" +
                "   \"supplier_bill_id\" : \"11111111111111111111\", \n" +
                "   \"bill_date\" : \"12354123512\", \n" +
                "   \"total_amount\" : \"500000\",\n" +
                "   \"purpose\" : \"Плата за предоставление сведений из Единого государственного реестра недвижимости\", \n" +
                "   \"kbk\" : \"11111111111111111111\",\n" +
                "   \"oktmo\" : \"11111111\",\n" +
//                "   // Payee\n" +
                "   \"payee_name\" : \"ФГБУ «ФКП Росреестра» по г Москва\",\n" +
                "   \"payee_kpp\" : \"111111111\",\n" +
                "   \"payee_ogrn\" : \"1111111111111\",\n" +
                "   \"payee_inn\" : \"11111111111111\",\n" +
//                "   // AccInfo\n" +
                "   \"account_number\" : \"11111111111111111111\",\n" +
                "   \"correspondent_bank_account\" : \"11111111111111111111\",\n" +
//                "   // BankInfo\n" +
                "   \"bank_name\" : \"ГУ Банка России по ЦФО\",\n" +
                "   \"bank_bik\" : \"111111111\",\n" +
//                "   //Payer \n" +
                "   \"payer_id\" : \"1111111111111111111111\",\n" +
                "   \"payer_name\" : \"&quot;Тестовый плательщик&quot;\"\n" +
                "}");

        inputController.createNewMessage(jsonObject);

        MessageFile file = messageFileBusinessService.getCurrentEntityByCommonID(1L);
        Assert.assertNotNull(file);
        file.setGUID("fe3a435e-a30f-4e7f-a844-87e1ba3933f5");
    }
}
