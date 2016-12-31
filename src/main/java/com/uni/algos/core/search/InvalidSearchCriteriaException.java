package com.uni.algos.core.search;

import com.uni.algos.core.domain.AlgosApplicationException;

public final class InvalidSearchCriteriaException extends AlgosApplicationException {
    public InvalidSearchCriteriaException() {
        super("Search criteria must be between 3 and 6 characters long!");
    }
}
