
package uk.ac.ebi.atlas.model.differential;

public interface DifferentialExpressionLimits {
    double getMaxUpRegulatedExpressionLevel();

    double getMinUpRegulatedExpressionLevel();

    double getMaxDownRegulatedExpressionLevel();

    double getMinDownRegulatedExpressionLevel();
}
