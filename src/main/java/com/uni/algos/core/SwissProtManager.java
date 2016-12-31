package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.search.FastaSequenceSearch;
import com.uni.algos.core.search.InvalidSearchCriteriaException;
import com.uni.algos.core.storage.DataFileNotFoundException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class SwissProtManager {
    private final PrintStream output;
    private final FastaSequenceSearch sequenceSearch;

    public SwissProtManager(PrintStream output,
                            FastaSequenceSearch sequenceSearch) throws InvalidArgumentException {
        if (output == null) {
            throw new InvalidArgumentException(new String[]{"'output' must be supplied"});
        }
        if (sequenceSearch == null) {
            throw new InvalidArgumentException(new String[]{"'sequenceSearch' must be supplied"});
        }
        this.output = output;
        this.sequenceSearch = sequenceSearch;
    }

    public void findAndDisplaySequence(String swissProtId) throws InvalidSearchCriteriaException,
            SequenceIdNotFoundException,
            IOException,
            DataFileNotFoundException,
            InvalidSequenceIdException,
            InvalidSequenceException {
        List<FastaSequence> sequences = sequenceSearch.Search(swissProtId);
        if (sequences.isEmpty()) {
            output.println("No sequences found for '" + swissProtId + "' search criteria.");
        } else {
            output.println("'" + sequences.size() + "' sequences have been found:");
            for (FastaSequence sequence : sequences) {
                output.println();
                output.println("    ID: " + sequence.getSequenceId());
                output.println("    Description: " + sequence.getDescription());
                output.println("    Sequence: " + sequence.getSequence());
            }
        }
    }

    public void findAndSaveSequencesAsFastaFormat(int minimumLengthForSequence) {
        // to output to a FASTA format file a list of sequences with a minimum length specified by the user
    }

    public void sortAndSaveAllSwissProtsWithOrganisms() {
        // to output to a CSV file a list of  SwissProt ID and their organism (OS) sorted by organism then SwissProt ID
    }
}

