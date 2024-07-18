package com.aril.plugin;

import com.aril.lib.Utils;
import com.aril.plugin.annotations.Extension;
import com.aril.plugin.annotations.process.OverrideProcess;
import com.aril.plugin.constant.ExtendableConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Extension(id = ExtendableConstants.GET_USER)
@Component
@Slf4j
public class GetUserExtension {

    @OverrideProcess
    public ResponseEntity<String> overrideProcess(Object object) {
        log.info("getUser extension is called. object: {}".toUpperCase(), Utils.reverse(object.toString(), true));
        return ResponseEntity.ok("SUCCESS");
    }
}
