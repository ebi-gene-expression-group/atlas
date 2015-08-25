package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Joiner;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroarrayProfilesHeatMapIT {

    public static final String E_MEXP_3628 = "E-MEXP-3628";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_MTAB_1066 = "E-MTAB-1066";

    @Inject
    private MicroarrayExperimentsCache experimentsCache;

    @Inject
    private MicroarrayProfilesHeatMap subject;

    @Inject
    MicroarrayRequestContextBuilder requestContextBuilder;

    private MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

    private MicroarrayRequestContext populateRequestContext(String experimentAccession) throws GenesNotFoundException {
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return requestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();
    }


    // http://localhost:8080/gxa/experiments/E-MTAB-1066
    @Test
    public void defaultParameters() throws GenesNotFoundException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        MicroarrayExperiment experiment = requestContext.getExperiment();
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        MicroarrayProfile profile1 = (MicroarrayProfile) profiles.get(0);
        MicroarrayExpression expression1 = profile1.getExpression(experiment.getContrast("g2_g1"));

        assertThat(profile1.getName(), is("CG31624"));
        assertThat(profile1.getDesignElementName(), is("1630811_at"));
        assertThat(expression1.getPValue(), is(3.23957214317996E-4));
        assertThat(expression1.getFoldChange(), is(-3.36862716666667));
        assertThat(expression1.getTstatistic(), is(-8.79117189098254));

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(54));
        assertThat(profiles.extractGeneNames(), (Matcher) contains("CG31624", "CG4669", "Mst84Db", "CG14540", "CG12689", "CG5043", "Atg8b", "CG9254", "CG30393", "CG7742", "Hsp60B", "CG5213", "dj", "CG18472", "CG15286", "CG13876", "CG31803", "CG6870", "Cda5", "CG9040", "mthl2", "Jupiter", "CG33459", "CG14605", "CG32686", "CG9589", "CG11147", "cher", "CG15615", "CG31606", "CG1103", "CG31139", "yip7", "skpB", "CG2150", "CG14265", "CG9981", "CG30272", "CG31463", "CG32444", "Ork1", "pgant8", "CG17974", "CG10165", "CG1368", "CG1443", "Cpr12A", "CG14694", "CG6403", "CG15213"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066&_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException {
        setNotSpecific();
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(54));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("CG14265", "CG9981", "CG30272", "CG31463", "CG32444", "Ork1", "pgant8", "CG17974", "CG10165", "CG1368", "CG1443", "CG31624", "Cpr12A", "CG14694", "CG6403", "CG15213", "CG4669", "Mst84Db", "CG42329", "CG3376", "CG14540", "CG15043", "sba", "CG12689", "CG5043", "Atg8b", "CG9254", "CG30393", "CG7742", "Hsp60B", "CG5213", "dj", "CG18472", "CG15286", "CG13876", "CG31803", "CG6870", "Cda5", "CG9040", "mthl2", "Jupiter", "CG33459", "CG14605", "CG32686", "CG9589", "CG11147", "cher", "CG15615", "CG31606", "CG1103"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException {
        setGeneQuery("protein_coding");
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(2));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D", "SPIRE1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?regulation=UP&foldChangeCutOff=0
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        requestPreferences.setRegulation(Regulation.UP);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(9));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D", "SPIRE1", "S100A14", "MTMR3", "DEPDC7", "NFX1", "CKAP2", "RPL22P19", "AC016629.3"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?regulation=DOWN&foldChangeCutOff=0
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        requestPreferences.setRegulation(Regulation.DOWN);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(14));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("NICN1", "DGCR2", "DCAF8", "KCTD15", "C3orf18", "TMEM70", "TMEM154", "PSMA7", "SS18L2", "SSR1", "COG2", "GNA12", "C19orf44", "DPP8"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException {
        requestPreferences.setCutoff(0.00014);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.getTotalResultCount(), is(1));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066?queryFactorValues=g2_g3&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        setNotSpecific();
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(21));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("CG14265", "CG9981", "CG30272", "CG32444", "CG31463", "CG17974", "Ork1", "pgant8", "CG10165", "CG1443", "CG6403", "CG15213", "CG42329", "CG1368", "CG3376", "CG33459", "Cpr12A", "sba", "CG15043", "CG14694", "CG2150"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066?queryFactorValues=g2_g3
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(9));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("CG33459", "CG2150", "CG17974", "CG30272", "CG9981", "CG32444", "CG42329", "CG6403", "Ork1"));
    }


    // http://localhost:8080/gxa/experiments/E-MEXP-3628?foldChangeCutOff=0
    @Test
    public void eMexp3628() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_MEXP_3628);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.getTotalResultCount(), is(7));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("FGR", "TSPAN6", "UTY", "C1orf112", "NFYA", "TSPAN6", "GCLC"));
    }

    private void setFoldChangeCutOff(double cuttOff) {
        requestPreferences.setFoldChangeCutOff(cuttOff);
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(GeneQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
