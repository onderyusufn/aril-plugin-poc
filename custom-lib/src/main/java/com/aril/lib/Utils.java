package com.aril.lib;

public class Utils {

    // version 1 method
//    public static String reverse(String str) {
//        return new StringBuilder(str).reverse().toString();
//    }

    // version 2 method
    public static String reverse(String str, boolean isReverse) {
        return isReverse ? new StringBuilder(str).reverse().toString() : str;
    }
}
