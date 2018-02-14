package uk.ac.ebi.atlas.commons.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.removeEnd;
import static org.apache.commons.lang3.StringUtils.removeStart;

public class TsvReader implements AutoCloseable{
    private final Reader reader;

    public TsvReader(Reader reader) {
        this.reader = reader;
    }

    public Stream<String[]> stream() {
        return new BufferedReader(this.reader).lines()
                .map(TsvReader::splitByTabsAndTrimWrappingQuotes)
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

    private static String[] splitByTabsAndTrimWrappingQuotes (String line) {
        return Stream
                .of(line.split("\t", -1))
                .map(TsvReader::trimDoubleQuotes)
                .toArray(String[]::new);
    }

    private static String trimDoubleQuotes(String str) {
        return removeStart(removeEnd(str, "\""), "\"");
    }

    public static TsvReader empty() {
        return DUMMY;
    }

    private final static TsvReader DUMMY = new DummyTsvReader();

    private final static class DummyTsvReader extends TsvReader {
        private DummyTsvReader() {
            super(null);
        }

        @Override
        public Stream<String[]> stream() {
            return Stream.empty();
        }

        @Override
        public void close() {

        }
    }
}