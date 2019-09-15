package com.tmobile.cns.acms.testservice3.exceptions;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
