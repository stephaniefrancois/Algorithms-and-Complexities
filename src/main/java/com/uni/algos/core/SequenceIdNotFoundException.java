package com.uni.algos.core;

import com.uni.algos.core.domain.AlgosApplicationException;

public final class SequenceIdNotFoundException extends AlgosApplicationException {
    public SequenceIdNotFoundException(String description) {
        super(getMessage(description));
    }

    private static String getMessage(String description) {
        return "Sequence Id was not found in given '" + description +
                "' description.";
    }
}
