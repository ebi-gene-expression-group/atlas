package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.profiles.differential.rnaseq.RnaSeqProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.RnaSeqProfilesTSVWriter;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesWriterIT {

    private static final int GENE_NAME_INDEX = 1;

    private RnaSeqProfilesWriter subject;

    @Inject
    private RnaSeqDiffExperimentsCache experimentsCache;

    @Inject
    RnaSeqRequestContextBuilder contextBuilder;

    @Mock
    PrintWriter printWriterMock;

    private DifferentialRequestPreferences requestPreferences = new DifferentialRequestPreferences();

    @Inject
    public RnaSeqProfilesTSVWriter geneProfileTsvWriter;

    @Inject
    private RnaSeqProfileStreamFactory inputStreamFactory;

    @Inject
    private SolrQueryService solrQueryService;

    @Inject
    private ExperimentTrader experimentTrader;

    private RnaSeqRequestContext populateRequestContext(String experimentAccession) throws ExecutionException {
        MockitoAnnotations.initMocks(this);
        DifferentialExperiment experiment = experimentsCache.getExperiment(experimentAccession);

        RnaSeqRequestContext requestContext = contextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();

        geneProfileTsvWriter.setRequestContext(requestContext);

        subject = new RnaSeqProfilesWriter(geneProfileTsvWriter, inputStreamFactory, solrQueryService);

        return requestContext;
    }

    @Test
    public void testSomeExperiments() throws Exception{
        Set<String> rnaSeqExperiments = experimentTrader.getRnaSeqDifferentialExperimentAccessions();
        assertTrue(rnaSeqExperiments.size()>0);

        for(String accession: rnaSeqExperiments){
            defaultParametersHeader(accession);
            teardown();

            weHaveSomeResults(accession);
            teardown();

            notSpecific(accession);
            teardown();

            upDownRegulationWorks(accession);
            teardown();

            noDataWithVeryStrictPValueCutoff(accession);
            teardown();

            noDataWithVeryLargeFoldChangeCutoff(accession);
            teardown();
        }
    }

    private void teardown(){
        Mockito.reset(printWriterMock);
        requestPreferences = new DifferentialRequestPreferences();
    }

    public void defaultParametersHeader(String accession) throws GenesNotFoundException, ExecutionException {
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        subject.write(printWriterMock, requestContext);

        String[] lines = headerLines();
        String queryLine = lines[1];

        assertTrue(Pattern.matches(".*Genes .* up/down differentially expressed.*"+accession,
                queryLine));

        String[] columnHeaders = csvLines().get(0);
        //System.out.println("\"" + Joiner.on("\", \"").join(columnHeaders) + "\"");
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertThat(columnHeaders.length, greaterThan(2));
    }

    public void weHaveSomeResults(String accession) throws GenesNotFoundException, ExecutionException {
        requestPreferences.setCutoff(1D);
        requestPreferences.setFoldChangeCutOff(0D);
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        long genesCount = subject.write(printWriterMock, requestContext);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        if(genesCount ==0 ){
            fail();
        }
        assertThat((int) genesCount, greaterThan(0) );
        assertEquals(genesCount, geneNames.size());
    }


    public void notSpecific(String accession) throws GenesNotFoundException, ExecutionException {
        requestPreferences.setSpecific(false);
        weHaveSomeResults(accession);
    }
    
    public void upDownRegulationWorks(String accession) throws GenesNotFoundException, ExecutionException {
        requestPreferences.setRegulation(Regulation.UP);

        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        long genesCountUp = subject.write(printWriterMock, requestContext);

        List<String> geneNamesUp = geneNames(csvLines());

        teardown();

        requestPreferences.setRegulation(Regulation.DOWN);

        requestContext = populateRequestContext(accession);
        long genesCountDown = subject.write(printWriterMock, requestContext);

        List<String> geneNamesDown = geneNames(csvLines());

        teardown();

        requestPreferences.setRegulation(Regulation.UP_DOWN);

        requestContext = populateRequestContext(accession);
        long genesCountUpDown = subject.write(printWriterMock, requestContext);

        List<String> geneNamesUpDown = geneNames(csvLines());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertEquals(resultsSeparately,new HashSet<>(geneNamesUpDown) );
        assertTrue(genesCountUp+genesCountDown >= genesCountUpDown);

         }

    
    public void noDataWithVeryStrictPValueCutoff(String accession) throws GenesNotFoundException, ExecutionException {
        requestPreferences.setCutoff(1e-100);
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        long genesCount = subject.write(printWriterMock, requestContext);
        assertEquals(0L, genesCount);
    }


    
    public void noDataWithVeryLargeFoldChangeCutoff(String accession) throws GenesNotFoundException, ExecutionException {
        requestPreferences.setFoldChangeCutOff(50000D);
        RnaSeqRequestContext requestContext = populateRequestContext(accession);
        long genesCount = subject.write(printWriterMock, requestContext);

        assertEquals(0L, genesCount);
    }

    private String[] headerLines() {
        ArgumentCaptor<String> lineCaptor = ArgumentCaptor.forClass(String.class);
        verify(printWriterMock, times(1)).write(lineCaptor.capture());

        return lineCaptor.getAllValues().get(0).split("\n");
    }

    private List<String[]> csvLines() {
        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        return lineCaptor.getAllValues();
    }


    private ImmutableMap<String, String[]> indexByGeneName(List<String[]> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String line[] : lines) {
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.put(line[GENE_NAME_INDEX-1]+" "+ line[GENE_NAME_INDEX], line);
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

}