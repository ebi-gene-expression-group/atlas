package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVWriter;
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
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.commands.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.commands.download.CsvWriterFactory;
import uk.ac.ebi.atlas.commands.download.MicroarrayProfilesTSVWriter;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class WriteMicroarrayProfilesCommandIT {

    public static final int GENE_NAME_INDEX = 1;
    private static final int GENE_SET_NAME_INDEX = 0;
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_GEOD_8122 = "E-GEOD-8122";

    private WriteMicroarrayProfilesCommand subject;

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

    @Value("classpath:/file-templates/download-headers-baseline.txt")
    public Resource tsvFileMastheadTemplateResource;

    @Inject
    private InputStreamFactory inputStreamFactory;

    @Inject
    private GeneProfilesFilter geneProfilesFilter;

    private void populateRequestContext(String experimentAccession) {
        MockitoAnnotations.initMocks(this);
        MicroarrayExperiment experiment = microarrayExperimentsCache.getExperiment(experimentAccession);

        requestPreferences.setArrayDesignAccession(experiment.getArrayDesignAccessions().first());

        MicroarrayRequestContext requestContext = contextBuilder.forExperiment(experiment)
                .withPreferences(requestPreferences)
                .build();

        MicroarrayProfilesTSVWriter geneProfileTsvWriter = new MicroarrayProfilesTSVWriter(csvWriterFactoryMock);
        geneProfileTsvWriter.setRequestContext(requestContext);
        geneProfileTsvWriter.setTsvFileMastheadTemplateResource(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);

        subject = new WriteMicroarrayProfilesCommand(geneProfileTsvWriter, requestContext, inputStreamFactory);
        subject.setGeneProfilesFilter(geneProfilesFilter);

        subject.setResponseWriter(printWriterMock);

    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv
    @Test
    public void eGeod43049() throws GenesNotFoundException {
        populateRequestContext(E_GEOD_43049);
        long genesCount = subject.execute(E_GEOD_43049);

        int expectedCount = 23;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));


        String[] sc5d = geneNameToLine.get("SC5D");
        String[] c19orf44 = geneNameToLine.get("C19orf44");

        //System.out.println(Joiner.on("\", \"").join(geneNames));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "DEPDC7", "GNA12", "TMEM154", "CKAP2", "S100A14", "AC016629.3", "TMEM70", "PSMA7", "NFX1", "DPP8", "SS18L2", "SSR1", "MTMR3", "RPL22P19", "C19orf44"));
        assertThat(c19orf44, is("ENSG00000105072\tC19orf44\tA_23_P107801\t0.0450131744387415\t-0.220014162430453\t-3.10009164460448".split("\t")));
        assertThat(sc5d, is("ENSG00000109929\tSC5D\tA_23_P372888\t1.08617530258573E-4\t1.44526451950158\t10.8203265434862".split("\t")));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-8122.tsv
    @Test
    public void eGeod8122() throws GenesNotFoundException {
        populateRequestContext(E_GEOD_8122);
        long genesCount = subject.execute(E_GEOD_8122);

        int expectedCount = 1;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] vma16 = geneNameToLine.get("VMA16");

        //System.out.println(Joiner.on("\", \"").join(geneNames));

        assertThat(geneNames, containsInAnyOrder("VMA16"));
        assertThat(vma16, is("YHR026W\tVMA16\t1779820_at\t0.0415772172547035\t-0.540554090488339\t-6.70529872966727".split("\t")));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-8122.tsv?geneQuery=YHR026W
    @Test
    public void eGeod8122_GeneQuery_For_Gene_Not_In_Experiment_Species() throws GenesNotFoundException {
        setGeneQuery("YHR026W");
        eGeod8122();
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

    private ImmutableMap<String, String[]> indexByGeneSetName(List<String[]> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String line[] : lines) {
            String geneName = line[GENE_SET_NAME_INDEX];
            if (!"Gene set".equals(geneName)) {
                builder.put(line[GENE_SET_NAME_INDEX], line);
            }
        }

        return builder.build();
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setGeneSet() {
        requestPreferences.setGeneSetMatch(true);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }


}
