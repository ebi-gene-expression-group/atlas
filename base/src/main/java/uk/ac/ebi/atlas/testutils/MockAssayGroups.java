package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.AssayGroup;

import java.util.List;

public class MockAssayGroups {
    protected MockAssayGroups() {
        throw new UnsupportedOperationException();
    }

    public static List<AssayGroup> create() {
        return ImmutableList.of(new AssayGroup("g1", "run1"), new AssayGroup("g2", "run2"));
    }
}
