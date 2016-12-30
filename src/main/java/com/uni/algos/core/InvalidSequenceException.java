package com.uni.algos.core;

import com.uni.algos.core.domain.AlgosApplicationException;

import java.util.List;

public final class InvalidSequenceException extends AlgosApplicationException {
    public InvalidSequenceException(List<String> validationErrors) {
        super(getMessage(validationErrors));
    }

    private static String getMessage(List<String> validationErrors) {
        StringBuilder errorMessageBuilder = new StringBuilder();

        errorMessageBuilder.append(validationErrors.size() +
                " FASTA sequence validation errors have been found:");

        for (String error : validationErrors) {
            errorMessageBuilder.append(getNewLineSeparator());
            errorMessageBuilder.append(error);
        }

        return errorMessageBuilder.toString();
    }

    private static String getNewLineSeparator() {
        return System.getProperty("line.separator");
    }
}
