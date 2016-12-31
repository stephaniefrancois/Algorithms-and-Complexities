package com.uni.algos.app;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.domain.AlgosApplicationException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.search.FastaSequenceSearch;
import com.uni.algos.core.search.InvalidSearchCriteriaException;
import com.uni.algos.core.storage.DataFileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public final class SearchUI {
    private final Scanner userInput;
    private final PrintStream out;
    private final FastaSequenceSearch sequenceSearch;

    public SearchUI(InputStream in, PrintStream out, FastaSequenceSearch sequenceSearch) throws InvalidArgumentException {

        if (in == null) {
            throw new InvalidArgumentException(new String[]{"'in' must be supplied"});
        }
        if (out == null) {
            throw new InvalidArgumentException(new String[]{"'out' must be supplied"});
        }
        if (sequenceSearch == null) {
            throw new InvalidArgumentException(new String[]{"'sequenceSearch' must be supplied"});
        }
        this.userInput = new Scanner(System.in);
        this.out = out;
        this.sequenceSearch = sequenceSearch;
    }

    public void displaySearch() throws IOException {
        out.println("Please enter full or partial swissProtId to perform search:");
        String userInput = this.userInput.nextLine();
        try {
            out.println("Searching for: " + userInput + " ...");
            search(userInput);
        } catch (AlgosApplicationException e) {
            out.println(e.getMessage());
            out.println("Please retry your search.");
        }
    }

    private void search(String userInput) throws InvalidSequenceException, InvalidSequenceIdException, SequenceIdNotFoundException, DataFileNotFoundException, IOException, InvalidSearchCriteriaException {
        List<FastaSequence> sequences = sequenceSearch.Search(userInput);
        if (sequences.isEmpty()) {
            out.println("No sequences found for '" + userInput + "' search criteria.");
        } else {
            out.println("'" + sequences.size() + "' sequences have been found:");
            for (FastaSequence sequence : sequences) {
                printSequence(sequence);
            }
        }
        out.println();
        out.println("   End of search results for '" + userInput + "'");
        out.println();
        out.println();
    }

    private void printSequence(FastaSequence sequence) {
        out.println();
        out.println("    ID: " + sequence.getSequenceId());
        out.println("    Description: " + sequence.getDescription());
        out.println("    Sequence: " + sequence.getSequence());
    }
}
