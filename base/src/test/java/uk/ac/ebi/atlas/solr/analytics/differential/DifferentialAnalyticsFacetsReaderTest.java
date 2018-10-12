//package uk.ac.ebi.atlas.search.analyticsindex.differential;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import uk.ac.ebi.atlas.model.Experiment;
//import uk.ac.ebi.atlas.model.ExperimentType;
//import uk.ac.ebi.atlas.model.differential.Contrast;
//import uk.ac.ebi.atlas.trader.ContrastTrader;
//import uk.ac.ebi.atlas.trader.ExperimentTrader;
//import uk.ac.ebi.atlas.utils.ColourGradient;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.Is.is;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class DifferentialAnalyticsFacetsReaderTest {
//
//    private DifferentialFacetsReader subject;
//
//    private Gson gson;
//
//    @Mock
//    ExperimentTrader experimentTraderMock;
//
//    @Mock
//    ContrastTrader contrastTraderMock;
//
//    @Mock
//    ColourGradient colourGradientMock;
//
//    @Mock
//    Experiment E_MTAB_698ExperimentMock, E_MEXP_1276ExperimentMock, E_GEOD_21860ExperimentMock,
//               E_GEOD_22351ExperimentMock, E_GEOD_12108ExperimentMock;
//
//    @Mock
//    Contrast G1_G2E_MTAB_698ContrastMock, G2_G1E_MEXP_1276ContrastMock, G1_G2E_GEOD_21860ContrastMock,
//             G1_G2E_GEOD_22351ContrastMock, G3_G1E_GEOD_12108ContrastMock, G3_G2E_GEOD_12108ContrastMock;
//
//    private final String FOOBAR_RESPONSE ="{\n" +
//            "  \"response\":{\"numFound\":0,\"start\":0,\"docs\":[]\n" +
//            "  },\n" +
//            "  \"facets\":{\n" +
//            "    \"count\":0}}";
//
//    private final String FOOBAR_RESULTS = "\"[]\"";
//
//    private final String ASPM_RESPONSE = "{\n" +
//            "  \"response\":{},\n" +
//            "  \"facets\":{\n" +
//            "    \"count\":11,\n" +
//            "    \"species\":{\n" +
//            "      \"buckets\":[{\n" +
//            "          \"val\":\"mus musculus\",\n" +
//            "          \"count\":7,\n" +
//            "          \"experimentType\":{\n" +
//            "            \"buckets\":[{\n" +
//            "                \"val\":\"rnaseq_mrna_differential\",\n" +
//            "                \"count\":5,\n" +
//            "                \"experimentAccession\":{\n" +
//            "                  \"buckets\":[{\n" +
//            "                      \"val\":\"E-MTAB-698\",\n" +
//            "                      \"count\":3,\n" +
//            "                      \"contrastId\":{\n" +
//            "                        \"buckets\":[{\n" +
//            "                            \"val\":\"g1_g2\",\n" +
//            "                            \"count\":3,\n" +
//            "                            \"geneCount\":1}]}},\n" +
//            "                    {\n" +
//            "                      \"val\":\"E-GEOD-21860\",\n" +
//            "                      \"count\":1,\n" +
//            "                      \"contrastId\":{\n" +
//            "                        \"buckets\":[{\n" +
//            "                            \"val\":\"g1_g2\",\n" +
//            "                            \"count\":1,\n" +
//            "                            \"geneCount\":1}]}},\n" +
//            "                    {\n" +
//            "                      \"val\":\"E-GEOD-22351\",\n" +
//            "                      \"count\":1,\n" +
//            "                      \"contrastId\":{\n" +
//            "                        \"buckets\":[{\n" +
//            "                            \"val\":\"g1_g2\",\n" +
//            "                            \"count\":1,\n" +
//            "                            \"geneCount\":1}]}}]}},\n" +
//            "              {\n" +
//            "                \"val\":\"microarray_1colour_mrna_differential\",\n" +
//            "                \"count\":2,\n" +
//            "                \"experimentAccession\":{\n" +
//            "                  \"buckets\":[{\n" +
//            "                      \"val\":\"E-MEXP-1276\",\n" +
//            "                      \"count\":2,\n" +
//            "                      \"contrastId\":{\n" +
//            "                        \"buckets\":[{\n" +
//            "                            \"val\":\"g2_g1\",\n" +
//            "                            \"count\":2,\n" +
//            "                            \"geneCount\":1}]}}]}}]}},\n" +
//            "        {\n" +
//            "          \"val\":\"homo sapiens\",\n" +
//            "          \"count\":4,\n" +
//            "          \"experimentType\":{\n" +
//            "            \"buckets\":[{\n" +
//            "                \"val\":\"microarray_1colour_mrna_differential\",\n" +
//            "                \"count\":4,\n" +
//            "                \"experimentAccession\":{\n" +
//            "                  \"buckets\":[{\n" +
//            "                      \"val\":\"E-GEOD-12108\",\n" +
//            "                      \"count\":4,\n" +
//            "                      \"contrastId\":{\n" +
//            "                        \"buckets\":[{\n" +
//            "                            \"val\":\"g3_g1\",\n" +
//            "                            \"count\":2,\n" +
//            "                            \"geneCount\":1},\n" +
//            "                          {\n" +
//            "                            \"val\":\"g3_g2\",\n" +
//            "                            \"count\":2,\n" +
//            "                            \"geneCount\":1}]}}]}}]}}]}}}";
//
//    private final String ASPM_RESULTS = "\"[" +
//            "{" +
//                "\\\"geneCount\\\":1, " +
//                "\\\"organism\\\":\\\"mus musculus\\\", " +
//                "\\\"contrastId\\\":\\\"g1_g2\\\", " +
//                "\\\"comparison\\\":\\\"sex:'male' vs 'female'\\\", " +
//                "\\\"experimentAccession\\\":\\\"E-MTAB-698\\\", " +
//                "\\\"experimentName\\\":\\\"RNA-seq of vomeronasal tissue from adult male and female mice\\\"" +
//            "}, " +
//            "{" +
//                "\\\"geneCount\\\":1, " +
//                "\\\"organism\\\":\\\"mus musculus\\\", " +
//                "\\\"contrastId\\\":\\\"g1_g2\\\", " +
//                "\\\"comparison\\\":\\\"clinical information:'non-pregnant' vs 'pregnant'\\\", " +
//                "\\\"experimentAccession\\\":\\\"E-GEOD-21860\\\", " +
//                "\\\"experimentName\\\":\\\"RNA-seq of pancreatic islets from pregnant mice\\\"" +
//            "}, " +
//            "{" +
//                "\\\"geneCount\\\":1, " +
//                "\\\"organism\\\":\\\"mus musculus\\\", " +
//                "\\\"contrastId\\\":\\\"g1_g2\\\", " +
//                "\\\"comparison\\\":\\\"genotype:'expressing human TDP-43' vs 'non transgenic'\\\", " +
//                "\\\"experimentAccession\\\":\\\"E-GEOD-22351\\\", " +
//                "\\\"experimentName\\\":\\\"RNA-seq of mouse spinal cord expressing wild type human TDP-43\\\"" +
//            "}, " +
//            "{" +
//                "\\\"geneCount\\\":1, " +
//                "\\\"organism\\\":\\\"mus musculus\\\", " +
//                "\\\"contrastId\\\":\\\"g2_g1\\\", " +
//                "\\\"comparison\\\":\\\"compound treatment:'10 micromole per kilogram dibenzazepine' vs
// 'none' on A-AFFY-36\\\", " +
//                "\\\"experimentAccession\\\":\\\"E-MEXP-1276\\\", " +
//                "\\\"experimentName\\\":\\\"Transcription profiling by array of pancreatic cells from C57BL/6 mice
// following dibenzazepine treatment\\\"" +
//            "}, " +
//            "{" +
//                "\\\"geneCount\\\":1, " +
//                "\\\"organism\\\":\\\"homo sapiens\\\", " +
//                "\\\"contrastId\\\":\\\"g3_g1\\\", " +
//                "\\\"comparison\\\":\\\"'Francisella tularensis Schu S4' vs 'uninfected'\\\", " +
//                "\\\"experimentAccession\\\":\\\"E-GEOD-12108\\\", " +
//                "\\\"experimentName\\\":\\\"Transcription profiling by array of human peripheral blood mononuclear " +
//                        "cells infected with Francisella tularensis\\\"" +
//            "}, " +
//            "{" +
//                "\\\"geneCount\\\":1, " +
//                "\\\"organism\\\":\\\"homo sapiens\\\", " +
//                "\\\"contrastId\\\":\\\"g3_g2\\\", " +
//                "\\\"comparison\\\":\\\"'Francisella tularensis novicida' vs 'uninfected'\\\", " +
//                "\\\"experimentAccession\\\":\\\"E-GEOD-12108\\\", " +
//                "\\\"experimentName\\\":\\\"Transcription profiling by array of human peripheral blood mononuclear " +
//                        "cells infected with Francisella tularensis\\\"" +
//            "}" +
//        "]\"";
//
//
//    @Before
//    public void setUp() {
//        subject = new DifferentialFacetsReader();
//        gson = new GsonBuilder().disableHtmlEscaping().create();
//        //gson = new GsonBuilder().create();
//    }
//
//    @Test
//    public void normalResultsTest() {
//        when(experimentTraderMock.getExperimentFromCache("E-MTAB-698", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL))
//                .thenReturn(E_MTAB_698ExperimentMock);
//        when(experimentTraderMock.getExperimentFromCache(
//                "E-MEXP-1276", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL))
//                .thenReturn(E_MEXP_1276ExperimentMock);
//        when(experimentTraderMock.getExperimentFromCache("E-GEOD-21860", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL))
//                .thenReturn(E_GEOD_21860ExperimentMock);
//        when(experimentTraderMock.getExperimentFromCache("E-GEOD-22351", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL))
//                .thenReturn(E_GEOD_22351ExperimentMock);
//        when(experimentTraderMock.getExperimentFromCache(
//                "E-GEOD-12108", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL))
//                .thenReturn(E_GEOD_12108ExperimentMock);
//
//        when(E_MTAB_698ExperimentMock.getDescription())
//                .thenReturn("RNA-seq of vomeronasal tissue from adult male and female mice");
//        when(E_MEXP_1276ExperimentMock.getDescription())
//                .thenReturn(
//                        "Transcription profiling by array of pancreatic cells from C57BL/6 mice following " +
//                        "dibenzazepine treatment");
//        when(E_GEOD_21860ExperimentMock.getDescription())
//                .thenReturn("RNA-seq of pancreatic islet from pregnant mice");
//        when(E_GEOD_22351ExperimentMock.getDescription())
//                .thenReturn("RNA-seq of mouse spinal cord expressing wild type human TDP-43");
//        when(E_GEOD_12108ExperimentMock.getDescription())
//                .thenReturn(
//                        "Transcription profiling by array of human peripheral blood mononuclear cells infected " +
//                        "with Francisella tularensis");
//
//        when(contrastTraderMock.getContrastFromCache(
//                "E-MTAB-698", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, "g1_g2"))
//                .thenReturn(G1_G2E_MTAB_698ContrastMock);
//        when(contrastTraderMock.getContrastFromCache(
//                "E-MEXP-1276", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "g2_g1"))
//                .thenReturn(G2_G1E_MEXP_1276ContrastMock);
//        when(contrastTraderMock.getContrastFromCache(
//                "E-GEOD-21860", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, "g1_g2"))
//                .thenReturn(G1_G2E_GEOD_21860ContrastMock);
//        when(contrastTraderMock.getContrastFromCache(
//                "E-GEOD-22351", ExperimentType.RNASEQ_MRNA_DIFFERENTIAL, "g1_g2"))
//                .thenReturn(G1_G2E_GEOD_22351ContrastMock);
//        when(contrastTraderMock.getContrastFromCache(
//                "E-GEOD-12108", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "g3_g1"))
//                .thenReturn(G3_G1E_GEOD_12108Contrast Mock);
//        when(contrastTraderMock.getContrastFromCache(
//                "E-GEOD-12108", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, "g3_g2"))
//                .thenReturn(G3_G2E_GEOD_12108ContrastMock);
//
//        when(G1_G2E_MTAB_698ContrastMock.getDisplayName())
//                .thenReturn("sex:'male' vs 'female'");
//        when(G2_G1E_MEXP_1276ContrastMock.getDisplayName())
//                .thenReturn("compound treatment:'10 micromole per kilogram dibenzazepine' vs 'none' on A-AFFY-36");
//        when(G1_G2E_GEOD_21860ContrastMock.getDisplayName())
//                .thenReturn("clinical information:'non-pregnant' vs 'pregnant'");
//        when(G1_G2E_GEOD_22351ContrastMock.getDisplayName())
//                .thenReturn("genotype:'expressing human TDP-43' vs 'non transgenic'");
//        when(G3_G1E_GEOD_12108ContrastMock.getDisplayName())
//                .thenReturn("'Francisella tularensis Schu S4' vs 'uninfected'");
//        when(G3_G2E_GEOD_12108ContrastMock.getDisplayName())
//                .thenReturn("'Francisella tularensis novicida' vs 'uninfected'");
//
//
//        assertThat(gson.toJson(subject.generateFacetsTreeJson(ASPM_RESPONSE)), is(ASPM_RESULTS));
//    }
//
//    @Test
//    public void noResultsTest() {
//        assertThat(gson.toJson(subject.generateFacetsTreeJson(FOOBAR_RESPONSE)), is(FOOBAR_RESULTS));
//    }
//
//}
