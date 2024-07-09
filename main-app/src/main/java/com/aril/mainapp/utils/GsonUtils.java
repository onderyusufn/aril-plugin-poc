package com.aril.mainapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtils {
    public static final Gson GSON = new GsonBuilder().create();

    public static <T> T fromInputStream(InputStream inputStream, Class<T> tClass) {
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return GSON.fromJson(reader, tClass);
    }

    public static InputStream toInputStream(Object data) {
        String content = GSON.toJson(data);
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    public static String toJson(Object data) {
        return GSON.toJson(data);
    }
}
