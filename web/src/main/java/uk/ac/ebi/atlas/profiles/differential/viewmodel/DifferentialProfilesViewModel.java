package uk.ac.ebi.atlas.profiles.differential.viewmodel;

import org.apache.commons.math.util.MathUtils;

public class DifferentialProfilesViewModel {

    private final double minUpLevel;
    private final double maxUpLevel;
    private final int totalGeneCount;
    private final DifferentialGeneViewModel[] genes;
    private final double minDownLevel;
    private final double maxDownLevel;

    public DifferentialProfilesViewModel(double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel, int totalGeneCount, DifferentialGeneViewModel[] genes) {
        this.minUpLevel = MathUtils.round(minUpLevel, 2);
        this.maxUpLevel = MathUtils.round(maxUpLevel, 2);
        this.minDownLevel = MathUtils.round(minDownLevel, 2);
        this.maxDownLevel = MathUtils.round(maxDownLevel, 2);
        this.totalGeneCount = totalGeneCount;
        this.genes = genes;
    }

    public double getMinUpLevel() {
        return minUpLevel;
    }

    public double getMaxUpLevel() {
        return maxUpLevel;
    }

    public double getMinDownLevel() {
        return minDownLevel;
    }

    public double getMaxDownLevel() {
        return maxDownLevel;
    }

    public int getTotalGeneCount() {
        return totalGeneCount;
    }

    public DifferentialGeneViewModel[] getGenes() {
        return genes;
    }
}
