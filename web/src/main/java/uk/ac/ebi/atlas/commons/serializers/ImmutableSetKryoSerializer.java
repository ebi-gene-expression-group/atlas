/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 28/05/15.
 */

package uk.ac.ebi.atlas.commons.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * A kryo {@link Serializer} for guava-libraries {@link ImmutableList}.
 */
public class ImmutableSetKryoSerializer extends Serializer<ImmutableSet<Object>> {

    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = true;

    public ImmutableSetKryoSerializer() {
        super(DOES_NOT_ACCEPT_NULL, IMMUTABLE);
    }

    @Override
    public void write(Kryo kryo, Output output, ImmutableSet<Object> object) {
        output.writeInt(object.size(), true);
        for (Object elm : object) {
            kryo.writeClassAndObject(output, elm);
        }
    }

    @Override
    public ImmutableSet<Object> read(Kryo kryo, Input input, Class<ImmutableSet<Object>> type) {
        final int size = input.readInt(true);
        final Object[] set = new Object[size];
        for (int i = 0; i < size; ++i) {
            set[i] = kryo.readClassAndObject(input);
        }
        return ImmutableSet.copyOf(set);
    }

    /**
     * Creates a new {@link ImmutableSetKryoSerializer} and registers its serializer
     * for the several ImmutableSet related classes.
     *
     * @param kryo the {@link Kryo} instance to set the serializer on
     */
    public static void registerSerializers(final Kryo kryo) {

        // ImmutableSet (abstract class)
        //  +- RegularImmutableSet
        //  |   RegularImmutableSet
        //  +- SingletonImmutableSet
        //  |   Optimized for List with only 1 element.
        //  +- Values (ImmutableTable values)
        //      Used by return value of #values() when there are multiple cells

        final ImmutableSetKryoSerializer serializer = new ImmutableSetKryoSerializer();

        kryo.register(ImmutableSet.class, serializer);

        // To register for optimized ImmutableSet with a few values (e.g. SingletonImmutableSet)
        // See https://github.com/google/guava/blob/master/guava/src/com/google/common/collect/ImmutableSet.java
        kryo.register(ImmutableSet.of().getClass(), serializer);
        kryo.register(ImmutableSet.of(1).getClass(), serializer);
        kryo.register(ImmutableSet.of(1,2).getClass(), serializer);
        kryo.register(ImmutableSet.of(1,2,3).getClass(), serializer);
        kryo.register(ImmutableSet.of(1,2,3,4).getClass(), serializer);
        kryo.register(ImmutableSet.of(1,2,3,4,5).getClass(), serializer);
    }
}

