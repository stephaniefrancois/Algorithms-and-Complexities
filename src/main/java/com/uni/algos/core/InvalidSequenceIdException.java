package com.uni.algos.core;

import com.uni.algos.core.domain.AlgosApplicationException;

public final class InvalidSequenceIdException extends AlgosApplicationException {
    public InvalidSequenceIdException(String sequenceId) {
        super(getMessage(sequenceId));
    }

    private static String getMessage(String sequenceId) {
        return "Sequence Id '" + sequenceId + "' is not valid! " +
                "Valid sequence id contains ONLY letters, numbers and is 6 characters long.";
    }
}