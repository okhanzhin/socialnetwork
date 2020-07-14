package com.getjavajob.training.okhanzhin.socialnetwork.dao;

public class DaoException extends RuntimeException {
    private String message;

    public DaoException() {
    }

    public DaoException(String message) {
        this.message = message;
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
