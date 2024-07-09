package com.aril;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PluginResponseDto {

    private String pluginResponse;

    private boolean success;
}
