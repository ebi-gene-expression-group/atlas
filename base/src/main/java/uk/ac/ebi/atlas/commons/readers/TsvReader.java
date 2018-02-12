package uk.ac.ebi.atlas.commons.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.stream.Stream;

public class TsvReader implements AutoCloseable{
    private final Reader reader;

    public TsvReader(Reader reader) {
        this.reader = reader;
    }

    public Stream<String[]> stream() {
        return new BufferedReader(this.reader).lines()
                .map(line -> line.split("\t", -1))
                .filter(line -> !line[0].startsWith("#"));
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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