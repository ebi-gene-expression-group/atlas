package uk.ac.ebi.atlas.model.resource;

import au.com.bytecode.opencsv.CSVReader;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.impl.TsvReaderImpl;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.commons.writers.FileTsvWriterBuilder;
import uk.ac.ebi.atlas.commons.writers.TsvWriter;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.zip.GZIPInputStream;

public abstract class MatrixMarket<T> extends AtlasResource<T>{

    public MatrixMarket(String parentDirectory, String template, String ... args) {
        super(Paths.get(parentDirectory, MessageFormat.format(template, (Object []) args)));
    }

    public static class ReadAsStream extends MatrixMarket<ObjectInputStream<String[]>> {

        public ReadAsStream(String parentDirectory, String template, String... args) {
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

        private static class TsvStreamReader implements Closeable, ObjectInputStream<String[]> {

            private CSVReader tsvReader;

            public TsvStreamReader(Reader reader) {
                this.tsvReader = new CSVReader(reader, '\t');
            }

            @Override
            public void close() throws IOException {
                tsvReader.close();
            }

            @Override
            public String[] readNext() {
                try {
                    return tsvReader.readNext();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static class ReadOnly extends MatrixMarket<TsvReader> {

        public ReadOnly(String parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
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

    public static class Appendable extends MatrixMarket<TsvWriter> {

        public Appendable(String parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public TsvWriter get() {
            return new FileTsvWriterBuilder().build(path, true);
        }
    }

    public static class Overwrite extends MatrixMarket<TsvWriter> {

        public Overwrite(String parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public TsvWriter get() {
            return new FileTsvWriterBuilder().build(path, false);
        }
    }

    //Use TsvReader too, maybe?
    public static class ReadCompressed extends MatrixMarket<CSVReader> {
        public ReadCompressed(String parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
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
