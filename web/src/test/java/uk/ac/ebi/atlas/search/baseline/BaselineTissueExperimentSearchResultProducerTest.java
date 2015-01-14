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
public class BaselineTissueExperimentSearchResultProducerTest {

    private static final double LUNG_LEVEL = 48948;
    private static final double THYMUS_LEVEL = 54922;
    private static final double SPLEEN_LEVEL = 387123;
    private static final String EXPERIMENT_DISPLAY_NAME = "mouse experiment";
    public static final String E_MTAB_599 = "E-MTAB-599";
    BaselineTissueExperimentSearchResultProducer subject;

    RnaSeqBslnExpression g3_thymus = RnaSeqBslnExpression.create(E_MTAB_599, "g3", THYMUS_LEVEL);
    RnaSeqBslnExpression g5_lung = RnaSeqBslnExpression.create(E_MTAB_599, "g5", LUNG_LEVEL);
    RnaSeqBslnExpression g6_spleen = RnaSeqBslnExpression.create(E_MTAB_599, "g6", SPLEEN_LEVEL);

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    private static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    private static final Factor THYMUS = new Factor(ORGANISM_PART, "thymus");

    private static final ImmutableSortedSet<Factor> ALL_FACTORS = ImmutableSortedSet.of(LUNG, SPLEEN, THYMUS);

    @Mock
    private ExperimentTrader experimentTrader;

    @Mock
    private BaselineExperiment baselineExperiment;

    @Mock
    private ExperimentalFactors experimentalFactors;

    private static final FactorSet EMPTY_FACTOR_SET = new FactorSet();

    @Before
    public void before() {
        when(experimentTrader.getPublicExperiment(Mockito.anyString())).thenReturn(baselineExperiment);
        when(baselineExperiment.getExperimentalFactors()).thenReturn(experimentalFactors);
        when(baselineExperiment.isTissueExperiment()).thenReturn(true);
        when(baselineExperiment.getAccession()).thenReturn(E_MTAB_599);
        when(baselineExperiment.getDisplayName()).thenReturn(EXPERIMENT_DISPLAY_NAME);
        when(experimentalFactors.getFactorGroupByAssayGroupId("g3")).thenReturn(new FactorSet(THYMUS));
        when(experimentalFactors.getFactorGroupByAssayGroupId("g5")).thenReturn(new FactorSet(LUNG));
        when(experimentalFactors.getFactorGroupByAssayGroupId("g6")).thenReturn(new FactorSet(SPLEEN));
        when(experimentalFactors.getFilteredFactors(Mockito.any(FactorGroup.class))).thenReturn(ALL_FACTORS);
        when(experimentalFactors.getNonDefaultFactors(Mockito.anyString())).thenReturn(EMPTY_FACTOR_SET);

        subject = new BaselineTissueExperimentSearchResultProducer(experimentTrader);
    }

    @Test
    public void buildProfiles() {
        ImmutableList<RnaSeqBslnExpression> expressions = ImmutableList.of(g3_thymus, g5_lung, g6_spleen);

        BaselineTissueExperimentSearchResult result = subject.buildProfilesForTissueExperiments(expressions);

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
