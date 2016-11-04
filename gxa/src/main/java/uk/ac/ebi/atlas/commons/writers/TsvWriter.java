package uk.ac.ebi.atlas.commons.writers;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Throwables;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class TsvWriter extends CSVWriter {
    // TODO Maybe we should consider to implement it as TsvWriter<ExperimentDesign>, TsvWriter<GeneProfiles>... and similar for the builders
    public TsvWriter(Writer writer){
        super(writer, '\t');
    }

    @Override
    public void writeAll(List<String[]> allLines) {
        try {
            super.writeAll(allLines);
            super.flush();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void writeNext(String[] line) {
        try {
            super.writeNext(line);
            super.flush();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
