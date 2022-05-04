package com.erwan.human.exceptions;

public class TechnicalException extends RuntimeException {

    /**
     * UUID
     */
    private static final long serialVersionUID = -2220798860886497980L;

    private final int errorCode;


    public TechnicalException(String message) {
        super(message);
        errorCode = 500;
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
        errorCode = 500;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
