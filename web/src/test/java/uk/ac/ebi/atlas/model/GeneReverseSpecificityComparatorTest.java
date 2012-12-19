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
public class GeneReverseSpecificityComparatorTest {

    private GeneSpecificityComparator subject;

    @Mock
    private GeneProfile geneWithSpecificity1;

    @Mock
    private GeneProfile geneWithSpecificity16;

    @Mock
    private GeneProfile geneWithSpecificity16AndSmallerExpressionLevel;

    private Set<String> selectedOrganismParts = Sets.newHashSet("heart", "nose");

    @Before
    public void initGeneExpressions() {
        when(geneWithSpecificity1.getSpecificity()).thenReturn(1);
        when(geneWithSpecificity16.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(10D);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(0D);
    }

    @Before
    public void initSubject() {
        subject = new GeneSpecificityComparator(false, selectedOrganismParts, null);
    }

    @Test
    public void highSpecificityShouldFollowLowSpecificity() {
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);

        //then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void lowSpecificityShouldPreceedHighSpecificity() {
        //when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        //then
        assertThat(comparison, is(lessThan(0)));

    }


}
