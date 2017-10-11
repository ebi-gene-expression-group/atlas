package uk.ac.ebi.atlas.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

public class BiologicalReplicate extends DescribesDataColumns {

    private final Set<String> technicalReplicates;

    public BiologicalReplicate(String assay) {
        super(assay);
        this.technicalReplicates = ImmutableSet.of(assay);
    }

    public BiologicalReplicate(String id, Set<String> assays){
        super(id);
        Preconditions.checkArgument(
                !assays.contains(id),
                "Expected: technical replicate group, like 't1' and assays corresponding to it"
        );
        Preconditions.checkArgument(
                assays.size() > 1,
                "technical replicate group must have multiple replicates inside!"
                );
        this.technicalReplicates = assays;
    }

    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return technicalReplicates;
    }

    @Override
    public Set<BiologicalReplicate> biologicalReplicatesForThisDataColumn() {
        return ImmutableSet.of(this);
    }

}
