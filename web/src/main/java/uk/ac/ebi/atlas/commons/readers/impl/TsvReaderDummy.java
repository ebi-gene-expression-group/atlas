package uk.ac.ebi.atlas.commons.readers.impl;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

public class TsvReaderDummy implements TsvReader {

    @Override
    public String[] readLine(long lineIndex) {
        return null;
    }

    @Override
    public ImmutableList<String[]> readAll() {
        return ImmutableList.of();
    }
}
