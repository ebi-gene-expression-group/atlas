package uk.ac.ebi.atlas.profiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.commons.serializers.ImmutableSetKryoSerializer;
import uk.ac.ebi.atlas.commons.serializers.OntologyTermKryoSerializer;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.UnsafeInput;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

public class BaselineExpressionsKryoReader implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineExpressionsKryoReader.class);
    private Kryo kryo;
    private UnsafeInput input;
    private boolean closed;

    private int totalNumberOfGenes;
    private int genesAlreadyDeserialized = 0;

    private String geneId;
    private String geneName;
    private BaselineExpression[] expressions;

    private static Kryo createKryo() {
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);
        return kryo;
    }

    public static BaselineExpressionsKryoReader create(String serializedFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(serializedFilePath));
            InputStream inputStream = Files.newInputStream(filePath);
            return new BaselineExpressionsKryoReader(createKryo(), new UnsafeInput(inputStream));
        } catch (IOException e) {
            LOGGER.warn("{} maybe the file does not exist?", e.getMessage());
            throw new IllegalArgumentException("Error trying to open " + serializedFilePath, e);
        }
    }

    public BaselineExpressionsKryoReader(Kryo kryo, UnsafeInput input) {
        this.kryo = kryo;
        this.input = input;
        totalNumberOfGenes = kryo.readObject(input, Integer.class);
        this.closed = false;
    }


    public boolean readLine() {
        checkInputStreamOpen();

        if (genesAlreadyDeserialized < totalNumberOfGenes) {
            geneId = kryo.readObject(input, String.class);
            geneName = kryo.readObject(input, String.class);
            expressions = kryo.readObject(input, BaselineExpression[].class);
            genesAlreadyDeserialized++;
            return true;
        }
        else {
            return false;
        }
    }

    public String getGeneId() {
        checkInputStreamOpen();

        return geneId;
    }

    public String getGeneName() {
        checkInputStreamOpen();

        return geneName;
    }

    public BaselineExpression[] getExpressions() {
        checkInputStreamOpen();

        return expressions;
    }

    public void close() throws KryoException {
        input.close();
        closed = true;
    }

    private void checkInputStreamOpen() {
        if (closed) {
            throw new IllegalStateException("Kryo input stream is closed");
        }
    }
}
