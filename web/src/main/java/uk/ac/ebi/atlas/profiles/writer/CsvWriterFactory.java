package uk.ac.ebi.atlas.profiles.writer;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.Writer;

import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

@Named
@Scope("singleton")
public class CsvWriterFactory {

    public CSVWriter createTsvWriter(Writer writer) {
        return new CSVWriter(writer, '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER);
    }
}
