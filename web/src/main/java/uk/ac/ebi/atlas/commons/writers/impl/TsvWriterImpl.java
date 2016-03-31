package uk.ac.ebi.atlas.commons.writers.impl;

import au.com.bytecode.opencsv.CSVWriter;
import autovalue.shaded.com.google.common.common.base.Throwables;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class TsvWriterImpl implements TsvWriter {

    private Writer writer;

    public TsvWriterImpl(Writer writer){
        this.writer=writer;
    }

    @Override
    public void write(List<String[]> lines) {
        try(CSVWriter csvWriter = new CSVWriter(writer, '\t')){
            csvWriter.writeAll(lines);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

}
