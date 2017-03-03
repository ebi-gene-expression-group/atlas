package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.io.StringWriter;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class MicroarrayExperimentDownloadControllerIT {

    private static final int GENE_NAME_INDEX = 1;

    @Inject
    MicroarrayExperimentDownloadController subject;

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

    private Pair<List<String>, List<String>> headersAndBody(Writer responseWriter){
        List<String> headers = new ArrayList<>();
        List<String> body = new ArrayList<>();

        for(String l : responseWriter.toString().split("\n")){
            if(l.startsWith("#")){
                headers.add(l);
            } else {
                body.add(l);
            }
        }

        return Pair.of(headers, body);
    }

    public void defaultParametersHeader(MicroarrayExperiment experiment) throws Exception {
        StringWriter responseWriter = new StringWriter(100000);

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();

        long genesCount = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);

        Pair<List<String>, List<String>> headersAndBody = headersAndBody(responseWriter);

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
        StringWriter responseWriter = new StringWriter(100000);

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        if(Math.random() < 0.5){
            requestPreferences.setSpecific(false);
        }
        requestPreferences.setCutoff(1D);
        requestPreferences.setFoldChangeCutOff(0D);
        long genesCount = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);
        Pair<List<String>, List<String>> headersAndBody = headersAndBody(responseWriter);


        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(headersAndBody.getRight());
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat((int) genesCount, greaterThan(0) );
        assertEquals(genesCount, geneNames.size());


    }

    public void upDownRegulationWorks(MicroarrayExperiment experiment) throws Exception {
        StringWriter responseWriter = new StringWriter(100000);

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setRegulation(Regulation.UP);

        long genesCountUp = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);

        List<String> geneNamesUp = geneNames(headersAndBody(responseWriter).getRight());

        responseWriter = new StringWriter(100000);
        requestPreferences.setRegulation(Regulation.DOWN);

        long genesCountDown = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);

        List<String> geneNamesDown = geneNames(headersAndBody(responseWriter).getRight());

        responseWriter = new StringWriter(100000);
        requestPreferences.setRegulation(Regulation.UP_DOWN);


        long genesCountUpDown = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);

        List<String> geneNamesUpDown = geneNames(headersAndBody(responseWriter).getRight());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertEquals(resultsSeparately,new HashSet<>(geneNamesUpDown) );
        assertTrue(genesCountUp+genesCountDown >= genesCountUpDown);

    }


    public void noDataWithVeryStrictPValueCutoff(MicroarrayExperiment experiment) throws Exception {
        StringWriter responseWriter = new StringWriter(100000);

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setCutoff(1e-100);
        long genesCount = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);

        assertEquals(0L, genesCount);
    }



    public void noDataWithVeryLargeFoldChangeCutoff(MicroarrayExperiment experiment) throws Exception {
        StringWriter responseWriter = new StringWriter(100000);

        MicroarrayRequestPreferences requestPreferences = new MicroarrayRequestPreferences();
        requestPreferences.setFoldChangeCutOff(50000D);
        long genesCount = subject.fetchAndWriteGeneProfiles(responseWriter, experiment, requestPreferences);

        assertEquals(0L, genesCount);
    }

    private ImmutableMap<String, String[]> indexByGeneName(List<String> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String l : lines) {
            String[] line = l.split("\t", -1);
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.put(line[GENE_NAME_INDEX-1]+" "+ line[GENE_NAME_INDEX]+" "+ line[GENE_NAME_INDEX+1], line);
            }
        }

        return builder.build();
    }

    private List<String> geneNames(List<String> lines) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (String l : lines) {
            String[] line = l.split("\t" ,-1);
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.add(line[GENE_NAME_INDEX]);
            }
        }

        return builder.build();
    }

}