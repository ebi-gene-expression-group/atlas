package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileTest {

    private static final String GENE_ID = "A_GENE_ID";
    private static final String GENE_NAME = "A_GENE_NAME";


    private List<Contrast> fakeContrasts = ContrastTestUtils.get(2);

    private RnaSeqProfile subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new RnaSeqProfile(GENE_ID, GENE_NAME);
    }



    @Test
    public void addingAnOverExpressedExpressionShouldUpdateMinAndMaxUpRegulatedLevelsAndSpecificity() {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, 0.4D);
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, 0.6D);

        //when
        subject.add(fakeContrasts.get(0), differentialExpressionMock1);
        subject.add(fakeContrasts.get(1), differentialExpressionMock2);

        //then
        assertThat(subject.getSpecificity(Regulation.UP), is(2L));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(2L));
        assertThat(subject.getSpecificity(Regulation.DOWN), is(0L));
    }

    @Test
    public void addingUnderExpressedExpressionsShouldUpdateMinAndMaxDownRegulatedLevelsAndSpecificity() {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, -0.3D);
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, -0.5D);

        //when
        subject.add(fakeContrasts.get(0), differentialExpressionMock1);
        subject.add(fakeContrasts.get(1), differentialExpressionMock2);

        //then
        assertThat(subject.getSpecificity(Regulation.DOWN), is(2L));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(2L));
        assertThat(subject.getSpecificity(Regulation.UP), is(0L));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfOneExpression() {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, -0.3D);
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, 0.3D);

        //when
        subject.add(fakeContrasts.get(0), differentialExpressionMock1);
        subject.add(fakeContrasts.get(1), differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(fakeContrasts));
        assertThat(averageExpressionLevelOn, is(0.3));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfBoth() {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, -0.3D);
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, -0.5D);

        //when
        subject.add(fakeContrasts.get(0), differentialExpressionMock1);
        subject.add(fakeContrasts.get(1), differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(fakeContrasts));
        assertThat(averageExpressionLevelOn, is(0.4D));
    }

}
