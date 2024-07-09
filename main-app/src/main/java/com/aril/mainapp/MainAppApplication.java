package com.aril.mainapp;

import com.aril.mainapp.classloader.PluginClassloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.DefaultResourceLoader;

@ComponentScan(basePackages = {"com.aril"})
@SpringBootApplication
public class MainAppApplication {
    public static final String PLUGINS_DIR = "C:\\Users\\YusufOnder\\IdeaProjects\\aril-plugin-poc\\main-app\\plugins";

    public static void main(String[] args) {
        PluginClassloader classLoader = new PluginClassloader(PLUGINS_DIR, Thread.currentThread().getContextClassLoader());
        SpringApplication app = new SpringApplication(MainAppApplication.class);
        app.setResourceLoader(new DefaultResourceLoader(classLoader));
        app.run(args);
    }

}
