package uk.ac.ebi.atlas.model.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileComparatorTest {

    private DifferentialProfileComparator subject;

    @Mock
    private Contrast contrastMock1;
    @Mock
    private Contrast contrastMock2;
    @Mock
    private Contrast contrastMock3;

    private Set<Contrast> allContrasts;
    private Set<Contrast> selectedContrasts;

    @Mock
    private DifferentialProfile profileMock1;

    @Mock
    private DifferentialProfile profileMock2;

    @Before
    public void init() {
        allContrasts = Sets.newHashSet(contrastMock1, contrastMock2, contrastMock3);
        selectedContrasts = Sets.newHashSet(contrastMock1);

        subject = new DifferentialProfileComparator(true, selectedContrasts, allContrasts, Regulation.UP, 0.05);
    }

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        //when
        subject = new DifferentialProfileComparator(true, null, allContrasts, Regulation.UP, 0.05);

        when(profileMock1.getSpecificity(Regulation.UP)).thenReturn(1);
        when(profileMock2.getSpecificity(Regulation.UP)).thenReturn(2);

        //then
        int comparison = subject.compare(profileMock1, profileMock2);

        assertThat(comparison, lessThan(0));
    }

    @Test
    public void lowerAverageAcrossSelectedContrasts() {

         //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.01);
        when(profileMock1.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3), Regulation.UP)).thenReturn(0.02);
        //and
         //when
        when(profileMock2.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.01);
        when(profileMock2.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3), Regulation.UP)).thenReturn(0.04);


        int comparison = subject.compare(profileMock1, profileMock2);
        // then
        assertThat(comparison, is(greaterThanOrEqualTo(0)));

    }

    @Test
    public void testGetExpressionLevelFoldChangeOn() throws Exception {
        //when
        when(profileMock1.getAverageExpressionLevelOn(selectedContrasts, Regulation.UP)).thenReturn(0.01);
        when(profileMock1.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock2, contrastMock3), Regulation.UP)).thenReturn(0.02);

        //then
        assertThat(subject.getExpressionLevelFoldChangeOn(profileMock1), is(2D));
    }
}
