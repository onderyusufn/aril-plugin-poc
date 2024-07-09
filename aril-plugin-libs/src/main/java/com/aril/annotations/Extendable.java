package com.aril.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// extend edilecek methodlar icin kullanilir
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Extendable {
    String id() default "";
}
