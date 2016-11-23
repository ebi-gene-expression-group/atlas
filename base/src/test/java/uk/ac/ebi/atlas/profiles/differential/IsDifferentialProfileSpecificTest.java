
package uk.ac.ebi.atlas.profiles.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsDifferentialProfileSpecificTest {

    IsDifferentialProfileSpecific subject;

    @Mock
    private AssayGroup referenceAssayGroup;

    @Mock
    private AssayGroup testAssayGroup;

    Contrast contrast1 = new Contrast("c1", "a1", referenceAssayGroup, testAssayGroup, "c1");

    Contrast contrast2 = new Contrast("c2", "a1", referenceAssayGroup, testAssayGroup, "c2");

    @Mock
    DifferentialProfile profileMock;

    Set<Contrast> allQueryFactors = Sets.newHashSet(contrast1, contrast2);

    Set<Contrast> selectedQueryFactors = Sets.newHashSet(contrast1);

    Set<Contrast> nonSelectedQueryContrasts = Sets.newHashSet(contrast2);

    @Before
    public void setUp() throws Exception {
        subject = new IsDifferentialProfileSpecific(selectedQueryFactors, allQueryFactors);
    }

    @Test
    public void testApply() throws Exception {
        assertThat(subject.apply(profileMock), is(false));

        when(profileMock.getAverageExpressionLevelOn(selectedQueryFactors)).thenReturn(0.01);
        when(profileMock.getStrongestExpressionLevelOn(nonSelectedQueryContrasts)).thenReturn(0.5);
        assertThat(subject.apply(profileMock), is(false));

        when(profileMock.getAverageExpressionLevelOn(selectedQueryFactors)).thenReturn(0.5);
        when(profileMock.getStrongestExpressionLevelOn(nonSelectedQueryContrasts)).thenReturn(0.01);
        assertThat(subject.apply(profileMock), is(true));
    }
}