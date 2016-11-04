
package uk.ac.ebi.atlas.baseline;

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

    private static final String FACTOR_TYPE = "ORG";
    private BaselineProfileComparator subject;

    @Mock
    private BaselineProfile geneWithSpecificity1;

    @Mock
    private BaselineProfile geneWithSpecificity16;

    @Mock
    private BaselineProfile geneWithSpecificity16AndSmallerExpressionLevel;

    Factor factor1 = new Factor(FACTOR_TYPE, "heart");
    Factor factor2 = new Factor(FACTOR_TYPE, "nose");

    private Set<Factor> selectedOrganismParts = Sets.newHashSet(factor1, factor2);

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
        subject = new BaselineProfileComparator(false, selectedOrganismParts, null, 0.5);
    }

    @Test
    public void lowSpecificityShouldFollowHighSpecificity() {
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);

        //then
        assertThat(comparison, is(lessThan(0)));

        //when
        comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        //then
        assertThat(comparison, is(greaterThan(0)));

    }


}
