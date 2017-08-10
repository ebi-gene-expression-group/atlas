package uk.ac.ebi.atlas.profiles.stream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;
import com.google.common.base.Predicate;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.resource.AtlasResource;
import uk.ac.ebi.atlas.model.resource.KryoFile;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.serializers.AtlasKryo;

import java.io.IOException;

public class ProfileStreamKryoLayer<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>> extends ProfileStreamFactory<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

    private final CreatesProfilesFromTsvFiles<DataColumnDescriptor, Expr, E, StreamOptions, Prof> profileStreamFactory;

    public ProfileStreamKryoLayer(CreatesProfilesFromTsvFiles<DataColumnDescriptor, Expr, E, StreamOptions, Prof> profileStreamFactory){
        this.profileStreamFactory = profileStreamFactory;
    }

    public ObjectInputStream<Prof> create(E experiment, StreamOptions options){
        final Predicate<Expr> filterExpressions = profileStreamFactory.filterExpressions(experiment, options);
        AtlasResource<KryoFile.Handle> kryoFile = getKryoFile(experiment, options);
        if(kryoFile.exists()){
            return createFromKryoFile(kryoFile.get(), filterExpressions);
        } else {
            return createFromTsvFile(experiment, options, filterExpressions);
        }
    }

    ObjectInputStream<Prof> createFromKryoFile(KryoFile.Handle kryoFile, final Predicate<Expr> filter){
        final UnsafeInput input = kryoFile.read();
        final Kryo kryo = AtlasKryo.get();

        return new ObjectInputStream<Prof>() {
            @Override
            public Prof readNext() {
                if(input.eof()){
                    return null;
                } else {
                    return ((Prof) kryo.readClassAndObject(input)).filter(filter);
                }
            }

            @Override
            public void close() throws IOException {
                input.close();
            }
        };
    }

    protected ObjectInputStream<Prof> createFromTsvFile(E experiment, StreamOptions options, Predicate<Expr> filter){
        return profileStreamFactory.create(experiment, options, filter);
    }

    private AtlasResource<KryoFile.Handle> getKryoFile(E experiment, StreamOptions options){
        return profileStreamFactory.dataFileHub.getKryoFile(experiment.getAccession(), options);
    }

    public void persist(E experiment, StreamOptions options){
        KryoFile.Handle kryoFileHandle = getKryoFile(experiment, options).get();
        final Kryo kryo = AtlasKryo.get();

        final UnsafeOutput output = kryoFileHandle.write();
        for(Prof profile : new IterableObjectInputStream<>(
                createFromTsvFile(experiment, options, expr -> true)
                )){
            kryo.writeClassAndObject(output, profile);
        }
        output.close();
        kryoFileHandle.addPermissionsAfterWrite();
    }
}
