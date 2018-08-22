package uk.ac.ebi.atlas.profiles.writer;

import au.com.bytecode.opencsv.CSVWriter;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

import static au.com.bytecode.opencsv.CSVWriter.NO_ESCAPE_CHARACTER;
import static au.com.bytecode.opencsv.CSVWriter.NO_QUOTE_CHARACTER;

public class ProfilesWriter<P extends Profile> {
    private final String masthead;
    private final String[] columnHeaders;
    private final Function<P, String[]> profileToLine;
    private final Writer responseWriter;

    public ProfilesWriter(Writer responseWriter,
                          String masthead, String[] columnHeaders,
                          Function<P, String[]> profileToLine) {
        this.responseWriter = responseWriter;
        this.masthead = masthead;
        this.columnHeaders = columnHeaders;
        this.profileToLine = profileToLine;
    }

    public long write(ObjectInputStream<P> profiles) {
        try {
            responseWriter.write(masthead + "\n");
            CSVWriter csvWriter = new CSVWriter(responseWriter, '\t', NO_QUOTE_CHARACTER, NO_ESCAPE_CHARACTER);
            csvWriter.writeNext(columnHeaders);
            long count = 0L;
            for (P p : new IterableObjectInputStream<>(profiles)) {
                csvWriter.writeNext(profileToLine.apply(p));
                count++;
            }

            csvWriter.flush();
            return count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
