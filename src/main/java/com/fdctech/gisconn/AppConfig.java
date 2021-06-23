package com.fdctech.gisconn;

import com.fdctech.coreandr.http.RequestJsonConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@EnableWebMvc
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        HttpMessageConverter<?> converter = null;
        for (int i = 0; i < converters.size(); i++) {
            converter = converters.get(i);
            if (converter instanceof AbstractJackson2HttpMessageConverter) {
                converters.remove(converter);
            }
        }

        converters.add(new RequestJsonConverter());
        converters.add(converter);
    }
}