package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.servlet.http.HttpServletRequest;

public class BaselineProfilesHeatMapWranglerFactory {

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final SolrQueryService solrQueryService;
    private final CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesHeatMapWranglerFactory(BaselineProfileStreamFactory inputStreamFactory
            , SolrQueryService solrQueryService, CoexpressedGenesService coexpressedGenesService) {
        this.baselineProfilesHeatMap = new BaselineProfilesHeatMap(inputStreamFactory);
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
    }

    public BaselineProfilesHeatmapWrangler create(HttpServletRequest httpServletRequest, BaselineRequestPreferences
            preferences, BaselineExperiment experiment) {
        return new BaselineProfilesHeatmapWrangler(
                baselineProfilesHeatMap, solrQueryService, coexpressedGenesService,
                preferences,
                experiment);
    }
}
