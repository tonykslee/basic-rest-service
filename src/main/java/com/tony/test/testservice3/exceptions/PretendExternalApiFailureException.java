package com.tony.test.testservice3.exceptions;

/**
 * The type Pretend external api failure.
 */
//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PretendExternalApiFailureException extends Exception {
    /**
     * Instantiates a new Pretend external api failure.
     *
     * @param message the message
     */
    public PretendExternalApiFailureException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Pretend external api failure.
     */
    public PretendExternalApiFailureException() {
    }

    /**
     * Instantiates a new Pretend external api failure.
     *
     * @param message the message
     * @param cause   the cause
     */
    public PretendExternalApiFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Pretend external api failure.
     *
     * @param cause the cause
     */
    public PretendExternalApiFailureException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Pretend external api failure.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public PretendExternalApiFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
