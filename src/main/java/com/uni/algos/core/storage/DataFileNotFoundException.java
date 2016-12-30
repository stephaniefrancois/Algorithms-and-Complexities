package com.uni.algos.core.storage;

import com.uni.algos.core.domain.AlgosApplicationException;

public final class DataFileNotFoundException extends AlgosApplicationException {
    public DataFileNotFoundException(String filePath) {
        super("'" + filePath + "' is not a valid file path!");
    }
}
