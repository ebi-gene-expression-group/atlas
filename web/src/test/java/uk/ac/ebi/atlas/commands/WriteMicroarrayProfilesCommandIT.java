package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matcher;
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
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.streams.InputStreamFactory;
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
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class WriteMicroarrayProfilesCommandIT {

    public static final int GENE_NAME_INDEX = 1;
    public static final String E_GEOD_8122 = "E-GEOD-8122";
    private static final String E_GEOD_43049 = "E-GEOD-43049";
    private static final String E_MTAB_1066 = "E-MTAB-1066";

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

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv
    @Test
    public void defaultParameters() throws GenesNotFoundException {
        populateRequestContext(E_MTAB_1066);
        long genesCount = subject.execute(E_MTAB_1066);

        List<String> geneNames = geneNames(recordedLines());

        int expectedCount = 174;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        assertThat(geneNames, containsInAnyOrder("Irc", "CG33054", "Prosbeta5", "CG14265", "wuc", "CG5043", "CG9782", "CG1103", "CG18472", "CG12943", "Takr99D", "CG17974", "CG3967", "MCPH1", "Mst84Db", "Mbs", "skpB", "CG32444", "Syx13", "CG1443", "CG13965", "CG13398", "CG15403", "ds", "CG4669", "CanA1", "dj", "CG33459", "CG12680", "Mbs", "Dmn", "CG15286", "Peritrophin-15b", "PSR", "CG8929", "CG9072", "CG7742", "sls", "CG30380", "lkb1", "CG42732", "CG14410", "CG9254", "rtGEF", "lin19", "CG9586", "CG42813", "CG14872", "Dh44-R2", "CG12689", "CG15213", "CG9981", "CG32391", "ftz", "CG14605", "Jupiter", "bru-3", "PGRP-LA", "CG9815", "CG3332", "CG7550", "CG30272", "CG10996", "Ykt6", "Bgb", "Ten-a", "CG14269", "CG11703", "HP1c", "CG5213", "CG15628", "ACXD", "PMCA", "CG3107", "sno", "Cpr92F", "CG1368", "yip3", "CG6490", "CG34010", "CG31606", "Cks30A", "CG8800", "CG31463", "CG8298", "CG42788", "Got1", "DMAP1", "CG30263", "cher", "CG14446", "CG16865", "Pros35", "Ady43A", "Atg8b", "CG14006", "CG3558", "CG10435", "CG14540", "olf186-M", "mthl2", "CG10663", "pUf68", "CSN5", "RfC3", "mod(mdg4)", "CG32686", "CG31715", "m2", "CG8303", "Paip2", "CG9589", "CG42329", "CG13876", "CG8003", "SmD1", "CG31624", "CG6870", "Ubp64E", "heph", "CG31139", "Ork1", "CG13604", "Elo68beta", "Karybeta3", "CG31803", "CG17669", "CG15043", "Kr", "pgant8", "Cda5", "Pk1r", "CG2150", "CG12975", "Cpr12A", "CG31954", "CG11790", "MED8", "Marf", "Tequila", "Dic3", "CG17843", "svp", "sba", "CG3376", "CG3704", "CG14694", "CG13531", "dock", "NC2alpha", "CG15642", "CG15615", "CG33523", "jhamt", "CG9040", "yip7", "CG7006", "RpS30", "Mst36Fa", "CG30393", "CG6361", "CG8184", "Rm62", "CG10165", "Hsp60B", "CG6403", "CG11147", "WASp", "CG2064", "Pkcdelta", "mRpL21", "Muc68Ca", "Tdc2", "CG7180"));
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
        populateRequestContext(E_GEOD_43049);
        long genesCount = subject.execute(E_GEOD_43049);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 23;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] sc5d = geneNameToLine.get("SC5D");
        String[] c19orf44 = geneNameToLine.get("C19orf44");

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "DEPDC7", "GNA12", "TMEM154", "CKAP2", "S100A14", "AC016629.3", "TMEM70", "PSMA7", "NFX1", "DPP8", "SS18L2", "SSR1", "MTMR3", "RPL22P19", "C19orf44"));
        assertThat(c19orf44, is("ENSG00000105072\tC19orf44\tA_23_P107801\t0.0450131744387415\t-0.220014162430453\t-3.10009164460448".split("\t")));
        assertThat(sc5d, is("ENSG00000109929\tSC5D\tA_23_P372888\t1.08617530258573E-4\t1.44526451950158\t10.8203265434862".split("\t")));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?geneQuery=protein_coding
    @Test
    public void geneQuery_ProteinCoding() throws GenesNotFoundException {
        setGeneQuery("protein_coding");
        populateRequestContext(E_GEOD_43049);
        long genesCount = subject.execute(E_GEOD_43049);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 21;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "DEPDC7", "GNA12", "TMEM154", "CKAP2", "S100A14", "TMEM70", "PSMA7", "NFX1", "DPP8", "SS18L2", "SSR1", "MTMR3", "C19orf44"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?regulation=UP
    @Test
    public void upRegulatedOnly() throws GenesNotFoundException {
        requestPreferences.setRegulation(Regulation.UP);
        populateRequestContext(E_GEOD_43049);
        long genesCount = subject.execute(E_GEOD_43049);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 9;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "DEPDC7", "CKAP2", "S100A14", "AC016629.3", "NFX1", "MTMR3", "RPL22P19"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?regulation=DOWN
    @Test
    public void downRegulatedOnly() throws GenesNotFoundException {
        requestPreferences.setRegulation(Regulation.DOWN);
        populateRequestContext(E_GEOD_43049);
        long genesCount = subject.execute(E_GEOD_43049);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 14;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2", "GNA12", "TMEM154", "TMEM70", "PSMA7", "DPP8", "SS18L2", "SSR1", "C19orf44"));
    }

    // http://localhost:8080/gxa/experiments/E-GEOD-43049.tsv?cutoff=0.002
    @Test
    public void withCutoff() throws GenesNotFoundException {
        requestPreferences.setCutoff(0.002);
        populateRequestContext(E_GEOD_43049);
        long genesCount = subject.execute(E_GEOD_43049);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 8;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("SC5D", "SPIRE1", "NICN1", "C3orf18", "DGCR2", "KCTD15", "DCAF8", "COG2"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g1&_specific=on
    @Test
    public void withContrastQueryFactor() throws GenesNotFoundException {
        setNotSpecific();
        withContrastQueryFactor_Specific();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-1066.tsv?queryFactorValues=g2_g1
    @Test
    public void withContrastQueryFactor_Specific() throws GenesNotFoundException {
        requestPreferences.setQueryFactorValues(Collections.singleton("g2_g1"));
        populateRequestContext(E_MTAB_1066);
        long genesCount = subject.execute(E_MTAB_1066);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();


        System.out.println("\"" + Joiner.on("\", \"").join(geneNames) + "\"");

        int expectedCount = 154;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        assertThat(geneNames, containsInAnyOrder("Irc", "CG33054", "Prosbeta5", "CG14265", "wuc", "CG5043", "CG1103", "CG18472", "CG12943", "Takr99D", "CG17974", "CG3967", "MCPH1", "Mst84Db", "Mbs", "skpB", "CG32444", "Syx13", "CG1443", "CG13965", "CG15403", "ds", "CG4669", "CanA1", "dj", "CG33459", "CG12680", "Dmn", "CG15286", "Peritrophin-15b", "PSR", "CG8929", "CG9072", "CG7742", "CG30380", "lkb1", "CG42732", "CG14410", "CG9254", "rtGEF", "lin19", "CG42813", "CG14872", "Dh44-R2", "CG12689", "CG15213", "CG9981", "CG32391", "ftz", "CG14605", "Jupiter", "PGRP-LA", "CG9815", "CG7550", "CG30272", "CG10996", "Bgb", "Ten-a", "CG14269", "CG11703", "HP1c", "CG5213", "CG15628", "ACXD", "PMCA", "CG3107", "sno", "CG1368", "yip3", "CG6490", "CG34010", "CG31606", "Cks30A", "CG8800", "CG31463", "CG8298", "Got1", "DMAP1", "CG30263", "cher", "CG14446", "CG16865", "Pros35", "Atg8b", "CG14006", "CG3558", "CG10435", "CG14540", "olf186-M", "mthl2", "CG10663", "CSN5", "RfC3", "mod(mdg4)", "CG32686", "CG31715", "m2", "CG8303", "Paip2", "CG9589", "CG42329", "CG13876", "SmD1", "CG31624", "CG6870", "Ubp64E", "heph", "CG31139", "Ork1", "CG13604", "Elo68beta", "Karybeta3", "CG31803", "CG17669", "CG15043", "pgant8", "Cda5", "Pk1r", "CG2150", "CG12975", "Cpr12A", "CG31954", "CG11790", "MED8", "Marf", "Tequila", "Dic3", "CG17843", "sba", "CG3376", "CG3704", "CG14694", "CG13531", "dock", "NC2alpha", "CG15642", "CG15615", "jhamt", "CG9040", "yip7", "CG7006", "RpS30", "Mst36Fa", "CG30393", "CG8184", "Rm62", "CG10165", "Hsp60B", "CG6403", "CG11147", "WASp", "CG2064", "Pkcdelta", "CG7180"));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-8122.tsv
    @Test
    public void eGeod8122() throws GenesNotFoundException {
        populateRequestContext(E_GEOD_8122);
        long genesCount = subject.execute(E_GEOD_8122);

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(recordedLines());
        Set<String> geneNames = geneNameToLine.keySet();

        int expectedCount = 1;
        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] vma16 = geneNameToLine.get("VMA16");

        assertThat(geneNames, containsInAnyOrder("VMA16"));
        assertThat(vma16, is("YHR026W\tVMA16\t1779820_at\t0.0415772172547035\t-0.540554090488339\t-6.70529872966727".split("\t")));
    }


    // http://localhost:8080/gxa/experiments/E-GEOD-8122.tsv?geneQuery=YHR026W
    @Test
    public void eGeod8122_GeneQuery_For_Gene_Not_In_Experiment_Species() throws GenesNotFoundException {
        setGeneQuery("YHR026W");
        eGeod8122();
    }

    private List<String[]> recordedLines() {
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
