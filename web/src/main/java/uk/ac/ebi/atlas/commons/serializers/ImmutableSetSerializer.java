/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 28/05/15.
 */

package uk.ac.ebi.atlas.commons.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.*;

/**
 * A kryo {@link Serializer} for guava-libraries {@link ImmutableList}.
 */
public class ImmutableSetSerializer extends Serializer<ImmutableSet<Object>> {

    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = true;

    public ImmutableSetSerializer() {
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
     * Creates a new {@link ImmutableSetSerializer} and registers its serializer
     * for the several ImmutableSet related classes.
     *
     * @param kryo the {@link Kryo} instance to set the serializer on
     */
    public static void registerSerializers(final Kryo kryo) {

        // ImmutableList (abstract class)
        //  +- RegularImmutableSet
        //  |   RegularImmutableSet
        //  +- SingletonImmutableSet
        //  |   Optimized for List with only 1 element.
        //  +- Values (ImmutableTable values)
        //      Used by return value of #values() when there are multiple cells

        final ImmutableSetSerializer serializer = new ImmutableSetSerializer();

        kryo.register(ImmutableSet.class, serializer);

        // Note:
        //  Only registering above is good enough for serializing/deserializing.
        //  but if using Kryo#copy, following is required.

        kryo.register(ImmutableSet.of().getClass(), serializer);
        kryo.register(ImmutableSet.of(1).getClass(), serializer);
//        kryo.register(ImmutableSet.of(1,2,3).subSet(1, 2).getClass(), serializer);
//            kryo.register(ImmutableSet.of().reverse().getClass(), serializer);
//
//            kryo.register(Sets.charactersOf("KryoRocks").getClass(), serializer);
//
//            Table<Integer,Integer,Integer> baseTable = HashBasedTable.create();
//            baseTable.put(1, 2, 3);
//            baseTable.put(4, 5, 6);
//            Table<Integer, Integer, Integer> table = ImmutableTable.copyOf(baseTable);
//            kryo.register(table.values().getClass(), serializer);

    }
}

