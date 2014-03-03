package uk.ac.ebi.atlas.commands;

import com.google.common.base.Joiner;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.commands.MicroarrayProfilesHeatMap;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
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

    public static final String E_GEOD_8122 = "E-GEOD-8122";
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

        assertThat(profile1.getName(), is("CG13876"));
        assertThat(profile1.getDesignElementName(), is("1638975_at"));
        assertThat(expression1.getLevel(), is(8.35994190521307E-6));
        assertThat(expression1.getFoldChange(), is(-1.55682156666667));
        assertThat(expression1.getTstatistic(), is(-19.1558475545564));

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(174));
        assertThat(profiles.extractGeneNames(), (Matcher) contains("CG13876", "CG7742", "CG31803", "CG30393", "CG15403", "Irc", "CG12943", "CG31139", "CG8303", "CG13531", "Ykt6", "MED8", "CG17843", "Mbs", "ds", "CG3332", "CG30380", "CG9586", "Mbs", "CG14006", "Ady43A", "CG17669", "wuc", "CG6490", "HP1c", "CG8003", "CG30263", "CG16865", "CG8298", "CanA1", "CG7550", "CG42788", "CG9072", "CSN5", "sls", "Cks30A", "Pk1r", "WASp", "CG13965", "MCPH1", "CG14269", "CG11790", "Tdc2", "Muc68Ca", "Pkcdelta", "Mst36Fa", "bru-3", "CG13398", "CG6361", "CG3967"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066&_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException {
        setNotSpecific();
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.getTotalResultCount(), is(174));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("CG10165", "CG14265", "pgant8", "CG30272", "CG32444", "CG3376", "CG6403", "CG14694", "Cpr12A", "yip7", "dj", "Ork1", "RfC3", "Peritrophin-15b", "CG1443", "CG32686", "Ten-a", "skpB", "Jupiter", "cher", "CG9981", "CG15213", "CG3704", "Ubp64E", "CG1103", "Prosbeta5", "CG33459", "Marf", "CG31624", "CG4669", "CG9589", "mthl2", "CG5213", "Mst84Db", "CG42813", "CG1368", "CG14540", "CG15615", "Atg8b", "sba", "CG15628", "CG9254", "CG11147", "CG10435", "lin19", "CG17974", "Got1", "CG5043", "CG18472", "Pros35"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException {
        setGeneQuery("protein_coding");
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(21));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "DEPDC7", "GNA12", "TMEM154", "CKAP2", "S100A14", "TMEM70", "PSMA7", "NFX1", "DPP8", "SS18L2", "SSR1", "MTMR3", "C19orf44"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?regulation=UP
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException {
        requestPreferences.setRegulation(Regulation.UP);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        //System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(9));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D", "SPIRE1", "DEPDC7", "CKAP2", "S100A14", "AC016629.3", "NFX1", "MTMR3", "RPL22P19"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?regulation=DOWN
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException {
        requestPreferences.setRegulation(Regulation.DOWN);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        //System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(14));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "GNA12", "TMEM154", "TMEM70", "PSMA7", "DPP8", "SS18L2", "SSR1", "C19orf44"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException {
        requestPreferences.setCutoff(0.002);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.getTotalResultCount(), is(8));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066?queryFactorValues=g2_g1&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g1"));
        setNotSpecific();
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.getTotalResultCount(), is(154));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("CG10165", "CG14265", "CG14694", "dj", "pgant8", "CG13876", "CG7742", "CG5043", "CG31803", "CG9589", "Cpr12A", "CG30272", "mthl2", "CG11147", "CG15615", "CG32686", "CG9254", "CG30393", "CG1443", "Atg8b", "CG15286", "CG6403", "CG4669", "CG3376", "Hsp60B", "yip7", "CG12689", "CG11703", "CG32444", "Ork1", "cher", "skpB", "CG1103", "Jupiter", "CG31606", "Peritrophin-15b", "RfC3", "CG3704", "CG5213", "CG15403", "Ubp64E", "Marf", "Ten-a", "CG31624", "Dic3", "CG9040", "olf186-M", "Irc", "CG12943", "CG18472"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066?queryFactorValues=g2_g3
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(40));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("Ykt6", "Mbs", "CG3332", "CG9586", "Ady43A", "CG8003", "CG42788", "sls", "Tdc2", "Muc68Ca", "bru-3", "CG13398", "CG6361", "Cpr92F", "CG9782", "Kr", "svp", "CG33523", "ACXD", "mRpL21", "pUf68", "CG15628", "CG33459", "CG32444", "Mst84Db", "Paip2", "CG2150", "CG17974", "CG10663", "Tequila", "CG42813", "CG8184", "CG9981", "Got1", "heph", "CG10435", "RfC3", "ftz", "CG42329", "PMCA"));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-8122
    @Test
    public void eGeod8122() throws GenesNotFoundException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_8122);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        assertThat(profiles.getTotalResultCount(), is(1));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("VMA16"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-8122?geneQuery=YHR026W
    @Test
    public void eGeod8122_GeneQueryForGeneNotInExperimentSpecies() throws GenesNotFoundException {
        setGeneQuery("YHR026W");
        eGeod8122();
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
