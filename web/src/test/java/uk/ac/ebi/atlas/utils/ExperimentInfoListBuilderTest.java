
package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentInfoListBuilderTest {

    private static final String FACTOR_NAME = "FACTOR_NAME";
    private static final String SPECIES = "SPECIES";
    private static final String ACCESSION = "ACCESSION";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String CONTRAST = "CONTRAST";
    private static final String ASSAY_1 = "ASSAY1";
    private static final String ASSAY_2 = "ASSAY2";
    private static final String ARRAY = "ARRAY";
    private static final String MICROARRAY = "MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL";
    private static final String DIFFERENTIAL = "RNASEQ_MRNA_DIFFERENTIAL";

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    private BaselineExperimentsCache baselineExperimentsCacheMock;

    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;

    @Mock
    private MicroarrayExperimentsCache microarrayExperimentsCacheMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCacheMock;

    @Mock
    private PublicExperimentTypesCache publicExperimentTypesCacheMock;

    @Mock
    private DifferentialExperiment differentialExperimentMock;

    @Mock
    private MicroarrayExperiment microarrayExperimentMock;

    @Mock
    private ArrayDesignTrader arrayDesignTraderMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private ExperimentInfoListBuilder subject;


    @Before
    public void setUp() throws Exception {

        Date lastUpdateStub = new GregorianCalendar(39 + 1900, 12, 12).getTime();

        when(experimentDesignMock.getFactorHeaders()).thenReturn(Sets.newTreeSet(Sets.newHashSet(FACTOR_NAME)));
        when(baselineExperimentMock.getExperimentDesign()).thenReturn(experimentDesignMock);
        when(differentialExperimentMock.getExperimentDesign()).thenReturn(experimentDesignMock);
        when(microarrayExperimentMock.getExperimentDesign()).thenReturn(experimentDesignMock);
        when(microarrayExperimentMock.getType()).thenReturn(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);

        when(baselineExperimentMock.getOrganisms()).thenReturn(Sets.newHashSet(SPECIES));
        when(baselineExperimentMock.getAccession()).thenReturn(ACCESSION);
        when(baselineExperimentMock.getLastUpdate()).thenReturn(lastUpdateStub);
        when(baselineExperimentMock.getDescription()).thenReturn(DESCRIPTION);
        when(baselineExperimentMock.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_BASELINE);

        when(experimentTraderMock.getBaselineExperimentAccessions()).thenReturn(Sets.newHashSet(ACCESSION));
        when(experimentTraderMock.getRnaSeqDifferentialExperimentAccessions()).thenReturn(Sets.newHashSet(DIFFERENTIAL));
        when(experimentTraderMock.getMicroarrayExperimentAccessions()).thenReturn(Sets.newHashSet(MICROARRAY));

        when(baselineExperimentsCacheMock.getExperiment(ACCESSION)).thenReturn(baselineExperimentMock);
        when(rnaSeqDiffExperimentsCacheMock.getExperiment(DIFFERENTIAL)).thenReturn(differentialExperimentMock);
        when(microarrayExperimentsCacheMock.getExperiment(MICROARRAY)).thenReturn(microarrayExperimentMock);

        when(microarrayExperimentMock.getAccession()).thenReturn(MICROARRAY);
        when(microarrayExperimentMock.getLastUpdate()).thenReturn(lastUpdateStub);
        when(microarrayExperimentMock.getAssayAccessions()).thenReturn(Sets.newHashSet(ASSAY_1, ASSAY_2));
        when(microarrayExperimentMock.getContrastIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet(CONTRAST)));
        when(microarrayExperimentMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY)));
        when(microarrayExperimentMock.getType()).thenReturn(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);

        when(differentialExperimentMock.getAccession()).thenReturn(DIFFERENTIAL);
        when(differentialExperimentMock.getLastUpdate()).thenReturn(lastUpdateStub);
        when(differentialExperimentMock.getAssayAccessions()).thenReturn(Sets.newHashSet(ASSAY_1, ASSAY_2));
        when(differentialExperimentMock.getContrastIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet(CONTRAST)));
        when(differentialExperimentMock.getType()).thenReturn(ExperimentType.RNASEQ_MRNA_DIFFERENTIAL);

        when(baselineExperimentMock.getExperimentRunAccessions()).thenReturn(Sets.newHashSet("RUN"));

        subject = new ExperimentInfoListBuilder(experimentTraderMock,
                baselineExperimentsCacheMock,
                proteomicsBaselineExperimentsCacheMock, rnaSeqDiffExperimentsCacheMock,
                microarrayExperimentsCacheMock,
                publicExperimentTypesCacheMock,
                arrayDesignTraderMock);
    }

    @Test
    public void testBuild()  {
        List<ExperimentInfo> experimentInfos = subject.build();
        assertThat(experimentInfos.size(), is(3));
        assertThat(experimentInfos.get(0).getExperimentAccession(), is(ACCESSION));
        assertThat(experimentInfos.get(1).getExperimentAccession(), is(DIFFERENTIAL));
        assertThat(experimentInfos.get(2).getExperimentAccession(), is(MICROARRAY));
    }

    @Test
    public void testExtractMicroarrayExperiments()  {
        List<ExperimentInfo> experimentInfos = subject.extractMicroarrayExperiments();
        assertThat(experimentInfos.size(), is(1));
        ExperimentInfo experimentInfo = experimentInfos.get(0);
        assertThat(experimentInfo.getNumberOfAssays(), is(2));
        assertThat(experimentInfo.getNumberOfContrasts(), is(1));
        assertThat(experimentInfo.getArrayDesigns(), contains(ARRAY));
    }

    @Test
    public void testExtractRnaSeqDiffExperiments()  {
        List<ExperimentInfo> experimentInfos = subject.extractRnaSeqDiffExperiments();
        assertThat(experimentInfos.size(), is(1));
        ExperimentInfo experimentInfo = experimentInfos.get(0);
        assertThat(experimentInfo.getNumberOfAssays(), is(2));
        assertThat(experimentInfo.getNumberOfContrasts(), is(1));
    }

    @Test
    public void testExtractBaselineExperiments() {
        List<ExperimentInfo> experimentInfos = subject.extractBaselineExperiments();
        assertThat(experimentInfos.size(), is(1));
        ExperimentInfo experimentInfo = experimentInfos.get(0);
        assertThat(experimentInfo.getExperimentalFactors(), contains(FACTOR_NAME));
        assertThat(experimentInfo.getNumberOfAssays(), is(1));
    }

    @Test
    public void testExtractBasicExperimentInfo()   {
        ExperimentInfo experimentInfo = subject.extractBasicExperimentInfo(baselineExperimentMock);
        assertThat(experimentInfo.getExperimentAccession(), is(ACCESSION));
        assertThat(experimentInfo.getLastUpdate(), is("12-01-1940"));
        assertThat(experimentInfo.getExperimentDescription(), is(DESCRIPTION));
        assertThat(experimentInfo.getSpecies(), contains(SPECIES));
        assertThat(experimentInfo.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
    }

}