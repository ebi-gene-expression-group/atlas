package uk.ac.ebi.atlas.experimentimport.expressiondataserializer;

import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 02/04/15.
 */
public interface ExpressionSerializer {
    void serializeExpressionData(String experimentAccession, ExperimentalFactors experimentalFactors);
}
