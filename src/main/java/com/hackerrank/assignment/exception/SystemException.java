package com.hackerrank.assignment.exception;

/**
 * The base exception of RuntimeException in the application.
 *
 * @author mukhtiar.ahmed
 * @version 1.0
 */
public class SystemException extends RuntimeException {

    /**
     * The serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * This is the constructor of <code>SystemException</code> class with message argument.
     * </p>
     *
     * @param message the error message.
     */
    public SystemException(String message) {
        super(message);
    }

    /**
     * <p>
     * This is the constructor of <code>SystemException</code> class with message and cause arguments.
     * </p>
     *
     * @param message the error message.
     * @param cause   the cause of the exception.
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
