package com.uni.algos.core;

public interface FastaSequenceIdParser {
    String parseSequenceId(String sequenceDescription) throws SequenceIdNotFoundException, InvalidSequenceIdException;
}
