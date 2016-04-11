package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesWriterIT {

    public static final int GENE_NAME_INDEX = 1;

    private static final String E_GEOD_38400 = "E-GEOD-38400";
    private static final String E_GEOD_21860 = "E-GEOD-21860";


    private RnaSeqProfilesWriter subject;

    @Inject
    private RnaSeqDiffExperimentsCache experimentsCache;

    @Inject
    RnaSeqRequestContextBuilder contextBuilder;

    @Mock
    PrintWriter printWriterMock;

    @Mock
    CSVWriter csvWriterMock;

    @Mock
    private CsvWriterFactory csvWriterFactoryMock;

    private DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

    @Value("classpath:/file-templates/download-headers-differential.txt")
    public Resource tsvFileMastheadTemplateResource;

    @Inject
    private RnaSeqProfileStreamFactory inputStreamFactory;

    @Inject
    private SolrQueryService solrQueryService;

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) throws ExecutionException {
        MockitoAnnotations.initMocks(this);
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        RnaSeqRequestContext requestContext = contextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();

        RnaSeqProfilesTSVWriter geneProfileTsvWriter = new RnaSeqProfilesTSVWriter(csvWriterFactoryMock);
        geneProfileTsvWriter.setRequestContext(requestContext);
        geneProfileTsvWriter.setTsvFileMastheadTemplateResource(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);


        subject = new RnaSeqProfilesWriter(geneProfileTsvWriter, inputStreamFactory, solrQueryService);

        return requestContext;
    }


    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv
    @Test
    public void defaultParameters_Header() throws GenesNotFoundException, ExecutionException {
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        subject.write(printWriterMock, requestContext);

        String[] lines = headerLines();
        String queryLine = lines[1];

        assertThat(queryLine, is("# Query: Genes matching: '' exactly, specifically up/down differentially expressed in any contrast given the p-value cutoff 0.05 and log2-fold change cutoff 1 in experiment E-GEOD-38400"));

        String[] columnHeaders = csvLines().get(0);
        //System.out.println("\"" + Joiner.on("\", \"").join(columnHeaders) + "\"");
        assertThat(columnHeaders, is(new String[] {"Gene ID", "Gene Name", "nrpe1 mutant vs wild type.p-value", "nrpe1 mutant vs wild type.log2foldchange", "swi3b mutant vs wild type.p-value", "swi3b mutant vs wild type.log2foldchange", "idn2 mutant vs wild type.p-value", "idn2 mutant vs wild type.log2foldchange"}));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv
    @Test
    public void defaultParameters() throws GenesNotFoundException, ExecutionException {
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 51;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("AT5G26220", "ANAC003", "ATMG00410", "F14M2.2", "AT5G24150", "NRPD1B", "SEN1", "AT1G33840", "AT5G27850", "AT4G04293", "AT1G62310", "AT3G59765", "F21F23.9", "F13F21.6", "AT5G35935", "GRXS5", "AT2G35382", "GRXS4", "AT3G29644", "GRXS8", "AT2G05200", "GRXS10", "AT5G24240", "T5N23_90", "AT5G40450", "ATCG00490", "ATMSRB6", "ATMG00510", "GRXS13", "AT5G62920", "AT2G07733", "AT3G48131", "AT5G52070", "AT5G35205", "AT5G11090", "MYR1", "RSU1", "AT4G04223", "ATMRU1", "AT5G61190", "AT1G12730", "T5N23_130", "AT5G28300", "AT5G27845", "AT5G35207", "AT2G16310", "DML1", "ATPF", "PSBE", "GRXS3", "AT1G11785"));

        String[] at = geneNameToLine.get("AT5G26220");
        String[] dml1 = geneNameToLine.get("DML1");

        assertThat(at, is(new String[]{"AT5G26220", "AT5G26220", null, null, null, null, "0.031661134059594", "1.40349306058053"}));
        assertThat(dml1, is(new String[]{"AT2G36490", "DML1", "3.22093702720161E-4", "-3.05187487907893", null, null, "7.28777185318565E-6", "-3.6788494811217"}));

    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv&_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        defaultParameters();
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-21860.tsv?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException, ExecutionException {
        setGeneQuery("protein_coding");
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_21860);
        long genesCount = subject.write(printWriterMock, requestContext);

        List<String> geneNames = geneNames(csvLines());

        int expectedCount = 108;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        assertThat(geneNames, containsInAnyOrder("Npas4", "Gm16314", "B3galnt1", "Hsph1", "Gabbr2", "Slc6a8", "Sptbn2", "Hspa1a", "Lgals12", "Reg3b", "Dnahc8", "Gpr26", "Gcsh", "Fam155a", "Cntn3", "Bsn", "Hspa1b", "Grem2", "Junb", "Map6", "Ogdhl", "Hsbp1", "Gatm", "Dnaja1", "Gbp8", "Fmo1", "Aldh9a1", "Nr4a1", "Car8", "Cux2", "Mctp1", "Ikzf4", "Gpr119", "D630039A03Rik", "Prok1", "Hopx", "Aldh1l2", "Matn2", "Ggact", "Prkd1", "Prkar2b", "Nupr1", "Cldn8", "Tmem255a", "Cish", "Reg3a", "Grp", "Igfals", "Enpp2", "Csrnp1", "Pycr1", "Wipi1", "Rnf150", "Rab3d", "Cttnbp2", "Prlr", "Sftpd", "Neb", "Mdm1", "Glp1r", "Chst11", "Synpr", "Bmp1", "Ngfr", "Lrrc55", "Rnf182", "Dnmt3b", "Txnrd2", "Apobec1", "Cspg5", "Dnajb1", "Lactbl1", "Cartpt", "Grhl1", "Itga6", "Dusp1", "Fmo4", "Socs2", "Syce2", "Tnfrsf11b", "Znrf2", "Gas2", "Rgs2", "Ern1", "Gyltl1b", "Tph1", "Dhcr7", "Tsc22d3", "Ivd", "Chgb", "Disp2", "Tph2", "Ovol2", "Ehhadh", "Derl3", "Enox1", "Reg3d", "Fosb", "Slc40a1", "Vip", "Fkbp11", "Aqp4", "Slc2a13", "Arc", "Lrp8", "Lonrf3", "Acvr1c", "Igfbp5"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?regulation=UP
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setRegulation(Regulation.UP);

        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        List<String> geneNames = geneNames(csvLines());

        int expectedCount = 36;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "T5N23_130", "AT5G35205", "T5N23_90", "AT2G16310", "AT4G04293", "AT2G05200", "AT5G24240", "AT1G11785", "AT4G04223", "ANAC003", "AT5G62920", "GRXS4", "AT5G35935", "AT5G52070", "GRXS5", "GRXS10", "F21F23.9", "AT3G59765", "AT5G27850", "AT5G11090", "F13F21.6", "GRXS8", "GRXS3", "GRXS13", "AT5G24150", "AT5G26220", "AT2G35382", "ATMRU1", "ATMSRB6", "AT3G29644"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?regulation=DOWN
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setRegulation(Regulation.DOWN);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        List<String> geneNames = geneNames(csvLines());

        int expectedCount = 15;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("NRPD1B", "ATMG00510", "PSBE", "ATMG00410", "AT1G12730", "ATPF", "AT2G07733", "AT5G28300", "AT5G40450", "AT1G62310", "ATCG00490", "MYR1", "SEN1", "AT5G61190", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setCutoff(0.002);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        List<String> geneNames = geneNames(csvLines());

        int expectedCount = 27;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "T5N23_130", "AT3G29644", "AT5G35205", "T5N23_90", "AT2G05200", "AT5G24240", "AT4G04223", "NRPD1B", "AT5G62920", "GRXS4", "AT5G35935", "AT5G52070", "GRXS5", "ATMG00410", "AT3G59765", "ATPF", "AT5G11090", "AT2G07733", "AT5G28300", "F13F21.6", "AT5G40450", "DML1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?foldChangeCutOff=5
    @Test
    public void withFoldChangeCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setFoldChangeCutOff(5D);
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        List<String> geneNames = geneNames(csvLines());

        int expectedCount = 5;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1"));
    }



    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?queryFactorValues=g1_g2&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        requestPreferences.setQueryFactorValues(Collections.singleton("g1_g2"));
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 19;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "AT5G35205", "DML1", "AT2G16310", "AT4G04293", "AT3G29644", "AT2G05200", "AT5G24240", "NRPD1B", "ANAC003", "AT5G52070", "F21F23.9", "AT1G12730", "AT5G27850"));
    }

    //  http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?queryFactorValues=g1_g2
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g1_g2"));
        RnaSeqRequestContext requestContext = populateRequestContext(E_GEOD_38400);
        long genesCount = subject.write(printWriterMock, requestContext);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 17;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("AT3G48131", "F14M2.2", "AT1G33840", "AT5G35207", "RSU1", "AT5G27845", "AT5G35205", "AT2G16310", "AT4G04293", "AT2G05200", "AT5G24240", "NRPD1B", "ANAC003", "AT5G52070", "F21F23.9", "AT1G12730", "AT5G27850"));
    }

    private String[] headerLines() {
        ArgumentCaptor<String> lineCaptor = ArgumentCaptor.forClass(String.class);
        verify(printWriterMock, times(1)).write(lineCaptor.capture());

        return lineCaptor.getAllValues().get(0).split("\n");
    }

    private List<String[]> csvLines() {
        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, atLeast(0)).writeNext(lineCaptor.capture());

       return lineCaptor.getAllValues();
    }


    private ImmutableMap<String, String[]> indexByGeneName(List<String[]> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String line[] : lines) {
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.put(line[GENE_NAME_INDEX], line);
            }
        }

        return builder.build();
    }

    private List<String> geneNames(List<String[]> lines) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (String line[] : lines) {
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.add(line[GENE_NAME_INDEX]);
            }
        }

        return builder.build();
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(GeneQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }


}