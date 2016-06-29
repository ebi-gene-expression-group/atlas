package uk.ac.ebi.atlas.commons.writers;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.writers.impl.TsvWriterImpl;

import javax.inject.Named;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.MessageFormat;
import java.util.Set;

@Named
@Scope("prototype")
public class FileTsvWriterBuilder {

    private String experimentAccession;
    private String tsvFilePathTemplate;
    private boolean append = true;
    private boolean groupWritable = false;
    private String path;

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

    public FileTsvWriterBuilder makeGroupWritable() {
        this.groupWritable = true;
        return this;
    }

    private void setFilePermissions() throws IOException {
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(Paths.get(path));
        if (this.groupWritable) {
            perms.add(PosixFilePermission.GROUP_WRITE);
        }
        Files.setPosixFilePermissions(Paths.get(path), perms);
    }

    public TsvWriter build() {
        path = MessageFormat.format(tsvFilePathTemplate, experimentAccession);
        try {
            TsvWriter tsvWriter = new TsvWriterImpl(new OutputStreamWriter(new FileOutputStream(new File(path), append)));
            setFilePermissions();
            return tsvWriter;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot write TSV file to path " + path, e);
        }
    }

}
