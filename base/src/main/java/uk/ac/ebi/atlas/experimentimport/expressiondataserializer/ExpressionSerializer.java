package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;

public interface ExpressionSerializer {
    String serializeExpressionData(String experimentAccession);
}
