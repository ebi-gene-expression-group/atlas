package uk.ac.ebi.atlas.search.analyticsindex.differential;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.GeneQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 12/03/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class DifferentialAnalyticsFacetsReaderTest {

    DifferentialAnalyticsFacetsReader subject;

    private Gson gson;

    @Mock
    ExperimentTrader experimentTraderMock;

    @Mock
    ContrastTrader contrastTraderMock;

    @Mock
    Experiment E_GEOD_38400ExperimentMock, E_MEXP_1276ExperimentMock, E_GEOD_2507ExperimentMock,
               E_GEOD_3779ExperimentMock, E_MTAB_1066ExperimentMock, E_GEOD_43049ExperimentMock;

    @Mock
    Contrast G1_G2E_GEOD_38400ContrastMock, G1_G3E_GEOD_38400ContrastMock, G1_G4E_GEOD_38400ContrastMock,
             G2_G1E_MEXP_1276ContrastMock,
             G4_G1E_GEOD_2507ContrastMock, G3_G2E_GEOD_2507ContrastMock,
             G3_G4E_GEOD_3779ContrastMock, G2_G1E_GEOD_3779ContrastMock,
             G2_G1E_MTAB_1066ContrastMock, G2_G3E_MTAB_1066ContrastMock,
             G2_G1E_GEOD_43049ContrastMock;

    private final String FOOBAR_RESPONSE ="{\n" +
            "  \"response\":{\"numFound\":0,\"start\":0,\"docs\":[]\n" +
            "  },\n" +
            "  \"facets\":{\n" +
            "    \"count\":0}}";

    private final String FOOBAR_RESULTS = "[]";

    private final String ZINC_FINGER_RESPONSE = "{\n" +
            "  \"response\":{},\n" +
            "  \"facets\":{\n" +
            "    \"count\":17307,\n" +
            "    \"species\":{\n" +
            "      \"buckets\":[{\n" +
            "          \"val\":\"arabidopsis thaliana\",\n" +
            "          \"count\":12705,\n" +
            "          \"experimentAccession\":{\n" +
            "            \"buckets\":[{\n" +
            "                \"val\":\"E-GEOD-38400\",\n" +
            "                \"count\":12705,\n" +
            "                \"contrastId\":{\n" +
            "                  \"buckets\":[{\n" +
            "                      \"val\":\"g1_g2\",\n" +
            "                      \"count\":4235,\n" +
            "                      \"geneCount\":1429},\n" +
            "                    {\n" +
            "                      \"val\":\"g1_g3\",\n" +
            "                      \"count\":4235,\n" +
            "                      \"geneCount\":1429},\n" +
            "                    {\n" +
            "                      \"val\":\"g1_g4\",\n" +
            "                      \"count\":4235,\n" +
            "                      \"geneCount\":1429}]}}]}},\n" +
            "        {\n" +
            "          \"val\":\"mus musculus\",\n" +
            "          \"count\":4586,\n" +
            "          \"experimentAccession\":{\n" +
            "            \"buckets\":[{\n" +
            "                \"val\":\"E-MEXP-1276\",\n" +
            "                \"count\":4545,\n" +
            "                \"contrastId\":{\n" +
            "                  \"buckets\":[{\n" +
            "                      \"val\":\"g2_g1\",\n" +
            "                      \"count\":4545,\n" +
            "                      \"geneCount\":957}]}},\n" +
            "              {\n" +
            "                \"val\":\"E-GEOD-2507\",\n" +
            "                \"count\":35,\n" +
            "                \"contrastId\":{\n" +
            "                  \"buckets\":[{\n" +
            "                      \"val\":\"g4_g1\",\n" +
            "                      \"count\":19,\n" +
            "                      \"geneCount\":16},\n" +
            "                    {\n" +
            "                      \"val\":\"g3_g2\",\n" +
            "                      \"count\":16,\n" +
            "                      \"geneCount\":14}]}},\n" +
            "              {\n" +
            "                \"val\":\"E-GEOD-3779\",\n" +
            "                \"count\":6,\n" +
            "                \"contrastId\":{\n" +
            "                  \"buckets\":[{\n" +
            "                      \"val\":\"g3_g4\",\n" +
            "                      \"count\":4,\n" +
            "                      \"geneCount\":4},\n" +
            "                    {\n" +
            "                      \"val\":\"g2_g1\",\n" +
            "                      \"count\":2,\n" +
            "                      \"geneCount\":2}]}}]}},\n" +
            "        {\n" +
            "          \"val\":\"drosophila melanogaster\",\n" +
            "          \"count\":8,\n" +
            "          \"experimentAccession\":{\n" +
            "            \"buckets\":[{\n" +
            "                \"val\":\"E-MTAB-1066\",\n" +
            "                \"count\":8,\n" +
            "                \"contrastId\":{\n" +
            "                  \"buckets\":[{\n" +
            "                      \"val\":\"g2_g1\",\n" +
            "                      \"count\":4,\n" +
            "                      \"geneCount\":4},\n" +
            "                    {\n" +
            "                      \"val\":\"g2_g3\",\n" +
            "                      \"count\":4,\n" +
            "                      \"geneCount\":4}]}}]}},\n" +
            "        {\n" +
            "          \"val\":\"homo sapiens\",\n" +
            "          \"count\":8,\n" +
            "          \"experimentAccession\":{\n" +
            "            \"buckets\":[{\n" +
            "                \"val\":\"E-GEOD-43049\",\n" +
            "                \"count\":8,\n" +
            "                \"contrastId\":{\n" +
            "                  \"buckets\":[{\n" +
            "                      \"val\":\"g2_g1\",\n" +
            "                      \"count\":8,\n" +
            "                      \"geneCount\":8}]}}]}}]}}}";

    private final String ZINC_FINGER_RESULTS ="[" +
            "{" +
                "\"geneCount\":1429," +
                "\"organism\":\"arabidopsis thaliana\"," +
                "\"contrastId\":\"g1_g2\"," +
                "\"comparison\":\"nrpe1 mutant vs wild type\"," +
                "\"experimentAccession\":\"E-GEOD-38400\"," +
                "\"experimentName\":\"RNA-seq of Arabidopsis mutants with defects in long-non-coding-RNA-mediated transcriptional silencing\"" +
            "}," +
            "{" +
                "\"geneCount\":1429," +
                "\"organism\":\"arabidopsis thaliana\"," +
                "\"contrastId\":\"g1_g3\"," +
                "\"comparison\":\"swi3b mutant vs wild type\"," +
                "\"experimentAccession\":\"E-GEOD-38400\"," +
                "\"experimentName\":\"RNA-seq of Arabidopsis mutants with defects in long-non-coding-RNA-mediated transcriptional silencing\"" +
            "}," +
            "{" +
                "\"geneCount\":1429," +
                "\"organism\":\"arabidopsis thaliana\"," +
                "\"contrastId\":\"g1_g4\"," +
                "\"comparison\":\"idn2 mutant vs wild type\"," +
                "\"experimentAccession\":\"E-GEOD-38400\"," +
                "\"experimentName\":\"RNA-seq of Arabidopsis mutants with defects in long-non-coding-RNA-mediated transcriptional silencing\"" +
            "}," +
            "{" +
                "\"geneCount\":957," +
                "\"organism\":\"mus musculus\"," +
                "\"contrastId\":\"g2_g1\"," +
                "\"comparison\":\"compound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36\"," +
                "\"experimentAccession\":\"E-MEXP-1276\"," +
                "\"experimentName\":\"Transcription profiling by array of pancreatic cells from C57BL/6 mice following dibenzazepine treatment\"" +
            "}," +
            "{" +
                "\"geneCount\":16," +
                "\"organism\":\"mus musculus\"," +
                "\"contrastId\":\"g4_g1\"," +
                "\"comparison\":\"'dysferlin mutant' vs 'wild type' in 'left ventricle cardiac muscle'\"," +
                "\"experimentAccession\":\"E-GEOD-2507\"," +
                "\"experimentName\":\"Transcription profiling by array of skeletal and cardiac muscle from dysferlin-deficient mice\"" +
            "}," +
            "{" +
                "\"geneCount\":14," +
                "\"organism\":\"mus musculus\"," +
                "\"contrastId\":\"g3_g2\"," +
                "\"comparison\":\"'dysferlin mutant' vs 'wild type' in 'skeletal muscle'\"," +
                "\"experimentAccession\":\"E-GEOD-2507\"," +
                "\"experimentName\":\"Transcription profiling by array of skeletal and cardiac muscle from dysferlin-deficient mice\"" +
            "}," +
            "{" +
                "\"geneCount\":4," +
                "\"organism\":\"mus musculus\"," +
                "\"contrastId\":\"g3_g4\"," +
                "\"comparison\":\"genotype:'p107 -/-' vs 'wild type' on A-AFFY-23\"," +
                "\"experimentAccession\":\"E-GEOD-3779\"," +
                "\"experimentName\":\"Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates\"" +
            "}," +
            "{" +
                "\"geneCount\":2," +
                "\"organism\":\"mus musculus\"," +
                "\"contrastId\":\"g2_g1\"," +
                "\"comparison\":\"genotype:'p107 -/-' vs 'wild type' on A-AFFY-24\"," +
                "\"experimentAccession\":\"E-GEOD-3779\"," +
                "\"experimentName\":\"Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates\"" +
            "}," +
            "{" +
                "\"geneCount\":4," +
                "\"organism\":\"drosophila melanogaster\"," +
                "\"contrastId\":\"g2_g1\"," +
                "\"comparison\":\"genotype:'cycC mutant' vs 'wild type'\"," +
                "\"experimentAccession\":\"E-MTAB-1066\"," +
                "\"experimentName\":\"Transcription profiling by array of Drosophila melanogaster CDK8 and Cyclin C homozygous mutants, determined using Affymetrix GeneChip Drosophila Genome 2.0 Array\"" +
            "}," +
            "{" +
                "\"geneCount\":4," +
                "\"organism\":\"drosophila melanogaster\"," +
                "\"contrastId\":\"g2_g3\"," +
                "\"comparison\":\"genotype:'cdk8 mutant' vs 'wild type'\"," +
                "\"experimentAccession\":\"E-MTAB-1066\"," +
                "\"experimentName\":\"Transcription profiling by array of Drosophila melanogaster CDK8 and Cyclin C homozygous mutants, determined using Affymetrix GeneChip Drosophila Genome 2.0 Array\"" +
            "}," +
            "{" +
                "\"geneCount\":8," +
                "\"organism\":\"homo sapiens\"," +
                "\"contrastId\":\"g2_g1\"," +
                "\"comparison\":\"'Apical anaerobic' vs. 'Conventional'\"," +
                "\"experimentAccession\":\"E-GEOD-43049\"," +
                "\"experimentName\":\"Caco-2 cells:cultured in conventional vs apical anaerobic conditions\"" +
            "}" +
            "]";

    @Before
    public void setUp() {
        when(experimentTraderMock.getPublicExperiment("E-GEOD-38400")).thenReturn(E_GEOD_38400ExperimentMock);
        when(experimentTraderMock.getPublicExperiment("E-MEXP-1276")).thenReturn(E_MEXP_1276ExperimentMock);
        when(experimentTraderMock.getPublicExperiment("E-GEOD-2507")).thenReturn(E_GEOD_2507ExperimentMock);
        when(experimentTraderMock.getPublicExperiment("E-GEOD-3779")).thenReturn(E_GEOD_3779ExperimentMock);
        when(experimentTraderMock.getPublicExperiment("E-MTAB-1066")).thenReturn(E_MTAB_1066ExperimentMock);
        when(experimentTraderMock.getPublicExperiment("E-GEOD-43049")).thenReturn(E_GEOD_43049ExperimentMock);

        when(E_GEOD_38400ExperimentMock.getDescription()).thenReturn("RNA-seq of Arabidopsis mutants with defects in long-non-coding-RNA-mediated transcriptional silencing");
        when(E_MEXP_1276ExperimentMock.getDescription()).thenReturn("Transcription profiling by array of pancreatic cells from C57BL/6 mice following dibenzazepine treatment");
        when(E_GEOD_2507ExperimentMock.getDescription()).thenReturn("Transcription profiling by array of skeletal and cardiac muscle from dysferlin-deficient mice");
        when(E_GEOD_3779ExperimentMock.getDescription()).thenReturn("Transcription profiling by array of mouse neurospheres cultured from p107-/- embryos and their wildtype littermates");
        when(E_MTAB_1066ExperimentMock.getDescription()).thenReturn("Transcription profiling by array of Drosophila melanogaster CDK8 and Cyclin C homozygous mutants, determined using Affymetrix GeneChip Drosophila Genome 2.0 Array");
        when(E_GEOD_43049ExperimentMock.getDescription()).thenReturn("Caco-2 cells:cultured in conventional vs apical anaerobic conditions");

        when(contrastTraderMock.getContrast("E-GEOD-38400", "g1_g2")).thenReturn(G1_G2E_GEOD_38400ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-38400", "g1_g3")).thenReturn(G1_G3E_GEOD_38400ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-38400", "g1_g4")).thenReturn(G1_G4E_GEOD_38400ContrastMock);
        when(contrastTraderMock.getContrast("E-MEXP-1276", "g2_g1")).thenReturn(G2_G1E_MEXP_1276ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-2507", "g4_g1")).thenReturn(G4_G1E_GEOD_2507ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-2507", "g3_g2")).thenReturn(G3_G2E_GEOD_2507ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-3779", "g3_g4")).thenReturn(G3_G4E_GEOD_3779ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-3779", "g2_g1")).thenReturn(G2_G1E_GEOD_3779ContrastMock);
        when(contrastTraderMock.getContrast("E-MTAB-1066", "g2_g1")).thenReturn(G2_G1E_MTAB_1066ContrastMock);
        when(contrastTraderMock.getContrast("E-MTAB-1066", "g2_g3")).thenReturn(G2_G3E_MTAB_1066ContrastMock);
        when(contrastTraderMock.getContrast("E-GEOD-43049", "g2_g1")).thenReturn(G2_G1E_GEOD_43049ContrastMock);

        when(G1_G2E_GEOD_38400ContrastMock.getDisplayName()).thenReturn("nrpe1 mutant vs wild type");
        when(G1_G3E_GEOD_38400ContrastMock.getDisplayName()).thenReturn("swi3b mutant vs wild type");
        when(G1_G4E_GEOD_38400ContrastMock.getDisplayName()).thenReturn("idn2 mutant vs wild type");
        when(G2_G1E_MEXP_1276ContrastMock.getDisplayName()).thenReturn("compound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36");
        when(G4_G1E_GEOD_2507ContrastMock.getDisplayName()).thenReturn("'dysferlin mutant' vs 'wild type' in 'left ventricle cardiac muscle'");
        when(G3_G2E_GEOD_2507ContrastMock.getDisplayName()).thenReturn("'dysferlin mutant' vs 'wild type' in 'skeletal muscle'");
        when(G3_G4E_GEOD_3779ContrastMock.getDisplayName()).thenReturn("genotype:'p107 -/-' vs 'wild type' on A-AFFY-23");
        when(G2_G1E_GEOD_3779ContrastMock.getDisplayName()).thenReturn("genotype:'p107 -/-' vs 'wild type' on A-AFFY-24");
        when(G2_G1E_MTAB_1066ContrastMock.getDisplayName()).thenReturn("genotype:'cycC mutant' vs 'wild type'");
        when(G2_G3E_MTAB_1066ContrastMock.getDisplayName()).thenReturn("genotype:'cdk8 mutant' vs 'wild type'");
        when(G2_G1E_GEOD_43049ContrastMock.getDisplayName()).thenReturn("'Apical anaerobic' vs. 'Conventional'");

        subject = new DifferentialAnalyticsFacetsReader(experimentTraderMock, contrastTraderMock);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Test
    public void normaldResultsTest() {
        assertThat(gson.toJson(subject.extractResultsAsJson(ZINC_FINGER_RESPONSE)), is(ZINC_FINGER_RESULTS));
    }

    @Test
    public void noResultsTest() {
        assertThat(gson.toJson(subject.extractResultsAsJson(FOOBAR_RESPONSE)), is(FOOBAR_RESULTS));
    }

}