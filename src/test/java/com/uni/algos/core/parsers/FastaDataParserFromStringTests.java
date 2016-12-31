package com.uni.algos.core.parsers;

import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.InvalidSequenceException;
import com.uni.algos.core.domain.FastaSequence;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.DataProvider;
import com.uni.algos.core.storage.FastaDataProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class FastaDataParserFromStringTests {

    @Test
    public void ShouldParseAndReturnSequenceFromDataProviderWhenDescriptionStartsWithLessThan() throws IOException, DataFileNotFoundException, InvalidArgumentException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException {
        // given
        ArrayList<String> data = new ArrayList<>();
        data.add(">My Description 1|ID|....");
        data.add("ABCDE 1");

        DataProvider providerMock = Mockito.mock(DataProvider.class);
        FastaSequenceIdParser idParserMock = Mockito.mock(FastaSequenceIdParser.class);
        when(providerMock.getData()).thenReturn(data);
        when(idParserMock.parseSequenceId(anyString())).thenReturn("ID");

        FastaDataProvider sut = new FastaDataParserFromString(providerMock, idParserMock);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.get(0).getSequenceId(), equalTo("ID"));
        assertThat(result.get(0).getDescription(), equalTo("My Description 1|ID|...."));
        assertThat(result.get(0).getSequence(), equalTo("ABCDE 1"));
    }

    @Test
    public void ShouldParseAndReturnSequenceFromDataProviderWhenDescriptionStartsWithSemicolon() throws IOException, DataFileNotFoundException, InvalidArgumentException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException {
        // given
        ArrayList<String> data = new ArrayList<>();
        data.add(";My Description 1");
        data.add("ABCDE 1");

        DataProvider providerMock = Mockito.mock(DataProvider.class);
        FastaSequenceIdParser idParserMock = Mockito.mock(FastaSequenceIdParser.class);
        when(providerMock.getData()).thenReturn(data);

        FastaDataProvider sut = new FastaDataParserFromString(providerMock, idParserMock);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.get(0).getDescription(), equalTo("My Description 1"));
        assertThat(result.get(0).getSequence(), equalTo("ABCDE 1"));
    }

    @Test
    public void ShouldNotReturnAnyResultsSinceNoDescriptionsFound() throws IOException, DataFileNotFoundException, InvalidArgumentException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException {
        // given
        ArrayList<String> data = new ArrayList<>();
        data.add("My Description 1");
        data.add("ABCDE 1");

        DataProvider providerMock = Mockito.mock(DataProvider.class);
        FastaSequenceIdParser idParserMock = Mockito.mock(FastaSequenceIdParser.class);
        when(providerMock.getData()).thenReturn(data);

        FastaDataProvider sut = new FastaDataParserFromString(providerMock, idParserMock);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.size(), equalTo(0));
    }

    @Test
    public void ShouldReturnSequenceThatSpansMultipleLines() throws IOException, DataFileNotFoundException, InvalidArgumentException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException {
        // given
        ArrayList<String> data = new ArrayList<>();
        data.add(";My Description 1");
        data.add("MDSKGSSQKGSRLLLLLVVSNLLLCQGVVS");
        data.add("EMFNEFDKRYAQGKGFITMALNSCHTSSLP");

        DataProvider providerMock = Mockito.mock(DataProvider.class);
        FastaSequenceIdParser idParserMock = Mockito.mock(FastaSequenceIdParser.class);
        when(providerMock.getData()).thenReturn(data);

        FastaDataProvider sut = new FastaDataParserFromString(providerMock, idParserMock);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        FastaSequence sequence = result.get(0);

        assertThat(sequence.getSequence(),
                allOf(containsString("MDSKGSSQKGSRLLLLLVVSNLLLCQGVVS"),
                        containsString("EMFNEFDKRYAQGKGFITMALNSCHTSSLP")));
    }

    @Test
    public void ShouldReturnTwoSequences() throws IOException, DataFileNotFoundException, InvalidArgumentException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException {
        // given
        ArrayList<String> data = new ArrayList<>();
        data.add(";My Description 1");
        data.add("ABCD");
        data.add(">My Description 2");
        data.add("EFGH");

        DataProvider providerMock = Mockito.mock(DataProvider.class);
        FastaSequenceIdParser idParserMock = Mockito.mock(FastaSequenceIdParser.class);
        when(providerMock.getData()).thenReturn(data);

        FastaDataProvider sut = new FastaDataParserFromString(providerMock, idParserMock);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.size(), equalTo(2));

        FastaSequence sequence1 = result.get(0);
        FastaSequence sequence2 = result.get(1);

        assertThat(sequence1.getDescription(), equalTo("My Description 1"));
        assertThat(sequence1.getSequence(), equalTo("ABCD"));
        assertThat(sequence2.getDescription(), equalTo("My Description 2"));
        assertThat(sequence2.getSequence(), equalTo("EFGH"));
    }

    @Test
    public void ShouldIgnoreSecondSequenceSinceDescriptionDoesntStartWithExpectedCharacter() throws IOException, DataFileNotFoundException, InvalidArgumentException, SequenceIdNotFoundException, InvalidSequenceIdException, InvalidSequenceException {
        // given
        ArrayList<String> data = new ArrayList<>();
        data.add(";My Description 1");
        data.add("ABCD");
        data.add(";My Description 2");
        data.add("EFGH");
        data.add(">My Description 3");
        data.add("KYLP");

        DataProvider providerMock = Mockito.mock(DataProvider.class);
        FastaSequenceIdParser idParserMock = Mockito.mock(FastaSequenceIdParser.class);
        when(providerMock.getData()).thenReturn(data);

        FastaDataProvider sut = new FastaDataParserFromString(providerMock, idParserMock);

        // when
        List<FastaSequence> result = sut.getSequences();

        // then
        assertThat(result.size(), equalTo(2));

        FastaSequence sequence1 = result.get(0);
        FastaSequence sequence2 = result.get(1);

        assertThat(sequence1.getDescription(), equalTo("My Description 1"));
        assertThat(sequence1.getSequence(), equalTo("ABCD"));
        assertThat(sequence2.getDescription(), equalTo("My Description 3"));
        assertThat(sequence2.getSequence(), equalTo("KYLP"));
    }
}