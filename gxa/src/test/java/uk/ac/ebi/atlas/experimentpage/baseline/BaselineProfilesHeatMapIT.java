package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import uk.ac.ebi.atlas.solr.bioentities.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.ProteomicsBaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import javax.inject.Inject;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class BaselineProfilesHeatMapIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaselineProfilesHeatMapIT.class);

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

    @Ignore
    public void benchmark() throws Exception {
        Set<String> assayGroupIds =
                IntStream.rangeClosed(1, 800).boxed().map(i -> "g" + Integer.toString(i)).collect(Collectors.toSet());
        baselineExperiment = (BaselineExperiment) experimentTrader.getPublicExperiment("E-MTAB-2770");

        requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setCutoff(1.0);
        requestPreferences.setSpecific(true);
        requestPreferences.setSelectedColumnIds(assayGroupIds);
        baselineRequestContext = new BaselineRequestContext<>(requestPreferences, baselineExperiment);
        subject = new BaselineProfilesHeatMap(rnaSeqBaselineProfileStreamFactory);
        BaselineRequestContext<ExpressionUnit.Absolute.Rna> requestContext = new BaselineRequestContext(requestPreferences, baselineExperiment);

        Stopwatch stopwatch = Stopwatch.createStarted();
        subject.fetch(baselineExperiment, requestContext, new GeneQueryResponse());
        LOGGER.info("Specific search across {} assay groups finished in {} ms", assayGroupIds.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS));
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
