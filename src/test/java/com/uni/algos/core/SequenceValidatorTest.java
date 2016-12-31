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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SequenceValidatorTest {

    @Test
    public void GivenValidSequenceValidationShouldPass() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "GLQRH"));

        List<String> characters = new ArrayList<>();
        characters.add("G");
        characters.add("L");
        characters.add("Q");
        characters.add("R");
        characters.add("H");

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        ValidFastaSequenceCharactersProvider validCharactersProviderMock = Mockito.mock(ValidFastaSequenceCharactersProvider.class);
        FastaDataProvider sut = new SequenceValidator(providerMock, validCharactersProviderMock);
        when(providerMock.getSequences()).thenReturn(sequences);
        when(validCharactersProviderMock.getCharacters()).thenReturn(characters);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0).getSequenceId(), equalTo("ID"));
        assertThat(result.get(0).getDescription(), equalTo("Description"));
        assertThat(result.get(0).getSequence(), equalTo("GLQRH"));
    }

    @Test
    public void GivenEmptySequenceValidaionShouldFail() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", ""));

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        ValidFastaSequenceCharactersProvider validCharactersProviderMock = Mockito.mock(ValidFastaSequenceCharactersProvider.class);
        FastaDataProvider sut = new SequenceValidator(providerMock, validCharactersProviderMock);
        when(providerMock.getSequences()).thenReturn(sequences);

        // when
        // then
        Exception thrown = assertThrows(InvalidSequenceException.class, () -> sut.getSequences());
        assertThat(thrown.getMessage(), containsString("ID"));
    }

    @Test
    public void GivenSequenceWithIllegalCharactersValidaionShouldFail() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID", "Description", "AB!C"));

        List<String> characters = new ArrayList<>();
        characters.add("A");
        characters.add("B");
        characters.add("C");

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        ValidFastaSequenceCharactersProvider validCharactersProviderMock = Mockito.mock(ValidFastaSequenceCharactersProvider.class);
        FastaDataProvider sut = new SequenceValidator(providerMock, validCharactersProviderMock);

        when(providerMock.getSequences()).thenReturn(sequences);
        when(validCharactersProviderMock.getCharacters()).thenReturn(characters);

        // when
        // then
        Exception thrown = assertThrows(InvalidSequenceException.class, () -> sut.getSequences());
        assertThat(thrown.getMessage(), containsString("ID"));
    }



    @Test
    public void GivenMultipleSequencesWithErrorsTheyShouldBeAggregated() throws InvalidSequenceIdException,
            SequenceIdNotFoundException,
            DataFileNotFoundException,
            IOException,
            InvalidArgumentException, InvalidSequenceException {

        // given
        List<FastaSequence> sequences = new ArrayList<>();
        sequences.add(new FastaSequence("ID1", "Description", "AB!C"));
        sequences.add(new FastaSequence("ID2", "Description", "ABC"));
        sequences.add(new FastaSequence("ID3", "Description", "AB#C"));

        List<String> characters = new ArrayList<>();
        characters.add("A");
        characters.add("B");
        characters.add("C");

        FastaDataProvider providerMock = Mockito.mock(FastaDataProvider.class);
        ValidFastaSequenceCharactersProvider validCharactersProviderMock = Mockito.mock(ValidFastaSequenceCharactersProvider.class);
        FastaDataProvider sut = new SequenceValidator(providerMock, validCharactersProviderMock);

        when(providerMock.getSequences()).thenReturn(sequences);
        when(validCharactersProviderMock.getCharacters()).thenReturn(characters);

        // when
        // then
        Exception thrown = assertThrows(InvalidSequenceException.class, () -> sut.getSequences());
        assertThat(thrown.getMessage(), allOf(containsString("ID1"),
                containsString("ID3")));
    }
}
