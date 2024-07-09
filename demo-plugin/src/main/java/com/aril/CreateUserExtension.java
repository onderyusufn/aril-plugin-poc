package com.aril;

import com.aril.annotations.Extension;
import com.aril.annotations.process.PreProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Extension(id = "createUser")
@Slf4j
public class CreateUserExtension {

    // validation ya da başka işlemler için ilgili request dtolar vs tanımlanmalı
    @PreProcess
    public Object postProcess(Object object) {
        log.info("createUser extension is called. object: {}", object);
        return object;
    }
}
