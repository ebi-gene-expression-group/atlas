package uk.ac.ebi.atlas.commons.writers;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.Writer;

public class TsvWriter extends CSVWriter {
    // TODO Maybe we should consider to implement it as TsvWriter<ExperimentDesign> and similar for the builders
    public TsvWriter(Writer writer){
        super(writer, '\t');
    }
}
