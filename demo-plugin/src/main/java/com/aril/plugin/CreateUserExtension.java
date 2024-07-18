package com.aril.plugin;

import com.aril.lib.Utils;
import com.aril.plugin.annotations.Extension;
import com.aril.plugin.annotations.process.PreProcess;
import com.aril.plugin.constant.ExtendableConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Extension(id = ExtendableConstants.CREATE_USER)
@Slf4j
@Component
public class CreateUserExtension {

    // validation ya da başka işlemler için ilgili request dtolar vs tanımlanmalı
    @PreProcess
    public Object postProcess(Object object) {
        log.info("createUser extension is called. object: {}".toUpperCase(), Utils.reverse(object.toString()));
        return object;
    }
}
