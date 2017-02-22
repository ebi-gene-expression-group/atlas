
package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesListTest {

    private BaselineProfilesList subject;

    @Mock
    private BaselineProfile profileMock1;

    @Mock
    private BaselineProfile profileMock2;

    @Mock
    private Factor factorMock1;

    @Mock
    private Factor factorMock2;

    @Before
    public void setUp() throws Exception {

        when(profileMock1.getMaxExpressionLevel()).thenReturn(10.0);
        when(profileMock1.getExpressionLevel(factorMock1)).thenReturn(5.0);

        when(profileMock2.getMinExpressionLevel()).thenReturn(-10.0);
        when(profileMock2.getExpressionLevel(factorMock2)).thenReturn(2.0);

        subject = new BaselineProfilesList(Lists.newArrayList(profileMock1, profileMock2));
        subject.setTotalResultCount(2);
    }

    @Test
    public void shouldReturnMaxExpressionLevel() throws Exception {
        assertThat(subject.getMaxExpressionLevel(), is(10.0));
    }

    @Test
    public void shouldReturnMinExpressionLevel() throws Exception {
        assertThat(subject.getMinExpressionLevel(), is(-10.0));
    }

    @Test
    public void shuoldReturnResultCount() throws Exception {
        assertThat(subject.getTotalResultCount(), is(2));
    }

}