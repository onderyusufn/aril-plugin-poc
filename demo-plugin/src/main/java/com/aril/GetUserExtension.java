package com.aril;

import com.aril.annotations.Extension;
import com.aril.annotations.process.OverrideProcess;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Extension(id = "getUser")
public class GetUserExtension {

    @OverrideProcess
    public ResponseEntity<PluginResponseDto> overrideProcess(Object object) {
        PluginResponseDto pluginResponseDto = PluginResponseDto.builder()
                .pluginResponse("plugin-override-result-" + object.toString())
                .success(true)
                .build();
        return ResponseEntity.ok(pluginResponseDto);
    }
}
