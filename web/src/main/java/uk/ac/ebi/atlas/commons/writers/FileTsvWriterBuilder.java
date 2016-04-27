package uk.ac.ebi.atlas.commons.writers;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.writers.impl.TsvWriterImpl;

import javax.inject.Named;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class FileTsvWriterBuilder {

    private String experimentAccession;
    private String tsvFilePathTemplate;
    private boolean append = true;

    public FileTsvWriterBuilder() {
    }

    public FileTsvWriterBuilder withExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public FileTsvWriterBuilder forTsvFilePathTemplate(String tsvFilePathTemplate) {
        this.tsvFilePathTemplate = tsvFilePathTemplate;
        return this;
    }
    public FileTsvWriterBuilder withAppend(boolean append) {
        this.append = append;
        return this;
    }

    public TsvWriter build() {
        String tsvFilePath = MessageFormat.format(tsvFilePathTemplate, experimentAccession);
        try {
            return new TsvWriterImpl(new OutputStreamWriter(new FileOutputStream(new File(tsvFilePath), append)));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write TSV file to path " + tsvFilePath, e);
        }
    }

}
