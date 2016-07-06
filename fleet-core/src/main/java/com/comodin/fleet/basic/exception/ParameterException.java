package com.comodin.fleet.basic.exception;

/**
 * Created by supeng on 4/18/2016.
 */
public class ParameterException extends RuntimeException {


    /**
     * Instantiates a new Parameter exception.
     */
    public ParameterException() {
        super();
    }

    /**
     * Instantiates a new Parameter exception.
     *
     * @param message the message
     */
    public ParameterException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Parameter exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Parameter exception.
     *
     * @param cause the cause
     */
    public ParameterException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Parameter exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
