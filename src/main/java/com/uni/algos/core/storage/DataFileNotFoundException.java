package com.uni.algos.core.storage;

public final class DataFileNotFoundException extends Exception {
    public DataFileNotFoundException(String filePath) {
        super("'" + filePath + "' is not a valid file path!");
    }
}
