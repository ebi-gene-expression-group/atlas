package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import uk.ac.ebi.atlas.utils.NumberUtils;

public class BaselineProfilesViewModel {

    private final double minExpressionLevel;
    private final double maxExpressionLevel;
    private final int totalGeneCount;
    private final BaselineGeneViewModel[] genes;

    public BaselineProfilesViewModel(NumberUtils numberUtils, double minExpressionLevel, double maxExpressionLevel, int totalGeneCount, BaselineGeneViewModel[] genes) {
        this.minExpressionLevel = numberUtils.round(minExpressionLevel);
        this.maxExpressionLevel = numberUtils.round(maxExpressionLevel);
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
