package uk.ac.ebi.atlas.experimentpage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.*;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class ExperimentDownloadControllerIT {

    private static final int GENE_NAME_INDEX = 1;

    @Inject
    ExperimentDownloadController subject;

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

    @Inject
    DataFileHub dataFileHub;

    @Test
    public void testSomeMicroarrayExperiments() throws Exception{
        Set<String> rnaSeqExperiments = experimentTrader.getMicroarrayExperimentAccessions();
        assertTrue(rnaSeqExperiments.size()>0);

        for(String accession: rnaSeqExperiments){

            MicroarrayExperiment differentialExperiment = (MicroarrayExperiment)
                    experimentTrader.getPublicExperiment(accession);

            defaultParametersHeader(differentialExperiment);

            weHaveSomeResults(differentialExperiment);

            upDownRegulationWorks(differentialExperiment);

            noDataWithVeryStrictPValueCutoff(differentialExperiment);

            noDataWithVeryLargeFoldChangeCutoff(differentialExperiment);
        }
    }

    private Pair<List<String>, List<String>> headersAndBody(MockHttpServletResponse response){
        List<String> headers = new ArrayList<>();
        List<String> body = new ArrayList<>();

        try {
            for(String l : response.getContentAsString().split("\n")){
                if(l.startsWith("#")){
                    headers.add(l);
                } else {
                    body.add(l);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return Pair.of(headers, body);
    }

    public void defaultParametersHeader(MicroarrayExperiment experiment) throws Exception {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertTrue(Pattern.matches(".*Genes .* up/down differentially expressed.*"+experiment.getAccession(),
                queryLine));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertEquals("Design Element", columnHeaders[2]);
        assertThat(columnHeaders.length, greaterThan(3));
    }

    public void weHaveSomeResults(MicroarrayExperiment experiment) throws Exception {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        if(Math.random() < 0.5){
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(1D);
        requestPreferences.setFoldChangeCutoff(0D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneNameMicroarray(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns.size(), greaterThan(0) );
        assertEquals(dataColumns.size(), geneNames.size());


    }

    public void upDownRegulationWorks(MicroarrayExperiment experiment) throws Exception {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setRegulation(Regulation.UP);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        List<String> geneNamesUp = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.DOWN);

        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesDown = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.UP_DOWN);


        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesUpDown = geneNames(headersAndBody(response).getRight());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertEquals(resultsSeparately,new HashSet<>(geneNamesUpDown) );
    }


    public void noDataWithVeryStrictPValueCutoff(MicroarrayExperiment experiment) throws Exception {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setCutoff(1e-100);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertEquals(1, headersAndBody.getRight().size());
    }



    public void noDataWithVeryLargeFoldChangeCutoff(MicroarrayExperiment experiment) throws Exception {

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setFoldChangeCutoff(50000D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.microarrayExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertEquals(1, headersAndBody.getRight().size());
    }

    private ImmutableMap<String, String[]> indexByGeneNameMicroarray(List<String> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String l : lines) {
            String[] line = l.split("\t", -1);
            builder.put(line[GENE_NAME_INDEX-1]+" "+ line[GENE_NAME_INDEX]+" "+ line[GENE_NAME_INDEX+1], line);
        }

        return builder.build();
    }

    private List<String> geneNames(List<String> lines) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (String l : lines) {
            String[] line = l.split("\t" ,-1);
            builder.add(line[GENE_NAME_INDEX]);
        }

        return builder.build();
    }

    @Test
    public void testSomeRnaSeqExperiments() throws Exception{
        Set<String> rnaSeqExperiments = experimentTrader.getRnaSeqDifferentialExperimentAccessions();
        assertTrue(rnaSeqExperiments.size()>0);

        for(String accession: rnaSeqExperiments){

            DifferentialExperiment differentialExperiment = (DifferentialExperiment)
                    experimentTrader.getPublicExperiment(accession);

            defaultParametersHeader(differentialExperiment);

            weHaveSomeResults(differentialExperiment);

            upDownRegulationWorks(differentialExperiment);

            noDataWithVeryStrictPValueCutoff(differentialExperiment);

            noDataWithVeryLargeFoldChangeCutoff(differentialExperiment);
        }
    }

    public void defaultParametersHeader(DifferentialExperiment experiment) throws Exception {

        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertTrue(Pattern.matches(".*Genes .* up/down differentially expressed.*"+experiment.getAccession(),
                queryLine));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertThat(columnHeaders.length, greaterThan(2));
    }

    public void weHaveSomeResults(DifferentialExperiment experiment) throws Exception {

        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        if(Math.random() < 0.5){
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(1D);
        requestPreferences.setFoldChangeCutoff(0D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns.size(), greaterThan(0) );
        assertEquals(dataColumns.size(), geneNames.size());
    }

    public void upDownRegulationWorks(DifferentialExperiment experiment) throws Exception {

        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        requestPreferences.setRegulation(Regulation.UP);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        List<String> geneNamesUp = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.DOWN);

        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesDown = geneNames(headersAndBody(response).getRight());

        response = new MockHttpServletResponse();
        requestPreferences.setRegulation(Regulation.UP_DOWN);


        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        List<String> geneNamesUpDown = geneNames(headersAndBody(response).getRight());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertEquals(resultsSeparately,new HashSet<>(geneNamesUpDown) );

    }

    public void noDataWithVeryStrictPValueCutoff(DifferentialExperiment experiment) throws Exception {
        //Sometimes the pValue is actually 0.0 due to rounding errors and then what can you do e.g. in E-GEOD-59612, not in test datasets though
        MockHttpServletResponse response = new MockHttpServletResponse();
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        requestPreferences.setCutoff(1e-100);
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        assertEquals(1, headersAndBody(response).getRight().size());
    }



    public void noDataWithVeryLargeFoldChangeCutoff(DifferentialExperiment experiment) throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();
        requestPreferences.setFoldChangeCutoff(50000D);
        subject.rnaSeqDifferentialExperimentDownload(experiment.getAccession(), "", requestPreferences, response);

        assertEquals(1, headersAndBody(response).getRight().size());
    }

    private ImmutableMap<String, String[]> indexByGeneName(List<String> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String l : lines) {
            String[] line = l.split("\t",-1);
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.put(line[GENE_NAME_INDEX-1]+" "+ line[GENE_NAME_INDEX], line);
            }
        }

        return builder.build();
    }


    @Test
    public void testSomeRnaSeqBaselineExperiments() throws Exception{
        Set<String> rnaSeqExperiments = experimentTrader.getPublicExperimentAccessions(ExperimentType.RNASEQ_MRNA_BASELINE);
        assertTrue(rnaSeqExperiments.size()>0);

        for(String accession: rnaSeqExperiments){
            for(ExpressionUnit.Absolute.Rna unit: dataFileHub.getRnaSeqBaselineExperimentFiles(accession).dataFiles()){
                BaselineExperiment experiment = (BaselineExperiment)
                        experimentTrader.getPublicExperiment(accession);

                defaultParametersHeaderRnaSeqBaseline(experiment, unit);

                weHaveSomeResultsRnaSeqBaseline(experiment, unit);

                noDataWithVeryLargeCutoffRnaSeqBaseline(experiment,unit);
            }
        }
    }

    public void defaultParametersHeaderRnaSeqBaseline(BaselineExperiment experiment,ExpressionUnit.Absolute.Rna unit) throws Exception {

        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(unit);

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqBaselineExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertTrue(Pattern.matches(".*Genes .* expressed.*"+experiment.getAccession(),
                queryLine));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertThat(columnHeaders.length, greaterThan(2));
    }

    public void weHaveSomeResultsRnaSeqBaseline(BaselineExperiment experiment,ExpressionUnit.Absolute.Rna unit) throws Exception {

        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(unit);

        if(Math.random() < 0.5){
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(0.0);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqBaselineExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneNameMicroarray(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns.size(), greaterThan(0) );
        assertEquals(dataColumns.size(), geneNames.size());


    }

    public void noDataWithVeryLargeCutoffRnaSeqBaseline(BaselineExperiment experiment,ExpressionUnit.Absolute.Rna unit) throws Exception {

        RnaSeqBaselineRequestPreferences requestPreferences = new RnaSeqBaselineRequestPreferences();
        requestPreferences.setUnit(unit);

        requestPreferences.setCutoff(10000000D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.rnaSeqBaselineExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertEquals(1, headersAndBody.getRight().size());
    }


    @Test
    public void testSomeProteomicsBaselineExperiments() throws Exception{
        Set<String> experimentAccessions = experimentTrader.getPublicExperimentAccessions(ExperimentType.PROTEOMICS_BASELINE);
        assertTrue(experimentAccessions.size()>0);

        for(String accession: experimentAccessions){

            BaselineExperiment experiment = (BaselineExperiment)
                    experimentTrader.getPublicExperiment(accession);

            defaultParametersHeaderProteomicsBaseline(experiment);

            weHaveSomeResultsProteomicsBaseline(experiment);

            noDataWithVeryLargeCutoffProteomicsBaseline(experiment);

        }
    }

    public void defaultParametersHeaderProteomicsBaseline(BaselineExperiment experiment) throws Exception {

        ProteomicsBaselineRequestPreferences requestPreferences = new ProteomicsBaselineRequestPreferences();

        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.proteomicsExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        String queryLine = headersAndBody.getLeft().get(1);

        assertTrue(Pattern.matches(".*Genes .* expressed.*"+experiment.getAccession(),
                queryLine));

        String[] columnHeaders = headersAndBody.getRight().get(0).split("\t");
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertThat(columnHeaders.length, greaterThan(2));
    }

    public void weHaveSomeResultsProteomicsBaseline(BaselineExperiment experiment) throws Exception {

        ProteomicsBaselineRequestPreferences requestPreferences = new ProteomicsBaselineRequestPreferences();
        if(Math.random() < 0.5){
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(0.0);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.proteomicsExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);


        List<String> dataColumns = headersAndBody.getRight().subList(1, headersAndBody.getRight().size());
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneNameMicroarray(dataColumns);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(dataColumns.size(), greaterThan(0) );
        assertEquals(dataColumns.size(), geneNames.size());


    }

    public void noDataWithVeryLargeCutoffProteomicsBaseline(BaselineExperiment experiment) throws Exception {

        ProteomicsBaselineRequestPreferences requestPreferences = new ProteomicsBaselineRequestPreferences();

        requestPreferences.setCutoff(100D);
        MockHttpServletResponse response = new MockHttpServletResponse();
        subject.proteomicsExperimentDownload(experiment.getAccession(), "", requestPreferences, response);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(response);

        assertEquals(1, headersAndBody.getRight().size());
    }

}