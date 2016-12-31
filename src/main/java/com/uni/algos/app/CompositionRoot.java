package com.uni.algos.app;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.*;
import com.uni.algos.core.search.FastaSequenceSearch;
import com.uni.algos.core.search.SimpleFastaSequenceSearch;
import com.uni.algos.core.storage.DataProvider;
import com.uni.algos.core.storage.FastaDataProvider;
import com.uni.algos.core.storage.FileBasedDataProvider;

import java.io.InputStream;
import java.io.PrintStream;

public class CompositionRoot {
    public static DashboardUI createRootComponent(InputStream in, PrintStream printer, String dataFilePath) throws InvalidArgumentException {
        DataProvider loader = new FileBasedDataProvider(dataFilePath);
        FastaSequenceIdParser idParser = new DescriptionBasedSequenceIdParser();
        FastaDataProvider parser = new FastaDataParserFromString(loader, idParser);
        ValidFastaSequenceCharactersProvider characters = new InMemoryValidFastaSequenceCharactersProvider();
        FastaDataProvider validator = new SequenceValidator(parser, characters);
        FastaDataProvider sanitizer = new SequenceSanitizer(validator);
        FastaDataProvider cache = new SequenceAbsoluteCache(sanitizer);
        FastaSequenceSearch search = new SimpleFastaSequenceSearch(cache);
        SearchUI searchUi = new SearchUI(in, printer, search);
        return new DashboardUI(in, printer, searchUi);
    }
}
