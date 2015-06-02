package uk.ac.ebi.atlas.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.UnsafeInput;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.profiles.KryoReader;

import javax.inject.Named;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class KryoReaderFactory {

    private static final Logger LOGGER = Logger.getLogger(KryoReaderFactory.class);

    public KryoReader createKryoReader(String serializedFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(serializedFilePath));
            InputStream inputStream = Files.newInputStream(filePath);
            //return createKryoReader(new Input(inputStream));
            return createKryoReader(new UnsafeInput(inputStream));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error trying to open " + serializedFilePath, e);
        }
    }

    // If performance suffers, use a KryoPool https://github.com/EsotericSoftware/kryo#pooling-kryo-instances
    public static KryoReader createKryoReader(UnsafeInput source) {
        return new KryoReader(new Kryo(), source);
    }

}
