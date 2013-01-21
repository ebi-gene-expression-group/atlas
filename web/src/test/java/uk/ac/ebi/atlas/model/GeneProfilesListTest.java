package uk.ac.ebi.atlas.model;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesListTest {

    private static final String GENE_ID_1 = "T1";
    private static final String GENE_ID_3 = "T3";
    private static final String GENE_ID_4 = "T4";

    @Mock
    GeneProfile profile_1;
    @Mock
    GeneProfile profile_2;
    @Mock
    GeneProfile profile_3;
    @Mock
    GeneProfile profile_4;
    @Mock
    GeneProfile profile_5;

    private GeneProfilesList subject;

    @Before
    public void setUp() throws Exception {

        subject = new GeneProfilesList(Lists.newArrayList(profile_5, profile_3, profile_4, profile_1, profile_2));

    }

    @Test
    public void testGetTop() throws Exception {
        //when
        GeneProfilesList topProfiles = subject.getTop(3);
        //then
        assertThat(topProfiles, hasSize(3));
        //and
        assertThat(topProfiles, contains(profile_5, profile_3, profile_4));
    }

    @Test
    public void testGetTopWhenListIsSmallerThanRequestedAmountOfExpressions() throws Exception {
        //when
        GeneProfilesList topProfiles = subject.getTop(6);
        //then
        assertThat(topProfiles, hasSize(5));
        //and
        assertThat(topProfiles, contains(profile_5, profile_3, profile_4, profile_1, profile_2));
    }

    @Test
    public void testGetTopWhenListIsEqualToRequestedAmountOfExpressions() throws Exception {
        //when
        GeneProfilesList topProfiles = subject.getTop(5);
        //then
        assertThat(topProfiles, hasSize(5));
        //and
        assertThat(topProfiles, contains(profile_5, profile_3, profile_4, profile_1, profile_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sublistShouldThrowIllegalArguemntExceptionWhenUpperIndexIsLessThanZero() throws Exception {
        //when
        GeneProfilesList e = subject.subList(0, -1);
        assertThat(e, is(nullValue()));
    }

    public void sublistTest() throws Exception {
        //when
        GeneProfilesList geneProfiles = subject.subList(0, 3);
        //then
        assertThat(geneProfiles, contains(profile_5, profile_3, profile_4));
    }

    public void sublistShouldReturnEntireListWhenTopIndexLargerThanListSize() throws Exception {
        //when
        GeneProfilesList geneProfiles = subject.subList(0, 7);
        //then
        assertThat(geneProfiles, hasSize(5));
    }

    @Test
    public void getMaxExpressionLevelTest() {
        //given
        when(profile_1.getMaxExpressionLevel()).thenReturn(55d);
        when(profile_2.getMaxExpressionLevel()).thenReturn(15d);
        when(profile_3.getMaxExpressionLevel()).thenReturn(25d);
        when(profile_4.getMaxExpressionLevel()).thenReturn(115d);
        when(profile_5.getMaxExpressionLevel()).thenReturn(35d);
        //then
        assertThat(subject.getMaxExpressionLevel(), is(115d));
    }

    @Test
    public void getMinExpressionLevelTest() {
        //given
        when(profile_1.getMinExpressionLevel()).thenReturn(55d);
        when(profile_2.getMinExpressionLevel()).thenReturn(15d);
        when(profile_3.getMinExpressionLevel()).thenReturn(25d);
        when(profile_4.getMinExpressionLevel()).thenReturn(115d);
        when(profile_5.getMinExpressionLevel()).thenReturn(35d);
        //then
        assertThat(subject.getMinExpressionLevel(), is(15d));
    }


}
