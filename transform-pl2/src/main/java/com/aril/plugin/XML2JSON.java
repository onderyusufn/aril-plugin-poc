package com.aril.plugin;

import com.aril.plugin.annotations.Extension;
import com.aril.plugin.annotations.process.PostProcess;
import com.aril.plugin.constant.ExtendableConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
@Extension(id = ExtendableConstants.CREATE_USER)
//@RequiredArgsConstructor
public class XML2JSON {

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public XML2JSON() {
        this.objectMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
    }
    @PostProcess
    public Object preProcess(Object input) throws JsonProcessingException {
        Object json = objectMapper.readValue(input.toString(), Object.class);
        return xmlMapper.writeValueAsString(json);
    }
}
