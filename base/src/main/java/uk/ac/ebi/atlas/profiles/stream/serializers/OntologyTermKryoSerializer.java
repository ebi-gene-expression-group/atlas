package uk.ac.ebi.atlas.profiles.stream.serializers;

import uk.ac.ebi.atlas.model.OntologyTerm;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class OntologyTermKryoSerializer extends Serializer<OntologyTerm>  {
    public OntologyTermKryoSerializer() {
        super();
    }

    @Override
    public void write(Kryo kryo, Output output, OntologyTerm ontologyTerm) {
        kryo.writeObject(output, ontologyTerm.accession());
        kryo.writeObject(output, ontologyTerm.name());
        kryo.writeObject(output, ontologyTerm.source());
        kryo.writeObject(output, ontologyTerm.depth());
    }

    @Override
    public OntologyTerm read(Kryo kryo, Input input, Class<OntologyTerm> aClass) {
        return OntologyTerm.create(
                kryo.readObject(input, String.class), kryo.readObject(input, String.class), kryo.readObject(input, String.class), kryo.readObject(input, Integer.class));
    }

    public static void registerSerializers(final Kryo kryo) {
        final OntologyTermKryoSerializer serializer = new OntologyTermKryoSerializer();
        kryo.register(OntologyTerm.create("").getClass(), serializer);
    }
}
