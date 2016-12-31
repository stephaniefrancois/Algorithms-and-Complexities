package com.uni.algos.app;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.*;
import com.uni.algos.core.search.FastaSequenceSearch;
import com.uni.algos.core.search.SimpleFastaSequenceSearch;
import com.uni.algos.core.storage.DataProvider;
import com.uni.algos.core.storage.FastaDataProvider;
import com.uni.algos.core.storage.FileBasedDataProvider;

import java.io.PrintStream;

public class CompositionRoot {
    public static SwissProtManager createRootComponent(PrintStream printer, String dataFilePath) throws InvalidArgumentException {
        DataProvider loader = new FileBasedDataProvider(dataFilePath);
        FastaSequenceIdParser idParser = new DescriptionBasedSequenceIdParser();
        FastaDataProvider parser = new FastaDataParserFromString(loader, idParser);
        ValidFastaSequenceCharactersProvider characters = new InMemoryValidFastaSequenceCharactersProvider();
        FastaDataProvider validator = new SequenceValidator(parser, characters);
        FastaDataProvider sanitizer = new SequenceSanitizer(validator);
        FastaSequenceSearch search = new SimpleFastaSequenceSearch(sanitizer);
        return new SwissProtManager(printer, search);
    }
}
