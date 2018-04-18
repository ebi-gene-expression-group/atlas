package uk.ac.ebi.atlas.model.experiment.baseline;

import uk.ac.ebi.atlas.model.BiologicalReplicate;
import uk.ac.ebi.atlas.model.DescribesDataColumns;

import java.util.Set;

public class Cell extends DescribesDataColumns {

    public Cell(String id) {
        super(id);
    }


    @Override
    public Set<String> assaysAnalyzedForThisDataColumn() {
        return null;
    }

    @Override
    public Set<BiologicalReplicate> biologicalReplicatesForThisDataColumn() {
        return null;
    }
}
