package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.util.List;

public final class SequenceAbsoluteCache implements FastaDataProvider {

    private final FastaDataProvider fastaDataProvider;
    List<FastaSequence> cachedSequences = null;

    public SequenceAbsoluteCache(FastaDataProvider fastaDataProvider) throws InvalidArgumentException {
        if (fastaDataProvider == null) {
            throw new InvalidArgumentException(new String[]{"'fastaDataProvider' must be supplied"});
        }
        this.fastaDataProvider = fastaDataProvider;
    }

    @Override
    public List<FastaSequence> getSequences() throws IOException,
            DataFileNotFoundException,
            SequenceIdNotFoundException,
            InvalidSequenceIdException,
            InvalidSequenceException {

        if (cachedSequences == null) {
            cachedSequences = fastaDataProvider.getSequences();
        }

        return cachedSequences;
    }
}
