package uk.ac.ebi.atlas.model.differential;

public interface DifferentialExpressionLimitsInterface {
    double getMaxUpRegulatedExpressionLevel();

    double getMinUpRegulatedExpressionLevel();

    double getMaxDownRegulatedExpressionLevel();

    double getMinDownRegulatedExpressionLevel();
}
