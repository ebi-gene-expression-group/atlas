
package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilesListTest {

    @Mock
    BaselineProfile profile_1;
    @Mock
    BaselineProfile profile_2;
    @Mock
    BaselineProfile profile_3;
    @Mock
    BaselineProfile profile_4;
    @Mock
    BaselineProfile profile_5;

    private BaselineProfilesList subject;

    @Before
    public void setUp() throws Exception {

        subject = new BaselineProfilesList(Lists.newArrayList(profile_5, profile_3, profile_4, profile_1, profile_2));

    }

    @Test
    public void testList() throws Exception {
        assertThat(subject, contains(profile_5, profile_3, profile_4, profile_1, profile_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sublistShouldThrowIllegalArguemntExceptionWhenUpperIndexIsLessThanZero() throws Exception {
        //when
        List<BaselineProfile> e = subject.subList(0, -1);
        assertThat(e, is(nullValue()));
    }

    public void sublistTest() throws Exception {
        //when
        List<BaselineProfile> geneProfiles = subject.subList(0, 3);
        //then
        assertThat(geneProfiles, contains(profile_5, profile_3, profile_4));
    }

    public void sublistShouldReturnEntireListWhenTopIndexLargerThanListSize() throws Exception {
        //when
        List<BaselineProfile> geneProfiles = subject.subList(0, 7);
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
