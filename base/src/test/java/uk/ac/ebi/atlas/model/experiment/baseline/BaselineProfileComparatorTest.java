package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileComparatorTest {

    private BaselineProfileComparator subject;

    @Mock
    private BaselineProfile geneWithSpecificity1;

    @Mock
    private BaselineProfile geneWithSpecificity16;

    @Mock
    private BaselineProfile geneWithAverageExpression3;

    @Mock
    private BaselineProfile geneWithAverageExpression8;

    @Mock
    private BaselineProfile geneWithSpecificity16AndSmallerExpressionLevel;

    private AssayGroup g1 = new AssayGroup("g1", "run11","run12","run13");
    private AssayGroup g2 = new AssayGroup("g2", "run21");

    private List<AssayGroup> selectedOrganismParts = ImmutableList.of(g1);
    private List<AssayGroup> allOrganismParts = ImmutableList.of(g1, g2);

    @Before
    public void initGeneExpressions() {
        when(geneWithSpecificity1.getId()).thenReturn("Gene with specificity 1");
        when(geneWithSpecificity1.getSpecificity()).thenReturn(1L);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(5D);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(5D);

        when(geneWithSpecificity16.getId()).thenReturn("Gene with specificity 16");
        when(geneWithSpecificity16.getSpecificity()).thenReturn(16L);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(10D);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(2D);

        when(geneWithSpecificity16AndSmallerExpressionLevel.getId()).thenReturn("Gene with specificity 16 and smaller");
        when(geneWithSpecificity16AndSmallerExpressionLevel.getSpecificity()).thenReturn(16L);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(0D);

        when(geneWithAverageExpression3.getAverageExpressionLevelOn(anyList())).thenReturn(3D);

        when(geneWithAverageExpression8.getAverageExpressionLevelOn(anyList())).thenReturn(8D);
    }


    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        subject = new BaselineProfileComparator(true, allOrganismParts, allOrganismParts);
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);
        //then
        assertThat(comparison, is(greaterThan(0)));
    }

    @Test
    public void highSpecificityShouldPrecedeLowSpecificity() {
        subject = new BaselineProfileComparator(true, allOrganismParts, allOrganismParts);
        //when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);
        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void sameSpecificityShouldBeSortedByExpressionLevel() {
        subject = new BaselineProfileComparator(true, allOrganismParts, allOrganismParts);
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity16AndSmallerExpressionLevel);
        // then
        assertThat(comparison, is(lessThan(0)));
    }


    @Test
    public void higherAverageAcrossAllTissues() {
        subject = new BaselineProfileComparator(false, allOrganismParts, allOrganismParts);
        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);
        // then
        assertThat(comparison, is(greaterThan(0)));
    }

    @Test
    public void higherAverageAcrossSelectedTissues() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts);
        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);
        // then
        assertThat(comparison, is(lessThan(0)));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOnForSpecificity1() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts);
        double averageExpressionLevel = subject.getExpressionLevelFoldChange(geneWithSpecificity1);
        assertThat(averageExpressionLevel, Matchers.is(5.0));
    }

    @Test
    public void testGetExpressionLevelFoldChangeOnForSpecificityGraterThan1() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts);
        double averageExpressionLevel = subject.getExpressionLevelFoldChange(geneWithSpecificity16);
        assertThat(averageExpressionLevel, is(2.0));
    }

    @Test
    public void averageExpressionLevelComparationIsBeingReversed(){
        subject = new BaselineProfileComparator(false, allOrganismParts, allOrganismParts);
        int comparison =
                subject.compareOnAverageExpressionLevel(
                        geneWithAverageExpression8, geneWithAverageExpression3, ImmutableList.of());
        assertThat(comparison, is(-1));
    }
}
