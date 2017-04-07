package uk.ac.ebi.atlas.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class AssayGroupsFake {

    public static List<AssayGroup> get() {

        String RUN_ACCESSION1 = "run1";
        String RUN_ACCESSION2 = "run2";
        return ImmutableList.of(new AssayGroup("g1", RUN_ACCESSION1), new AssayGroup("g2", RUN_ACCESSION2));
    }
}