package uk.ac.ebi.atlas.util;

import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.model.FactorValue;

public class ExpressionLevelBuilder {

    public static ExpressionLevel createExpressionLevelInstance(String identifier, int rpkm, FactorValue... factorValues) {
        ExpressionLevel expressionLevel = new ExpressionLevel(identifier, rpkm);
        for (FactorValue factorValue : factorValues) {
            expressionLevel.addFactorValue(factorValue);

        }
        return expressionLevel;
    }
}
