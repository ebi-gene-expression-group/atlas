package uk.ac.ebi.atlas.model.resource;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;

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

    //Use TsvReader too, maybe?
    public static class ReadCompressed extends TsvFile<CSVReader> {
        public ReadCompressed(String dataFilesLocation, String template, String... args) {
            super(dataFilesLocation, template, args);
        }

        @Override
        public CSVReader get() {
            try {
                return new CSVReader(new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(path)))), '\t');
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
