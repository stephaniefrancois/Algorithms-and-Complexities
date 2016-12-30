package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SequenceSanitizer implements FastaDataProvider {

    private final FastaDataProvider fastaDataProvider;

    public SequenceSanitizer(FastaDataProvider fastaDataProvider) throws InvalidArgumentException {
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

        List<FastaSequence> sequences = fastaDataProvider.getSequences();
        ArrayList<FastaSequence> sanitizedSequences = new ArrayList<>();

        for (FastaSequence sequenceToSanitize: sequences) {
            FastaSequence sanitizedSequence = sanitizeSequence(sequenceToSanitize);
            sanitizedSequences.add(sanitizedSequence);
        }

        return sanitizedSequences;
    }

    private FastaSequence sanitizeSequence(FastaSequence sequenceToSanitize) {
        String sequenceContents = sequenceToSanitize.getSequence().toUpperCase();
        return new FastaSequence(sequenceToSanitize.getSequenceId(),
                sequenceToSanitize.getDescription(),
                sequenceContents);
    }
}
