package uk.ac.ebi.atlas.experimentpage.differential.download;

import java.io.IOException;

public interface ExpressionsWriter {

    Long write() throws IOException;

    void close() throws IOException;

}
