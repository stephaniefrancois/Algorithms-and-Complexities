package com.uni.algos.core.storage;

import com.uni.algos.core.domain.FastaSequence;

import java.io.IOException;
import java.util.List;

public interface FastaDataProvider {
    List<FastaSequence> getSequences() throws IOException, DataFileNotFoundException;
}
