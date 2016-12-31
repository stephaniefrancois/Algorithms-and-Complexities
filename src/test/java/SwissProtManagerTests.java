import com.sun.javaws.exceptions.InvalidArgumentException;
import com.uni.algos.core.*;
import com.uni.algos.core.parsers.*;
import com.uni.algos.core.search.FastaSequenceSearch;
import com.uni.algos.core.search.InvalidSearchCriteriaException;
import com.uni.algos.core.search.SimpleFastaSequenceSearch;
import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.DataProvider;
import com.uni.algos.core.storage.FastaDataProvider;
import com.uni.algos.core.storage.FileBasedDataProvider;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class SwissProtManagerTests {

    @Test
    public void ShouldRetrieveAndPrintSwissProtSequence() throws IOException, InvalidArgumentException, InvalidSearchCriteriaException, InvalidSequenceException, SequenceIdNotFoundException, DataFileNotFoundException, InvalidSequenceIdException {
        // Given
        String sequenceId = "Q6GZX3";
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(byteStream);

        DataProvider loader = new FileBasedDataProvider("src/test/resources/sample-data.txt");
        FastaSequenceIdParser idParser = new DescriptionBasedSequenceIdParser();
        FastaDataProvider parser = new FastaDataParserFromString(loader, idParser);
        ValidFastaSequenceCharactersProvider characters = new InMemoryValidFastaSequenceCharactersProvider();
        FastaDataProvider validator = new SequenceValidator(parser, characters);
        FastaDataProvider sanitizer = new SequenceSanitizer(validator);
        FastaSequenceSearch search = new SimpleFastaSequenceSearch(sanitizer);
        SwissProtManager sut = new SwissProtManager(printer, search);

        // When
        sut.findAndDisplaySequence(sequenceId);

        // Then
        String output = byteStream.toString("UTF-8");
        assertThat(output, allOf(containsString(sequenceId),
                containsString("MSIIGATRLQNDKSDTYSAGPCYAGGCSAFTPRGTCGKDWDLGEQTCASGFCTSQPLCAR"),
                containsString("IKKTQVCGLRYSSKGKDPLVSAEWDSRGAPYVRCTYDADLIDTQAQVDQFVSMFGESPSL"),
                containsString("AERYCMRGVKNTAGELVSRVSSDADPAGGWCRKWYSAHRGPDQDAALGSFCIKNPGAADC"),
                containsString("KCINRASDPVYQKVKTLHAYPDQCWYVPCAADVGELKMGTQRDTPTNCPTQVCQIVFNML"),
                containsString("DDGSVTMDDVKNTINCDFSKYVPPPPPPKPTPPTPPTPPTPPTPPTPPTPPTPRPVHNRK"),
                containsString("VMFFVAGAVLVAILISTVRW")));
    }
}
