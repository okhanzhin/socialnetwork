package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(value = AccessDeniedException.class)
    public String handleAccessDeniedException(Exception e) {
        logger.warn(e.getMessage());

        return "/templates/access-denied";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(HttpServletRequest req, Exception e) {
        logger.error("Request: " + req.getRequestURI() + " raised exception: " + e.getMessage(), e);
        return "/templates/error";
    }
}
