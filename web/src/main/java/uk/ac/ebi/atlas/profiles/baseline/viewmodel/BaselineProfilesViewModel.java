package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import uk.ac.ebi.atlas.utils.NumberUtils;

public class BaselineProfilesViewModel {

    private final double minExpressionLevel;
    private final double maxExpressionLevel;
    private final int searchResultTotal;
    private final BaselineProfileRowViewModel[] rows;

    public BaselineProfilesViewModel(NumberUtils numberUtils, double minExpressionLevel, double maxExpressionLevel, int searchResultTotal, BaselineProfileRowViewModel[] rows) {
        this.minExpressionLevel = numberUtils.round(minExpressionLevel);
        this.maxExpressionLevel = numberUtils.round(maxExpressionLevel);
        this.searchResultTotal = searchResultTotal;
        this.rows = rows;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public int getSearchResultTotal() {
        return searchResultTotal;
    }

    public BaselineProfileRowViewModel[] getRows() {
        return rows;
    }
}
