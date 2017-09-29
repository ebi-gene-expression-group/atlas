package uk.ac.ebi.atlas.profiles.stream.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpressionPerBiologicalReplicate;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;

import java.util.HashMap;
import java.util.HashSet;

public class AtlasKryo {

    private AtlasKryo(){
    }

    public static Kryo get(){
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);

        kryo.register(BaselineExpression.class,
                new Serializer<BaselineExpression>() {
            @Override
            public void write(Kryo kryo, Output output, BaselineExpression baselineExpression) {
                double[] quartiles = baselineExpression.getQuartiles();
                output.writeBoolean(quartiles.length == 5);
                for(double d : quartiles){
                    output.writeDouble(d);
                }
            }

            @Override
            public BaselineExpression read(Kryo kryo, Input input, Class<BaselineExpression> aClass) {
                if(input.readBoolean()){
                    return new BaselineExpression(
                            input.readDouble(),
                            input.readDouble(),
                            input.readDouble(),
                            input.readDouble(),
                            input.readDouble()
                    );
                } else {
                    return new BaselineExpression(
                            input.readDouble()
                    );
                }
            }
        });

        kryo.register(DifferentialExpression.class, new Serializer<DifferentialExpression>() {

            @Override
            public void write(Kryo kryo, Output output, DifferentialExpression differentialExpression) {
                output.writeDouble(differentialExpression.getPValue());
                output.writeDouble(differentialExpression.getFoldChange());
            }

            @Override
            public DifferentialExpression read(Kryo kryo, Input input, Class<DifferentialExpression> aClass) {
                return new DifferentialExpression(input.readDouble(), input.readDouble());
            }
        });

        kryo.register(MicroarrayExpression.class, new Serializer<MicroarrayExpression>(){

            @Override
            public void write(Kryo kryo, Output output, MicroarrayExpression microarrayExpression) {
                output.writeDouble(microarrayExpression.getPValue());
                output.writeDouble(microarrayExpression.getFoldChange());
                output.writeDouble(microarrayExpression.getTstatistic());
            }

            @Override
            public MicroarrayExpression read(Kryo kryo, Input input, Class<MicroarrayExpression> aClass) {
                return new MicroarrayExpression(input.readDouble(), input.readDouble(), input.readDouble());
            }
        });

        kryo.register(BaselineExpressionPerBiologicalReplicate.class, new Serializer<BaselineExpressionPerBiologicalReplicate>(){

            @Override
            public void write(Kryo kryo, Output output, BaselineExpressionPerBiologicalReplicate baselineExpressionPerBiologicalReplicate) {
                kryo.writeObject(output, new HashMap<>(baselineExpressionPerBiologicalReplicate.data));
            }

            @Override
            public BaselineExpressionPerBiologicalReplicate read(Kryo kryo, Input input, Class<BaselineExpressionPerBiologicalReplicate> aClass) {
                return new BaselineExpressionPerBiologicalReplicate(
                        kryo.readObject(input, HashMap.class)
                );
            }
        });

        /*
        not currently used since profile stores Strings for IDs:
        AssayGroup.class
        Contrast.class
         */
        kryo.register(BiologicalReplicate.class, new Serializer<BiologicalReplicate>() {
            @Override
            public void write(Kryo kryo, Output output, BiologicalReplicate biologicalReplicate) {
                output.writeString(biologicalReplicate.getId());
                boolean b = biologicalReplicate.assaysAnalyzedForThisDataColumn().size() > 1;
                output.writeBoolean(b);
                if(b){
                    kryo.writeObject(output, Sets.newHashSet(biologicalReplicate.assaysAnalyzedForThisDataColumn()));
                }
            }

            @Override
            public BiologicalReplicate read(Kryo kryo, Input input, Class<BiologicalReplicate> aClass) {
                String id = input.readString();
                boolean b = input.readBoolean();
                if(b){
                    return new BiologicalReplicate(id, kryo.readObject(input, HashSet.class));
                } else {
                    return new BiologicalReplicate(id);
                }
            }
        });

        return kryo;
    }
}
