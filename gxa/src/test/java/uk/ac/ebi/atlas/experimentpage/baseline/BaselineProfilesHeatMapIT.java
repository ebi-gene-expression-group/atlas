package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
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
import uk.ac.ebi.atlas.search.SemanticQuery;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class BaselineProfilesHeatMapIT {

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
        baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment(experimentAccessions.get(randomIndex));

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
        if(Math.random() < 0.5) {
            setGeneQueryProteinCoding();
        }

        GeneQueryResponse geneQueryResponse = solrQueryService.fetchResponse
                (baselineRequestContext.getGeneQuery(), baselineRequestContext.getSpecies());

        BaselineProfilesList profiles = subject.fetch(baselineExperiment, baselineRequestContext, geneQueryResponse);

        assertThat(profiles.size(), greaterThan(0));
    }

    private void setGeneQueryProteinCoding() {
        requestPreferences.setGeneQuery(SemanticQuery.create("protein_coding"));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
