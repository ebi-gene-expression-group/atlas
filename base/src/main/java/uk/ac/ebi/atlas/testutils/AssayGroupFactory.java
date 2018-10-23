package uk.ac.ebi.atlas.testutils;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.BiologicalReplicate;

import java.util.Arrays;

import static java.util.stream.Collectors.toSet;

public class AssayGroupFactory {
    public AssayGroupFactory() {
        throw new UnsupportedOperationException();
    }

    public static AssayGroup create(String id, String... assayAccessions) {
        return new AssayGroup(
                id,
                Arrays.stream(assayAccessions)
                        .map(BiologicalReplicate::new)
                        .collect(toSet()));
    }
}
