package com.hackerrank.assignment.exception;

/**
 * @author Mukhtiar Ahmed
 */
public class InitializationNotAllowedException extends Exception {

    private static final long serialVersionUID = 3699257678803467858L;

    private final String message;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public InitializationNotAllowedException() {
        super();
        this.message = "";
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InitializationNotAllowedException(String message) {
        super(message);
        this.message = message;
    }


}
