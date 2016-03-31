package uk.ac.ebi.atlas.commons.writers;

import java.util.List;

public interface TsvWriter {
    public void write(List<String[]> lines);
}
