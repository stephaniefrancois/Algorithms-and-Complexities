package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public final class SequenceSanitizerTest {


    @Test
    public void GivenCorrectSequenceNoChangesShouldBeApplied() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "ABCD"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaDataProvider sut = new SequenceSanitizer(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getSequence(), equalTo("ABCD"));
    }

    @Test
    public void GivenSequenceLowerCaseLettersShouldBeUpperCased() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "aB123*ccDD"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaDataProvider sut = new SequenceSanitizer(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getSequence(), equalTo("AB123*CCDD"));
    }
}