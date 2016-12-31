package com.uni.algos.core.export;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.util.List;

public final class SequenceSorterBySequenceLengthInDescendingOrder implements FastaDataProvider {

    private final FastaDataProvider fastaDataProvider;

    public SequenceSorterBySequenceLengthInDescendingOrder(FastaDataProvider fastaDataProvider) throws InvalidArgumentException {
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
        List<FastaSequence> sequencesToSort = fastaDataProvider.getSequences();
        sequencesToSort.sort((o1, o2) -> Integer.compare(
                o2.getSequence().length(),
                o1.getSequence().length()));

        return sequencesToSort;
    }
}
