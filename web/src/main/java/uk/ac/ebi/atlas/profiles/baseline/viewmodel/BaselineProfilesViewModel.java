package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineProfilesViewModel {

    private final double minExpressionLevel;
    private final double maxExpressionLevel;
    private final int totalGeneCount;
    private final BaselineGeneViewModel[] genes;

    public BaselineProfilesViewModel(double minExpressionLevel, double maxExpressionLevel, int totalGeneCount, BaselineGeneViewModel[] genes) {
        this.minExpressionLevel = minExpressionLevel;
        this.maxExpressionLevel = maxExpressionLevel;
        this.totalGeneCount = totalGeneCount;
        this.genes = genes;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public int getTotalGeneCount() {
        return totalGeneCount;
    }

    public BaselineGeneViewModel[] getGenes() {
        return genes;
    }
}
