package com.erwan.human.exceptions;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ApiError {

    private int statusCode;
    private String message;


    public ApiError(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
