package uk.ac.ebi.atlas.experimentpage.baseline;

import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.ProfileStreamFactory;
import uk.ac.ebi.atlas.resource.AtlasResourceHub;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.utils.HeatmapDataToJsonService;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineExperimentPageServiceFactory {

    private final TracksUtil tracksUtil;
    private final ApplicationProperties applicationProperties;
    private final CoexpressedGenesService coexpressedGenesService;
    private final SolrQueryService solrQueryService;
    private final AtlasResourceHub atlasResourceHub;
    private final HeatmapDataToJsonService heatmapDataToJsonService;

    @Inject
    public BaselineExperimentPageServiceFactory(TracksUtil tracksUtil, ApplicationProperties applicationProperties,
                                                CoexpressedGenesService coexpressedGenesService, SolrQueryService solrQueryService,
                                                AtlasResourceHub atlasResourceHub, HeatmapDataToJsonService heatmapDataToJsonService) {
        this.tracksUtil = tracksUtil;
        this.applicationProperties = applicationProperties;
        this.coexpressedGenesService = coexpressedGenesService;
        this.solrQueryService = solrQueryService;
        this.atlasResourceHub = atlasResourceHub;
        this.heatmapDataToJsonService = heatmapDataToJsonService;
    }

    public BaselineExperimentPageService create(ProfileStreamFactory<AssayGroup, BaselineExpression,
                BaselineExperiment, ? extends BaselineProfileStreamOptions<?>,BaselineProfile> inputStreamFactory){
        return new BaselineExperimentPageService(new BaselineProfilesHeatmapsWranglerFactory(
                inputStreamFactory, solrQueryService, coexpressedGenesService),
                applicationProperties,
                tracksUtil, heatmapDataToJsonService
        );
    }
}
