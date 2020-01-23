package com.hackerrank.assignment.exception;

/**
 * @author Mukhtiar Ahmed
 */
public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8087867092781813380L;

    private final String exceptionKey;

    public String getExceptionKey() {
        return exceptionKey;
    }

    public RecordNotFoundException(final String exceptionKey) {
        this.exceptionKey = exceptionKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecordNotFoundException)) return false;

        RecordNotFoundException that = (RecordNotFoundException) o;

        return getExceptionKey().equals(that.getExceptionKey());

    }

    @Override
    public int hashCode() {
        return getExceptionKey().hashCode();
    }

    @Override
    public String toString() {
        return "RecordNotFoundException{" +
                "exceptionKey='" + exceptionKey + '\'' +
                '}';
    }
}
