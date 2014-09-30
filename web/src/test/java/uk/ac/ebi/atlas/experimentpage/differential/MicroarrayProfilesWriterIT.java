package uk.ac.ebi.atlas.experimentpage.differential;

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
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroarrayProfilesWriterIT {

    public static final int GENE_NAME_INDEX = 1;
    public static final String E_GEOD_8122 = "E-GEOD-8122";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final String E_GEOD_3779 = "E-GEOD-3779";

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
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Inject
    private DifferentialProfileStreamPipelineBuilder<MicroarrayProfile> pipelineBuilder;

    private MicroarrayRequestContext populateRequestContext(String experimentAccession) {
        MockitoAnnotations.initMocks(this);
        MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

        MicroarrayRequestContext requestContext = contextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();

        MicroarrayProfilesTSVWriter geneProfileTsvWriter = new MicroarrayProfilesTSVWriter(csvWriterFactoryMock);
        geneProfileTsvWriter.setRequestContext(requestContext);
        geneProfileTsvWriter.setTsvFileMastheadTemplateResource(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);

        subject = new MicroarrayProfilesWriter(pipelineBuilder, geneProfileTsvWriter, inputStreamFactory, loadGeneIdsIntoRequestContext);

        return requestContext;

    }


    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv
    @Test
    public void defaultParameters_Header() throws GenesNotFoundException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        String[] lines = headerLines();
        String queryLine = lines[1];

        assertThat(queryLine, is("# Query: Genes matching: '' exactly, specifically up/down differentially expressed in any contrast given the p-value cutoff 0.05 and log2-fold change cutoff 1 in experiment E-MTAB-1066"));

        String[] columnHeaders = csvLines().get(0);
        assertThat(columnHeaders, is(new String[] {"Gene ID", "Gene Name", "Design Element", "genotype:'cycC mutant' vs 'wild type'.p-value", "genotype:'cycC mutant' vs 'wild type'.log2foldchange", "genotype:'cycC mutant' vs 'wild type'.t-statistic", "genotype:'cdk8 mutant' vs 'wild type'.p-value", "genotype:'cdk8 mutant' vs 'wild type'.log2foldchange", "genotype:'cdk8 mutant' vs 'wild type'.t-statistic"}));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv
    @Test
    public void defaultParameters() throws GenesNotFoundException {
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
    public void notSpecific() throws GenesNotFoundException {
        setNotSpecific();
        defaultParameters();
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv
    @Test
    public void eGeod43049() throws GenesNotFoundException {
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 2;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] sc5d = geneNameToLine.get("SC5D");
        String[] spire1 = geneNameToLine.get("SPIRE1");

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1"));
        assertThat(spire1, is("ENSG00000134278\tSPIRE1\tA_23_P135878\t1.54888175264243E-4\t1.0942741427441\t10.0906730728867".split("\t")));
        assertThat(sc5d, is("ENSG00000109929\tSC5D\tA_23_P372888\t1.08617530258573E-4\t1.44526451950158\t10.8203265434862".split("\t")));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException {
        setGeneQuery("protein_coding");
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 2;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?regulation=UP&foldChangeCutOff=0
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        requestPreferences.setRegulation(Regulation.UP);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 9;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "DEPDC7", "CKAP2", "S100A14", "AC016629.3", "NFX1", "MTMR3", "RPL22P19"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?regulation=DOWN&foldChangeCutOff=0
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        requestPreferences.setRegulation(Regulation.DOWN);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 14;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "GNA12", "TMEM154", "TMEM70", "PSMA7", "DPP8", "SS18L2", "SSR1", "C19orf44"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException {
        requestPreferences.setCutoff(0.00014);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_43049);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 1;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g3&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException {
        setNotSpecific();
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 21;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("CG14265", "CG17974", "CG32444", "CG1443", "CG33459", "CG15213", "CG9981", "CG30272", "CG1368", "CG31463", "CG42329", "Ork1", "CG15043", "pgant8", "CG2150", "Cpr12A", "sba", "CG3376", "CG14694", "CG10165", "CG6403"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g3
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g3"));
        MicroarrayRequestContext requestContext = populateRequestContext(E_MTAB_1066);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();


        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 9;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("CG17974", "CG32444", "CG33459", "CG9981", "CG30272", "CG42329", "Ork1", "CG2150", "CG6403"));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-8122.tsv?foldChangeCutOff=0
    @Test
    public void eGeod8122() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_8122);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 1;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] vma16 = geneNameToLine.get("VMA16");

        assertThat(geneNames, containsInAnyOrder("VMA16"));
        assertThat(vma16, is("YHR026W\tVMA16\t1779820_at\t0.0415772172547035\t-0.540554090488339\t-6.70529872966727".split("\t")));
    }

    private void setFoldChangeCutOff(double cutOff) {
        requestPreferences.setFoldChangeCutOff(cutOff);
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-8122.tsv?geneQuery=YHR026W&foldChangeCutOff=0
    @Test
    public void eGeod8122_GeneQuery_For_Gene_Not_In_Experiment_Species() throws GenesNotFoundException {
        setGeneQuery("YHR026W");
        eGeod8122();
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-3779.tsv?foldChangeCutOff=0
    @Test
    public void experimentWithMultipleArrayDesigns_ArrayDesignAccession1() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_3779);
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getArrayDesignAccessions().iterator().next());

        String[] columnHeaders = csvLines().get(0);
        System.out.println("\"" + Joiner.on("\", \"").join(columnHeaders) + "\"");
        assertThat(columnHeaders, is(new String[] {"Gene ID", "Gene Name", "Design Element", "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23.p-value", "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23.log2foldchange", "genotype:'p107 -/-' vs 'wild type' on A-AFFY-23.t-statistic"}));

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 1;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("Mycl1"));

        String[] geneLine = geneNameToLine.get("Mycl1");
        assertThat(geneLine, is(new String[] {"ENSMUSG00000028654", "Mycl1", "1422088_at", "0.04", "0.00409480833333209", "0.0502355223125666"}));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-3779.tsv?foldChangeCutOff=0
    @Test
    public void experimentWithMultipleArrayDesigns_ArrayDesignAccession2() throws GenesNotFoundException {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = populateRequestContext(E_GEOD_3779);
        requestContext.setArrayDesignAccession(requestContext.getExperiment().getArrayDesignAccessions().last());
        long genesCount = subject.write(printWriterMock, requestContext, requestContext.getExperiment().getArrayDesignAccessions().last());

        String[] columnHeaders = csvLines().get(0);
        assertThat(columnHeaders, is(new String[] {"Gene ID", "Gene Name", "Design Element", "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24.p-value", "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24.log2foldchange", "genotype:'p107 -/-' vs 'wild type' on A-AFFY-24.t-statistic"}));

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 1;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("Snx30"));

        String[] geneLine = geneNameToLine.get("Snx30");
        assertThat(geneLine, is(new String[] {"ENSMUSG00000028385", "Snx30", "1456479_at", "0.02", "0.00641014999999978", "0.120394369986336"}));
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
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
