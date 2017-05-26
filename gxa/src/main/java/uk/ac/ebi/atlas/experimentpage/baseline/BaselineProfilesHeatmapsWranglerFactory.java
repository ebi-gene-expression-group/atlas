package uk.ac.ebi.atlas.experimentpage.baseline;


import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.profiles.stream.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

public class BaselineProfilesHeatmapsWranglerFactory {

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final SolrQueryService solrQueryService;
    private final CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesHeatmapsWranglerFactory(BaselineProfileStreamFactory inputStreamFactory
            , SolrQueryService solrQueryService, CoexpressedGenesService coexpressedGenesService) {
        this.baselineProfilesHeatMap = new BaselineProfilesHeatMap(inputStreamFactory);
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
    }

    public BaselineProfilesHeatmapsWrangler create(BaselineRequestPreferences
                                                          preferences, BaselineExperiment experiment) {
        return new BaselineProfilesHeatmapsWrangler(
                baselineProfilesHeatMap, solrQueryService, coexpressedGenesService,
                preferences,
                experiment);
    }
}
