import com.uni.algos.core.SwissProtManager;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SwissProtManagerTests {

    @Test
    public void ShouldRetrieveAndPrintSwissProtSequence() throws UnsupportedEncodingException {
        // Given
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PrintStream printer = new PrintStream(byteStream);
        SwissProtManager sut = new SwissProtManager(printer);

        // When
        sut.findAndDisplaySequence("id");

        // Then
        String output = byteStream.toString("UTF-8");
        assertThat(output, containsString("id"));
    }
}
