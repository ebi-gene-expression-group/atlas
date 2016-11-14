package uk.ac.ebi.atlas.search;

import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.is;

public class SearchTestUtil {

    public static BaselineBioEntitiesSearchResult selectResult(List<BaselineBioEntitiesSearchResult> baselineCounts, String experimentAccession) {
        return selectUnique(baselineCounts, having(on(BaselineBioEntitiesSearchResult.class).getExperimentAccession(), is(experimentAccession)));
    }

    public static BaselineBioEntitiesSearchResult selectFirstResult(List<BaselineBioEntitiesSearchResult> baselineCounts, String experimentAccession) {
        return selectFirst(baselineCounts, having(on(BaselineBioEntitiesSearchResult.class).getExperimentAccession(), is(experimentAccession)));
    }

    public static boolean hasResult(List<BaselineBioEntitiesSearchResult> baselineCounts, String experimentAccession) {
        return selectFirst(baselineCounts, having(on(BaselineBioEntitiesSearchResult.class).getExperimentAccession(), is(experimentAccession))) != null;
    }

}
