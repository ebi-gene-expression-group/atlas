package uk.ac.ebi.atlas.model.barcharts;

import com.google.common.collect.Iterators;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.streams.GeneProfilesInputStream;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import static org.mockito.Mockito.mock;
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

    @Before
    public void initializeSubject() {

        Experiment experiment = mock(Experiment.class);

        SortedSet<String> organismParts = new TreeSet<>();
        organismParts.add(ORGANISM_PART_1);
        organismParts.add(ORGANISM_PART_2);
        organismParts.add(ORGANISM_PART_3);
        when(experiment.getAllOrganismParts()).thenReturn(organismParts);

        when(experimentsCacheMock.getExperiment(MOCK_EXPERIMENT_ACCESSION)).thenReturn(experiment);

        SortedSet<Double> cutOffs = new TreeSet<>();
        cutOffs.add(0d);
        cutOffs.add(1d);
        cutOffs.add(5d);
        when(cutoffScale.getValuesSmallerThan(3)).thenReturn(cutOffs);

        //Init GeneProfiles
        Expression expression1 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(1d);

        Expression expression2 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(6d);

        Expression expression3 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(0d);

        GeneProfile geneProfile1 = mock(GeneProfile.class);

        Iterator<Expression> iterator = Iterators.forArray(expression1, expression2, expression3);
        when(geneProfile1.iterator()).thenReturn(iterator);

        Expression expression21 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(1d);

        Expression expression22 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(6d);

        Expression expression23 = mock(Expression.class);
        when(expression1.getOrganismPart()).thenReturn(ORGANISM_PART_1);
        when(expression1.getLevel()).thenReturn(0d);

        GeneProfile geneProfile2 = mock(GeneProfile.class);

        Iterator<Expression> iterator2 = Iterators.forArray(expression21, expression22, expression23);
        when(geneProfile2.iterator()).thenReturn(iterator2);

        //Init input stream

        //mock stream builder

//        subject = new BarChartTrader.Builder(experimentsCacheMock);
    }

}
