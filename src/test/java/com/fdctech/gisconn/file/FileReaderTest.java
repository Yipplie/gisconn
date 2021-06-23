package com.fdctech.gisconn.file;

import com.fdctech.gisconn.GenericTest;
import com.fdctech.gisconn.api.model.entity.MessageFile;
import com.fdctech.gisconn.core.reader.AnswerMessageReader;
import com.fdctech.gisconn.core.reader.ErrorMessageReader;
import com.fdctech.gisconn.core.reader.SentMessageReader;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;

public class FileReaderTest extends GenericTest {
    @Resource
    private ErrorMessageReader errorMessageReader;
    @Resource
    private SentMessageReader sentMessageReader;
    @Resource
    private AnswerMessageReader answerMessageReader;



    @Test
    public void readErrorMessageTest() throws Exception {
        File file = new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\{fe3a435e-a30f-4e7f-a844-87e1ba3933f5.xml}.error");
        MessageFile messageFile = errorMessageReader.readMessage(file);
        Assert.assertEquals("fe3a435e-a30f-4e7f-a844-87e1ba3933f5", messageFile.getGUID());
        Assert.assertEquals("Ошибка при отправке в СМЭВ", messageFile.getStatusDescription());
        Assert.assertEquals(Integer.valueOf(300), messageFile.getStatusCode());
    }

    @Test
    public void readSentMessageTest() throws Exception {
        File file = new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\sentSample.xml");
        MessageFile messageFile = sentMessageReader.readMessage(file);
        Assert.assertEquals("fe3a435e-a30f-4e7f-a844-87e1ba3933f5", messageFile.getGUID());
        Assert.assertEquals("Сообщение отправлено в СМЭВ Адаптер", messageFile.getStatusDescription());
        Assert.assertEquals(Integer.valueOf(0), messageFile.getStatusCode());
    }

    @Test
    public void readStatusMessage() throws Exception {
        File file = new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\statusSample.xml");
        MessageFile messageFile = answerMessageReader.readMessage(file);
        Assert.assertEquals("fe3a435e-a30f-4e7f-a844-87e1ba3933f5", messageFile.getGUID());
        Assert.assertEquals("Сообщение отправлено в СМЭВ", messageFile.getStatusDescription());
        Assert.assertEquals(Integer.valueOf(0), messageFile.getStatusCode());
    }

    @Test
    public void readPrimaryMessage() throws Exception {
        File file = new File("D:\\Sources\\IdeaProjects\\gisconn\\src\\test\\resources\\primarySample.xml");
        MessageFile messageFile = answerMessageReader.readMessage(file);
        Assert.assertEquals("fe3a435e-a30f-4e7f-a844-87e1ba3933f5", messageFile.getGUID());
        Assert.assertEquals("успешно", messageFile.getStatusDescription());
        Assert.assertEquals(Integer.valueOf(0), messageFile.getStatusCode());
    }
}
