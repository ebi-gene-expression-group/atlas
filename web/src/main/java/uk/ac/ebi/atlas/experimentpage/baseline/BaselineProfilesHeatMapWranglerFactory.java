package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ProfilesHeatMapSource;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

public class BaselineProfilesHeatMapWranglerFactory {

    private final BaselineProfilesHeatMap baselineProfilesHeatMap;

    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;
    private final SolrQueryService solrQueryService;
    private final CoexpressedGenesDao coexpressedGenesDao;

    public BaselineProfilesHeatMapWranglerFactory(RankBaselineProfilesFactory rankProfilesFactory, BaselineProfileInputStreamFactory inputStreamFactory
            , BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder, SolrQueryService solrQueryService, CoexpressedGenesDao coexpressedGenesDao) {
        this.baselineProfilesHeatMap = new BaselineProfilesHeatMap(rankProfilesFactory,inputStreamFactory);

        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.solrQueryService = solrQueryService;
        this.coexpressedGenesDao=coexpressedGenesDao;
    }

    public BaselineProfilesHeatMapWrangler create(BaselineRequestPreferences preferences, BaselineExperiment
            experiment) {
        return new BaselineProfilesHeatMapWrangler(baselineProfilesHeatMap,baselineProfilesViewModelBuilder,
                solrQueryService,
                coexpressedGenesDao,
                preferences, experiment);
    }
}
