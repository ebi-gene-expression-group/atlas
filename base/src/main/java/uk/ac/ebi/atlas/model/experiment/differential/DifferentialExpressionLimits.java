
package uk.ac.ebi.atlas.model.experiment.differential;

public interface DifferentialExpressionLimits {
    double getMaxUpRegulatedExpressionLevel();

    double getMinUpRegulatedExpressionLevel();

    double getMaxDownRegulatedExpressionLevel();

    double getMinDownRegulatedExpressionLevel();
}
