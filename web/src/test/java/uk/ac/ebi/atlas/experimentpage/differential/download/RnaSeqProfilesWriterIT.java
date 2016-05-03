package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RnaSeqProfilesWriterIT {

    public static final int GENE_NAME_INDEX = 1;

    private static final String ACCESSION = "E-GEOD-22351";

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
        RnaSeqRequestContext requestContext = populateRequestContext(ACCESSION);
        subject.write(printWriterMock, requestContext);

        String[] lines = headerLines();
        String queryLine = lines[1];

        assertTrue(Pattern.matches(".*Genes .* up/down differentially expressed.*"+ACCESSION,
                queryLine));

        String[] columnHeaders = csvLines().get(0);
        //System.out.println("\"" + Joiner.on("\", \"").join(columnHeaders) + "\"");
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertThat(columnHeaders.length, greaterThan(2));
                    }


    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv
    @Test
    public void defaultParameters() throws GenesNotFoundException, ExecutionException {
        RnaSeqRequestContext requestContext = populateRequestContext(ACCESSION);
        long genesCount = subject.write(printWriterMock, requestContext);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(csvLines());
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat((int) genesCount, greaterThan(0) );
        assertEquals(genesCount, geneNames.size());
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv&_specific=on
    @Test
    public void notSpecific() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        defaultParameters();
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-21860.tsv?geneQuery=protein_coding
    @Test
    public void proteinCodingContainsAllResults() throws GenesNotFoundException, ExecutionException {
        setGeneQuery("protein_coding");
        RnaSeqRequestContext requestContext = populateRequestContext(ACCESSION);
        long genesCount = subject.write(printWriterMock, requestContext);

        List<String> geneNames = geneNames(csvLines());

        Mockito.reset(printWriterMock);
        setGeneQuery("");
        requestContext = populateRequestContext(ACCESSION);
        long genesCount2 = subject.write(printWriterMock, requestContext);

        List<String> geneNames2 = geneNames(csvLines());

        assertTrue(genesCount>0);
        assertEquals(geneNames, geneNames2);
        assertEquals(genesCount, genesCount2);

    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?regulation=UP
    @Test
    public void upDownRegulationWorks() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setRegulation(Regulation.UP);

        RnaSeqRequestContext requestContext = populateRequestContext(ACCESSION);
        long genesCountUp = subject.write(printWriterMock, requestContext);

        List<String> geneNamesUp = geneNames(csvLines());

        Mockito.reset(printWriterMock);

        requestPreferences.setRegulation(Regulation.DOWN);

        requestContext = populateRequestContext(ACCESSION);
        long genesCountDown = subject.write(printWriterMock, requestContext);

        List<String> geneNamesDown = geneNames(csvLines());

        Mockito.reset(printWriterMock);

        requestPreferences.setRegulation(Regulation.UP_DOWN);

        requestContext = populateRequestContext(ACCESSION);
        long genesCountUpDown = subject.write(printWriterMock, requestContext);

        List<String> geneNamesUpDown = geneNames(csvLines());

        Set<String> resultsSeparately = new HashSet<>();
        resultsSeparately.addAll(geneNamesUp);
        resultsSeparately.addAll(geneNamesDown);

        assertEquals(resultsSeparately,new HashSet<>(geneNamesUpDown) );
        assertEquals(genesCountUp+genesCountDown, genesCountUpDown);

         }


    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setCutoff(0.00000001);
        RnaSeqRequestContext requestContext = populateRequestContext(ACCESSION);
        long genesCount = subject.write(printWriterMock, requestContext);

        assertEquals(0L, genesCount);

    }

    // http://localhost:8080/gxa/experiments/E-GEOD-38400.tsv?foldChangeCutOff=5
    @Test
    public void withFoldChangeCutoff() throws GenesNotFoundException, ExecutionException {
        requestPreferences.setFoldChangeCutOff(50000D);
        RnaSeqRequestContext requestContext = populateRequestContext(ACCESSION);
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