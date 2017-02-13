package uk.ac.ebi.atlas.model.resource;

import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class TsvFile<T> extends AtlasResource<T>{

    public TsvFile(String dataFilesLocation, String template, String ... args) {
        super(Paths.get(dataFilesLocation, MessageFormat.format(template, (Object []) args)));
    }

    public static class ReadOnly extends TsvFile<TsvReader> {

        public ReadOnly(String dataFilesLocation, String template, String... args) {
            super(dataFilesLocation, template, args);
        }

        @Override
        public TsvReader get() {
            try {
                return new TsvReaderImpl(Files.newBufferedReader(path, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public static class Appendable extends TsvFile<TsvWriter> {

        public Appendable(String dataFilesLocation, String template, String... args) {
            super(dataFilesLocation, template, args);
        }

        @Override
        public TsvWriter get() {
            return new FileTsvWriterBuilder().build(path, true);
        }
    }

    public static class Overwrite extends TsvFile<TsvWriter> {

        public Overwrite(String dataFilesLocation, String template, String... args) {
            super(dataFilesLocation, template, args);
        }

        @Override
        public TsvWriter get() {
            return new FileTsvWriterBuilder().build(path, false);
        }
    }
}
