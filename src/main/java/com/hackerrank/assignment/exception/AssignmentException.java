package com.hackerrank.assignment.exception;

/**
 * @author Mukhtiar Ahmed
 */
public class AssignmentException extends BusinessException {

    private static final long serialVersionUID = 8087867092781813380L;


    /**
     * <p>
     * This is the constructor of <code>AssignmentException</code> class with message argument.
     * </p>
     *
     * @param message the error message.
     */
    public AssignmentException(String message) {
        super(message);
    }

    /**
     * <p>
     * This is the constructor of <code>AssignmentException</code> class with message and cause arguments.
     * </p>
     *
     * @param message the error message.
     * @param cause   the cause of the exception.
     */
    public AssignmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
