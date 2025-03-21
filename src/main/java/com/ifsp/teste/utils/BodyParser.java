package com.ifsp.teste.utils;

import java.util.HashMap;
import java.util.Map;

public class BodyParser {
    public static Map<String, String> parse(String bodyStr) {
        Map<String, String> body = new HashMap<>();
        String[] values = bodyStr.split("&");
        for (String value : values) {
            String[] pair = value.split("=");
            if (pair.length == 2) {
            body.put(pair[0], pair[1]);
            }
        }
        return body;
    }
}
