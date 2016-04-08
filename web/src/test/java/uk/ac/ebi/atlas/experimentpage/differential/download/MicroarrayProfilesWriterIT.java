package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
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
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

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
public class MicroarrayProfilesWriterIT {

    public static final int GENE_NAME_INDEX = 1;
    public static final String E_TABM_713 = "E-TABM-713";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final String E_GEOD_3307 = "E-GEOD-3307";

    private MicroarrayProfilesWriter subject;

    @Inject
    private MicroarrayExperimentsCache microarrayExperimentsCache;

    @Inject
    MicroarrayRequestContextBuilder contextBuilder;

    @Mock
    PrintWriter printWriterMock;

    @Mock
    CSVWriter csvWriterMock;

    @Mock
    private CsvWriterFactory csvWriterFactoryMock;

    private MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

    @Value("classpath:/file-templates/download-headers-differential.txt")
    public Resource tsvFileMastheadTemplateResource;

    @Inject
    private MicroarrayProfileStreamFactory inputStreamFactory;

    @Inject
    private SolrQueryService solrQueryService;

    @Inject
    private DifferentialProfileStreamPipelineBuilder<MicroarrayProfile> pipelineBuilder;

    private MicroarrayRequestContext populateRequestContext(String experimentAccession) throws ExecutionException {
        MockitoAnnotations.initMocks(this);
        MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

        MicroarrayRequestContext requestContext = contextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();

        MicroarrayProfilesTSVWriter geneProfileTsvWriter = new MicroarrayProfilesTSVWriter(csvWriterFactoryMock);
        geneProfileTsvWriter.setRequestContext(requestContext);
        geneProfileTsvWriter.setTsvFileMastheadTemplateResource(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);

        subject = new MicroarrayProfilesWriter(pipelineBuilder, geneProfileTsvWriter, inputStreamFactory, solrQueryService);

        return requestContext;

    }


    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv
    @Test
    public void defaultParameters_Header() throws GenesNotFoundException, ExecutionException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        String[] lines = headerLines();
        String queryLine = lines[1];

        assertThat(queryLine, is("# Query: Genes matching: '' exactly, specifically up/down differentially expressed in any contrast given the p-value cutoff 0.05 and log2-fold change cutoff 1 in experiment E-MTAB-1066"));

        String[] columnHeaders = csvLines().get(0);
        assertThat(columnHeaders, is(new String[]{"Gene ID", "Gene Name", "Design Element", "'cycC mutant' vs 'wild type'.p-value", "'cycC mutant' vs 'wild type'.log2foldchange", "'cycC mutant' vs 'wild type'.t-statistic", "'cdk8 mutant' vs 'wild type'.p-value", "'cdk8 mutant' vs 'wild type'.log2foldchange", "'cdk8 mutant' vs 'wild type'.t-statistic"}));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv
    @Test
    public void defaultParameters() throws GenesNotFoundException, ExecutionException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        List<String> geneNames = geneNames(csvLines());

        int expectedCount = 54;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        assertThat(geneNames, containsInAnyOrder("CG14265", "CG5043", "CG1103", "CG18472", "CG17974", "Mst84Db", "skpB", "CG32444", "CG1443", "CG4669", "dj", "CG33459", "CG15286", "CG7742", "CG9254", "CG12689", "CG15213", "CG9981", "CG14605", "Jupiter", "CG30272", "CG5213", "CG1368", "CG31606", "CG31463", "cher", "Atg8b", "CG14540", "mthl2", "CG32686", "CG9589", "CG42329", "CG13876", "CG31624", "CG6870", "CG31139", "Ork1", "CG31803", "CG15043", "pgant8", "Cda5", "CG2150", "Cpr12A", "sba", "CG3376", "CG14694", "CG15615", "CG9040", "yip7", "CG30393", "CG10165", "Hsp60B", "CG6403", "CG11147"));
    }

    // http://localhost:8080/gxa/experiments/E_MTAB_1066.tsv&_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        defaultParameters();
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv
    @Test
    public void eGeod43049() throws GenesNotFoundException, ExecutionException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 2;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] sc5d = geneNameToLine.get("SC5D").asList().get(0);
        String[] spire1 = geneNameToLine.get("SPIRE1").asList().get(0);

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1"));
        assertThat(spire1, is("ENSG00000134278\tSPIRE1\tA_23_P135878\t1.54888175264243E-4\t1.0942741427441\t10.0906730728867".split("\t")));
        assertThat(sc5d, is("ENSG00000109929\tSC5D\tA_23_P372888\t1.08617530258573E-4\t1.44526451950158\t10.8203265434862".split("\t")));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException, ExecutionException {
        setGeneQuery("protein_coding");
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 2;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?regulation=UP&foldChangeCutOff=0
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException, ExecutionException {
        setFoldChangeCutOff(0);
        requestPreferences.setRegulation(Regulation.UP);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 9;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "DEPDC7", "CKAP2", "S100A14", "AC016629.3", "NFX1", "MTMR3", "RPL22P19"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?regulation=DOWN&foldChangeCutOff=0
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException, ExecutionException {
        setFoldChangeCutOff(0);
        requestPreferences.setRegulation(Regulation.DOWN);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 14;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "GNA12", "TMEM154", "TMEM70", "PSMA7", "DPP8", "SS18L2", "SSR1", "C19orf44"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setCutoff(0.00014);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 1;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g3&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 21;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("CG14265", "CG17974", "CG32444", "CG1443", "CG33459", "CG15213", "CG9981", "CG30272", "CG1368", "CG31463", "CG42329", "Ork1", "CG15043", "pgant8", "CG2150", "Cpr12A", "sba", "CG3376", "CG14694", "CG10165", "CG6403"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g3
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();


        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 9;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("CG17974", "CG32444", "CG33459", "CG9981", "CG30272", "CG42329", "Ork1", "CG2150", "CG6403"));
    }


    // http://localhost:8080/gxa/experiments/E-TABM-713.tsv?foldChangeCutOff=0
    @Test
    public void eTabm713() throws GenesNotFoundException, ExecutionException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_TABM_713);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 73;
        int uniqueExpectedCount = 48;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(uniqueExpectedCount));

        assertThat(geneNames, containsInAnyOrder("MIMAT0000267", "MIMAT0000083", "MIMAT0002826", "MIMAT0003306", "MIMAT0003249", "MIMAT0003237", "MIMAT0000255", "MIMAT0003303", "MIMAT0000448", "MIMAT0000274", "MIMAT0000095", "MIMAT0002821", "MIMAT0000069", "MIMAT0000693", "MIMAT0002843", "MIMAT0002809", "MIMAT0000089", "MIMAT0003227", "MIMAT0000259", "MIMAT0002869", "MIMAT0003289", "MIMAT0002844", "MIMAT0001618", "MIMAT0003883", "MIMAT0004800", "MIMAT0003304", "MIMAT0000078", "MIMAT0003242", "MIMAT0002874", "MIMAT0000093", "MIMAT0002177", "MIMAT0000258", "MIMAT0002851", "MIMAT0000449", "MIMAT0003301", "MIMAT0003220", "MIMAT0003246", "MIMAT0003337", "MIMAT0000420", "MIMAT0000269", "MIMAT0001075", "MIMAT0000439", "MIMAT0000458", "MIMAT0003253", "MIMAT0000082", "MIMAT0001541", "MIMAT0000753", "MIMAT0000261"));

        String[] mimat0000267 = geneNameToLine.get("MIMAT0000267").asList().get(0);
        assertThat(mimat0000267, is(new String[] {"hsa-miR-210-3p", "MIMAT0000267", "A_25_P00010995", "0.0333837230765599", "0.827455526749746", "3.82721624713512"}));
    }

    private void setFoldChangeCutOff(double cutOff) {
        requestPreferences.setFoldChangeCutOff(cutOff);
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-3307.tsv?foldChangeCutOff=0
    @Test
    public void experimentWithMultipleArrayDesigns_ArrayDesignAccession1() throws GenesNotFoundException, ExecutionException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_3307);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        String[] columnHeaders = csvLines().get(0);
        System.out.println("\"" + Joiner.on("\", \"").join(columnHeaders) + "\"");
        assertThat(columnHeaders, is(new String[] {"Gene ID", "Gene Name", "Design Element", "'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'LGMD2I' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'BMD' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'BMD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'BMD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'EDMD AD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'HSP' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'HSP' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'HSP' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'FSHD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'JDM' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'JDM' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'JDM' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'LGMD2A' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'AQM' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'AQM' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'AQM' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'ALS' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'ALS' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'ALS' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'LGMD2B' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic", "'DMD' vs 'normal' on 'Affymetrix HG-U133A'.p-value", "'DMD' vs 'normal' on 'Affymetrix HG-U133A'.log2foldchange", "'DMD' vs 'normal' on 'Affymetrix HG-U133A'.t-statistic"}));

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 89;
        int uniqueExpectedCount = 45;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(uniqueExpectedCount));

        assertThat(geneNames, containsInAnyOrder("TSPAN6", "DPM1", "SCYL3", "C1orf112", "CFH", "GCLC", "NFYA", "NIPAL3", "LAS1L", "ENPP4", "SEMA3F", "CFTR", "RAD52", "BAD", "LAP3", "CD99", "HS3ST1", "AOC1", "HECW1", "MAD1L1", "LASP1", "SNX11", "TMEM176A", "M6PR", "CYP26B1", "ICA1", "DBNDD1", "CASP10", "CFLAR", "TFPI", "RBM5", "SLC7A2", "SARM1", "POLDIP2", "PLXND1", "AK2", "CD38", "FKBP4", "KDM1A", "RBM6", "RECQL", "CCDC132", "HSPB6", "ARHGAP33", "NDUFAB1"));

        String[] geneLine = geneNameToLine.get("TSPAN6").asList().get(0);
        assertThat(geneLine, is(new String[] {"ENSG00000000003", "TSPAN6", "209108_at", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "3.22392001630122E-5", "0.413107096522765", "5.02112235896101", null, null, null, null, null, null, "0.0119843116644908", "0.313794065216056", "3.12520915964694", null, null, null, "0.0110142369806691", "0.453232593478132", "3.27165792658763"}));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-3307.tsv?foldChangeCutOff=0
    @Test
    public void experimentWithMultipleArrayDesigns_ArrayDesignAccession2() throws GenesNotFoundException, ExecutionException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_3307);
        requestContext.setArrayDesignAccession(requestContext.getExperiment().getArrayDesignAccessions().last());
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getExperiment().getArrayDesignAccessions().last());

        String[] columnHeaders = csvLines().get(0);
        assertThat(columnHeaders, is(new String[] {"Gene ID", "Gene Name", "Design Element", "'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'FSHD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'EDMD AD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'BMD' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'BMD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'BMD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'DMD' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'DMD' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'DMD' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'LGMD2I' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'EDMD XR' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'AQM' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'AQM' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'AQM' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'LGMD2A' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'HSP' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'HSP' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'HSP' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'JDM' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'JDM' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'JDM' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'LGMD2B' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic", "'ALS' vs 'normal' on 'Affymetrix HG-U133B'.p-value", "'ALS' vs 'normal' on 'Affymetrix HG-U133B'.log2foldchange", "'ALS' vs 'normal' on 'Affymetrix HG-U133B'.t-statistic"}));

        ImmutableMultimap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 88;
        int uniqueExpectedCount = 61;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(uniqueExpectedCount));

        assertThat(geneNames, containsInAnyOrder("SCYL3", "FUCA2", "NFYA", "STPG1", "NIPAL3", "LAS1L", "CFTR", "ANKIB1", "HECW1", "KLHL13", "CYP26B1", "ICA1", "ALS2", "CFLAR", "NDUFAF7", "RBM5", "MTMR7", "SLC7A2", "SARM1", "POLDIP2", "CAMKK1", "CCDC132", "HSPB6", "ARHGAP33", "PDK4", "SLC22A16", "ARX", "SLC25A13", "SKAP2", "DHX33", "THSD7A", "LIG3", "SPPL2B", "COPZ2", "CREBBP", "WDR54", "KMT2E", "RHBDD2", "SOX8", "FBXL3", "ZFX", "ASB4", "GDE1", "OSBPL7", "TMEM98", "TMEM132A", "ZNF263", "DLX6", "RALA", "BAIAP2L1", "KDM7A", "TTC22", "CCL26", "FARP2", "IFRD1", "ARSD", "UPP2", "CCDC124", "DNAH9", "SLC13A2", "ST7L"));

        String[] geneLine = geneNameToLine.get("SCYL3").asList().get(0);
        assertThat(geneLine, is(new String[] {"ENSG00000000457", "SCYL3", "231111_at", "0.0130556396535823", "0.212702005676215", "3.1690924162594", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "7.74486134849082E-4", "0.300456061009448", "3.9026735704204", null, null, null, null, null, null}));
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

    private ImmutableMultimap<String, String[]> indexByGeneName(List<String[]> lines) {
        ImmutableMultimap.Builder<String, String[]> builder = ImmutableMultimap.builder();
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
