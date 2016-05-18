
package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineRequestContextTest {

    BaselineRequestContext subject;

    @Mock
    BaselineRequestPreferences preferencesMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineRequestContext();
        subject.setRequestPreferences(preferencesMock);

        when(preferencesMock.getQueryFactorType()).thenReturn("QUERY_FACTOR");
    }

    @Test
    public void testGetSelectedFilterFactors() throws Exception {
        assertThat(subject.getSelectedFilterFactors(), is(nullValue()));
    }

    @Test
    public void testGetQueryFactorType() throws Exception {
        assertThat(subject.getQueryFactorType(), is("QUERY_FACTOR"));
    }

    @Test
    public void testSetSelectedFilterFactors() throws Exception {
        Factor factor = new Factor("type", "value");
        subject.setSelectedFilterFactors(Sets.newTreeSet(Sets.newHashSet(factor)));
        assertThat(subject.getSelectedFilterFactors(), hasItem(factor));
    }

    @Test
    public void testToString() throws Exception {
        assertThat(subject.toString(), containsString("selectedFilterFactors"));
    }
}