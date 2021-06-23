package com.fdctech.gisconn;

import com.fdctech.coreandr.exception.DAOException;
import com.fdctech.gisconn.api.controller.InputController;
import com.fdctech.gisconn.api.model.entity.TemplateFile;
import com.fdctech.gisconn.core.business.MessageFileBusinessService;
import com.fdctech.gisconn.core.business.TemplateFileBusinessService;
import com.fdctech.gisconn.core.creator.TemplateConverter;
import com.fdctech.gisconn.core.reader.TemplateFileReader;
import com.fdctech.nautils.JSON.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class SaveDBTest extends GenericTest {

    @Resource
    TemplateFileBusinessService templateFileBusinessService;
    @Resource
    TemplateFileReader templateFileReader;
    @Resource
    private TemplateConverter templateConverter;
    @Resource
    private InputController inputController;
    @Resource
    private MessageFileBusinessService messageFileBusinessService;

    @Test
    public void templateSaveTest() throws DAOException, JAXBException, IOException {
        templateFileBusinessService.save(
                templateFileReader.readMessage(
                        new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\import.xml"))
        );

        TemplateFile file = templateFileBusinessService.getByName("import");
        Assert.assertNotNull(file);
        System.out.println(file.getBody());
    }

    @Test
    public void receiveMessageTest() throws DAOException, JAXBException, IOException {
        templateSaveTest();
        JSONObject jsonObject = new JSONObject("{\n" +
                "   \"order_id\":\"1\", \n" +
                "   \"order_type\":\"import\",\n" +
                "   \"is_test\":\"false\",\n" +
//                "   // business fields\n" +
                "   \"supplier_bill_id\" : \"32116102414550976332\", \n" +
                "   \"bill_date\" : \"12354123512\", \n" +
                "   \"total_amount\" : \"500000\",\n" +
                "   \"purpose\" : \"Плата за предоставление сведений из Единого государственного реестра недвижимости\", \n" +
                "   \"kbk\" : \"32111301031016000130\",\n" +
                "   \"oktmo\" : \"45348000\",\n" +
//                "   // Payee\n" +
                "   \"payee_name\" : \"ФГБУ «ФКП Росреестра» по г Москва\",\n" +
                "   \"payee_kpp\" : \"770542151\",\n" +
                "   \"payee_ogrn\" : \"7723819340452\",\n" +
                "   \"payee_inn\" : \"7705401341\",\n" +
//                "   // AccInfo\n" +
                "   \"account_number\" : \"40101810045250010041\",\n" +
                "   \"correspondent_bank_account\" : \"40101810045250010041\",\n" +
//                "   // BankInfo\n" +
                "   \"bank_name\" : \"ГУ Банка России по ЦФО\",\n" +
                "   \"bank_bik\" : \"044525000\",\n" +
//                "   //Payer \n" +
                "   \"payer_id\" : \"1220000000007712579832\",\n" +
                "   \"payer_name\" : \"&quot;Тестовый плательщик&quot;\"\n" +
                "}");

        inputController.createNewMessage(jsonObject);

        Assert.assertNotNull(messageFileBusinessService.getCurrentEntityByCommonID(1L));
    }
}
