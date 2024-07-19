package com.aril.mainapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String id;
    private String name;
    private String email;
    private int age;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"age\":" + age +
                "}";
    }
}
