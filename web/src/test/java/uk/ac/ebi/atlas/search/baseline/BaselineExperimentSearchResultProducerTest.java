package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.primitives.Doubles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentSearchResultProducerTest {

    private static final double LUNG_LEVEL = 48948;
    private static final double THYMUS_LEVEL = 54922;
    private static final double SPLEEN_LEVEL = 387123;
    private static final String EXPERIMENT_DISPLAY_NAME = "mouse experiment";
    public static final String E_MTAB_599 = "E-MTAB-599";
    private static final double HOMO_SAPIENS_LEVEL = 123;
    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final java.lang.String E_MTAB_513_DISPLAY_NAME = "homo sapiens non tissue experiment";
    private static final java.lang.String ORGANISM = "ORGANISM";
    BaselineExperimentSearchResultProducer subject;

    RnaSeqBslnExpression g3_thymus = RnaSeqBslnExpression.create(E_MTAB_599, "g3", THYMUS_LEVEL);
    RnaSeqBslnExpression g5_lung = RnaSeqBslnExpression.create(E_MTAB_599, "g5", LUNG_LEVEL);
    RnaSeqBslnExpression g6_spleen = RnaSeqBslnExpression.create(E_MTAB_599, "g6", SPLEEN_LEVEL);
    RnaSeqBslnExpression g_nonTissueExpression = RnaSeqBslnExpression.create(E_MTAB_513, "gnt", HOMO_SAPIENS_LEVEL);

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    private static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    private static final Factor THYMUS = new Factor(ORGANISM_PART, "thymus");
    private static final Factor HOMO_SAPIENS = new Factor(ORGANISM, "homo sapiens");

    private static final ImmutableSortedSet<Factor> ALL_FACTORS = ImmutableSortedSet.of(LUNG, SPLEEN, THYMUS);

    @Mock
    private ExperimentTrader experimentTrader;

    @Mock
    private BaselineExperiment baselineExperiment1;

    @Mock
    private BaselineExperiment baselineExperiment2;

    @Mock
    private ExperimentalFactors experimentalFactors1;


    @Mock
    private ExperimentalFactors experimentalFactors2;

    private static final FactorSet EMPTY_FACTOR_SET = new FactorSet();

    @Before
    public void before() {
        when(experimentTrader.getPublicExperiment(E_MTAB_599)).thenReturn(baselineExperiment1);

        when(baselineExperiment1.getExperimentalFactors()).thenReturn(experimentalFactors1);
        when(baselineExperiment1.isTissueExperiment()).thenReturn(true);
        when(baselineExperiment1.getAccession()).thenReturn(E_MTAB_599);
        when(baselineExperiment1.getDisplayName()).thenReturn(EXPERIMENT_DISPLAY_NAME);
        when(experimentalFactors1.getFactorGroupByAssayGroupId("g3")).thenReturn(new FactorSet(THYMUS));
        when(experimentalFactors1.getFactorGroupByAssayGroupId("g5")).thenReturn(new FactorSet(LUNG));
        when(experimentalFactors1.getFactorGroupByAssayGroupId("g6")).thenReturn(new FactorSet(SPLEEN));
        when(experimentalFactors1.getFilteredFactors(Mockito.any(FactorGroup.class))).thenReturn(ALL_FACTORS);
        when(experimentalFactors1.getNonDefaultFactors(Mockito.anyString())).thenReturn(EMPTY_FACTOR_SET);
        when(experimentalFactors1.getDefaultQueryFactorType()).thenReturn(ORGANISM_PART);

        when(experimentTrader.getPublicExperiment(E_MTAB_513)).thenReturn(baselineExperiment2);

        when(baselineExperiment2.getExperimentalFactors()).thenReturn(experimentalFactors2);
        when(baselineExperiment2.isTissueExperiment()).thenReturn(false);
        when(baselineExperiment2.getAccession()).thenReturn(E_MTAB_513);
        when(baselineExperiment2.getDisplayName()).thenReturn(E_MTAB_513_DISPLAY_NAME);
        when(experimentalFactors2.getFactorGroupByAssayGroupId("gnt")).thenReturn(new FactorSet(HOMO_SAPIENS));
        when(experimentalFactors2.getFilteredFactors(Mockito.any(FactorGroup.class))).thenReturn(ImmutableSortedSet.of(HOMO_SAPIENS));
        when(experimentalFactors2.getNonDefaultFactors(Mockito.anyString())).thenReturn(EMPTY_FACTOR_SET);
        when(experimentalFactors2.getDefaultQueryFactorType()).thenReturn(ORGANISM);

        subject = new BaselineExperimentSearchResultProducer(experimentTrader);
    }

    @Test
    public void buildProfilesForTissueExperiments() {
        ImmutableList<RnaSeqBslnExpression> expressions = ImmutableList.of(g3_thymus, g5_lung, g6_spleen, g_nonTissueExpression);

        BaselineExperimentSearchResult result = subject.buildProfilesForTissueExperiments(expressions);

        BaselineExperimentProfilesList profiles = result.experimentProfiles;
        SortedSet<Factor> factors = result.tissueFactorsAcrossAllExperiments;

        assertThat(factors, contains(ALL_FACTORS.toArray()));

        assertThat(profiles, hasSize(1));

        BaselineProfile baselineProfile = profiles.get(0);

        assertThat(baselineProfile.getId(), is(E_MTAB_599));
        assertThat(baselineProfile.getName(), is(EXPERIMENT_DISPLAY_NAME));
        assertThat(baselineProfile.getMinExpressionLevel(), is(Doubles.min(LUNG_LEVEL, SPLEEN_LEVEL, THYMUS_LEVEL)));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(Doubles.max(LUNG_LEVEL, SPLEEN_LEVEL, THYMUS_LEVEL)));
        assertThat(baselineProfile.getKnownExpressionLevel(LUNG), is(LUNG_LEVEL));
        assertThat(baselineProfile.getKnownExpressionLevel(SPLEEN), is(SPLEEN_LEVEL));
        assertThat(baselineProfile.getKnownExpressionLevel(THYMUS), is(THYMUS_LEVEL));

    }

}
