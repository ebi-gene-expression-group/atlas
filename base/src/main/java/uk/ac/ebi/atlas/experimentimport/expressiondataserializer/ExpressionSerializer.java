package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;

import java.io.IOException;

public interface ExpressionSerializer {
    String serializeExpressionData(String experimentAccession, ExperimentalFactors experimentalFactors);
}
