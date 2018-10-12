package uk.ac.ebi.atlas.commons.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

// A closeable Stream<String[]>, remember to close once you’re done with it! (i.e. use try-with-resources)

public class TsvStreamer implements AutoCloseable, Supplier<Stream<String[]>> {
    private final Reader reader;

    public TsvStreamer(Reader reader) {
        this.reader = reader;
    }

    @Override
    public Stream<String[]> get() {
        return new BufferedReader(this.reader).lines()
                .map(TsvStreamer::splitByTabsAndTrimWrappingQuotes)
                .filter(lineFields -> !lineFields[0].startsWith("#"));
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String[] splitByTabsAndTrimWrappingQuotes(String line) {
        return Stream
                .of(line.split("\t", -1))
                .map(TsvStreamer::trimDoubleQuotes)
                .toArray(String[]::new);
    }

    private static String trimDoubleQuotes(String str) {
        return removeStart(removeEnd(str, "\""), "\"");
    }

    public static TsvStreamer empty() {
        return DUMMY;
    }

    private static final TsvStreamer DUMMY = new DummyTsvStreamer();
    private static final class DummyTsvStreamer extends TsvStreamer {
        private DummyTsvStreamer() {
            super(null);
        }

        @Override
        public Stream<String[]> get() {
            return Stream.empty();
        }

        @Override
        public void close() {

        }
    }
}
