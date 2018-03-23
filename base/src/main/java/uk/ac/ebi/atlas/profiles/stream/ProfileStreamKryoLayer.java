package uk.ac.ebi.atlas.profiles.stream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.UnsafeInput;
import com.esotericsoftware.kryo.io.UnsafeOutput;
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

import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

public class ProfileStreamKryoLayer<D extends DescribesDataColumns,
                                    E extends Expression,
                                    T extends Experiment<D>,
                                    O extends ProfileStreamOptions<D>,
                                    P extends Profile<D, E, P>>
        extends ProfileStreamFactory<D, E, T, O, P> {

    private final CreatesProfilesFromTsvFiles<D, E, T, O, P> profileStreamFactory;

    public ProfileStreamKryoLayer(CreatesProfilesFromTsvFiles<D, E, T, O, P> profileStreamFactory) {
        this.profileStreamFactory = profileStreamFactory;
    }

    public ObjectInputStream<P> create(T experiment, O options, Collection<String> keepGeneIds) {
        AtlasResource<KryoFile.Handle> kryoFile = getKryoFile(experiment, options);
        if (kryoFile.exists()) {
            return createFromKryoFile(kryoFile.get(), profileStreamFactory.filterExpressions(experiment, options), keepGeneIds);
        } else {
            return createFromTsvFile(experiment, options, keepGeneIds);
        }
    }

    private ObjectInputStream<P> createFromKryoFile(KryoFile.Handle kryoFile, final Predicate<E> keepExpressions, Collection<String> keepGeneIds) {
        final UnsafeInput input = kryoFile.read();
        final Kryo kryo = AtlasKryo.get();

        if (!keepGeneIds.isEmpty()) {
            return new ObjectInputStream<P>() {
                @Override
                public P readNext() {
                    while (!input.eof()) {
                        P p = ((P) kryo.readClassAndObject(input));
                        if (keepGeneIds.contains(p.getId())) {
                            return p.filter(keepExpressions);
                        }
                    }
                    return null;
                }

                @Override
                public void close() {
                    input.close();
                }
            };
        } else {
            return new ObjectInputStream<P>() {
                @Override
                public P readNext() {
                    if (input.eof()) {
                        return null;
                    } else {
                        return ((P) kryo.readClassAndObject(input)).filter(keepExpressions);
                    }
                }

                @Override
                public void close() {
                    input.close();
                }
            };
        }

    }

    protected ObjectInputStream<P> createFromTsvFile(T experiment, O options, Collection<String> keepGeneIds) {
        return profileStreamFactory.create(experiment, options, keepGeneIds);
    }

    private AtlasResource<KryoFile.Handle> getKryoFile(T experiment, O options) {
        return profileStreamFactory.dataFileHub.getKryoFile(experiment.getAccession(), options);
    }

    public void persist(T experiment, O options) {
        KryoFile.Handle kryoFileHandle = getKryoFile(experiment, options).get();
        final Kryo kryo = AtlasKryo.get();

        final UnsafeOutput output = kryoFileHandle.write();
        for (P profile : new IterableObjectInputStream<>(
                createFromTsvFile(experiment, options, Collections.emptySet())
        )) {
            kryo.writeClassAndObject(output, profile);
        }
        output.close();
        kryoFileHandle.addPermissionsAfterWrite();
    }
}
