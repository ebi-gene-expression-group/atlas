package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.CompleteGeneProfile;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfileInputStreamBuilder;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarChartTraderBuilderTest {

    private BitIndexBuilder subject;

    private static final String ORGANISM_PART_1 = "op1";
    private static final String ORGANISM_PART_2 = "op2";
    private static final String ORGANISM_PART_3 = "op3";

    private static final String ORIGIN_1 = "origin1";
    private static final String ORIGIN_2 = "origin2";

    @Mock
    private FactorValue factorValueMock2;
    @Mock
    private FactorValue factorValueMock1;
    @Mock
    private FactorValue factorValueMock3;


    @Mock
    private FactorValue factorValueMock12;
    @Mock
    private FactorValue factorValueMock11;
    @Mock
    private FactorValue factorValueMock13;


    @Mock
    private ExperimentsCache experimentsCacheMock;

    @Mock
    private GeneProfileInputStreamBuilder geneProfileInputStreamBuilder;

    @Mock
    private CutoffScale cutoffScale;

    private HashSet<FactorValue> factorValueHashSet1;
    private HashSet<FactorValue> factorValueHashSet2;
    private HashSet<FactorValue> factorValueHashSet3;

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";
    public static final CompleteGeneProfile GENE_PROFILE_1 = mock(CompleteGeneProfile.class);
    public static final CompleteGeneProfile GENE_PROFILE_2 = mock(CompleteGeneProfile.class);


    @Before
    public void initializeSubject() {

        when(factorValueMock1.getValue()).thenReturn(ORGANISM_PART_1);
        when(factorValueMock2.getValue()).thenReturn(ORGANISM_PART_2);
        when(factorValueMock3.getValue()).thenReturn(ORGANISM_PART_3);

        when(factorValueMock11.getValue()).thenReturn(ORIGIN_1);
        when(factorValueMock12.getValue()).thenReturn(ORIGIN_2);

        factorValueHashSet1 = Sets.newHashSet(factorValueMock1, factorValueMock11);
        factorValueHashSet2 = Sets.newHashSet(factorValueMock2, factorValueMock12);
        factorValueHashSet3 = Sets.newHashSet(factorValueMock3, factorValueMock11);

        when(cutoffScale.getValuesSmallerThan(1)).thenReturn(initTreeSetWithValuesLessThen(1));
        when(cutoffScale.getValuesSmallerThan(2)).thenReturn(initTreeSetWithValuesLessThen(2));
        when(cutoffScale.getValuesSmallerThan(3)).thenReturn(initTreeSetWithValuesLessThen(3));
        when(cutoffScale.getValuesSmallerThan(4)).thenReturn(initTreeSetWithValuesLessThen(4));

        //Init GeneProfiles
        initGeneProfile1();

        initGeneProfile2();

        //Init input stream
        ObjectInputStream<CompleteGeneProfile> inputStream = mock(ObjectInputStream.class);
        when(inputStream.readNext()).thenReturn(GENE_PROFILE_1).thenReturn(GENE_PROFILE_2).thenReturn(null);

        //mock stream builder
        when(geneProfileInputStreamBuilder.forExperiment(anyString())).thenReturn(geneProfileInputStreamBuilder);
        when(geneProfileInputStreamBuilder.createCompleteGeneProfileInputStream()).thenReturn(inputStream);

        subject = new BitIndexBuilder(geneProfileInputStreamBuilder, cutoffScale);

    }

    private CompleteGeneProfile initGeneProfile1() {
        Expression expression1 = mock(Expression.class);
        when(expression1.getLevel()).thenReturn(1d);
        when(expression1.getAllFactorValues()).thenReturn(factorValueHashSet1);


        Expression expression2 = mock(Expression.class);
        when(expression2.getLevel()).thenReturn(2d);
        when(expression2.getAllFactorValues()).thenReturn(factorValueHashSet2);

        Expression expression3 = mock(Expression.class);
        when(expression3.getLevel()).thenReturn(0d);
        when(expression3.getAllFactorValues()).thenReturn(factorValueHashSet3);


        Iterator<Expression> iterator = Iterators.forArray(expression1, expression2, expression3);
        when(GENE_PROFILE_1.iterator()).thenReturn(iterator);
        return GENE_PROFILE_1;
    }


    private CompleteGeneProfile initGeneProfile2() {
        Expression expression21 = mock(Expression.class);
        when(expression21.getLevel()).thenReturn(3d);
        when(expression21.getAllFactorValues()).thenReturn(factorValueHashSet1);

        Expression expression22 = mock(Expression.class);
        when(expression22.getLevel()).thenReturn(2d);
        when(expression22.getAllFactorValues()).thenReturn(factorValueHashSet2);


        Expression expression23 = mock(Expression.class);
        when(expression23.getLevel()).thenReturn(1d);
        when(expression23.getAllFactorValues()).thenReturn(factorValueHashSet3);


        Iterator<Expression> iterator2 = Iterators.forArray(expression21, expression22, expression23);
        when(GENE_PROFILE_2.iterator()).thenReturn(iterator2);
        return GENE_PROFILE_2;
    }


    @Test
    public void testAddGeneToIndexes() {
        //given

        //when
        subject.addGeneToIndexes(GENE_PROFILE_1, 0);

        //then
        NavigableMap<Double, Map<Set<FactorValue>, BitSet>> geneExpressionIndexes = subject.getGeneExpressionIndexes();
        assertThat(geneExpressionIndexes.size(), is(2));
        assertThat(geneExpressionIndexes.keySet().contains(0d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(1d), is(true));

        assertThat(geneExpressionIndexes.get(1d).size(), is(1));
        assertThat(geneExpressionIndexes.get(1d).get(factorValueHashSet2).cardinality(), is(1));

        assertThat(geneExpressionIndexes.get(0d).size(), is(2));
        assertThat(geneExpressionIndexes.get(0d).get(factorValueHashSet1).cardinality(), is(1));
        assertThat(geneExpressionIndexes.get(0d).get(factorValueHashSet2).cardinality(), is(1));
    }

    @Test
    public void testAddTwoGenesToIndexes() {

        //when
        subject.addGeneToIndexes(GENE_PROFILE_1, 0);
        subject.addGeneToIndexes(GENE_PROFILE_2, 1);

        //then
        NavigableMap<Double, Map<Set<FactorValue>, BitSet>> geneExpressionIndexes = subject.getGeneExpressionIndexes();
        assertThat(geneExpressionIndexes.size(), is(3));
        assertThat(geneExpressionIndexes.keySet().contains(0d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(1d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(2d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(3d), is(false));


        assertThat(geneExpressionIndexes.get(1d).get(factorValueHashSet1).cardinality(), is(1));
        assertThat(geneExpressionIndexes.get(1d).get(factorValueHashSet2).cardinality(), is(2));
        assertThat(geneExpressionIndexes.get(2d).get(factorValueHashSet1).cardinality(), is(1));
    }

    @Test
    public void testTrimIndexes() {
        //given

        for (int i = 0; i < 50; i = i + 2) {

            subject.addGeneToIndexes(initGeneProfile1(), i);
            subject.addGeneToIndexes(initGeneProfile2(), i + 1);
        }

        NavigableMap<Double, Map<Set<FactorValue>, BitSet>> geneExpressionIndexes = subject.getGeneExpressionIndexes();

        assertThat(geneExpressionIndexes.size(), is(3));
        assertThat(geneExpressionIndexes.keySet().contains(2d), is(true));

        //when
        subject.trimIndexes();

        //then
        geneExpressionIndexes = subject.getGeneExpressionIndexes();
        assertThat(geneExpressionIndexes.size(), is(2));
        assertThat(geneExpressionIndexes.keySet().contains(2d), is(false));
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
