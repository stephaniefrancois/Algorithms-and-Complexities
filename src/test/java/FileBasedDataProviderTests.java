import com.uni.algos.core.storage.DataFileNotFoundException;
import com.uni.algos.core.storage.FileBasedDataProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileBasedDataProviderTests {

    @Test
    public void ShouldReadFileWithSingleLineOfData() throws IOException, DataFileNotFoundException {
        // given
        String dataFile = "src/test/resources/data.txt";
        FileBasedDataProvider sut = new FileBasedDataProvider(dataFile);

        // when
        List<String> result = sut.getData();

        // then
        assertThat(result, hasItem("My First Line of Data!"));
    }

    @Test
    public void ShouldReadFileWithTwoLinesOfData() throws IOException, DataFileNotFoundException {
        // given
        String dataFile = "src/test/resources/data.txt";
        FileBasedDataProvider sut = new FileBasedDataProvider(dataFile);

        // when
        List<String> result = sut.getData();

        // then
        assertThat(result, hasItem("My Second Line of Data!"));
    }

    @Test
    public void ShouldReadFileWithAnyNumberLinesOfData() throws IOException, DataFileNotFoundException {
        // given
        String dataFile = "src/test/resources/data.txt";
        FileBasedDataProvider sut = new FileBasedDataProvider(dataFile);

        // when
        List<String> result = sut.getData();

        // then
        assertThat(result.size(),is(13));
        assertThat(result, hasItem("Phasellus vulputate pulvinar magna."));
    }

    @Test
    public void ShouldThrowIfDataFileIsNotFound() throws IOException, DataFileNotFoundException {
        // given
        String dataFile = "src/test/resources/invalid-file.txt";
        FileBasedDataProvider sut = new FileBasedDataProvider(dataFile);
        // when
        // then
        DataFileNotFoundException thrown = assertThrows(DataFileNotFoundException.class, () -> sut.getData());
        assertThat(thrown.getMessage(), containsString("invalid-file.txt"));
    }
}
