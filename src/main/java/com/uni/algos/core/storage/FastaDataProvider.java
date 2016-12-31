package com.uni.algos.core.storage;

import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FastaDataProvider {
    List<FastaSequence> getSequences() throws IOException, DataFileNotFoundException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException;
}
