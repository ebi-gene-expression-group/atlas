package uk.ac.ebi.atlas.commons.readers;

import java.util.List;
import java.util.stream.Stream;

public interface TsvReader extends AutoCloseable {
    String[] readLine(long lineIndex);
    List<String[]> readAll();
    Stream<String[]> stream();
}
