package com.hackerrank.assignment.exception;

/**
 * This is the parent exception of all checked exceptions in this application.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
public class BusinessException extends Exception {

    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 5574958301465662619L;

    /**
     * <p>
     * This is the constructor of <code>BusinessException</code> class with message argument.
     * </p>
     *
     * @param message the error message.
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * <p>
     * This is the constructor of <code>BusinessException</code> class with message and cause arguments.
     * </p>
     *
     * @param message the error message.
     * @param cause   the cause of the exception.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
