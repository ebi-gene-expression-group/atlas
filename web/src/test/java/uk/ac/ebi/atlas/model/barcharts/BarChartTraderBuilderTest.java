package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Iterators;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BarChartTraderBuilderTest {

    private BarChartTrader.Builder subject;

    private static final String ORGANISM_PART_1 = "op1";
    private static final String ORGANISM_PART_2 = "op2";
    private static final String ORGANISM_PART_3 = "op3";


    @Mock
    private ExperimentsCache experimentsCacheMock;

    @Mock
    private GeneProfilesInputStream.Builder geneProfilesInputStreamBuilder;

    @Mock
    private CutoffScale cutoffScale;

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";
    public static final GeneProfile GENE_PROFILE_1 = mock(GeneProfile.class);
    public static final GeneProfile GENE_PROFILE_2 = mock(GeneProfile.class);
    public static final SortedSet<String> ORGANISM_PARTS = new TreeSet<>();

    @Before
    public void initializeSubject() {

        Experiment experiment = mock(Experiment.class);

        ORGANISM_PARTS.add(ORGANISM_PART_1);
        ORGANISM_PARTS.add(ORGANISM_PART_2);
        ORGANISM_PARTS.add(ORGANISM_PART_3);
        when(experiment.getAllOrganismParts()).thenReturn(ORGANISM_PARTS);

        when(experimentsCacheMock.getExperiment(MOCK_EXPERIMENT_ACCESSION)).thenReturn(experiment);

        when(cutoffScale.getValuesSmallerThan(1)).thenReturn(initTreeSetWithValuesLessThen(1));
        when(cutoffScale.getValuesSmallerThan(2)).thenReturn(initTreeSetWithValuesLessThen(2));
        when(cutoffScale.getValuesSmallerThan(3)).thenReturn(initTreeSetWithValuesLessThen(3));
        when(cutoffScale.getValuesSmallerThan(4)).thenReturn(initTreeSetWithValuesLessThen(4));

        //Init GeneProfiles
        initGeneProfile1();

        initGeneProfile2();

        //Init input stream
        ObjectInputStream<GeneProfile> inputStream = mock(ObjectInputStream.class);
        when(inputStream.readNext()).thenReturn(GENE_PROFILE_1).thenReturn(GENE_PROFILE_2).thenReturn(null);

        //mock stream builder
        when(geneProfilesInputStreamBuilder.forExperiment(anyString())).thenReturn(geneProfilesInputStreamBuilder);
        when(geneProfilesInputStreamBuilder.create()).thenReturn(inputStream);

        subject = new BarChartTrader.Builder(experimentsCacheMock, geneProfilesInputStreamBuilder, cutoffScale);

    }


    @Test
    public void testAddGeneToIndexes() {
        //given
        subject.setOrganismParts(ORGANISM_PARTS);

        //when
        subject.addGeneToIndexes(GENE_PROFILE_1, 0);

        //then
        NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = subject.getGeneExpressionIndexes();
        assertThat(geneExpressionIndexes.size(), is(2));
        assertThat(geneExpressionIndexes.keySet().contains(0d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(1d), is(true));

        assertThat(geneExpressionIndexes.get(1d).get(ORGANISM_PART_1).cardinality(), is(0));
        assertThat(geneExpressionIndexes.get(1d).get(ORGANISM_PART_2).cardinality(), is(1));
        assertThat(geneExpressionIndexes.get(1d).get(ORGANISM_PART_3).cardinality(), is(0));
    }

    @Test
    public void testAddTwoGenesToIndexes() {
        //given
        subject.setOrganismParts(ORGANISM_PARTS);

        //when
        subject.addGeneToIndexes(GENE_PROFILE_1, 0);
        subject.addGeneToIndexes(GENE_PROFILE_2, 1);

        //then
        NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = subject.getGeneExpressionIndexes();
        assertThat(geneExpressionIndexes.size(), is(3));
        assertThat(geneExpressionIndexes.keySet().contains(0d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(1d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(2d), is(true));
        assertThat(geneExpressionIndexes.keySet().contains(3d), is(false));


        assertThat(geneExpressionIndexes.get(1d).get(ORGANISM_PART_1).cardinality(), is(1));
        assertThat(geneExpressionIndexes.get(1d).get(ORGANISM_PART_2).cardinality(), is(2));
        assertThat(geneExpressionIndexes.get(1d).get(ORGANISM_PART_3).cardinality(), is(0));
    }

    @Test
    public void testTrimIndexes() {
        //given
        subject.setOrganismParts(ORGANISM_PARTS);

        for (int i = 0; i < 50; i=i+2) {
            subject.addGeneToIndexes(initGeneProfile1(), i);
            subject.addGeneToIndexes(initGeneProfile2(), i+1);
        }
        NavigableMap<Double, Map<String, BitSet>> geneExpressionIndexes = subject.getGeneExpressionIndexes();

        assertThat(geneExpressionIndexes.size(), is(3));
        assertThat(geneExpressionIndexes.keySet().contains(2d), is(true));

        //when
        subject.trimIndexes();

        //then
        geneExpressionIndexes = subject.getGeneExpressionIndexes();
        assertThat(geneExpressionIndexes.size(), is(2));
        assertThat(geneExpressionIndexes.keySet().contains(2d), is(false));
    }

    private GeneProfile initGeneProfile2() {
        Expression expression21 = mock(Expression.class);
        when(expression21.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression21.getLevel()).thenReturn(3d);

        Expression expression22 = mock(Expression.class);
        when(expression22.getOrganismPart()).thenReturn(ORGANISM_PART_2);
        when(expression22.getLevel()).thenReturn(2d);

        Expression expression23 = mock(Expression.class);
        when(expression23.getOrganismPart()).thenReturn(ORGANISM_PART_3);
        when(expression23.getLevel()).thenReturn(1d);

        Iterator<Expression> iterator2 = Iterators.forArray(expression21, expression22, expression23);
        when(GENE_PROFILE_2.iterator()).thenReturn(iterator2);
        return GENE_PROFILE_2;
    }

    private GeneProfile initGeneProfile1() {
        Expression expression1 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(1d);

        Expression expression2 = mock(Expression.class);
        when(expression2.getOrganismPart()).thenReturn(ORGANISM_PART_2);
        when(expression2.getLevel()).thenReturn(2d);

        Expression expression3 = mock(Expression.class);
        when(expression3.getOrganismPart()).thenReturn(ORGANISM_PART_3);
        when(expression3.getLevel()).thenReturn(0d);

        Iterator<Expression> iterator = Iterators.forArray(expression1, expression2, expression3);
        when(GENE_PROFILE_1.iterator()).thenReturn(iterator);
        return GENE_PROFILE_1;
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
