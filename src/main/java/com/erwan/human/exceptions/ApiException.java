package com.erwan.human.exceptions;

public abstract class ApiException extends Exception {

    private final String message;
    private final int statusCode;

    public ApiException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

}
