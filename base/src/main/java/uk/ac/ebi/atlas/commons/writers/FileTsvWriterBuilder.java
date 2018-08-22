package uk.ac.ebi.atlas.commons.writers;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.file.Path;

@Named
@Scope("prototype")
public class FileTsvWriterBuilder {
    public TsvWriter build(Path path, boolean appendTo) {
        try {
            return new TsvWriter(new OutputStreamWriter(new FileOutputStream(path.toFile(), appendTo)));
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write TSV file to path " + path, e);
        }
    }
}
