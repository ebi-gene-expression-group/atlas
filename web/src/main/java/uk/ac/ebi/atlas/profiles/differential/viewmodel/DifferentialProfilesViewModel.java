package uk.ac.ebi.atlas.profiles.differential.viewmodel;

public class DifferentialProfilesViewModel {

    private final double minUpLevel;
    private final double maxUpLevel;
    private final int searchResultTotal;
    private final DifferentialProfileRowViewModel[] rows;
    private final double minDownLevel;
    private final double maxDownLevel;

    private transient final FoldChangeRounder foldChangeRounder = new FoldChangeRounder();

    public DifferentialProfilesViewModel(double minUpLevel, double maxUpLevel, double minDownLevel, double maxDownLevel, int searchResultTotal, DifferentialProfileRowViewModel[] rows) {
        this.minUpLevel = foldChangeRounder.round(minUpLevel);
        this.maxUpLevel = foldChangeRounder.round(maxUpLevel);
        this.minDownLevel = foldChangeRounder.round(minDownLevel);
        this.maxDownLevel = foldChangeRounder.round(maxDownLevel);
        this.searchResultTotal = searchResultTotal;
        this.rows = rows;
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

    public int getSearchResultTotal() {
        return searchResultTotal;
    }

    public DifferentialProfileRowViewModel[] getRows() {
        return rows;
    }
}
