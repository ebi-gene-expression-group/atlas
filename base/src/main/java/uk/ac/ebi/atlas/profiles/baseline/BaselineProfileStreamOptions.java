package uk.ac.ebi.atlas.profiles.baseline;

import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

public interface BaselineProfileStreamOptions<Unit extends ExpressionUnit.Absolute> extends ProfileStreamOptions<AssayGroup> {
    double getCutoff();

    Unit getExpressionUnit();
}
