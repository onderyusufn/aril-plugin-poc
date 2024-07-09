package com.aril;

import com.aril.annotations.Extension;
import com.aril.annotations.process.PostProcess;
import com.aril.annotations.process.PreProcess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Extension(id = "createUser")
@RequiredArgsConstructor
public class XML2JSON {

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;
    @PostProcess
    public Object preProcess(String input) throws JsonProcessingException {
        Object json = objectMapper.readValue(input, Object.class);
        return xmlMapper.writeValueAsString(json);
    }
}
