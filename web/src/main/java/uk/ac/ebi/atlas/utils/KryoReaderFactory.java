package uk.ac.ebi.atlas.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.UnsafeInput;
import org.apache.log4j.Logger;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetKryoSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermKryoSerializer;
import uk.ac.ebi.atlas.profiles.BaselineExpressionsKryoReader;

import javax.inject.Named;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
public class KryoReaderFactory {

    private static final Logger LOGGER = Logger.getLogger(KryoReaderFactory.class);

    private static Kryo createKryo() {
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);
        return kryo;
    }

    public BaselineExpressionsKryoReader createBaselineExpressionsKryoReader(String serializedFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(serializedFilePath));
            InputStream inputStream = Files.newInputStream(filePath);
            return createBaselineExpressionsKryoReader(new UnsafeInput(inputStream));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalArgumentException("Error trying to open " + serializedFilePath, e);
        }
    }

    public static BaselineExpressionsKryoReader createBaselineExpressionsKryoReader(UnsafeInput source) {
        return new BaselineExpressionsKryoReader(createKryo(), source);
    }
}
