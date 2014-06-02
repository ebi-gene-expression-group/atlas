package uk.ac.ebi.atlas.profiles.differential.viewmodel;

public class DifferentialProfilesViewModel {

    private final double minUpLevel;
    private final double maxUpLevel;
    private final int totalGeneCount;
    private final DifferentialGeneViewModel[] genes;
    private final double minDownLevel;
    private final double maxDownLevel;

    public DifferentialProfilesViewModel(double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel, int totalGeneCount, DifferentialGeneViewModel[] genes) {
        this.minUpLevel = minUpLevel;
        this.maxUpLevel = maxUpLevel;
        this.minDownLevel = minDownLevel;
        this.maxDownLevel = maxDownLevel;
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
