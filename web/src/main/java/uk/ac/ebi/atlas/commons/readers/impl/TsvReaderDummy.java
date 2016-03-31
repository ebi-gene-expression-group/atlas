package uk.ac.ebi.atlas.commons.readers.impl;

import uk.ac.ebi.atlas.commons.readers.TsvReader;

import java.util.Arrays;
import java.util.List;

public class TsvReaderDummy implements TsvReader {

    @Override
    public String[] readLine(long lineIndex) {
        return null;
    }

    @Override
    public List<String[]> readAll() {
        return Arrays.asList();
    }
}
