package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Doubles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentProfileSearchServiceTest {

    private static final double LUNG_LEVEL = 48948;
    private static final double THYMUS_LEVEL = 54922;
    private static final double SPLEEN_LEVEL = 387123;
    private static final String EXPERIMENT_DISPLAY_NAME = "mouse experiment";
    public static final String E_MTAB_599 = "E-MTAB-599";
    BaselineExperimentProfileSearchService subject;

    RnaSeqBslnExpression g3_thymus = new RnaSeqBslnExpression("ENSMUSG00000093014", E_MTAB_599, "g3", THYMUS_LEVEL);
    RnaSeqBslnExpression g5_lung = new RnaSeqBslnExpression("ENSMUSG00000093014", E_MTAB_599, "g5", LUNG_LEVEL);
    RnaSeqBslnExpression g6_spleen = new RnaSeqBslnExpression("ENSMUSG00000093014", E_MTAB_599, "g6", SPLEEN_LEVEL);

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    private static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    private static final Factor THYMUS = new Factor(ORGANISM_PART, "thymus");


    @Mock
    private RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao;

    @Mock
    private SolrQueryService solrQueryService;

    @Mock
    private BaselineExperimentsCache baselineExperimentsCache;

    @Mock
    private BaselineExperiment baselineExperiment;

    @Mock
    private ExperimentalFactors experimentalFactors;

    @Before
    public void before() {
        when(baselineExperimentsCache.getExperiment(Mockito.anyString())).thenReturn(baselineExperiment);
        when(baselineExperiment.getExperimentalFactors()).thenReturn(experimentalFactors);
        when(baselineExperiment.isTissueExperiment()).thenReturn(true);
        when(baselineExperiment.getDisplayName()).thenReturn(EXPERIMENT_DISPLAY_NAME);
        when(experimentalFactors.getFactorGroupByAssayGroupId("g3")).thenReturn(new FactorSet(THYMUS));
        when(experimentalFactors.getFactorGroupByAssayGroupId("g5")).thenReturn(new FactorSet(LUNG));
        when(experimentalFactors.getFactorGroupByAssayGroupId("g6")).thenReturn(new FactorSet(SPLEEN));
        //when(experimentalFactors.getFactorsByType("ORGANISM_PART")).thenReturn(ImmutableSortedSet.of(LUNG, SPLEEN, THYMUS));

        subject = new BaselineExperimentProfileSearchService(rnaSeqBslnExpressionDao, solrQueryService, baselineExperimentsCache);
    }

    @Test
    public void buildProfiles() {
        ImmutableList<RnaSeqBslnExpression> expressions = ImmutableList.of(g3_thymus, g5_lung, g6_spleen);

        BaselineProfilesList baselineProfilesList = subject.buildProfilesForTissueExperiments(expressions);

        assertThat(baselineProfilesList, hasSize(1));

        BaselineProfile baselineProfile = baselineProfilesList.get(0);

        assertThat(baselineProfile.getId(), is(E_MTAB_599));
        assertThat(baselineProfile.getName(), is(EXPERIMENT_DISPLAY_NAME));
        assertThat(baselineProfile.getMinExpressionLevel(), is(Doubles.min(LUNG_LEVEL, SPLEEN_LEVEL, THYMUS_LEVEL)));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(Doubles.max(LUNG_LEVEL, SPLEEN_LEVEL, THYMUS_LEVEL)));
        assertThat(baselineProfile.getKnownExpressionLevel(LUNG), is(LUNG_LEVEL));
        assertThat(baselineProfile.getKnownExpressionLevel(SPLEEN), is(SPLEEN_LEVEL));
        assertThat(baselineProfile.getKnownExpressionLevel(THYMUS), is(THYMUS_LEVEL));

    }

}
