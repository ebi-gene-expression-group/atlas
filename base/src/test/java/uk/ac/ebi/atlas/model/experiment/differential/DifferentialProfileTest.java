package uk.ac.ebi.atlas.model.experiment.differential;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.rnaseq.RnaSeqProfile;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialProfileTest {

    private static final String GENE_ID = "A_GENE_ID";
    private static final String GENE_NAME = "A_GENE_NAME";


    private List<Contrast> fakeContrasts = ContrastTest.get(2);

    private RnaSeqProfile subject;

    @Before
    public void setUp() throws Exception {
        this.subject = new RnaSeqProfile(GENE_ID, GENE_NAME);
    }



    @Test
    public void addingAnOverExpressedExpressionShouldUpdateMinAndMaxUpRegulatedLevelsAndSpecificity() throws Exception {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, 0.4D, fakeContrasts.get(0));
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, 0.6D, fakeContrasts.get(1));

        //when
        subject.add(fakeContrasts.get(0),differentialExpressionMock1);
        subject.add(fakeContrasts.get(1),differentialExpressionMock2);

        //then
        assertThat(subject.getSpecificity(Regulation.UP), is(2));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(2));
        assertThat(subject.getSpecificity(Regulation.DOWN), is(0));
    }

    @Test
    public void addingUnderExpressedExpressionsShouldUpdateMinAndMaxDownRegulatedLevelsAndSpecificity() throws Exception {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, -0.3D, fakeContrasts.get(0));
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, -0.5D, fakeContrasts.get(1));

        //when
        subject.add(fakeContrasts.get(0),differentialExpressionMock1);
        subject.add(fakeContrasts.get(1),differentialExpressionMock2);

        //then
        assertThat(subject.getSpecificity(Regulation.DOWN), is(2));
        assertThat(subject.getSpecificity(Regulation.UP_DOWN), is(2));
        assertThat(subject.getSpecificity(Regulation.UP), is(0));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfOneExpression() throws Exception {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, -0.3D, fakeContrasts.get(0));
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, 0.3D, fakeContrasts.get(1));

        //when
        subject.add(fakeContrasts.get(0),differentialExpressionMock1);
        subject.add(fakeContrasts.get(1),differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(fakeContrasts));
        assertThat(averageExpressionLevelOn, is(0.3));
    }

    @Test
    public void getAverageExpressionLevelOnShouldReturnAverageValueOfBoth() throws Exception {
        DifferentialExpression differentialExpressionMock1
                = new DifferentialExpression(0.05, -0.3D, fakeContrasts.get(0));
        DifferentialExpression differentialExpressionMock2
                = new DifferentialExpression(0.05, -0.5D, fakeContrasts.get(1));

        //when
        subject.add(fakeContrasts.get(0),differentialExpressionMock1);
        subject.add(fakeContrasts.get(1),differentialExpressionMock2);

        //then
        double averageExpressionLevelOn = subject.getAverageExpressionLevelOn(Sets.newHashSet(fakeContrasts));
        assertThat(averageExpressionLevelOn, is(0.4D));
    }

}
