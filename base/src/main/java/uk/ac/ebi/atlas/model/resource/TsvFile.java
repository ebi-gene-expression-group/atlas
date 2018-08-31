package uk.ac.ebi.atlas.model.resource;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.commons.readers.TsvStreamer;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;

public abstract class TsvFile<T> extends AtlasResource<T> {

    public TsvFile(Path parentDirectory, String template, String... args) {
        super(parentDirectory.resolve(MessageFormat.format(template, (Object[]) args)));
    }

    public static class ReadAsStream extends TsvFile<ObjectInputStream<String[]>> {

        public ReadAsStream(Path parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public ObjectInputStream<String[]> get() {
            try {
                return new TsvStreamReader(Files.newBufferedReader(path, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private static class TsvStreamReader implements ObjectInputStream<String[]> {
            private CSVReader tsvReader;

            TsvStreamReader(Reader reader) {
                this.tsvReader = new CSVReader(reader, '\t');
            }

            @Override
            public String[] readNext() {
                try {
                    return tsvReader.readNext();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void close() throws IOException {
                tsvReader.close();
            }
        }
    }

    public static class ReadOnly extends TsvFile<TsvStreamer> {
        public ReadOnly(Path parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public TsvStreamer get() {
            try {
                return new TsvStreamer(Files.newBufferedReader(path, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public static class Overwrite extends TsvFile<TsvWriter> {
        public Overwrite(Path parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public TsvWriter get() {
            return new FileTsvWriterBuilder().build(path, false);
        }
    }

    //Use TsvReader too, maybe?
    public static class ReadCompressed extends TsvFile<CSVReader> {
        public ReadCompressed(Path parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public CSVReader get() {
            try {
                return new CSVReader(
                        new BufferedReader(
                                new InputStreamReader(new GZIPInputStream(Files.newInputStream(path)))),
                        '\t');
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
