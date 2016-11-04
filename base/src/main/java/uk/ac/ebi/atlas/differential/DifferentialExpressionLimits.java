
package uk.ac.ebi.atlas.differential;

public interface DifferentialExpressionLimits {
    double getMaxUpRegulatedExpressionLevel();

    double getMinUpRegulatedExpressionLevel();

    double getMaxDownRegulatedExpressionLevel();

    double getMinDownRegulatedExpressionLevel();
}
