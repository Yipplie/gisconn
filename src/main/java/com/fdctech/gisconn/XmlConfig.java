package com.fdctech.gisconn;

import com.fdctech.gisconn.gen.smev.AdapterMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

@Configuration
public class XmlConfig {

    @Bean
    public JAXBContext getXmlJaxbContext() throws JAXBException {
        return JAXBContext.newInstance(AdapterMessage.class);
    }

    @Bean
    public Unmarshaller getUnmarshaller(@Autowired JAXBContext context) throws JAXBException {
        return context.createUnmarshaller();
    }
}
