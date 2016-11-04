package uk.ac.ebi.atlas.differential;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfilesListTest {

    private uk.ac.ebi.atlas.model.differential.DifferentialProfilesList subject;

    @Mock
    private DifferentialProfile differentialProfileMock1;
    @Mock
    private DifferentialProfile differentialProfileMock2;
    @Mock
    private DifferentialProfile differentialProfileMock3;

    @Before
    public void initSubject() throws Exception {

        Collection<DifferentialProfile> differentialProfiles = Lists.newArrayList(differentialProfileMock1, differentialProfileMock2, differentialProfileMock3);

        subject = new DifferentialProfilesList(differentialProfiles);
    }

    @Test
    public void maxUpRegulatedExpressionLevelShouldBeZeroWhenAllProfilesHaveNoUpRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMaxUpRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxUpRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock3.getMaxUpRegulatedExpressionLevel()).willReturn(0D);

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0D));
    }

    @Test
    public void minUpRegulatedExpressionLevelShouldBeNaNWhenAllProfilesHaveNoUpRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMinUpRegulatedExpressionLevel()).willReturn(Double.NaN);
        given(differentialProfileMock2.getMinUpRegulatedExpressionLevel()).willReturn(Double.NaN);
        given(differentialProfileMock3.getMinUpRegulatedExpressionLevel()).willReturn(Double.NaN);

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.NaN));
    }

    @Test
    public void maxUpRegulatedExpressionLevelShouldReturnTheMaxUpRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        given(differentialProfileMock1.getMaxUpRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxUpRegulatedExpressionLevel()).willReturn(23.3D);
        given(differentialProfileMock3.getMaxUpRegulatedExpressionLevel()).willReturn(0.22D);

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(23.3D));
    }

    @Test
    public void minUpRegulatedExpressionLevelShouldReturnTheMinUpRegulatedExpressionLevelAcrossAllProfiiles() throws Exception {
        //given
        given(differentialProfileMock1.getMinUpRegulatedExpressionLevel()).willReturn(23D);
        given(differentialProfileMock2.getMinUpRegulatedExpressionLevel()).willReturn(0.001D);
        given(differentialProfileMock3.getMinUpRegulatedExpressionLevel()).willReturn(3D);

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(0.001D));
    }

    @Test
    public void maxDownRegulatedExpressionLevelShouldBeZeroWhenAllProfilesHaveNoDownRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMaxDownRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxDownRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock3.getMaxDownRegulatedExpressionLevel()).willReturn(0D);

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(0D));
    }

    @Test
    public void minDownRegulatedExpressionLevelShouldBeNaNWhenAllProfilesHaveNoDownRegulatedExpressionLevel() throws Exception {
        //given
        given(differentialProfileMock1.getMinDownRegulatedExpressionLevel()).willReturn(Double.NaN);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(Double.NaN);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(Double.NaN);

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.NaN));
    }

    @Test
    public void maxDownRegulatedExpressionLevelShouldReturnTheMaxDownRegulatedExpressionLevelAcrossAllProfiles() throws Exception {
        //given
        given(differentialProfileMock1.getMaxDownRegulatedExpressionLevel()).willReturn(0D);
        given(differentialProfileMock2.getMaxDownRegulatedExpressionLevel()).willReturn(-23.3D);
        given(differentialProfileMock3.getMaxDownRegulatedExpressionLevel()).willReturn(-0.22D);

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(-23.3D));
    }

    @Test
    public void minDownRegulatedExpressionLevelShouldReturnTheMinDownRegulatedExpressionLevelAcrossAllProfiles() throws Exception {
        //given
        given(differentialProfileMock1.getMinDownRegulatedExpressionLevel()).willReturn(-23D);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(-0.001D);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(-3D);

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(-0.001D));
    }

    @Test
    public void minDownRegulatedExpressionLevelNegativeAllInfinity() throws Exception {
        //given
        given(differentialProfileMock1.getMinDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void maxDownRegulatedExpressionLevelNegativeAllInfinity() throws Exception {
        //given
        given(differentialProfileMock1.getMaxDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialProfileMock2.getMaxDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialProfileMock3.getMaxDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void minUpRegulatedExpressionLevelAllInfinity() throws Exception {
        //given
        given(differentialProfileMock1.getMinUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialProfileMock2.getMinUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialProfileMock3.getMinUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.POSITIVE_INFINITY));
    }

    @Test
    public void maxUpRegulatedExpressionLevelAllInfinity() throws Exception {
        //given
        given(differentialProfileMock1.getMaxUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialProfileMock2.getMaxUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialProfileMock3.getMaxUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(Double.POSITIVE_INFINITY));
    }

    @Test
    public void minDownRegulatedExpressionLevel1NegativeInfinity() throws Exception {
        //given
        given(differentialProfileMock1.getMinDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(-3D);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(-4D);

        //
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(-3D));
    }

    @Test
    public void maxDownRegulatedExpressionLevel1NegativeInfinity() throws Exception {
        //given
        given(differentialProfileMock1.getMaxDownRegulatedExpressionLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialProfileMock2.getMinDownRegulatedExpressionLevel()).willReturn(-3D);
        given(differentialProfileMock3.getMinDownRegulatedExpressionLevel()).willReturn(-4D);

        //
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void minUpRegulatedExpressionLevel1Infinity() throws Exception {
        //given
        given(differentialProfileMock1.getMinUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialProfileMock2.getMinUpRegulatedExpressionLevel()).willReturn(3D);
        given(differentialProfileMock3.getMinUpRegulatedExpressionLevel()).willReturn(4D);

        //
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(3D));
    }

    @Test
    public void maxUpRegulatedExpressionLevel1Infinity() throws Exception {
        //given
        given(differentialProfileMock1.getMaxUpRegulatedExpressionLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialProfileMock2.getMaxUpRegulatedExpressionLevel()).willReturn(3D);
        given(differentialProfileMock3.getMaxUpRegulatedExpressionLevel()).willReturn(4D);

        //
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(Double.POSITIVE_INFINITY));
    }


}
