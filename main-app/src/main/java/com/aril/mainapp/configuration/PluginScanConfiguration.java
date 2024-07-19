package com.aril.mainapp.configuration;

import com.aril.plugin.annotations.Extension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//@ComponentScan(basePackages = "com.aril.plugin")
@ComponentScan(basePackages = "com.aril.plugin",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Extension.class))
public class PluginScanConfiguration {
}
