package com.uni.algos.core.search;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.InvalidSequenceIdException;
import com.uni.algos.core.SequenceIdNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public final class SimpleFastaSequenceSearchTest {

    @Test
    public void GivenSequenceIdShouldReturnMatchingSequences() throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidArgumentException, InvalidSearchCriteriaException {

        // Given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID3", "Description", "GLQRH"));
        sequences.add(new FastaSequence("ID1", "Description", "ABC"));
        sequences.add(new FastaSequence("ID2", "Description", "GLQRHT"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaSequenceSearch sut = new SimpleFastaSequenceSearch(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);
        // When
        List<FastaSequence> result = sut.Search("ID1");

        // Then

        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getSequenceId(), equalTo("ID1"));
        assertThat(result.get(0).getSequence(), equalTo("ABC"));
    }

    @Test
    public void GivenSequenceIdWhenNoMatchesFoundShouldReturnEmptyL() throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidArgumentException, InvalidSearchCriteriaException {

        // Given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID3", "Description", "GLQRH"));
        sequences.add(new FastaSequence("ID2", "Description", "GLQRHT"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaSequenceSearch sut = new SimpleFastaSequenceSearch(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);
        // When
        List<FastaSequence> result = sut.Search("ID1");

        // Then

        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void GivenPartialSequenceIdShouldReturnMatchingSequences() throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidArgumentException, InvalidSearchCriteriaException {

        // Given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID3", "Description", "GLQRH"));
        sequences.add(new FastaSequence("ID10", "Description", "ABCD"));
        sequences.add(new FastaSequence("ID12", "Description", "ABCE"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaSequenceSearch sut = new SimpleFastaSequenceSearch(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);
        // When
        List<FastaSequence> result = sut.Search("ID1");

        // Then

        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0).getSequenceId(), equalTo("ID10"));
        assertThat(result.get(1).getSequenceId(), equalTo("ID12"));
    }

    @Test
    public void GivenSequenceIdShouldIgnoreCasingAndReturnMatchingSequences() throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidArgumentException, InvalidSearchCriteriaException {

        // Given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID3", "Description", "GLQRH"));
        sequences.add(new FastaSequence("Id10", "Description", "ABCD"));
        sequences.add(new FastaSequence("ID12", "Description", "ABCE"));
        sequences.add(new FastaSequence("id13", "Description", "ABCE"));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaSequenceSearch sut = new SimpleFastaSequenceSearch(providerMock);
        when(providerMock.getSequences()).thenReturn(sequences);
        // When
        List<FastaSequence> result = sut.Search("ID1");

        // Then

        assertThat(result.size(), equalTo(3));
        assertThat(result.get(0).getSequenceId(), equalTo("Id10"));
        assertThat(result.get(1).getSequenceId(), equalTo("ID12"));
        assertThat(result.get(2).getSequenceId(), equalTo("id13"));
    }

    @Test
    public void GivenSearchCriteriaThatsLessThanThreeCharactersShouldThrow() throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidArgumentException {

        // Given
        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaSequenceSearch sut = new SimpleFastaSequenceSearch(providerMock);

        // When
        // Then

        assertThrows(InvalidSearchCriteriaException.class, () -> sut.Search("ID"));
    }

    @Test
    public void GivenSearchCriteriaThatsMoreThanTenCharactersShouldThrow() throws InvalidSequenceException,
            InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException, InvalidArgumentException {

        // Given
        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        FastaSequenceSearch sut = new SimpleFastaSequenceSearch(providerMock);

        // When
        // Then

        assertThrows(InvalidSearchCriteriaException.class, () -> sut.Search("0123456789x"));
    }
}