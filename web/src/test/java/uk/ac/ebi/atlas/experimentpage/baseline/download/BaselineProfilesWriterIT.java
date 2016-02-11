package uk.ac.ebi.atlas.experimentpage.baseline.download;

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
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesWriterIT {

    public static final String E_MTAB_513 = "E-MTAB-513";
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");
    public static final int GENE_NAME_INDEX = 1;
    private static final int GENE_SET_NAME_INDEX = 0;

    private BaselineProfilesWriter subject;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    BaselineRequestContextBuilder baselineRequestContextBuilder;

    @Mock
    PrintWriter printWriterMock;

    @Mock
    CSVWriter csvWriterMock;

    @Mock
    private CsvWriterFactory csvWriterFactoryMock;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    @Inject
    private BaselineProfileInputStreamFactory baselineProfileInputStreamFactory;
    @Inject
    private BaselineProfileStreamPipelineBuilder baselineProfileStreamPipelineBuilder;

    @Inject
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Value("classpath:/file-templates/download-headers-baseline.txt")
    public Resource tsvFileMastheadTemplateResource;

    private BaselineRequestContext populateRequestContext(String experimentAccession) throws ExecutionException {
        MockitoAnnotations.initMocks(this);
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(experimentAccession);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        BaselineRequestContext baselineRequestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences)
                .build();

        BaselineProfilesTSVWriter baselineProfilesTSVWriter = new BaselineProfilesTSVWriter(csvWriterFactoryMock);
        baselineProfilesTSVWriter.setRequestContext(baselineRequestContext);
        baselineProfilesTSVWriter.setTsvFileMastheadTemplateResource(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);

        subject = new BaselineProfilesWriter(baselineProfileStreamPipelineBuilder, baselineProfilesTSVWriter, baselineProfileInputStreamFactory, loadGeneIdsIntoRequestContext);

        return baselineRequestContext;
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true
    @Test
    public void eMTab513_Specific() throws GenesNotFoundException, ExecutionException {
        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.write(printWriterMock, requestContext);

        int expectedCount = 147;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));


        String[] mettl25 = geneNameToLine.get("METTL25");
        String[] srsf2 = geneNameToLine.get("SRSF2");
        String[] nebl = geneNameToLine.get("NEBL");

        assertThat(geneNames, containsInAnyOrder("RP11-466P24.2", "METTL25", "THOC3", "AHI1", "SNX27", "MRPL13", "RTDR1", "TERF2", "PRMT8", "TRPM2", "IL13RA1", "GFI1", "PRSS16", "CALU", "SCN2A", "ARHGAP1", "RGS7BP", "BMI1", "TMSB10", "SLC10A1", "RP11-192H23.4", "B3GNT1", "THOC6", "RGP1", "PTBP3", "ZKSCAN5", "VTI1B", "CBX1", "DCAF4L2", "FAM47B", "C6orf203", "COL4A3BP", "LIPF", "FZD7", "EFNB2", "DVL1", "BET1", "CPAMD8", "C1QL3", "MFSD7", "SRGAP3", "PPT1", "TGDS", "OCEL1", "DEPTOR", "RAE1", "VRK3", "CRY2", "GSTZ1", "NEBL", "RNF41", "TUBA1C", "CCNT2", "MSH6", "C6orf1", "PLCG2", "CC2D1B", "POLR2B", "GPAT2", "PVRL4", "GPD2", "FAM102B", "CTNNA1", "CRLS1", "ABCG8", "GOLPH3L", "VPS4A", "NOTUM", "RNF25", "FBXO38", "ASNS", "MT-ATP6", "CTNNBIP1", "APOBR", "SBK2", "SNRPA", "RRP8", "ZDHHC18", "RTN4", "IRF2BPL", "AIDA", "CCDC66", "SEMA3G", "ARHGAP8", "C17orf85", "C1QTNF2", "RHBDF1", "NEDD8", "UBQLNL", "ZNF350", "RAB27B", "IL12RB2", "POLE3", "MYOD1", "TEX33", "ZSCAN5B", "RAB13", "WDR76", "ABCD4", "GLB1L2", "COL15A1", "S1PR1", "ASPA", "FAM89A", "SLAMF6", "ZCRB1", "RNF208", "ZFP2", "VAPB", "ACTL7A", "MAPRE2", "PMM2", "ZNF236", "ARPC5", "CCNE2", "CRISP3", "RANBP17", "USP26", "DSCC1", "ERLIN1", "BOD1", "OSBP2", "BICD1", "RPRD2", "SLK", "ZNF713", "AXIN2", "C17orf64", "FAM172A", "KDM4A", "TMEM56", "WWC2", "DAPL1", "ATP1B3", "TRIM65", "ESPL1", "GFPT1", "MKS1", "GGPS1", "PLEKHB2", "EMCN", "EPB41L4A", "INPP5D", "NEK3", "WFDC10A", "INPP4A", "SRSF2"));
        assertThat(mettl25, is(new String[]{"ENSG00000127720", "METTL25", null, "2", "2", "0.9", "3", "3", "3", "2", "1", "2", "5", "4", "3", "0.7", "4", "0.9"}));
        assertThat(srsf2, is(new String[]{"ENSG00000161547", "SRSF2", null, null, null, null, null, null, "0.6", null, null, null, null, null, null, null, null, null}));
        assertThat(nebl, is(new String[] {"ENSG00000078114", "NEBL", "1", "0.7", "210", "17", "0.9", "5", "3", "0.6", null, "4", "11", null, "38", "59", null, "3"}));
    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on
    @Test
    public void eMTab513_NotSpecific() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        eMTab513_Specific();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513_Specific_QueryFactorLeukocyte() throws GenesNotFoundException, ExecutionException {
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.write(printWriterMock, requestContext);
        int expectedCount = 10;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] gpd2 = geneNameToLine.get("GPD2");
        String[] myod1 = geneNameToLine.get("MYOD1");

        assertThat(geneNames, containsInAnyOrder("TERF2", "DVL1", "DEPTOR", "CRY2", "CC2D1B", "GPD2", "VPS4A", "IL12RB2", "MYOD1", "RPRD2"));
        assertThat(gpd2, is(new String[]{"ENSG00000115159", "GPD2", "8", "4", "4", "7", "9", "6", "9", "5", "17", "8", "6", "8", "10", "13", "1", "6"}));
        assertThat(myod1, is(new String[]{"ENSG00000129152", "MYOD1", null, null, null, null, null, null, null, null, "2", null, null, null, "0.7", null, null, null}));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.write(printWriterMock, requestContext);
        int expectedCount = 101;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] snx27 = geneNameToLine.get("SNX27");
        String[] bmi1 = geneNameToLine.get("BMI1");

        assertThat(geneNames, containsInAnyOrder("METTL25", "THOC3", "AHI1", "SNX27", "MRPL13", "RTDR1", "TERF2", "IL13RA1", "CALU", "ARHGAP1", "BMI1", "TMSB10", "B3GNT1", "THOC6", "PTBP3", "ZKSCAN5", "VTI1B", "CBX1", "C6orf203", "FZD7", "EFNB2", "DVL1", "BET1", "CPAMD8", "PPT1", "TGDS", "OCEL1", "DEPTOR", "RAE1", "VRK3", "CRY2", "GSTZ1", "RNF41", "TUBA1C", "CCNT2", "MSH6", "C6orf1", "PLCG2", "CC2D1B", "POLR2B", "GPAT2", "GPD2", "CTNNA1", "CRLS1", "GOLPH3L", "VPS4A", "RNF25", "ASNS", "CTNNBIP1", "SNRPA", "RRP8", "ZDHHC18", "RTN4", "IRF2BPL", "AIDA", "CCDC66", "SEMA3G", "C17orf85", "RHBDF1", "NEDD8", "RP11-466P24.2", "ZNF350", "IL12RB2", "POLE3", "MYOD1", "RAB13", "ABCD4", "COL15A1", "S1PR1", "ASPA", "FAM89A", "ZCRB1", "RNF208", "ZFP2", "VAPB", "MAPRE2", "PMM2", "ZNF236", "ARPC5", "DSCC1", "ERLIN1", "BOD1", "OSBP2", "BICD1", "RPRD2", "SLK", "AXIN2", "FAM172A", "KDM4A", "TMEM56", "WWC2", "ATP1B3", "TRIM65", "GFPT1", "MKS1", "PLEKHB2", "EMCN", "EPB41L4A", "INPP5D", "NEK3", "INPP4A"));
        assertThat(snx27, is(new String[]{"ENSG00000143376", "SNX27", "9", "5", "8", "6", "10", "10", "15", "10", "11", "7", "14", "20", "8", "14", "4", "8"}));
        assertThat(bmi1, is(new String[] {"ENSG00000168283", "BMI1", "16", "22", "14", "18", "28", "34", "38", "45", "21", "18", "29", "23", "22", "23", "13", "43"}));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&geneQuery=R-HSA-372790%09R-HSA-388396%09R-HSA-109582%09R-HSA-162582%09R-HSA-1430728%09R-HSA-168256%09R-HSA-74160%09R-HSA-1643685%09R-HSA-1280218%09R-HSA-168249%09R-HSA-392499%09R-HSA-556833%09R-HSA-382551%09R-HSA-1640170%09R-HSA-212436&exactMatch=true
    @Test
    public void eMTab513_NotSpecific_MultipleGeneSets() throws GenesNotFoundException, ExecutionException {
        setNotSpecific();
        eMTab513_Specific_MultipleGeneSets();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&geneQuery=R-HSA-372790%09R-HSA-388396%09R-HSA-109582%09R-HSA-162582%09R-HSA-1430728%09R-HSA-168256%09R-HSA-74160%09R-HSA-1643685%09R-HSA-1280218%09R-HSA-168249%09R-HSA-392499%09R-HSA-556833%09R-HSA-382551%09R-HSA-1640170%09R-HSA-212436&geneSetMatch=true
    @Test
    public void eMTab513_Specific_MultipleGeneSets() throws GenesNotFoundException, ExecutionException {
        String geneSets = "R-HSA-372790\tR-HSA-388396\tR-HSA-109582\tR-HSA-162582\tR-HSA-1430728\tR-HSA-168256\tR-HSA-74160\tR-HSA-1643685\tR-HSA-1280218\tR-HSA-168249\tR-HSA-392499\tR-HSA-556833\tR-HSA-382551\tR-HSA-1640170\tR-HSA-212436";
        setGeneQuery(geneSets);

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.writeAsGeneSets(printWriterMock, requestContext);
        int expectedCount = 15;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneSetName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] r_hsa_1430728 = geneNameToLine.get("R-HSA-1430728");
        String[] r_hsa_212436 = geneNameToLine.get("R-HSA-212436");

        assertThat(geneNames, containsInAnyOrder(geneSets.split("\t")));
        assertThat(r_hsa_1430728, is(new String[]{"R-HSA-1430728", "8", "681", "9", "440", "430", "15", "9", "500", "8", "8", "10", "275", "550", "17", "11", "8"}));
        assertThat(r_hsa_212436, is(new String[] {"R-HSA-212436", "2", "4", "2", "2", "5", "6", "5", "3", "2", "3", "4", "4", "5", "3", "1", "2"}));
    }


    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=R-HSA-74160&specific=true&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513r_hsa_74160_Specific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException, ExecutionException {
        setQueryFactor(FACTOR_LEUKOCYTE);
        setGeneQuery("R-HSA-74160");

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.writeAsGeneSets(printWriterMock, requestContext);
        int expectedCount = 0;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneSetName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        // no results because Leukoctye is not specific to R-HSA-74160
        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, is(empty()));
    }


    private Factor factor(String factorValue) {
        return new Factor("ORGANISM_PART", factorValue);
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
        requestPreferences.setGeneQuery(GeneQuery.create(geneQuery));
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

    private void setQueryFactor(Factor factor) {
        requestPreferences.setQueryFactorType(factor.getType());
        requestPreferences.setQueryFactorValues(Collections.singleton(factor.getValue()));
    }

}
