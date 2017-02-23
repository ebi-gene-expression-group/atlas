package uk.ac.ebi.atlas.model.resource;

import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class KryoFiles {
    static final String template = "/serialized_expression/{0}.kryo";

    public static class Input extends AtlasResource<UnsafeInput> {

        public Input(String dataFilesLocation, String experimentAccession) {
            super(Paths.get(dataFilesLocation, MessageFormat.format(template, experimentAccession)));
        }

        @Override
        public UnsafeInput get() {
            try {
                return new UnsafeInput(Files.newInputStream(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Output extends AtlasResource<UnsafeOutput> {

        public Output(String dataFilesLocation, String experimentAccession) {
            super(Paths.get(dataFilesLocation, MessageFormat.format(template, experimentAccession)));
        }

        @Override
        public UnsafeOutput get() {
            try {
                return new UnsafeOutput(Files.newOutputStream(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
