package uk.ac.ebi.atlas.model.resource;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class JsonFile<T> extends AtlasResource<T> {
    public JsonFile(String parentDirectory, String template, String... args) {
        super(Paths.get(parentDirectory, MessageFormat.format(template, (Object[]) args)));
    }

    public static class ReadOnly extends JsonFile<JsonReader> {

        public ReadOnly(String parentDirectory, String template, String... args) {
            super(parentDirectory, template, args);
        }

        @Override
        public JsonReader get() {
            try {
                return new JsonReader(Files.newBufferedReader(path, StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}
