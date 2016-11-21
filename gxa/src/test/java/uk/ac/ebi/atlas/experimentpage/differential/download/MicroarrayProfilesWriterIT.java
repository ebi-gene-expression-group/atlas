package uk.ac.ebi.atlas.experimentpage.differential.download;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
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
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.microarray.MicroarrayProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class MicroarrayProfilesWriterIT {

    public static final int GENE_NAME_INDEX = 1;
    
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
    private ExpressionAtlasExperimentTrader experimentTrader;

    private MicroarrayRequestContext setUpAndPopulateRequestContext(String experimentAccession){
        MockitoAnnotations.initMocks(this);
        MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

        MicroarrayRequestContext requestContext = contextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();

        MicroarrayProfilesTSVWriter geneProfileTsvWriter = new MicroarrayProfilesTSVWriter(csvWriterFactoryMock);
        geneProfileTsvWriter.setRequestContext(requestContext);
        geneProfileTsvWriter.setTsvFileMastheadTemplate(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);

        subject = new MicroarrayProfilesWriter(geneProfileTsvWriter, inputStreamFactory, solrQueryService);

        return requestContext;

    }

    @Test
    public void testAllMicroarrayExperiments() throws Exception {
        Set<String> accessions = experimentTrader.getMicroarrayExperimentAccessions();

        for (String accession : accessions) {
            MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(accession);
            String arrayDesignAccession = experiment.getArrayDesignAccessions().iterator().next();
            Set<String> queryFactors = experiment.getContrastIds();

            defaultParametersHeader(accession, arrayDesignAccession);
            teardown();

            defaultParameters(accession, arrayDesignAccession);
            teardown();

            notSpecific(accession, arrayDesignAccession);
            teardown();

            upDownRegulationWorks(accession, arrayDesignAccession);
            teardown();

            verySmallPValueGivesNoData(accession, arrayDesignAccession);
            teardown();

            veryLargeLogFoldCutoffGivesNoData(accession, arrayDesignAccession);
            teardown();

            withContrastQueryFactorNonspecific(accession, arrayDesignAccession, queryFactors);
            teardown();

            withContrastQueryFactor(accession, arrayDesignAccession, queryFactors);
            teardown();
        }

    }

    private void teardown() {
        Mockito.reset(printWriterMock, csvWriterFactoryMock, csvWriterMock);
        requestPreferences = new MicroarrayRequestPreferences();
        subject = null;
    }

    public void defaultParametersHeader(String accession, String arrayDesignAccession) throws Exception {
        setFoldChangeCutOff(0);
        MicroarrayRequestContext requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);

        String[] lines = headerLines();
        String queryLine = lines[1];

        assertTrue(queryLine, Pattern.matches(".*Genes.*differentially expressed.*any contrast.*experiment " +
                accession, queryLine));

        String[] columnHeaders = csvLines().get(0);
        assertEquals("Gene ID", columnHeaders[0]);
        assertEquals("Gene Name", columnHeaders[1]);
        assertEquals("Design Element", columnHeaders[2]);
        assertTrue(columnHeaders.length > 3);
    }

    public void defaultParameters(String accession, String arrayDesignAccession) throws Exception {
        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);

        MicroarrayRequestContext requestContext = setUpAndPopulateRequestContext(accession);
        long genesCount = subject.write(printWriterMock, requestContext, arrayDesignAccession);

        List<String> geneNames = geneNames(csvLines());

        assertThat((int) genesCount, greaterThan(0));
        assertEquals(genesCount, geneNames.size());
    }

    public void notSpecific(String accession, String arrayDesignAccession) throws Exception {
        setNotSpecific();
        defaultParameters(accession, arrayDesignAccession);
    }


    public void upDownRegulationWorks(String accession, String arrayDesignAccession) throws Exception {
        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestPreferences.setRegulation(Regulation.UP);
        MicroarrayRequestContext requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesUp = indexByGeneName(csvLines()).keySet();

        teardown();

        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestPreferences.setRegulation(Regulation.DOWN);
        requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesDown = indexByGeneName(csvLines()).keySet();

        teardown();

        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestPreferences.setRegulation(Regulation.UP_DOWN);
        requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesUpDown = indexByGeneName(csvLines()).keySet();

        teardown();

        assertTrue(geneNamesUpDown.size() > 0);
        assertTrue(geneNamesUpDown.containsAll(geneNamesUp));
        assertTrue(geneNamesUpDown.containsAll(geneNamesDown));
    }


    public void verySmallPValueGivesNoData(String accession, String arrayDesignAccession) throws Exception {
        requestPreferences.setCutoff(1E-100);

        MicroarrayRequestContext requestContext = setUpAndPopulateRequestContext(accession);
        long genesCount = subject.write(printWriterMock, requestContext, arrayDesignAccession);
        assertEquals(accession, 0, genesCount);
    }

    public void veryLargeLogFoldCutoffGivesNoData(String accession, String arrayDesignAccession) throws Exception {
        requestPreferences.setFoldChangeCutOff(1e100);

        MicroarrayRequestContext requestContext = setUpAndPopulateRequestContext(accession);
        long genesCount = subject.write(printWriterMock, requestContext, arrayDesignAccession);
        assertEquals(accession, 0, genesCount);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g3&_specific=on

    public void withContrastQueryFactor(String accession, String arrayDesignAccession, Set<String> queryFactors) throws Exception {
        MicroarrayRequestContext requestContext;

        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestPreferences.setQueryFactorValues(queryFactors);
        requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext,
                arrayDesignAccession);
        Set<String> geneNamesForAllQueryFactors = indexByGeneName(csvLines()).keySet();

        teardown();

        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesForUnspecifiedQueryFactors = indexByGeneName(csvLines()).keySet();

        teardown();

        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestContext = setUpAndPopulateRequestContext(accession);
        requestPreferences.setQueryFactorValues(Collections.singleton(queryFactors.iterator().next()));
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesForOneQueryFactor = indexByGeneName(csvLines()).keySet();

        teardown();

        assertEquals(geneNamesForAllQueryFactors, geneNamesForUnspecifiedQueryFactors);
        assertTrue(geneNamesForAllQueryFactors.containsAll(geneNamesForOneQueryFactor));
    }

    public void withContrastQueryFactorNonspecific(String accession, String arrayDesignAccession, Set<String>
            queryFactors) throws Exception {
        MicroarrayRequestContext requestContext;

        setNotSpecific();
        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestPreferences.setQueryFactorValues(queryFactors);
        requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesForAllQueryFactors = indexByGeneName(csvLines()).keySet();

        teardown();

        setNotSpecific();
        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestContext = setUpAndPopulateRequestContext(accession);
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesForUnspecifiedQueryFactors = indexByGeneName(csvLines()).keySet();

        teardown();

        setNotSpecific();
        setFoldChangeCutOff(0);
        requestPreferences.setCutoff(1d);
        requestContext = setUpAndPopulateRequestContext(accession);
        requestPreferences.setQueryFactorValues(Collections.singleton(queryFactors.iterator().next()));
        subject.write(printWriterMock, requestContext, arrayDesignAccession);
        Set<String> geneNamesForOneQueryFactor = indexByGeneName(csvLines()).keySet();

        teardown();

        assertEquals(geneNamesForAllQueryFactors, geneNamesForUnspecifiedQueryFactors);
        assertTrue(geneNamesForAllQueryFactors.containsAll(geneNamesForOneQueryFactor));
        assertTrue(geneNamesForOneQueryFactor.size() > 0);
    }

    private void setFoldChangeCutOff(double cutOff) {
        requestPreferences.setFoldChangeCutOff(cutOff);
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
        requestPreferences.setGeneQuery(SemanticQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

}
