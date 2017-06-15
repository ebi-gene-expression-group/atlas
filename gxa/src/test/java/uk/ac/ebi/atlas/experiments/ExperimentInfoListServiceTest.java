package uk.ac.ebi.atlas.experiments;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.DescribesDataColumns;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentBuilder;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.trader.ExpressionAtlasExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

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

    private BaselineExperiment baselineExperiment;
    @Mock
    private DifferentialExperiment differentialExperiment;

    @Mock
    private MicroarrayExperiment microarrayExperiment;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private ExperimentInfoListService subject;


    @Before
    public void setUp() throws Exception {
        Date lastUpdateStub = new GregorianCalendar(39 + 1900, 12, 12).getTime();

        List<AssayGroup> assayGroups = ImmutableList.of(new AssayGroup("RUN", ASSAY_1, ASSAY_2));

        when(experimentDesignMock.getFactors(Matchers.anyString())).thenReturn(mock(FactorSet.class));

        baselineExperiment = Mockito.spy(new BaselineExperimentBuilder()
                .forSpecies(new Species(SPECIES, SpeciesProperties.UNKNOWN))
                .withAccession(BASELINE_ACCESSION)
                .withLastUpdate(lastUpdateStub)
                .withDescription(DESCRIPTION)
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withExperimentDesign(experimentDesignMock)
                .withAssayGroups(assayGroups)
                .withPubMedIds(ImmutableSet.<String>of())
                .create());

        Contrast contrast = mock(Contrast.class);
        when(contrast.getId()).thenReturn(CONTRAST);

        when(contrast.getReferenceAssayGroup()).thenReturn(new AssayGroup("id", ASSAY_1,ASSAY_2));
        when(contrast.getTestAssayGroup()).thenReturn(new AssayGroup("test",ASSAY_1));
        List<Pair<Contrast, Boolean>> contrasts = ImmutableList.of(Pair.of(contrast, true));
        differentialExperiment = Mockito.spy(
                new DifferentialExperiment(DIFFERENTIAL_ACCESSION,
                lastUpdateStub, contrasts,
                "description", new Species(SPECIES, SpeciesProperties.UNKNOWN),
                new HashSet<String>(),experimentDesignMock));

        microarrayExperiment = Mockito.spy(new MicroarrayExperiment(ExperimentType
                .MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL, MICROARRAY_ACCESSION,
                lastUpdateStub ,contrasts,
                "description", new Species(SPECIES, SpeciesProperties.UNKNOWN), Sets.newTreeSet(Sets.newHashSet(ARRAY)),
                Sets.newTreeSet(Sets.newHashSet("ARRAY_NAME")), experimentDesignMock, new HashSet<String>()));

        final ImmutableMap<ExperimentType, ImmutableSet<? extends Experiment<? extends DescribesDataColumns>>>
                experimentAccessionsPerType =
                ImmutableMap.of(
                baselineExperiment.getType(), ImmutableSet.of(baselineExperiment),
                differentialExperiment.getType(), ImmutableSet.of(differentialExperiment),
                microarrayExperiment.getType(), ImmutableSet.of(microarrayExperiment)
        );


        doAnswer(invocationOnMock -> {
            ExperimentType experimentType = (ExperimentType) invocationOnMock.getArguments()[0];
            return experimentAccessionsPerType.containsKey(experimentType) ? experimentAccessionsPerType.get
                    (experimentType) : ImmutableSet.of();
        }).when(experimentTraderMock).getPublicExperiments(Mockito.<ExperimentType[]>anyVararg());

        //call real method on big method, small one takes from this map
        when(experimentDesignMock.getFactorHeaders()).thenReturn(Sets.newTreeSet(Sets.newHashSet(FACTOR_NAME)));

        subject = new ExperimentInfoListService(experimentTraderMock, ImmutableList.of(baselineExperiment.getType(),
                differentialExperiment.getType(), microarrayExperiment.getType()));
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
        ExperimentInfo experimentInfo = baselineExperiment.buildExperimentInfo();
        assertThat(experimentInfo.getExperimentAccession(), is(BASELINE_ACCESSION));
        assertThat(experimentInfo.getLastUpdate(), is("12-01-1940"));
        assertThat(experimentInfo.getExperimentDescription(), is(DESCRIPTION));
        assertThat(experimentInfo.getSpecies(), is(SPECIES));
        assertThat(experimentInfo.getExperimentType(), is(ExperimentType.RNASEQ_MRNA_BASELINE));
    }

}