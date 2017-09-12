package com.cqs.qicaiyun.modules.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

@ControllerAdvice
@Log4j2
public class ControllerAdviceTest {  
  

    @ExceptionHandler(Exception.class)  
    @ResponseStatus(HttpStatus.BAD_REQUEST)  
    public String processUnauthenticatedException(NativeWebRequest request,Exception e) {  
        log.error(e.getMessage());
        return "viewName"; //返回一个逻辑视图名  
    }  
}