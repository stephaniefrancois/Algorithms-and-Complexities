import com.uni.algos.core.DescriptionBasedSequenceIdParser;
import com.uni.algos.core.FastaSequenceIdParser;
import com.uni.algos.core.InvalidSequenceIdException;
import com.uni.algos.core.SequenceIdNotFoundException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DescriptionBasedSequenceIdParserTest {

    @Test
    public void GivenValidFastaDescriptionShouldReturnSequenceId() throws SequenceIdNotFoundException, InvalidSequenceIdException {
        // given
        FastaSequenceIdParser sut = new DescriptionBasedSequenceIdParser();
        String description = ">sp|Q5TYQ1|ZYG11_DANRE Protein zyg-11 homolog OS=Danio rerio GN=zyg11 PE=2 SV=1";

        // when
        String result = sut.parseSequenceId(description);

        // then
        assertThat(result, is("Q5TYQ1"));
    }

    @Test
    public void GivenDescriptionWithMissingStartingPipeShouldThrow() {
        // given
        FastaSequenceIdParser sut = new DescriptionBasedSequenceIdParser();
        String description = ">spQ5TYQ1|ZYG11_DANRE Protein zyg-11 homolog OS=Danio rerio GN=zyg11 PE=2 SV=1";

        // when
        // then
        Exception thrown = assertThrows(SequenceIdNotFoundException.class, () -> sut.parseSequenceId(description));
        assertThat(thrown.getMessage(), containsString(description));
    }

    @Test
    public void GivenDescriptionWithMissingEndingPipeShouldThrow() {
        // given
        FastaSequenceIdParser sut = new DescriptionBasedSequenceIdParser();
        String description = ">sp|Q5TYQ1ZYG11_DANRE Protein zyg-11 homolog OS=Danio rerio GN=zyg11 PE=2 SV=1";

        // when
        // then
        Exception thrown = assertThrows(SequenceIdNotFoundException.class, () -> sut.parseSequenceId(description));
        assertThat(thrown.getMessage(), containsString(description));
    }

    @Test
    public void GivenDescriptionWithIdentifierShorterThanSixCharactersShouldThrow() {
        // given
        FastaSequenceIdParser sut = new DescriptionBasedSequenceIdParser();
        String description = ">sp|Q5TYQ|ZYG11_DANRE Protein zyg-11 homolog OS=Danio rerio GN=zyg11 PE=2 SV=1";

        // when
        // then
        Exception thrown = assertThrows(InvalidSequenceIdException.class, () -> sut.parseSequenceId(description));
        assertThat(thrown.getMessage(), containsString("Q5TYQ"));
    }

    @Test
    public void GivenDescriptionWithIdentifierLongerThanTenCharactersShouldThrow() {
        // given
        FastaSequenceIdParser sut = new DescriptionBasedSequenceIdParser();
        String description = ">sp|0123456789x|ZYG11_DANRE Protein zyg-11 homolog OS=Danio rerio GN=zyg11 PE=2 SV=1";

        // when
        // then
        Exception thrown = assertThrows(InvalidSequenceIdException.class, () -> {
            sut.parseSequenceId(description);
        });
        assertThat(thrown.getMessage(), containsString("0123456789x"));
    }

    @Test
    public void GivenDescriptionWithInvalidCharactersShouldThrow() {
        // given
        FastaSequenceIdParser sut = new DescriptionBasedSequenceIdParser();
        String description = ">sp|+5TYQ1|ZYG11_DANRE Protein zyg-11 homolog OS=Danio rerio GN=zyg11 PE=2 SV=1";

        // when
        // then
        Exception thrown = assertThrows(InvalidSequenceIdException.class, () -> sut.parseSequenceId(description));
        assertThat(thrown.getMessage(), containsString("+5TYQ1"));
    }
}