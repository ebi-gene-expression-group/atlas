package uk.ac.ebi.atlas.search.analyticsindex.differential;

import autovalue.shaded.com.google.common.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsFacetsReader;
import uk.ac.ebi.atlas.search.analyticsindex.baseline.BaselineAnalyticsSearchDao;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResultProducer;
import uk.ac.ebi.atlas.search.baseline.RnaSeqBslnExpression;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class DifferentialAnalyticsSearchService {

    private final DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao;
    private final DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader;

    @Inject
    public DifferentialAnalyticsSearchService(DifferentialAnalyticsSearchDao differentialAnalyticsSearchDao, DifferentialAnalyticsFacetsReader differentialAnalyticsFacetsReader) {
        this.differentialAnalyticsSearchDao = differentialAnalyticsSearchDao;
        this.differentialAnalyticsFacetsReader = differentialAnalyticsFacetsReader;
    }

    String fetchDifferentialGeneQueryFacetsAsJson(GeneQuery geneQuery, Gson gson) {
        Multimap<String, NameValue> facets = differentialAnalyticsSearchDao.fetchFacets(geneQuery);
        return gson.toJson(facets.asMap());
    }

    String fecthDifferentialGeneQueryResultsAsJson(GeneQuery geneQuery, Gson gson) {
        String differentialResults = differentialAnalyticsSearchDao.fetchDifferentialGeneQueryResults(geneQuery);
        return gson.toJson(differentialAnalyticsFacetsReader.extractResultsAsJson(differentialResults));
    }

}
