package com.uni.algos.core;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.parsers.InvalidSequenceIdException;
import com.uni.algos.core.parsers.SequenceIdNotFoundException;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FastaDataProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public final class SequenceAbsoluteCacheTest {
    @Test
    public void GivenWeRequestSequencesForTheFirstTimeThenSequenceProviderShouldBeQueried() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "ABCD"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaDataProvider sut = new SequenceAbsoluteCache(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        verify(providerMock, times(1)).getSequences();
        assertThat(result.size(), equalTo(1));
    }

    @Test
    public void GivenWeRequestSequencesNotForTheFirstTimeThenCachedSequencesShouldBeReturned() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "ABCD"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaDataProvider sut = new SequenceAbsoluteCache(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);
        List<FastaSequence> result = sut.getSequences();

        // when
        result = sut.getSequences();

        // then
        verify(providerMock, times(1)).getSequences();
        assertThat(result.size(), equalTo(1));
    }
}