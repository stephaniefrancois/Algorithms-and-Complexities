package com.uni.algos.core.export;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.DataStore;
import com.uni.algos.core.storage.FastaDataProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public final class SequenceExporterToFastaDataFileTest {
    @Test
    public void GivenSequencesTheyShouldBeSortedByLengthInDescendingOrder() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "12345"));
        sequences.add(new FastaSequence("ID", "Description", "1234"));
        sequences.add(new FastaSequence("ID", "Description", "123"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        PrintStream printerMock = Mockito.mock(PrintStream.class);
        DataStore dataStoreMock = Mockito.mock(DataStore.class);
        SequenceExporterToFastaDataFile sut =
                new SequenceExporterToFastaDataFile(providerMock,
                        dataStoreMock,
                        printerMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        int minimumSequenceLength = 4;
        sut.exportSequences(minimumSequenceLength);

        // then
        final ArgumentCaptor<List<FastaSequence>> exportedSequencesArgument
                = ArgumentCaptor.forClass((Class) List.class);

        verify(dataStoreMock).saveData(exportedSequencesArgument.capture());
        assertThat(exportedSequencesArgument.getValue(), hasItems(sequences.get(0), sequences.get(1)));
    }

    @Test
    public void GivenSequencesThatAreTooShortThenNothingShouldBeExported() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "12345"));
        sequences.add(new FastaSequence("ID", "Description", "1234"));
        sequences.add(new FastaSequence("ID", "Description", "123"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        PrintStream printerMock = Mockito.mock(PrintStream.class);
        DataStore dataStoreMock = Mockito.mock(DataStore.class);
        SequenceExporterToFastaDataFile sut =
                new SequenceExporterToFastaDataFile(providerMock,
                        dataStoreMock,
                        printerMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        int minimumSequenceLength = 6;
        sut.exportSequences(minimumSequenceLength);

        // then
        verify(dataStoreMock, never()).saveData(any());
    }
}