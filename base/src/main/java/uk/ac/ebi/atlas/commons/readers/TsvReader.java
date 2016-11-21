package uk.ac.ebi.atlas.commons.readers;

import java.util.List;

public interface TsvReader {
    String[] readLine(long lineIndex);
    List<String[]> readAll();
}