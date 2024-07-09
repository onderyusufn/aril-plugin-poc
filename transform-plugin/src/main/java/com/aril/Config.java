package com.aril;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class Config {

    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
