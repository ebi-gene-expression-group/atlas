package uk.ac.ebi.atlas.commons.writers;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.file.Path;
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
        String path = MessageFormat.format(tsvFilePathTemplate, experimentAccession);
        try {
            return new TsvWriter(new OutputStreamWriter(new FileOutputStream(new File(path), append)));
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write TSV file to path " + path, e);
        }
    }

    public TsvWriter build(Path path, boolean appendTo) {
        try {
            return new TsvWriter(new OutputStreamWriter(new FileOutputStream(path.toFile(), appendTo)));
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write TSV file to path " + path, e);
        }
    }

}
