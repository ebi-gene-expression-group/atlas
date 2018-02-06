package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReader;
import uk.ac.ebi.atlas.commons.readers.MatrixMarketReaderTest;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TsvReaderTest {

    private static final String COMMENT_LINE = "#Comment\tComment";
    private static final String HEADER_LINE = "Column1\tColumn2\tColumn3";
    private static final String VALUES_LINE = "Data1\tData2\tData3";

    private static final String[] HEADER_LINE_AS_ARRAY = HEADER_LINE.split("\t");
    private static final String[] VALUES_LINE_AS_ARRAY = VALUES_LINE.split("\t");

    private static final String DATA =
            COMMENT_LINE + "\n" +
            HEADER_LINE + "\n" +
            VALUES_LINE + "\n";

    private TsvReader subject;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        subject = new TsvReader(new InputStreamReader(new ByteArrayInputStream(DATA.getBytes())));
    }

    @Test
    public void commentLinesAreFiltered() {
        assertThat(subject.stream().collect(Collectors.toList()))
                .hasSize(2)
                .containsExactly(HEADER_LINE_AS_ARRAY, VALUES_LINE_AS_ARRAY);
    }

    @Test
    public void streamIsExhaustedAfterReading() {
        assertThat(subject.stream().collect(Collectors.toList()))
                .hasSize(2)
                .containsExactly(HEADER_LINE_AS_ARRAY, VALUES_LINE_AS_ARRAY);

        assertThat(subject.stream().collect(Collectors.toList())).isEmpty();
    }

    @Test
    public void autoClosesUnderlyingReader() throws IOException {
        ByteArrayInputStream inputStreamSpy = spy(new ByteArrayInputStream(DATA.getBytes()));
        InputStreamReader tsvInputStreamReader = new InputStreamReader(inputStreamSpy);

        try (TsvReader tsvReader = new TsvReader(tsvInputStreamReader)) {
            // Use tsvReader
        }

        verify(inputStreamSpy).close();
    }

    @Test
    public void throwsWrappedIOException() throws IOException {
        // The decorator pattern used with readers makes it very hard to have a pure mock
        Reader readerSpy = spy(new InputStreamReader(new ByteArrayInputStream(DATA.getBytes())));
        doThrow(new IOException()).when(readerSpy).close();

        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> {
            try (TsvReader tsvReader = new TsvReader(readerSpy)) {
                // Use tsvReader
            }
        });
    }

    @Test
    public void emptyIsEmpty() {
        assertThat(TsvReader.empty().stream().collect(Collectors.toList())).hasSize(0);
    }

    @Test
    public void closeOnEmptyIsANoOp() {
        // We check .empty() is a singleton so that each call to close operates on the same object
        assertThat(TsvReader.empty()).isSameAs(TsvReader.empty());
        for (int i = 0 ; i < ThreadLocalRandom.current().nextInt(2, 100) ; i++) {
            TsvReader.empty().close();
        }
    }
}
