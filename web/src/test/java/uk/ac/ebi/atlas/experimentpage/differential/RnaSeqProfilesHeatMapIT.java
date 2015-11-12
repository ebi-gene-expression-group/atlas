package uk.ac.ebi.atlas.experimentpage.differential;

import com.google.common.base.Joiner;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

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

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) throws GenesNotFoundException, ExecutionException {
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        return requestContextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400
    @Test
    public void defaultParameters() throws GenesNotFoundException, ExecutionException {
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialExperiment experiment = requestContext.getExperiment();
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        RnaSeqProfile profile1 = (RnaSeqProfile) profiles.get(0);
        DifferentialExpression g1_g2Expression = profile1.getExpression(experiment.getContrast("g1_g2"));

        assertThat(profile1.getName(), is("AT3G48131"));
        assertThat(g1_g2Expression.getPValue(), is(4.2479998366313E-5));
        assertThat(g1_g2Expression.getFoldChange(), is(6.89620951324986));

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(51));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "T5N23_130", "AT5G35205", "T5N23_90", "AT2G16310", "AT4G04293", "AT2G05200", "AT5G24240", "AT1G11785", "AT4G04223", "NRPD1B", "ATMG00510", "ANAC003", "AT5G62920", "GRXS4", "AT5G35935", "AT5G52070", "PSBE", "GRXS5", "GRXS10", "F21F23.9", "ATMG00410", "AT1G12730", "AT3G59765", "AT5G27850", "ATPF", "AT5G11090", "AT2G07733", "AT5G28300", "F13F21.6", "AT5G40450", "GRXS8", "GRXS3", "GRXS13", "AT5G24150", "AT1G62310", "AT5G26220", "ATCG00490", "MYR1", "SEN1", "AT2G35382", "ATMRU1", "AT5G61190", "ATMSRB6", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(51));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT3G48131", "DML1", "F14M2.2", "AT3G29644", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "T5N23_130", "AT5G35205", "T5N23_90", "AT2G16310", "AT4G04293", "AT2G05200", "AT5G24240", "AT1G11785", "AT4G04223", "NRPD1B", "ATMG00510", "ANAC003", "AT5G62920", "GRXS4", "AT5G35935", "AT5G52070", "PSBE", "GRXS5", "GRXS10", "F21F23.9", "ATMG00410", "AT1G12730", "AT3G59765", "AT5G27850", "ATPF", "AT5G11090", "AT2G07733", "AT5G28300", "F13F21.6", "AT5G40450", "GRXS8", "GRXS3", "GRXS13", "AT5G24150", "AT1G62310", "AT5G26220", "ATCG00490", "MYR1", "SEN1", "AT2G35382", "ATMRU1", "AT5G61190"));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-21860?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException, ExecutionException {
        setGeneQuery("protein_coding");
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_21860);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(108));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("Lactbl1", "Prok1", "Gpr26", "Lrrc55", "Tph1", "Cldn8", "Fmo1", "Sftpd", "Lonrf3", "Neb", "Tnfrsf11b", "Fmo4", "Mctp1", "Tph2", "Ivd", "Matn2", "Dnahc8", "Cspg5", "Gbp8", "Cish", "Ehhadh", "Reg3b", "Cartpt", "Grem2", "Chgb", "Reg3a", "Tmem255a", "Ikzf4", "Gm16314", "Grp", "Ovol2", "Cntn3", "Synpr", "Npas4", "Txnrd2", "Acvr1c", "Rnf182", "Syce2", "Aqp4", "Grhl1", "Wipi1", "Rgs2", "Ogdhl", "Gas2", "Dnmt3b", "Hspa1b", "Vip", "Igfals", "Dhcr7", "Reg3d"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?regulation=UP
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setRegulation(Regulation.UP);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(36));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "T5N23_130", "AT5G35205", "T5N23_90", "AT2G16310", "AT4G04293", "AT2G05200", "AT5G24240", "AT1G11785", "AT4G04223", "ANAC003", "AT5G62920", "GRXS4", "AT5G35935", "AT5G52070", "GRXS5", "GRXS10", "F21F23.9", "AT3G59765", "AT5G27850", "AT5G11090", "F13F21.6", "GRXS8", "GRXS3", "GRXS13", "AT5G24150", "AT5G26220", "AT2G35382", "ATMRU1", "ATMSRB6", "AT3G29644"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?regulation=DOWN
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setRegulation(Regulation.DOWN);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(15));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("NRPD1B", "ATMG00510", "PSBE", "ATMG00410", "AT1G12730", "ATPF", "AT2G07733", "AT5G28300", "AT5G40450", "AT1G62310", "ATCG00490", "MYR1", "SEN1", "AT5G61190", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setCutoff(0.002);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(27));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "T5N23_130", "AT3G29644", "AT5G35205", "T5N23_90", "AT2G05200", "AT5G24240", "AT4G04223", "NRPD1B", "AT5G62920", "GRXS4", "AT5G35935", "AT5G52070", "GRXS5", "ATMG00410", "AT3G59765", "ATPF", "AT5G11090", "AT2G07733", "AT5G28300", "F13F21.6", "AT5G40450", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?foldChangeCutOff=5
    @Test
    public void withFoldChangeCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setFoldChangeCutOff(5D);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(5));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400?queryFactorValues=g1_g2&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g1_g2"));
        setNotSpecific();
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(19));
        assertThat(profiles.extractGeneNames(), (Matcher)contains("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "AT5G35205", "DML1", "AT2G16310", "AT4G04293", "AT3G29644", "AT2G05200", "AT5G24240", "NRPD1B", "ANAC003", "AT5G52070", "F21F23.9", "AT1G12730", "AT5G27850"));
    }

    //  http://localhost:8080/gxa/experiments/E-GEOD-38400?queryFactorValues=g1_g2
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g1_g2"));
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        DifferentialProfilesList profiles = subject.fetch(requestContext);

        System.out.println("\"" + Joiner.on("\", \"").join(profiles.extractGeneNames()) + "\"");

        assertThat(profiles.getTotalResultCount(), is(17));
        assertThat(profiles.extractGeneNames(), (Matcher) contains("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "AT5G35205", "AT2G16310", "AT4G04293", "AT2G05200", "AT5G24240", "NRPD1B", "ANAC003", "AT5G52070", "F21F23.9", "AT1G12730", "AT5G27850"));
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(GeneQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
