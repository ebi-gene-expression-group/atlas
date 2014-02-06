package uk.ac.ebi.atlas.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ArraySortByIndicesTest {

    @Test
    public void match() {
        String[] patterns = new String[] {"g1", "g2", "g3", "g4", "g5"};
        String[] values = new String[] {"g2", "g4", "g1", "g5", "g3"};

        assertThat(ArraySortByIndices.match(patterns, values), is(new int[]{1, 3, 0, 4, 2}));
    }

    @Test(expected = NullPointerException.class)
    public void match_MissingValue() {
        String[] patterns = new String[] {"g1", "g2", "g3", "g4", "g5"};
        String[] values = new String[] {"g6", "g4", "g1", "g5", "g3"};

        ArraySortByIndices.match(patterns, values);
    }

    @Test
    public void sort() {
        String[] values = new String[] {"g2", "g4", "g1", "g5", "g3"};
        int[] indices = new int[]{1, 3, 0, 4, 2};

        assertThat(ArraySortByIndices.sort(values, indices), is(new String[] {"g1", "g2", "g3", "g4", "g5"}));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void sort_InvalidIndex() {
        String[] values = new String[] {"g2", "g4", "g1", "g5", "g3"};
        int[] indices = new int[]{9999, 3, 0, 4, 2};

        ArraySortByIndices.sort(values, indices);
    }

}
