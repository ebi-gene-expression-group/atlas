package uk.ac.ebi.atlas.model.differential;

public class DifferentialExpressionLimits implements DifferentialExpressionLimitsInterface {
    private double maxUpRegulatedExpressionLevel;
    private double minUpRegulatedExpressionLevel;
    private double maxDownRegulatedExpressionLevel;
    private double minDownRegulatedExpressionLevel;

    public DifferentialExpressionLimits(double maxUpRegulatedExpressionLevel, double minUpRegulatedExpressionLevel, double maxDownRegulatedExpressionLevel, double minDownRegulatedExpressionLevel) {

        this.maxUpRegulatedExpressionLevel = maxUpRegulatedExpressionLevel;
        this.minUpRegulatedExpressionLevel = minUpRegulatedExpressionLevel;
        this.maxDownRegulatedExpressionLevel = maxDownRegulatedExpressionLevel;
        this.minDownRegulatedExpressionLevel = minDownRegulatedExpressionLevel;
    }

    public double getMaxUpRegulatedExpressionLevel() {
        return maxUpRegulatedExpressionLevel;
    }

    public double getMinUpRegulatedExpressionLevel() {
        return minUpRegulatedExpressionLevel;
    }

    public double getMaxDownRegulatedExpressionLevel() {
        return maxDownRegulatedExpressionLevel;
    }

    public double getMinDownRegulatedExpressionLevel() {
        return minDownRegulatedExpressionLevel;
    }
}
