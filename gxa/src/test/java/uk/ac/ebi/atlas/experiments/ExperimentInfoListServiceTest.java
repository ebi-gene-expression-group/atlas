package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.trader.ArrayDesignTrader;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.MicroarrayExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.PublicExperimentTypesCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.RnaSeqDiffExperimentsCache;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentInfoListServiceTest {

    private static final String FACTOR_NAME = "FACTOR_NAME";
    private static final String SPECIES = "SPECIES";
    private static final String BASELINE_ACCESSION = "E-BLAH-1";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String CONTRAST = "CONTRAST";
    private static final String ASSAY_1 = "ASSAY1";
    private static final String ASSAY_2 = "ASSAY2";
    private static final String ARRAY = "ARRAY";
    private static final String MICROARRAY_ACCESSION = "E-BLAH-2";
    private static final String DIFFERENTIAL_ACCESSION = "E-BLAH-3";

    @Mock
    private ExpressionAtlasExperimentTrader experimentTraderMock;

    @Mock
    private RnaSeqBaselineExperimentsCache rnaSeqBaselineExperimentsCacheMock;

    @Mock
    private RnaSeqDiffExperimentsCache rnaSeqDiffExperimentsCacheMock;

    @Mock
    private MicroarrayExperimentsCache microarrayExperimentsCacheMock;

    private BaselineExperiment baselineExperiment;

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

    private ExperimentInfoListService subject;


    @Before
    public void setUp() throws Exception {
        Date lastUpdateStub = new GregorianCalendar(39 + 1900, 12, 12).getTime();

        AssayGroups assayGroups = mock(AssayGroups.class);
        when(assayGroups.getAssayGroupIds()).thenReturn(Sets.newHashSet("RUN"));

        baselineExperiment = Mockito.spy(new BaselineExperimentBuilder()
                .forSpecies(new Species(SPECIES,SPECIES,"kingdom","ensembl_db"))
                .withAccession(BASELINE_ACCESSION)
                .withLastUpdate(lastUpdateStub)
                .withDescription(DESCRIPTION)
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withExperimentDesign(experimentDesignMock)
                .withAssayGroups(assayGroups)
                .withExperimentalFactors(mock(ExperimentalFactors.class))
                .withPubMedIds(ImmutableSet.<String>of())
                .create());

        Contrast contrast = mock(Contrast.class);
        when(contrast.getId()).thenReturn(CONTRAST);

        when(contrast.getReferenceAssayGroup()).thenReturn(new AssayGroup("id", ASSAY_1,ASSAY_2));
        when(contrast.getTestAssayGroup()).thenReturn(new AssayGroup("test",ASSAY_1));
        Set<Contrast> contrasts = Sets.newTreeSet(Sets.newHashSet(contrast));
        differentialExperimentMock = Mockito.spy(
                new DifferentialExperiment(DIFFERENTIAL_ACCESSION,
                lastUpdateStub, contrasts,
                "description", false, new Species(SPECIES,SPECIES, "kingdom", "ensemblDb"),
                new HashSet<String>(),experimentDesignMock));

        microarrayExperimentMock = Mockito.spy(new MicroarrayExperiment(ExperimentType
                .MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, MICROARRAY_ACCESSION,
                lastUpdateStub ,contrasts,
                "description", false, new Species(SPECIES,SPECIES, "kingdom", "ensemblDb"), Sets.newTreeSet(Sets.newHashSet(ARRAY)),
                Sets.newTreeSet(Sets.newHashSet("ARRAY_NAME")), experimentDesignMock, new HashSet<String>()));

        final ImmutableMap<ExperimentType, ImmutableSet<? extends Experiment>> experimentAccessionsPerType = ImmutableMap.of(
                baselineExperiment.getType(), ImmutableSet.of(baselineExperiment),
                differentialExperimentMock.getType(), ImmutableSet.of(differentialExperimentMock),
                microarrayExperimentMock.getType(), ImmutableSet.of(microarrayExperimentMock)
        );


        doAnswer(new Answer() {
            @Override
            public Set<?> answer(InvocationOnMock invocationOnMock) throws Throwable {
                ExperimentType experimentType = (ExperimentType) invocationOnMock.getArguments()[0];
                return experimentAccessionsPerType.containsKey(experimentType) ? experimentAccessionsPerType.get
                        (experimentType) : ImmutableSet.of();
            }
        }).when(experimentTraderMock).getPublicExperiments(Mockito.<ExperimentType[]>anyVararg());

        //call real method on big method, small one takes from this map
        when(experimentDesignMock.getFactorHeaders()).thenReturn(Sets.newTreeSet(Sets.newHashSet(FACTOR_NAME)));

        subject = new ExperimentInfoListService(experimentTraderMock
        );
    }

    @Test
    public void testBuild()  {
        List<ExperimentInfo> experimentInfos = subject.listPublicExperiments();
        assertThat(experimentInfos.size(), is(3));
        assertThat(experimentInfos.get(0).getExperimentAccession(), is(BASELINE_ACCESSION));
        assertThat(experimentInfos.get(1).getExperimentAccession(), is(DIFFERENTIAL_ACCESSION));
        assertThat(experimentInfos.get(2).getExperimentAccession(), is(MICROARRAY_ACCESSION));
    }

    @Test
    public void testExtractBasicExperimentInfo()   {
        ExperimentInfo experimentInfo = baselineExperiment.getExperimentInfo();
        assertThat(experimentInfo.getExperimentAccession(), is(BASELINE_ACCESSION));
        assertThat(experimentInfo.getLastUpdate(), is("12-01-1940"));
        assertThat(experimentInfo.getExperimentDescription(), is(DESCRIPTION));
        assertThat(experimentInfo.getSpecies(), is(SPECIES));
        assertThat(experimentInfo.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
    }

}