package uk.ac.ebi.atlas.commands;

import com.google.common.base.Joiner;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.commands.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesHeatMapIT {

    private static final String E_GEOD_38400 = "E-GEOD-38400";
    private static final String E_GEOD_21860 = "E-GEOD-21860";

    @Inject
    private RnaSeqDiffExperimentsCache experimentsCache;

    @Inject
    private RnaSeqProfilesHeatMap subject;

    @Inject
    RnaSeqRequestContextBuilder requestContextBuilder;

    private DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) throws GenesNotFoundException {
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return requestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400
    @Test
    public void defaultParameters() throws GenesNotFoundException {
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialExperiment experiment = requestContext.getExperiment();
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        RnaSeqProfile profile1 = (RnaSeqProfile) profiles.get(0);
        DifferentialExpression g1_g2Expression = profile1.getExpression(experiment.getContrast("g1_g2"));

        assertThat(profile1.getName(), is("AT1G33840"));
        assertThat(g1_g2Expression.getLevel(), is(1.20805302393608E-16));
        assertThat(g1_g2Expression.getFoldChange(), is(5.6341090817188));

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(51));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT1G33840", "F14M2.2", "T5N23_130", "AT5G35207", "GRXS4", "RSU1", "AT5G62920", "AT4G04223", "AT3G48131", "NRPD1B", "AT2G05200", "GRXS5", "AT5G35935", "T5N23_90", "ATPF", "AT5G40450", "AT3G59765", "AT2G07733", "AT5G11090", "AT5G28300", "F13F21.6", "AT5G35205", "AT5G52070", "AT5G24240", "ATMG00410", "ATMG00510", "ANAC003", "GRXS10", "ATCG00490", "AT2G16310", "AT4G04293", "AT1G11785", "AT5G27850", "AT1G62310", "AT5G24150", "MYR1", "F21F23.9", "PSBE", "SEN1", "AT5G27845", "AT1G12730", "ATMRU1", "AT2G35382", "GRXS13", "AT5G26220", "AT5G61190", "GRXS3", "GRXS8", "ATMSRB6", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException {
        setNotSpecific();
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(51));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("DML1", "AT3G29644", "AT1G33840", "F14M2.2", "T5N23_130", "AT5G35207", "GRXS4", "RSU1", "AT5G62920", "AT4G04223", "AT3G48131", "NRPD1B", "AT2G05200", "GRXS5", "AT5G35935", "T5N23_90",  "ATPF", "AT5G40450", "AT3G59765", "AT2G07733", "AT5G11090", "AT5G28300", "F13F21.6", "AT5G35205", "AT5G52070", "AT5G24240", "ATMG00410", "ATMG00510", "ANAC003", "GRXS10", "ATCG00490", "AT2G16310", "AT4G04293", "AT1G11785", "AT5G27850", "AT1G62310", "MYR1", "AT5G24150", "F21F23.9", "SEN1", "PSBE", "AT5G27845", "AT1G12730", "ATMRU1", "AT2G35382", "GRXS13", "AT5G26220", "AT5G61190", "GRXS3", "ATMSRB6"));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-21860?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException {
        setGeneQuery("protein_coding");
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_21860);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(148));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("Cldn8", "Lactbl1", "Tph1", "Ivd", "Fmo1", "Matn2", "Chgb", "Cish", "Lrrc55", "Neb", "Ogdhl", "Ehhadh", "Wipi1", "Rgs2", "Tmem255a", "Gpr26", "Reg3b", "Vip", "Prlr", "Dnahc8", "Hsbp1", "Tnfrsf11b", "Npas4", "Dnajb1", "Enpp2", "Sftpd", "Reg3a", "Disp2", "Igfals", "B3galnt1", "Ikzf4", "Nr4a1", "Cspg5", "Dnaja1", "Ern1", "Dhcr7", "Junb", "Aqp4", "Ovol2", "Hspa1a", "Igfbp5", "Lonrf3", "Nupr1", "Dusp1", "Gabbr2", "Rab3d", "Znrf2", "Hsph1", "Socs2", "Rnf182"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?regulation=UP
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException {
        requestPreferences.setRegulation(Regulation.UP);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(36));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT1G33840", "F14M2.2", "T5N23_130", "AT5G35207", "GRXS4", "RSU1", "AT5G62920", "AT4G04223", "AT3G48131", "AT2G05200", "GRXS5", "AT5G35935", "T5N23_90", "AT3G59765", "AT5G11090", "F13F21.6", "AT5G35205", "AT5G52070", "AT5G24240", "ANAC003", "GRXS10", "AT4G04293", "AT2G16310", "AT1G11785", "AT5G27850", "AT5G24150", "F21F23.9", "AT5G27845", "ATMRU1", "AT2G35382", "GRXS13", "AT5G26220", "GRXS3", "ATMSRB6", "GRXS8",  "AT3G29644"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?regulation=DOWN
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException {
        requestPreferences.setRegulation(Regulation.DOWN);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(15));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("NRPD1B", "AT2G07733", "AT5G40450", "ATPF", "AT5G28300", "ATMG00410", "ATMG00510", "ATCG00490", "AT1G62310", "MYR1", "PSBE", "SEN1", "AT1G12730", "AT5G61190", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException {
        requestPreferences.setCutoff(0.002);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(27));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT1G33840", "F14M2.2", "T5N23_130", "AT3G29644", "AT5G35207", "GRXS4", "RSU1", "AT5G62920", "AT4G04223", "NRPD1B", "AT3G48131", "AT2G05200", "GRXS5", "AT5G35935", "T5N23_90", "AT2G07733", "AT5G40450", "AT3G59765", "ATPF", "AT5G11090", "AT5G28300", "F13F21.6", "AT5G35205", "AT5G52070", "AT5G24240", "ATMG00410", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?queryFactorValues=g1_g2&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g1_g2"));
        setNotSpecific();
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(19));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT1G33840", "F14M2.2", "AT5G35207", "RSU1", "NRPD1B", "AT3G48131", "AT2G05200", "DML1", "AT5G35205", "AT5G52070", "AT5G24240", "ANAC003", "AT2G16310", "AT4G04293", "AT5G27850", "F21F23.9", "AT3G29644", "AT5G27845", "AT1G12730"));
    }

    //  http://localhost:8080/gxa/experiments/E-GEOD-38400?queryFactorValues=g1_g2
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g1_g2"));
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(17));
        assertThat(profiles.extractGeneNames(), (Matcher) contains("AT1G33840", "F14M2.2", "AT5G35207", "RSU1", "NRPD1B", "AT3G48131", "AT2G05200", "AT5G35205", "AT5G52070", "AT5G24240", "ANAC003", "AT2G16310", "AT4G04293", "AT5G27850", "F21F23.9", "AT5G27845", "AT1G12730"));
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
