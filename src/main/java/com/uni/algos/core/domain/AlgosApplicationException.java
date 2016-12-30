package com.uni.algos.core.domain;

public abstract class AlgosApplicationException extends Exception {
    public AlgosApplicationException(String message) {
        super(message);
    }
    public AlgosApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
