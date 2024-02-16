package com.jun.platform.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(value = Exception.class)
    public Object handlerException(Exception e) {
        e.printStackTrace();
        Map<String, String> result = new HashMap<>(2);
        result.put("state", "err");
        return result;
    }
}
