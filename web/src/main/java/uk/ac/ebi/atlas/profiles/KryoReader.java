package uk.ac.ebi.atlas.profiles;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;

import java.io.Closeable;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 05/05/15.
 */
public class KryoReader implements Closeable {
    private Kryo kryo;
    private Input input;

    private String geneId;
    private String geneName;
    private Double[][] expressionLevels;

    public KryoReader(Kryo kryo, Input input) {
        this.kryo = kryo;
        this.input = input;
    }

    public String[] rewindAndreadFirstLine() {
        input.rewind();
        return kryo.readObject(input, String[].class);
    }

    public boolean readLine() {
        if (input.eof()) {
            return false;
        }
        else {
            geneId = kryo.readObject(input, String.class);
            geneName = kryo.readObject(input, String.class);
            expressionLevels = kryo.readObject(input, Double[][].class);
            return true;
        }
    }

    public String getGeneId() {
        return geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public Double[][] getExpressionLevels() {
        return expressionLevels;
    }

    public void close() throws KryoException {
        input.close();
    }
}
