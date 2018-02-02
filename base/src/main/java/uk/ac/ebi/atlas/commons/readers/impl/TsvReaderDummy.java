package uk.ac.ebi.atlas.commons.readers.impl;

import uk.ac.ebi.atlas.commons.readers.TsvReader;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TsvReaderDummy implements TsvReader {

    @Override
    public String[] readLine(long lineIndex) {
        return null;
    }

    @Override
    public List<String[]> readAll() {
        return Collections.emptyList();
    }

    @Override
    public Stream<String[]> stream() {
        return Stream.empty();
    }

    @Override
    public void close() {

    }
}
