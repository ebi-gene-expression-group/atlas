package uk.ac.ebi.atlas.profiles.baseline.viewmodel;

public class BaselineProfilesViewModel<R> {

    private final int searchResultTotal;
    private final R[] rows;

    public BaselineProfilesViewModel(int searchResultTotal, R[] rows) {
        this.searchResultTotal = searchResultTotal;
        this.rows = rows;
    }

    public int getSearchResultTotal() {
        return searchResultTotal;
    }

    public R[] getRows() {
        return rows;
    }
}
