
package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

}
