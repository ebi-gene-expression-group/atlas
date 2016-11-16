package uk.ac.ebi.atlas.experimentpage.baseline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesHeatMapIT {

    public static final String ORGANISM_PART = "ORGANISM_PART";

    private BaselineProfilesHeatMap subject;

    @Inject
    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCache;

    @Inject
    SolrQueryService solrQueryService;

    @Inject
    RankBaselineProfilesFactory rankProfilesFactory;

    @Resource(name="baselineProfileInputStreamFactory")
    BaselineProfileInputStreamFactory inputStreamFactory;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    private BaselineRequestContext baselineRequestContext;

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Before
    public void initRequestContext() throws ExecutionException {

        String randomAccession = experimentTrader.getAllBaselineExperimentAccessions().iterator().next();
        BaselineExperiment baselineExperiment = rnaSeqBaselineExperimentsCache.getExperiment(randomAccession);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        baselineRequestContext = BaselineRequestContext.createFor(baselineExperiment, requestPreferences);

        subject = new BaselineProfilesHeatMap(rankProfilesFactory,inputStreamFactory);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1733?displayLevels=true&_specific=on&geneQuery=R-HSA-73887&geneSetMatch=true
    @Test
    public void weCanGetAnyExperimentAtAll()  {
        setNotSpecific();
        setGeneQuery("protein_coding");

        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse
                (baselineRequestContext.getGeneQuery(),baselineRequestContext.getFilteredBySpecies());

        BaselineProfilesList profiles = subject.fetch(baselineRequestContext,geneQueryResponse, true);

        assertThat(profiles.size(), greaterThan(0));
    }

    private void setGeneQuery(String geneQueryString) {
        requestPreferences.setGeneQuery(SemanticQuery.create(geneQueryString));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
