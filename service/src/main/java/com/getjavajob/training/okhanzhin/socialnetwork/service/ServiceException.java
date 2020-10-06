package com.getjavajob.training.okhanzhin.socialnetwork.service;

public class ServiceException extends RuntimeException {
    private String message;

    public ServiceException() {}

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
