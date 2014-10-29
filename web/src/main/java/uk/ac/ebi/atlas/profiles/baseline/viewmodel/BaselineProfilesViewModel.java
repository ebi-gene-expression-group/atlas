package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

public class BaselineProfilesViewModel<R> {

    private final double minExpressionLevel;
    private final double maxExpressionLevel;
    private final int searchResultTotal;
    private final R[] rows;

    public BaselineProfilesViewModel(BaselineExpressionLevelRounder baselineExpressionLevelRounder, double minExpressionLevel, double maxExpressionLevel, int searchResultTotal, R[] rows) {
        this.minExpressionLevel = baselineExpressionLevelRounder.round(minExpressionLevel);
        this.maxExpressionLevel = baselineExpressionLevelRounder.round(maxExpressionLevel);
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

    public R[] getRows() {
        return rows;
    }
}
