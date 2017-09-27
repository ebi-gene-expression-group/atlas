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
import java.util.Collection;
import java.util.Optional;

public class ProfileStreamKryoLayer<DataColumnDescriptor extends DescribesDataColumns, Expr extends Expression,
        E extends Experiment<DataColumnDescriptor>, StreamOptions extends ProfileStreamOptions<DataColumnDescriptor>,
        Prof extends Profile<DataColumnDescriptor, Expr, Prof>> extends ProfileStreamFactory<DataColumnDescriptor, Expr, E, StreamOptions, Prof> {

    private final CreatesProfilesFromTsvFiles<DataColumnDescriptor, Expr, E, StreamOptions, Prof> profileStreamFactory;

    public ProfileStreamKryoLayer(CreatesProfilesFromTsvFiles<DataColumnDescriptor, Expr, E, StreamOptions, Prof> profileStreamFactory) {
        this.profileStreamFactory = profileStreamFactory;
    }

    public ObjectInputStream<Prof> create(E experiment, StreamOptions options, Optional<Collection<String>> keepGeneIds) {
        AtlasResource<KryoFile.Handle> kryoFile = getKryoFile(experiment, options);
        if (kryoFile.exists()) {
            return createFromKryoFile(kryoFile.get(), profileStreamFactory.filterExpressions(experiment, options), keepGeneIds);
        } else {
            return createFromTsvFile(experiment, options, keepGeneIds);
        }
    }

    ObjectInputStream<Prof> createFromKryoFile(KryoFile.Handle kryoFile, final Predicate<Expr> keepExpressions, Optional<Collection<String>> keepGeneIds) {
        final UnsafeInput input = kryoFile.read();
        final Kryo kryo = AtlasKryo.get();

        if (keepGeneIds.isPresent()) {
            final Collection<String> geneIdsToKeep = keepGeneIds.get();
            return new ObjectInputStream<Prof>() {
                @Override
                public Prof readNext() {
                    if (input.eof()) {
                        return null;
                    } else {
                        Prof p = ((Prof) kryo.readClassAndObject(input));
                        if (geneIdsToKeep.contains(p.getId())) {
                            return p.filter(keepExpressions);
                        } else {
                            return readNext();
                        }
                    }
                }

                @Override
                public void close() throws IOException {
                    input.close();
                }
            };
        } else {
            return new ObjectInputStream<Prof>() {
                @Override
                public Prof readNext() {
                    if (input.eof()) {
                        return null;
                    } else {
                        return ((Prof) kryo.readClassAndObject(input)).filter(keepExpressions);
                    }
                }

                @Override
                public void close() throws IOException {
                    input.close();
                }
            };
        }

    }

    protected ObjectInputStream<Prof> createFromTsvFile(E experiment, StreamOptions options, Optional<Collection<String>> keepGeneIds) {
        return profileStreamFactory.create(experiment, options, keepGeneIds);
    }

    private AtlasResource<KryoFile.Handle> getKryoFile(E experiment, StreamOptions options) {
        return profileStreamFactory.dataFileHub.getKryoFile(experiment.getAccession(), options);
    }

    public void persist(E experiment, StreamOptions options) {
        KryoFile.Handle kryoFileHandle = getKryoFile(experiment, options).get();
        final Kryo kryo = AtlasKryo.get();

        final UnsafeOutput output = kryoFileHandle.write();
        for (Prof profile : new IterableObjectInputStream<>(
                createFromTsvFile(experiment, options, Optional.empty())
        )) {
            kryo.writeClassAndObject(output, profile);
        }
        output.close();
        kryoFileHandle.addPermissionsAfterWrite();
    }
}
