package uk.ac.ebi.atlas.commons.readers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TsvStreamerTest {

    private static final String COMMENT_LINE = "#Comment\tComment";
    private static final String HEADER_LINE = "Column1\tColumn2\tColumn3";
    private static final String VALUES_LINE = "Data1\tData2\tData3";

    private static final String DATA =
            COMMENT_LINE + "\n" +
            HEADER_LINE + "\n" +
            VALUES_LINE + "\n";

    private static final String COMMENT_LINE_WITH_BEGIN_QUOTES = "\"#Comment\t\"Comment";
    private static final String HEADER_LINE_WITH_END_QUOTES = "Column1\"\tColumn2\"\tColumn3\"";
    private static final String VALUES_LINE_WITH_MIXED_QUOTES = "\"Data1\"\tData2\"\t\"Data3";
    private static final String VALUES_LINE_WITH_IN_QUOTES = "\"Da\"ta1\"\tDa\"ta2\"\t\"Da\"ta3";

    private static final String DATA_WITH_QUOTES =
            COMMENT_LINE_WITH_BEGIN_QUOTES + "\n" +
            HEADER_LINE_WITH_END_QUOTES + "\n" +
            VALUES_LINE_WITH_MIXED_QUOTES + "\n" +
            VALUES_LINE_WITH_IN_QUOTES;

    private static final String[] HEADER_LINE_AS_ARRAY = HEADER_LINE.split("\t");
    private static final String[] VALUES_LINE_AS_ARRAY = VALUES_LINE.split("\t");

    private static final String[] VALUES_LINE_WITH_IN_QUOTES_ONLY = new String[] {"Da\"ta1", "Da\"ta2", "Da\"ta3"};

    private TsvStreamer subject;

    @Test
    public void commentLinesAreFiltered() {
        subject = new TsvStreamer(new InputStreamReader(new ByteArrayInputStream(DATA.getBytes())));

        assertThat(subject.get().collect(Collectors.toList()))
                .hasSize(DATA.split("\n").length - 1)
                .containsExactly(HEADER_LINE_AS_ARRAY, VALUES_LINE_AS_ARRAY);
    }

    @Test
    public void wrappingQuotesAreTrimmed() {
        subject = new TsvStreamer(new InputStreamReader(new ByteArrayInputStream(DATA_WITH_QUOTES.getBytes())));

        assertThat(subject.get().collect(Collectors.toList()))
                .hasSize(DATA_WITH_QUOTES.split("\n").length - 1)
                .containsExactly(HEADER_LINE_AS_ARRAY, VALUES_LINE_AS_ARRAY, VALUES_LINE_WITH_IN_QUOTES_ONLY);
    }

    @Test
    public void streamIsExhaustedAfterReading() {
        subject = new TsvStreamer(new InputStreamReader(new ByteArrayInputStream(DATA.getBytes())));

        assertThat(subject.get().collect(Collectors.toList()))
                .hasSize(2)
                .containsExactly(HEADER_LINE_AS_ARRAY, VALUES_LINE_AS_ARRAY);

        assertThat(subject.get().collect(Collectors.toList())).isEmpty();
    }

    @Test
    public void autoClosesUnderlyingReader() throws IOException {
        ByteArrayInputStream inputStreamSpy = spy(new ByteArrayInputStream(DATA.getBytes()));
        InputStreamReader tsvInputStreamReader = new InputStreamReader(inputStreamSpy);

        try (TsvStreamer tsvReader = new TsvStreamer(tsvInputStreamReader)) {
            // Use tsvReader
            tsvReader.get();
        }

        verify(inputStreamSpy).close();
    }

    @Test
    public void throwsWrappedIOException() throws IOException {
        // The decorator pattern used with readers makes it very hard to have a pure mock
        Reader readerSpy = spy(new InputStreamReader(new ByteArrayInputStream(DATA.getBytes())));
        doThrow(new IOException()).when(readerSpy).close();

        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> {
            try (TsvStreamer tsvReader = new TsvStreamer(readerSpy)) {
                // Use tsvReader
                tsvReader.get();
            }
        });
    }

    @Test
    public void emptyIsEmpty() {
        assertThat(TsvStreamer.empty().get().collect(Collectors.toList())).hasSize(0);
    }

    @Test
    public void closeOnEmptyIsANoOp() {
        // We check .empty() is a singleton so that each call to close operates on the same object
        assertThat(TsvStreamer.empty()).isSameAs(TsvStreamer.empty());
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(2, 100); i++) {
            TsvStreamer.empty().close();
        }
    }
}
