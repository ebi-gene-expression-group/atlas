package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileComparatorTest {

    private GeneProfileComparator subject;

    @Mock
    private GeneProfile geneWithSpecificity1;

    @Mock
    private GeneProfile geneWithSpecificity16;

    @Mock
    private GeneProfile geneWithSpecificity16AndSmallerExpressionLevel;

    private Set<String> selectedOrganismParts = Sets.newHashSet("heart");

    private Set<String> nonSelectedOrganismParts = Sets.newHashSet("heart");

    private Set<String> allOrganismParts = Sets.newHashSet("heart", "nose");

    @Before
    public void initGeneExpressions() {
        when(geneWithSpecificity1.getSpecificity()).thenReturn(1);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(5D);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(5D);
        when(geneWithSpecificity16.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(10D);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(2D);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(0D);
    }

    @Before
    public void initSubject() {

    }

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {

        subject = new GeneProfileComparator(true, null, allOrganismParts);

        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);

        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void highSpecificityShouldPreceedLowSpecificity() {

        subject = new GeneProfileComparator(true, null, allOrganismParts);

        //when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        //then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void sameSpecificityShouldBeSortedByExpressionLevel() {

        subject = new GeneProfileComparator(true, null, allOrganismParts);

        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity16AndSmallerExpressionLevel);

        // then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void higherAverageAcrossSelectedTissuesMinusAverageNonSelected() {

        subject = new GeneProfileComparator(true, selectedOrganismParts, allOrganismParts);

        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        // then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void higherAverageAcrossAllTissues() {

        subject = new GeneProfileComparator(false, null, allOrganismParts);

        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        // then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void higherAverageAcrossSelectedTissues() {

        subject = new GeneProfileComparator(false, selectedOrganismParts, allOrganismParts);

        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        // then
        assertThat(comparison, is(greaterThan(0)));

    }


}
