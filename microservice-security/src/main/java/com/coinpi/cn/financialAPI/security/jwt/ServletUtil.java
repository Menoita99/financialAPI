package com.coinpi.cn.financialAPI.security.jwt;

import net.minidev.json.JSONObject;

import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtil {

    public static void write(HttpServletResponse response, HttpStatus status, String json) throws IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(json);
    }

    public static String getJson(String key, String value) {
        JSONObject json = new JSONObject();
        json.put(key, value);
        return json.toString();
    }
}
