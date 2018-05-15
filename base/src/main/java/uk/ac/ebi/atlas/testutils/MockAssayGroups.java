package uk.ac.ebi.atlas.testutils;

import com.google.common.collect.ImmutableList;
import uk.ac.ebi.atlas.model.AssayGroup;

import java.util.List;

public class MockAssayGroups {

    public static List<AssayGroup> create() {

        String RUN_ACCESSION1 = "run1";
        String RUN_ACCESSION2 = "run2";
        return ImmutableList.of(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2));
    }
}