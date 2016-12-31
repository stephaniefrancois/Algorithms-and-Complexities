package com.uni.algos.core.parsers;

public interface FastaSequenceIdParser {
    String parseSequenceId(String sequenceDescription) throws SequenceIdNotFoundException, InvalidSequenceIdException;
}
