package com.uni.algos.core.search;

import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.InvalidSequenceIdException;
import com.uni.algos.core.SequenceIdNotFoundException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;

import java.io.IOException;
import java.util.List;

public interface FastaSequenceSearch {
    List<FastaSequence> Search(String sequenceId) throws InvalidSequenceException, InvalidSequenceIdException, SequenceIdNotFoundException, DataFileNotFoundException, IOException, InvalidSearchCriteriaException;
}
