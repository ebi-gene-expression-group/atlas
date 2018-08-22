
package uk.ac.ebi.atlas.search.diffanalytics;

import java.util.ArrayList;
import java.util.List;

public class DiffAnalyticsList extends ArrayList<DiffAnalytics> {

    private int totalNumberOfResults;

    public DiffAnalyticsList() {
    }

    public DiffAnalyticsList(List<DiffAnalytics> diffAnalyticses, int totalNumberOfResults) {
        super(diffAnalyticses);
        this.totalNumberOfResults = totalNumberOfResults;
    }

    public int getTotalNumberOfResults() {
        return totalNumberOfResults;
    }
}


