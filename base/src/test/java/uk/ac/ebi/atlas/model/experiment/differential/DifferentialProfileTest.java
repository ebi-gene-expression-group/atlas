package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialProfile;
import uk.ac.ebi.atlas.model.experiment.differential.Regulation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileTest {

    private static final String GENE_ID = "A_GENE_ID";
    private static final String GENE_NAME = "A_GENE_NAME";

    @Mock
    private DifferentialExpression differentialExpressionMock1;

    @Mock
    private DifferentialExpression differentialExpressionMock2;

    @Mock
    private Contrast contrastMock1;

    private DifferentialProfile<DifferentialExpression> subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new DifferentialProfile<>(GENE_ID, GENE_NAME);
    }

    @Test
    public void testDefaultMinMaxExpressionLevels() throws Exception {
        //given
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(Double.NaN));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.NaN));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(Double.NaN));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.NaN));
    }


    @Test
    public void addingAnOverExpressedExpressionShouldUpdateMinAndMaxUpRegulatedLevelsAndSpecificity() throws Exception {
        //given
        given(differentialExpressionMock1.isOverExpressed()).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.4D);
        given(differentialExpressionMock2.isOverExpressed()).willReturn(true);
        given(differentialExpressionMock2.getLevel()).willReturn(0.6D);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock1);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(0.6D));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(0.4D));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(Double.NaN));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.NaN));

        //and
        assertThat(subject.getSpecificity(Regulation.UP), is(2));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(2));
        assertThat(subject.getSpecificity(Regulation.DOWN), is(0));
    }

    @Test
    public void addingUnderExpressedExpressionsShouldUpdateMinAndMaxDownRegulatedLevelsAndSpecificity() throws Exception {
        //given
        given(differentialExpressionMock1.isUnderExpressed()).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(-0.3D);
        given(differentialExpressionMock2.isUnderExpressed()).willReturn(true);
        given(differentialExpressionMock2.getLevel()).willReturn(-0.5D);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock1);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(Double.NaN));
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.NaN));
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(-0.5D));
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(-0.3D));

        //and
        assertThat(subject.getSpecificity(Regulation.DOWN), is(2));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(2));
        assertThat(subject.getSpecificity(Regulation.UP), is(0));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfOneExpression() throws Exception {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.3D);
        given(differentialExpressionMock1.isKnown()).willReturn(true);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);


        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(false);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);
        given(differentialExpressionMock2.isKnown()).willReturn(true);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock1, contrastMock2));
        assertThat(averageExpressionLevelOn, is(0.15));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfBoth() throws Exception {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(-0.3D);
        given(differentialExpressionMock1.isKnown()).willReturn(true);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(-0.5D);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(contrastMock1, contrastMock2));
        assertThat(averageExpressionLevelOn, is(0.4D));
    }

    @Test
    public void getMinDownRegulatedWhen1ExpressionIsNegativeInfinity() {
        //given
        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock2.isUnderExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(Double.NEGATIVE_INFINITY));
    }


    @Test
    public void getMinDownRegulatedWhen1of2ExpressionIsNegativeInfinity() {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock1.isUnderExpressed()).willReturn(true);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getLevel()).willReturn(-5D);
        given(differentialExpressionMock1.isKnown()).willReturn(true);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock2.isUnderExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMinDownRegulatedExpressionLevel(), is(-5D));
    }


    @Test
    public void getMaxDownRegulatedWhen1of2ExpressionIsNegativeInfinity() {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock1.isUnderExpressed()).willReturn(true);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getLevel()).willReturn(-5D);
        given(differentialExpressionMock1.isKnown()).willReturn(true);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock2.isUnderExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(Double.NEGATIVE_INFINITY));
    }


    @Test
    public void getMaxDownRegulatedWhen1ExpressionIsNegativeInfinity() {
        //given
        given(differentialExpressionMock2.isRegulatedLike(Regulation.DOWN)).willReturn(true);
        given(differentialExpressionMock2.isUnderExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.NEGATIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMaxDownRegulatedExpressionLevel(), is(Double.NEGATIVE_INFINITY));
    }


    @Test
    public void getMinUpRegulatedWhen1ExpressionIsInfinity() {
        //given
        given(differentialExpressionMock2.isRegulatedLike(Regulation.UP)).willReturn(true);
        given(differentialExpressionMock2.isOverExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(Double.POSITIVE_INFINITY));
    }

    @Test
    public void getMinUpRegulatedWhen1of2ExpressionsIsInfinity() {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.UP)).willReturn(true);
        given(differentialExpressionMock1.isOverExpressed()).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(5D);
        given(differentialExpressionMock1.isKnown()).willReturn(true);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.UP)).willReturn(true);
        given(differentialExpressionMock2.isOverExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMinUpRegulatedExpressionLevel(), is(5D));
    }

    @Test
    public void getMaxUpRegulatedWhen1ExpressionIsInfinity() {
        //given
        given(differentialExpressionMock2.isRegulatedLike(Regulation.UP)).willReturn(true);
        given(differentialExpressionMock2.isOverExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(Double.POSITIVE_INFINITY));
    }


    @Test
    public void getMaxUpRegulatedWhen1of2ExpressionsIsInfinity() {
        //given
        given(differentialExpressionMock1.isRegulatedLike(Regulation.UP)).willReturn(true);
        given(differentialExpressionMock1.isOverExpressed()).willReturn(true);
        given(differentialExpressionMock1.getLevel()).willReturn(0.3D);
        given(differentialExpressionMock1.isKnown()).willReturn(true);
        Contrast contrastMock1 = mock(Contrast.class);
        given(differentialExpressionMock1.getContrast()).willReturn(contrastMock1);

        given(differentialExpressionMock2.isRegulatedLike(Regulation.UP)).willReturn(true);
        given(differentialExpressionMock2.isOverExpressed()).willReturn(true);
        Contrast contrastMock2 = mock(Contrast.class);
        given(differentialExpressionMock2.getLevel()).willReturn(Double.POSITIVE_INFINITY);
        given(differentialExpressionMock2.isKnown()).willReturn(true);
        given(differentialExpressionMock2.getContrast()).willReturn(contrastMock2);

        //when
        subject.add(differentialExpressionMock1);
        subject.add(differentialExpressionMock2);

        //then
        assertThat(subject.getMaxUpRegulatedExpressionLevel(), is(Double.POSITIVE_INFINITY));
    }

}
