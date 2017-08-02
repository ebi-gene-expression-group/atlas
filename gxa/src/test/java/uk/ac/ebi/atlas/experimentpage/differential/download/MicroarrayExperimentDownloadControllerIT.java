package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.ExperimentDownloadController;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class MicroarrayExperimentDownloadControllerIT {

    private static final int GENE_NAME_INDEX = 1;

    @Inject
    ExperimentDownloadController subject;

    @Inject
    ExpressionAtlasExperimentTrader experimentTrader;

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
        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(dataColumns);
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

    private ImmutableMap<String, String[]> indexByGeneName(List<String> lines) {
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

}