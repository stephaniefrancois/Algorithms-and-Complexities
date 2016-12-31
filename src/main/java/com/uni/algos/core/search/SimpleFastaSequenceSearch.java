package com.uni.algos.core.search;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.InvalidSequenceIdException;
import com.uni.algos.core.SequenceIdNotFoundException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleFastaSequenceSearch implements FastaSequenceSearch {

    private final int SearchCriteriaMinLength = 3;
    private final int SearchCriteriaMaxLength = 6;
    private final FastaDataProvider fastaDataProvider;

    public SimpleFastaSequenceSearch(FastaDataProvider fastaDataProvider) throws InvalidArgumentException {
        if (fastaDataProvider == null) {
            throw new InvalidArgumentException(new String[]{"'fastaDataProvider' must be supplied"});
        }
        this.fastaDataProvider = fastaDataProvider;
    }

    @Override
    public List<FastaSequence> Search(String searchCriteria) throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidSearchCriteriaException {

        if (InvalidSearchCriteria(searchCriteria)) {
            throw new InvalidSearchCriteriaException();
        }

        List<FastaSequence> sequences = fastaDataProvider.getSequences();

        return sequences.stream()
                .filter(sequence -> sequence.getSequenceId()
                        .toUpperCase()
                        .startsWith(searchCriteria.toUpperCase()))
                .collect(Collectors.toList());
    }

    private boolean InvalidSearchCriteria(String searchCriteria) {
        return searchCriteria == null ||
                searchCriteria.length() < SearchCriteriaMinLength ||
                searchCriteria.length() > SearchCriteriaMaxLength;
    }
}
