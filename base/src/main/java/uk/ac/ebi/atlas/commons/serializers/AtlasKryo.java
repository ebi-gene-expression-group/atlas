package uk.ac.ebi.atlas.commons.serializers;

import com.esotericsoftware.kryo.Kryo;

public class AtlasKryo {

    private AtlasKryo(){
    }

    public static Kryo get(){
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);
        return kryo;
    }
}
