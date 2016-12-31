package com.uni.algos.core.export;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
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
import static org.mockito.Mockito.when;

public final class SequenceSorterBySequenceLengthInDescendingOrderTests {
    @Test
    public void GivenSequencesTheyShouldBeSortedByLengthInDescendingOrder() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "12345"));
        sequences.add(new FastaSequence("ID", "Description", "123"));
        sequences.add(new FastaSequence("ID", "Description", "1234"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaDataProvider sut = new SequenceSorterBySequenceLengthInDescendingOrder(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.get(0).getSequence(), equalTo("12345"));
        assertThat(result.get(1).getSequence(), equalTo("1234"));
        assertThat(result.get(2).getSequence(), equalTo("123"));
    }
}