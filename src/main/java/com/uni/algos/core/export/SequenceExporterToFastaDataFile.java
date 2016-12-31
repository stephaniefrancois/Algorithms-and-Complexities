package com.uni.algos.core.export;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.DataStore;
import com.uni.algos.core.storage.FastaDataProvider;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

public final class SequenceExporterToFastaDataFile {

    private final FastaDataProvider fastaDataProvider;
    private final DataStore dataStore;
    private final PrintStream printer;

    public SequenceExporterToFastaDataFile(FastaDataProvider fastaDataProvider,
                                           DataStore dataStore,
                                           PrintStream printer
    ) throws InvalidArgumentException {
        if (fastaDataProvider == null) {
            throw new InvalidArgumentException(new String[]{"'fastaDataProvider' must be supplied"});
        }
        if (dataStore == null) {
            throw new InvalidArgumentException(new String[]{"'dataStoreMock' must be supplied"});
        }
        if (printer == null) {
            throw new InvalidArgumentException(new String[]{"'printer' must be supplied"});
        }
        this.fastaDataProvider = fastaDataProvider;
        this.dataStore = dataStore;
        this.printer = printer;
    }

    public void exportSequences(int minimumSequenceLength) throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException {
        List<FastaSequence> sequencesToFilter = fastaDataProvider.getSequences();
        List<FastaSequence> sequencesToExport = sequencesToFilter
                .stream()
                .filter(s -> s.getSequence().length() >= minimumSequenceLength)
                .collect(Collectors.toList());
        if (sequencesToExport.size() > 0) {
            printer.println("'" + sequencesToExport.size() + "' found and exporting ...");
            dataStore.saveData(sequencesToExport);
            printer.println("'" + sequencesToExport.size() + "' have been successfully exported.");
        } else {
            printer.println("No sequences match minimum length of '" + minimumSequenceLength + "' filter!");
        }
    }
}
