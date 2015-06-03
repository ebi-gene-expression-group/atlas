package uk.ac.ebi.atlas.commons.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import uk.ac.ebi.atlas.model.OntologyTerm;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 28/05/15.
 */
public class OntologyTermKryoSerializer extends Serializer<OntologyTerm>  {
    public OntologyTermKryoSerializer() {
        super();
    }

    @Override
    public void write(Kryo kryo, Output output, OntologyTerm ontologyTerm) {
        kryo.writeObject(output, ontologyTerm.id());
        kryo.writeObjectOrNull(output, ontologyTerm.source(), String.class);
    }

    @Override
    public OntologyTerm read(Kryo kryo, Input input, Class<OntologyTerm> aClass) {
        return OntologyTerm.create(kryo.readObject(input, String.class), kryo.readObjectOrNull(input, String.class));
    }

    public static void registerSerializers(final Kryo kryo) {
        final OntologyTermKryoSerializer serializer = new OntologyTermKryoSerializer();
        kryo.register(OntologyTerm.create("").getClass(), serializer);
    }
}
