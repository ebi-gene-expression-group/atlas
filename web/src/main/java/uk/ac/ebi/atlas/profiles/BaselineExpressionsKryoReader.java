package uk.ac.ebi.atlas.profiles;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.UnsafeInput;
import uk.ac.ebi.atlas.model.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;

import java.io.Closeable;

public class BaselineExpressionsKryoReader implements Closeable {
    private Kryo kryo;
    private UnsafeInput input;
    private boolean closed;

    private int totalNumberOfGenes;
    private int geneCount;
    private String[] assays;
    private FactorGroup[] factorGroups;

    private String geneId;
    private String geneName;
    private BaselineExpression[] expressions;

    public BaselineExpressionsKryoReader(Kryo kryo, UnsafeInput input) {
        this.kryo = kryo;
        this.input = input;
        this.closed = false;
    }

    public String[] rewindAndReadAssays() {
        checkInputStreamOpen();

        input.rewind();
        totalNumberOfGenes = kryo.readObject(input, Integer.class);
        geneCount = 0;
        assays = kryo.readObject(input, String[].class);
        factorGroups = kryo.readObject(input, FactorGroup[].class);
        return assays;
    }

    public boolean readLine() {
        checkInputStreamOpen();

        if (geneCount < totalNumberOfGenes) {
            geneId = kryo.readObject(input, String.class);
            geneName = kryo.readObject(input, String.class);
            expressions = kryo.readObject(input, BaselineExpression[].class);
            for (int i = 0 ; i < expressions.length ; i++) {
                expressions[i].setFactorGroup(factorGroups[i]);
            }
            geneCount++;
            return true;
        }
        else {
            return false;
        }
    }

    public void readAll() {
        checkInputStreamOpen();

        //if (input.eof()) {
        while (geneCount < totalNumberOfGenes) {
            geneId = kryo.readObject(input, String.class);
            geneName = kryo.readObject(input, String.class);
            expressions = kryo.readObject(input, BaselineExpression[].class);
            for (int i = 0 ; i < expressions.length ; i++) {
                expressions[i].setFactorGroup(factorGroups[i]);
            }
            geneCount++;
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
