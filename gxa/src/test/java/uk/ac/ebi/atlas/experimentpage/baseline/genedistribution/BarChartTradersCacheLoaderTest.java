
package uk.ac.ebi.atlas.experimentpage.baseline.genedistribution;

import com.google.common.collect.Iterators;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarChartTradersCacheLoaderTest {

    private BarChartTradersCacheLoader subject;

    private static final String ORGANISM_PART_1 = "op1";
    private static final String ORGANISM_PART_2 = "op2";
    private static final String ORGANISM_PART_3 = "op3";

    private static final String ORIGIN_1 = "origin1";
    private static final String ORIGIN_2 = "origin2";

    @Mock
    private Factor factorMock2;
    @Mock
    private Factor factorMock1;
    @Mock
    private Factor factorMock3;


    @Mock
    private Factor factorMock12;
    @Mock
    private Factor factorMock11;
    @Mock
    private Factor factorMock13;

    @Mock
    ExperimentTrader experimentTrader;

    @Mock
    Experiment experiment;

    @Mock
    private RnaSeqBaselineExperimentsCache experimentsCacheMock;

    @Mock
    private BaselineExpressionsInputStreamFactory baselineExpressionsInputStreamFactory;

    @Mock
    private CutoffScale cutoffScale;

    private FactorGroup factorHashSet1;
    private FactorGroup factorHashSet2;
    private FactorGroup factorHashSet3;

    private static final BaselineExpressions GENE_PROFILE_1 = mock(BaselineExpressions.class);
    private static final BaselineExpressions GENE_PROFILE_2 = mock(BaselineExpressions.class);


    @Before
    public void initializeSubject() throws Exception {

        when(experimentTrader.getExperiment(anyString(), anyString())).thenReturn(experiment);

        when(factorMock1.getValue()).thenReturn(ORGANISM_PART_1);
        when(factorMock2.getValue()).thenReturn(ORGANISM_PART_2);
        when(factorMock3.getValue()).thenReturn(ORGANISM_PART_3);

        when(factorMock11.getValue()).thenReturn(ORIGIN_1);
        when(factorMock12.getValue()).thenReturn(ORIGIN_2);

        factorHashSet1 = new FactorSet().add(factorMock1).add(factorMock11);
        factorHashSet2 = new FactorSet().add(factorMock2).add(factorMock12);
        factorHashSet3 = new FactorSet().add(factorMock3).add(factorMock11);

        when(cutoffScale.getValuesSmallerThan(1)).thenReturn(initTreeSetWithValuesLessThen(1));
        when(cutoffScale.getValuesSmallerThan(2)).thenReturn(initTreeSetWithValuesLessThen(2));
        when(cutoffScale.getValuesSmallerThan(3)).thenReturn(initTreeSetWithValuesLessThen(3));
        when(cutoffScale.getValuesSmallerThan(4)).thenReturn(initTreeSetWithValuesLessThen(4));

        //Init GeneProfiles
        initGeneProfile1();

        initGeneProfile2();

        //Init input stream
        BaselineExpressionsTsvInputStream inputStream = mock(BaselineExpressionsTsvInputStream.class);
        when(inputStream.readNext()).thenReturn(GENE_PROFILE_1).thenReturn(GENE_PROFILE_2).thenReturn(null);

        //mock stream builder
        when(baselineExpressionsInputStreamFactory.createGeneExpressionsInputStream((BaselineExperiment) Matchers.any()))
                .thenReturn(inputStream);

        subject = new BarChartTradersCacheLoader(experimentTrader,baselineExpressionsInputStreamFactory, cutoffScale);

    }

    private BaselineExpressions initGeneProfile1() {
        BaselineExpression expression1 = mock(BaselineExpression.class);
        when(expression1.getLevel()).thenReturn(1d);
        when(expression1.getFactorGroup()).thenReturn(factorHashSet1);


        BaselineExpression expression2 = mock(BaselineExpression.class);
        when(expression2.getLevel()).thenReturn(2d);
        when(expression2.getFactorGroup()).thenReturn(factorHashSet2);

        BaselineExpression expression3 = mock(BaselineExpression.class);
        when(expression3.getLevel()).thenReturn(0d);
        when(expression3.getFactorGroup()).thenReturn(factorHashSet3);


        Iterator<BaselineExpression> iterator = Iterators.forArray(expression1, expression2, expression3);
        when(GENE_PROFILE_1.iterator()).thenReturn(iterator);
        return GENE_PROFILE_1;
    }


    private BaselineExpressions initGeneProfile2() {
        BaselineExpression expression21 = mock(BaselineExpression.class);
        when(expression21.getLevel()).thenReturn(3d);
        when(expression21.getFactorGroup()).thenReturn(factorHashSet1);

        BaselineExpression expression22 = mock(BaselineExpression.class);
        when(expression22.getLevel()).thenReturn(2d);
        when(expression22.getFactorGroup()).thenReturn(factorHashSet2);


        BaselineExpression expression23 = mock(BaselineExpression.class);
        when(expression23.getLevel()).thenReturn(1d);
        when(expression23.getFactorGroup()).thenReturn(factorHashSet3);


        Iterator<BaselineExpression> iterator2 = Iterators.forArray(expression21, expression22, expression23);
        when(GENE_PROFILE_2.iterator()).thenReturn(iterator2);
        return GENE_PROFILE_2;
    }


    @Test
    public void testAddGeneToIndexes() {
        //given
        BaselineExpression expression1 = mock(BaselineExpression.class);
        when(expression1.isKnown()).thenReturn(true);
        when(expression1.getFactorGroup()).thenReturn(factorHashSet1);
        when(expression1.getLevel()).thenReturn(1.0);

        BaselineExpression expression2 = mock(BaselineExpression.class);
        when(expression2.isKnown()).thenReturn(true);
        when(expression2.getFactorGroup()).thenReturn(factorHashSet2);
        when(expression2.getLevel()).thenReturn(2.0);

        Iterator<BaselineExpression> iterator = Iterators.forArray(expression1, expression2);
        when(GENE_PROFILE_1.iterator()).thenReturn(iterator);

        //when
        NavigableMap<Double, Map<FactorGroup, BitSet>> geneExpressionIndexes = new TreeMap<>();
        subject.addGeneToIndexes(GENE_PROFILE_1, 0, geneExpressionIndexes);

        //then
        assertThat(geneExpressionIndexes.size(), CoreMatchers.is(2));
        assertThat(geneExpressionIndexes.keySet().contains(0d), CoreMatchers.is(true));
        assertThat(geneExpressionIndexes.keySet().contains(1d), CoreMatchers.is(true));

        assertThat(geneExpressionIndexes.get(1d).size(), CoreMatchers.is(1));
        assertThat(geneExpressionIndexes.get(1d).get(factorHashSet2).cardinality(), CoreMatchers.is(1));

        assertThat(geneExpressionIndexes.get(0d).size(), CoreMatchers.is(2));
        assertThat(geneExpressionIndexes.get(0d).get(factorHashSet1).cardinality(), CoreMatchers.is(1));
        assertThat(geneExpressionIndexes.get(0d).get(factorHashSet2).cardinality(), CoreMatchers.is(1));
    }

    @Test
    public void testAddTwoGenesToIndexes() {
        //given
        BaselineExpression expression1 = mock(BaselineExpression.class);
        when(expression1.isKnown()).thenReturn(true);
        when(expression1.getFactorGroup()).thenReturn(factorHashSet1);
        when(expression1.getLevel()).thenReturn(1d);

        BaselineExpression expression2 = mock(BaselineExpression.class);
        when(expression2.isKnown()).thenReturn(true);
        when(expression2.getFactorGroup()).thenReturn(factorHashSet2);
        when(expression2.getLevel()).thenReturn(2d);

        Iterator<BaselineExpression> iterator = Iterators.forArray(expression1, expression2);
        when(GENE_PROFILE_1.iterator()).thenReturn(iterator);

        //given
        BaselineExpression expression21 = mock(BaselineExpression.class);
        when(expression21.isKnown()).thenReturn(true);
        when(expression21.getFactorGroup()).thenReturn(factorHashSet1);
        when(expression21.getLevel()).thenReturn(3d);

        Iterator<BaselineExpression> iterator2 = Iterators.forArray(expression21);
        when(GENE_PROFILE_2.iterator()).thenReturn(iterator2);

        //when
        //when
        NavigableMap<Double, Map<FactorGroup, BitSet>> geneExpressionIndexes = new TreeMap<>();
        subject.addGeneToIndexes(GENE_PROFILE_1, 0, geneExpressionIndexes);
        subject.addGeneToIndexes(GENE_PROFILE_2, 1, geneExpressionIndexes);

        //then
        assertThat(geneExpressionIndexes.size(), CoreMatchers.is(3));
        assertThat(geneExpressionIndexes.keySet().contains(0d), CoreMatchers.is(true));
        assertThat(geneExpressionIndexes.keySet().contains(1d), CoreMatchers.is(true));
        assertThat(geneExpressionIndexes.keySet().contains(2d), CoreMatchers.is(true));
        assertThat(geneExpressionIndexes.keySet().contains(3d), CoreMatchers.is(false));


        assertThat(geneExpressionIndexes.get(1d).get(factorHashSet1).cardinality(), CoreMatchers.is(1));
        assertThat(geneExpressionIndexes.get(1d).get(factorHashSet2).cardinality(), CoreMatchers.is(1));
        assertThat(geneExpressionIndexes.get(2d).get(factorHashSet1).cardinality(), CoreMatchers.is(1));
    }

    private SortedSet<Double> initTreeSetWithValuesLessThen(double value) {
        SortedSet<Double> cutOffs = new TreeSet<>();
        for (int i = 0; i < value; i++) {
            cutOffs.add((double) i);
        }
        if (value == 0d) {
            cutOffs.add(0d);
        }
        return cutOffs;
    }
}