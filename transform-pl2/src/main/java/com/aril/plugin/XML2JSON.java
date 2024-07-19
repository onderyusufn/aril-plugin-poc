package com.aril.plugin;

import com.aril.plugin.annotations.Extension;
import com.aril.plugin.annotations.process.PostProcess;
import com.aril.plugin.constant.ExtendableConstants;
import com.aril.plugin.type.Transform;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Extension(id = ExtendableConstants.CREATE_USER)
//@RequiredArgsConstructor
public class XML2JSON implements Transform {

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public XML2JSON() {
        this.objectMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
    }

    @PostProcess
    @Override
    public Object transform(Object input) {
        Object json;
        try {
            json = objectMapper.readValue(input.toString(), Object.class);
            return xmlMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
