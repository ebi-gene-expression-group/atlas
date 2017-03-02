package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamFactory;
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
                                                JdbcTemplate jdbcTemplate, SolrQueryService solrQueryService,
                                                AtlasResourceHub atlasResourceHub, HeatmapDataToJsonService heatmapDataToJsonService) {
        this.tracksUtil = tracksUtil;
        this.applicationProperties = applicationProperties;
        this.coexpressedGenesService = new CoexpressedGenesService(new CoexpressedGenesDao(jdbcTemplate));
        this.solrQueryService = solrQueryService;
        this.atlasResourceHub = atlasResourceHub;
        this.heatmapDataToJsonService = heatmapDataToJsonService;
    }

    public BaselineExperimentPageService create(BaselineProfileStreamFactory inputStreamFactory){
        return new BaselineExperimentPageService(new BaselineProfilesHeatMapWranglerFactory(
                inputStreamFactory, solrQueryService, coexpressedGenesService),
                applicationProperties,atlasResourceHub,
                tracksUtil, heatmapDataToJsonService
        );
    }
}
