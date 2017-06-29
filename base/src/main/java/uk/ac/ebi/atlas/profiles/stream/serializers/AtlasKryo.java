package uk.ac.ebi.atlas.profiles.stream.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.base.Joiner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;

public class AtlasKryo {

    private AtlasKryo(){
    }

    public static Kryo get(){
        Kryo kryo = new Kryo();
        ImmutableSetKryoSerializer.registerSerializers(kryo);
        OntologyTermKryoSerializer.registerSerializers(kryo);

        kryo.register(AssayGroup.class,
                new Serializer<AssayGroup>() {
                    @Override
                    public void write(Kryo kryo, Output output, AssayGroup assayGroup) {
                        output.writeString(assayGroup.getId());
                        output.writeInt(assayGroup.getReplicates());
                        output.writeString(Joiner.on("\n").join(assayGroup));
                    }

                    @Override
                    public AssayGroup read(Kryo kryo, Input input, Class<AssayGroup> aClass) {
                        String id = input.readString();
                        int replicates = input.readInt();
                        return new AssayGroup(id, replicates, input.readString().split("\n"));
                    }
                });

        kryo.register(Contrast.class,
                new Serializer<Contrast>(){

                    @Override
                    public void write(Kryo kryo, Output output, Contrast contrast) {
                        output.writeString(contrast.getId());
                        output.writeString(contrast.getArrayDesignAccession());
                        kryo.writeObject(output, contrast.getReferenceAssayGroup());
                        kryo.writeObject(output, contrast.getTestAssayGroup());
                        output.writeString(contrast.getDisplayName());
                    }

                    @Override
                    public Contrast read(Kryo kryo, Input input, Class<Contrast> aClass) {
                        return new Contrast(
                                input.readString(),
                                input.readString(),
                                kryo.readObject(input, AssayGroup.class),
                                kryo.readObject(input, AssayGroup.class),
                                input.readString());
                    }
                });

        kryo.register(BaselineExpression.class,
                new Serializer<BaselineExpression>() {
            @Override
            public void write(Kryo kryo, Output output, BaselineExpression baselineExpression) {
                double[] quartiles = baselineExpression.getQuartiles();
                output.writeBoolean(quartiles.length == 5);
                for(double d : quartiles){
                    output.writeDouble(d);
                }
                output.writeString(baselineExpression.getDataColumnDescriptorId());
            }

            @Override
            public BaselineExpression read(Kryo kryo, Input input, Class<BaselineExpression> aClass) {
                if(input.readBoolean()){
                    return new BaselineExpression(
                            input.readDouble(),
                            input.readDouble(),
                            input.readDouble(),
                            input.readDouble(),
                            input.readDouble(),
                            input.readString()
                            );
                } else {
                    return new BaselineExpression(
                            input.readDouble(),
                            input.readString()
                    );
                }
            }
        });

        kryo.register(DifferentialExpression.class, new Serializer<DifferentialExpression>() {

            @Override
            public void write(Kryo kryo, Output output, DifferentialExpression differentialExpression) {
                output.writeDouble(differentialExpression.getPValue());
                output.writeDouble(differentialExpression.getFoldChange());
                output.writeString(differentialExpression.getDataColumnDescriptorId());
            }

            @Override
            public DifferentialExpression read(Kryo kryo, Input input, Class<DifferentialExpression> aClass) {
                return new DifferentialExpression(input.readDouble(), input.readDouble(), input.readString());
            }
        });

        kryo.register(MicroarrayExpression.class, new Serializer<MicroarrayExpression>(){

            @Override
            public void write(Kryo kryo, Output output, MicroarrayExpression microarrayExpression) {

                output.writeDouble(microarrayExpression.getPValue());
                output.writeDouble(microarrayExpression.getFoldChange());
                output.writeDouble(microarrayExpression.getTstatistic());
                output.writeString(microarrayExpression.getDataColumnDescriptorId());
            }

            @Override
            public MicroarrayExpression read(Kryo kryo, Input input, Class<MicroarrayExpression> aClass) {
                return new MicroarrayExpression(input.readDouble(), input.readDouble(), input.readDouble(), input.readString());
            }
        });

        return kryo;
    }
}
