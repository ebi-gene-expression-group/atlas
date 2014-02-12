package uk.ac.ebi.atlas.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class ArraySortByIndices {

    private ArraySortByIndices() {

    }

    public static String[] sort(String[] values, int[] indicesOrder) {
        checkArgument(values.length == indicesOrder.length, "Arguments must have same length. Expecting a total of " + indicesOrder.length +
                                                            " but only got " + values.length +
                                                            ". Current values are => " + Joiner.on(", ").join(values));


        String[] sorted = new String[values.length];

        for (int i = 0; i < indicesOrder.length; i++) {
            int index = indicesOrder[i];
            sorted[index] = values[i];
        }

        return sorted;
    }

    public static int[] match(String[] patterns, String[] values) {
        checkArgument(patterns.length == values.length, "Arguments must have same length");

        ImmutableMap.Builder<String, Integer> builder = ImmutableMap.<String, Integer>builder();

        for (int i = 0; i < patterns.length; i++) {
            builder.put(patterns[i],i);
        }

        ImmutableMap<String, Integer> patternsToIndex = builder.build();

        int[] indices = new int[values.length];

        for (int i = 0; i < values.length; i++) {
            Integer index = patternsToIndex.get(values[i]);
            checkNotNull(index, String.format("%s is not matched in %s", values[i], Joiner.on(", ").join(patterns)));
            indices[i] = index;
        }

        return indices;
    }

}
