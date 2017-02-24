package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;

public class BaselineProfilesHeatMapWranglerFactory {

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;
    private final SolrQueryService solrQueryService;
    private final CoexpressedGenesService coexpressedGenesService;

    public BaselineProfilesHeatMapWranglerFactory(RankBaselineProfilesFactory rankProfilesFactory, BaselineProfileStreamFactory inputStreamFactory
            , BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder, SolrQueryService solrQueryService, CoexpressedGenesService coexpressedGenesService) {
        this.baselineProfilesHeatMap = new BaselineProfilesHeatMap(inputStreamFactory);

        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesService = coexpressedGenesService;
    }

    public BaselineProfilesHeatMapWrangler create(BaselineRequestPreferences preferences, BaselineExperiment experiment) {
        return new BaselineProfilesHeatMapWrangler(
                baselineProfilesHeatMap,
                baselineProfilesViewModelBuilder,
                solrQueryService,
                coexpressedGenesService,
                preferences,
                experiment);
    }
}
