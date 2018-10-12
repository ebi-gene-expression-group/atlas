package uk.ac.ebi.atlas.commons.writers;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;

public class TsvWriter extends CSVWriter {
    // TODO Maybe we should consider to implement it as TsvWriter<ExperimentDesign>, TsvWriter<GeneProfiles>...
    public TsvWriter(Writer writer) {
        super(writer, '\t');
    }

    @Override
    public void writeAll(List<String[]> allLines) {
        try {
            super.writeAll(allLines);
            super.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void writeNext(String[] line) {
        try {
            super.writeNext(line);
            super.flush();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
