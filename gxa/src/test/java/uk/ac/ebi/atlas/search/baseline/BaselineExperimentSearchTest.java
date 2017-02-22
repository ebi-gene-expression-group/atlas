package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentSearchTest {

    private static final double LUNG_LEVEL = 48948;
    private static final double THYMUS_LEVEL = 54922;
    private static final double SPLEEN_LEVEL = 387123;
    private static final String E_MTAB_599_NAME = "mouse experiment";
    public static final String E_MTAB_599 = "E-MTAB-599";
    private static final double IMR_90_LEVEL = 123;
    private static final String E_GEOD_26284 = "E-GEOD-26284";
    private static final java.lang.String E_GEOD_26284_NAME = "cell line experiment";
    private static final java.lang.String CELL_LINE = "CELL_LINE";

    BaselineExperimentExpression g3_thymus = BaselineExperimentExpression.create(E_MTAB_599, "g3", THYMUS_LEVEL);
    BaselineExperimentExpression g5_lung = BaselineExperimentExpression.create(E_MTAB_599, "g5", LUNG_LEVEL);
    BaselineExperimentExpression g6_spleen = BaselineExperimentExpression.create(E_MTAB_599, "g6", SPLEEN_LEVEL);
    BaselineExperimentExpression g_nonTissueExpression = BaselineExperimentExpression.create(E_GEOD_26284, "g12", IMR_90_LEVEL);

    private static final String ORGANISM_PART = "ORGANISM_PART";
    private static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    private static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    private static final Factor THYMUS = new Factor(ORGANISM_PART, "thymus");
    private static final Factor IMR_90 = new Factor(CELL_LINE, "IMR-90");
    public static final ImmutableSortedSet<Factor> E_GEOD_26284_ALL_FACTORS = ImmutableSortedSet.of(IMR_90);

    private static final ImmutableSortedSet<Factor> E_MTAB_599_ALL_FACTORS = ImmutableSortedSet.of(LUNG, SPLEEN, THYMUS);

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

    @Mock
    BaselineExpressionDao baselineExpressionDao;

    BaselineExperimentSearchResultProducer searchResultProducer;

    BaselineExperimentProfileSearchService searchService;

    @Before
    public void before() {
        when(experimentTrader.getPublicExperiment(E_MTAB_599)).thenReturn(baselineExperiment1);

        when(baselineExperiment1.getExperimentalFactors()).thenReturn(experimentalFactors1);
        when(baselineExperiment1.getAccession()).thenReturn(E_MTAB_599);
        when(baselineExperiment1.getDisplayName()).thenReturn(E_MTAB_599_NAME);
        when(experimentalFactors1.getFactorGroup("g3")).thenReturn(new FactorSet(THYMUS));
        when(experimentalFactors1.getFactorGroup("g5")).thenReturn(new FactorSet(LUNG));
        when(experimentalFactors1.getFactorGroup("g6")).thenReturn(new FactorSet(SPLEEN));
        when(experimentalFactors1.getComplementFactors(Mockito.any(FactorGroup.class))).thenReturn(E_MTAB_599_ALL_FACTORS);
        when(experimentalFactors1.getNonDefaultFactors(Mockito.anyString())).thenReturn(EMPTY_FACTOR_SET);
        when(experimentalFactors1.getDefaultQueryFactorType()).thenReturn(ORGANISM_PART);

        when(experimentTrader.getPublicExperiment(E_GEOD_26284)).thenReturn(baselineExperiment2);

        when(baselineExperiment2.getExperimentalFactors()).thenReturn(experimentalFactors2);
        when(baselineExperiment2.getAccession()).thenReturn(E_GEOD_26284);
        when(baselineExperiment2.getDisplayName()).thenReturn(E_GEOD_26284_NAME);
        when(experimentalFactors2.getFactorGroup("g12")).thenReturn(new FactorSet(IMR_90));
        when(experimentalFactors2.getComplementFactors(Mockito.any(FactorGroup.class))).thenReturn(E_GEOD_26284_ALL_FACTORS);
        when(experimentalFactors2.getNonDefaultFactors(Mockito.anyString())).thenReturn(EMPTY_FACTOR_SET);
        when(experimentalFactors2.getDefaultQueryFactorType()).thenReturn(CELL_LINE);

        searchResultProducer = new BaselineExperimentSearchResultProducer(experimentTrader);

        searchService = new BaselineExperimentProfileSearchService(baselineExpressionDao,searchResultProducer);
    }

    @Test
    public void buildProfilesForTissueExperiments() {
        // include g_nonTissueExpression which should be filtered out
        ImmutableList<BaselineExperimentExpression> expressions = ImmutableList.of(g3_thymus, g5_lung, g6_spleen, g_nonTissueExpression);

        BaselineExperimentSearchResult result = searchResultProducer.buildProfilesForTissueExperiments(expressions);

        assertAbout(result);

    }

    @Test
    public void testBaselineExperimentProfileSearchServiceWithTheSameScenario(){
        Set<String> geneIds = Sets.newHashSet("ENSG00000001337","ANOTHER_FAKE_IDENTIFIER");
        ImmutableList<BaselineExperimentExpression> expressions = ImmutableList.of(g3_thymus, g5_lung, g6_spleen, g_nonTissueExpression);

        when(baselineExpressionDao.fetchAverageExpressionByExperimentAssayGroup(geneIds)).thenReturn(expressions);

        BaselineExperimentSearchResult result = searchService.query(geneIds);

        assertAbout(result);
    }

    public void assertAbout(BaselineExperimentSearchResult result){
        BaselineExperimentProfilesList profiles = result.experimentProfiles;
        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;

        assertThat(factors, contains(E_MTAB_599_ALL_FACTORS.toArray()));

        assertThat(profiles, hasSize(1));

        BaselineProfile baselineProfile = profiles.get(0);

        assertThat(baselineProfile.getId(), is(E_MTAB_599));
        assertThat(baselineProfile.getName(), is(E_MTAB_599_NAME));
        assertThat(baselineProfile.getMinExpressionLevel(), is(Doubles.min(LUNG_LEVEL, SPLEEN_LEVEL, THYMUS_LEVEL)));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(Doubles.max(LUNG_LEVEL, SPLEEN_LEVEL, THYMUS_LEVEL)));
        assertThat(baselineProfile.getSpecificity(), is(3));
        assertThat(baselineProfile.getExpressionLevel(LUNG), is(LUNG_LEVEL));
        assertThat(baselineProfile.getExpressionLevel(SPLEEN), is(SPLEEN_LEVEL));
        assertThat(baselineProfile.getExpressionLevel(THYMUS), is(THYMUS_LEVEL));
    }

    @Test
    public void buildProfilesForCellLineExperiments() {
        // include tissue expressions which should be filtered out
        ImmutableList<BaselineExperimentExpression> expressions = ImmutableList.of(g3_thymus, g5_lung, g6_spleen, g_nonTissueExpression);

        BaselineExperimentSearchResult result = searchResultProducer.buildProfilesForExpressions(expressions, CELL_LINE);

        BaselineExperimentProfilesList profiles = result.experimentProfiles;
        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;

        assertThat(factors, contains(E_GEOD_26284_ALL_FACTORS.toArray()));

        assertThat(profiles, hasSize(1));

        BaselineProfile baselineProfile = profiles.get(0);

        assertThat(baselineProfile.getId(), is(E_GEOD_26284));
        assertThat(baselineProfile.getName(), is(E_GEOD_26284_NAME));
        assertThat(baselineProfile.getMinExpressionLevel(), is(Doubles.min(IMR_90_LEVEL)));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(Doubles.max(IMR_90_LEVEL)));
        assertThat(baselineProfile.getSpecificity(), is(1));
        assertThat(baselineProfile.getExpressionLevel(IMR_90), is(IMR_90_LEVEL));

    }

}

