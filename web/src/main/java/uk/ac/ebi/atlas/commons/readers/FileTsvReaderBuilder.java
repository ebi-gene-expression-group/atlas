package uk.ac.ebi.atlas.commons.readers;

import autovalue.shaded.com.google.common.common.base.Throwables;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderDummy;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;

@Named
@Scope("prototype")
public class FileTsvReaderBuilder {

    private String experimentAccession;
    private String tsvFilePathTemplate;
    private boolean defaultToADummyIfFileMissing = false;

    public FileTsvReaderBuilder() {
    }

    public FileTsvReaderBuilder withExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        return this;
    }

    public FileTsvReaderBuilder forTsvFilePathTemplate(String tsvFilePathTemplate) {
        this.tsvFilePathTemplate = tsvFilePathTemplate;
        return this;
    }

    public FileTsvReaderBuilder returnDummyIfFileMissing() {
        this.defaultToADummyIfFileMissing = true;
        return this;
    }

    public TsvReader build() {
        String tsvFilePath = MessageFormat.format(tsvFilePathTemplate, experimentAccession);
        Path tsvFileSystemPath = FileSystems.getDefault().getPath(tsvFilePath);
        try {
            if(defaultToADummyIfFileMissing && !tsvFileSystemPath.toFile().exists()) {
                return new TsvReaderDummy();
            } else {
                InputStreamReader tsvFileInputStreamReader = new InputStreamReader(Files.newInputStream(tsvFileSystemPath));
                return new TsvReaderImpl(tsvFileInputStreamReader);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
