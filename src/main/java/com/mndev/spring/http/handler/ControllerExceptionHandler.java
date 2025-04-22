package com.mndev.spring.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
//Доступно для всех контроллеров
@ControllerAdvice(basePackages = "com.mndev.spring.http.controller")
public class ControllerExceptionHandler {
//    //обработчик ошибок, в него поступают все ошибки
//    @ExceptionHandler(Exception.class)
//    public String handlerException(Exception exception) {
//        log.error("Failed to return response", exception);
//        return "error/error500";
//    }
}
