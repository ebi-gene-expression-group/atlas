package uk.ac.ebi.atlas.commands;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RankMicroarrayProfilesCommandIT {

    public static final String E_GEOD_8122 = "E-GEOD-8122";
    private static final String E_GEOD_43049 = "E-GEOD-43049";

    @Inject
    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    private RankMicroarrayProfilesCommand subject;

    @Inject
    MicroarrayRequestContextBuilder baselineRequestContextBuilder;

    private MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

    private void populateRequestContext(String experimentAccession) {
        MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

        baselineRequestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-8122
    @Test
    public void eGeod8122() throws GenesNotFoundException {
        populateRequestContext(E_GEOD_8122);
        DifferentialProfilesList profiles = subject.execute(E_GEOD_8122);

        assertThat(profiles.getTotalResultCount(), is(1));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("VMA16"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049
    @Test
    public void eGeod43049() throws GenesNotFoundException {
        populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.execute(E_GEOD_43049);

        //System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(23));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "DEPDC7", "GNA12", "TMEM154", "CKAP2", "S100A14", "AC016629.3", "TMEM70", "PSMA7", "NFX1", "DPP8", "SS18L2", "SSR1", "MTMR3", "RPL22P19", "C19orf44"));

    }

    // http://localhost:8080/gxa/experiments/E-GEOD-8122?geneQuery=YHR026W
    @Test
    public void eGeod8122_GeneQuery_For_Gene_Not_In_Experiment_Species() throws GenesNotFoundException {
        setGeneQuery("YHR026W");
        eGeod8122();
    }


    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setGeneSet() {
        requestPreferences.setGeneSetMatch(true);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }


}
