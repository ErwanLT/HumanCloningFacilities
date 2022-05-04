package com.erwan.human.exceptions;

public class RestApiException extends ApiException {

    public RestApiException(String message, int statusCode) {
        super(message, statusCode);
    }

}
