package uk.ac.ebi.atlas.experimentpage.baseline;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ebi.atlas.dao.OrganismEnsemblDAO;
import uk.ac.ebi.atlas.dao.OrganismKingdomDAO;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.download.BaselineExperimentUtil;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.tracks.TracksUtil;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;
import uk.ac.ebi.atlas.web.ApplicationProperties;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BaselineExperimentPageServiceFactory {

    private final TracksUtil tracksUtil;
    private final ApplicationProperties applicationProperties;
    private final BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;
    private final SpeciesKingdomTrader speciesKingdomTrader;
    private final BaselineExperimentUtil bslnUtil;
    private final PreferencesForBaselineExperiments preferencesForBaselineExperiments;
    private final CoexpressedGenesDao coexpressedGenesDao;
    private final SolrQueryService solrQueryService;
    private final RankBaselineProfilesFactory rankProfilesFactory;

    @Inject
    public BaselineExperimentPageServiceFactory(TracksUtil tracksUtil, ApplicationProperties applicationProperties,
                                                BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder,
                                                JdbcTemplate jdbcTemplate,BaselineExperimentUtil bslnUtil,
                                                PreferencesForBaselineExperiments
                                                        preferencesForBaselineExperiments,
                                                SolrQueryService solrQueryService,RankBaselineProfilesFactory rankProfilesFactory) {
        this.tracksUtil = tracksUtil;
        this.applicationProperties = applicationProperties;
        this.baselineProfilesViewModelBuilder = baselineProfilesViewModelBuilder;
        this.speciesKingdomTrader = new SpeciesKingdomTrader(new OrganismKingdomDAO(jdbcTemplate), new
                OrganismEnsemblDAO(jdbcTemplate));
        this.bslnUtil = bslnUtil;
        this.preferencesForBaselineExperiments = preferencesForBaselineExperiments;
        this.coexpressedGenesDao = new CoexpressedGenesDao(jdbcTemplate);
        this.solrQueryService = solrQueryService;
        this.rankProfilesFactory = rankProfilesFactory;

    }

    public BaselineExperimentPageService create(BaselineProfileInputStreamFactory inputStreamFactory){
        return new BaselineExperimentPageService(new BaselineProfilesHeatMap(rankProfilesFactory, inputStreamFactory),
                applicationProperties,
                baselineProfilesViewModelBuilder, speciesKingdomTrader, tracksUtil, bslnUtil,
                preferencesForBaselineExperiments, coexpressedGenesDao, solrQueryService);
    }
}
