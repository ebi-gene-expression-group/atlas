package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.stream.ProteomicsBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.stream.RnaSeqBaselineProfileStreamFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class BaselineProfilesHeatMapIT {

    static final ImmutableSet<String> EXPERIMENTS_ACCESSIONS_WITH_NO_PROTEIN_CODING_RESULTS =
            ImmutableSet.of("E-MTAB-2037", "E-MTAB-3028");

    BaselineProfilesHeatMap<BaselineProfileStreamOptions<? extends ExpressionUnit.Absolute>> subject;

    @Inject
    SolrQueryService solrQueryService;

    @Inject
    RnaSeqBaselineProfileStreamFactory rnaSeqBaselineProfileStreamFactory;

    @Inject
    ProteomicsBaselineProfileStreamFactory proteomicsBaselineProfileStreamFactory;

    BaselineRequestPreferences<? extends ExpressionUnit.Absolute> requestPreferences;

    BaselineRequestContext<? extends ExpressionUnit.Absolute> baselineRequestContext;

    BaselineExperiment baselineExperiment;

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Before
    public void initRequestContext() throws ExecutionException {
        ImmutableList<String> experimentAccessions = ImmutableList.copyOf(experimentTrader.getAllBaselineExperimentAccessions());
        int randomIndex = ThreadLocalRandom.current().nextInt(0, experimentAccessions.size());
//        baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccessions.get(randomIndex));
        baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-MTAB-2037");

        if (baselineExperiment.getType().isRnaSeqBaseline()) {
            requestPreferences = new RnaSeqBaselineRequestPreferences();
            baselineRequestContext = new BaselineRequestContext<>(requestPreferences, baselineExperiment);
            subject = new BaselineProfilesHeatMap(rnaSeqBaselineProfileStreamFactory);
        } else { //if (baselineExperiment.getType().isProteomicsBaseline()) {
            requestPreferences = new ProteomicsBaselineRequestPreferences();
            baselineRequestContext = new BaselineRequestContext<>(requestPreferences, baselineExperiment);
            subject = new BaselineProfilesHeatMap(proteomicsBaselineProfileStreamFactory);
        }
    }

    @Test
    public void weCanGetAnyExperimentAtAll()  {
        if(Math.random() < 0.5) {
            setNotSpecific();
        }
        if (EXPERIMENTS_ACCESSIONS_WITH_NO_PROTEIN_CODING_RESULTS.contains(baselineExperiment.getAccession()) ||
            Math.random() < 0.5) {
            removeDefaultGeneQuery();
        }

        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse
                (baselineRequestContext.getGeneQuery(), baselineRequestContext.getSpecies());

        BaselineProfilesList profiles = subject.fetch(baselineExperiment, baselineRequestContext, geneQueryResponse);

        assertThat(profiles.size(), greaterThan(0));
    }

    private void removeDefaultGeneQuery() {
        requestPreferences.setGeneQuery(null);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
