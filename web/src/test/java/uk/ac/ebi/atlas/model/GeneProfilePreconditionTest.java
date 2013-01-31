package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilePreconditionTest {

    private GeneExpressionPrecondition subject;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    private Factor factor3 = new Factor("type3", "value3");

    @Mock
    private Expression expressionMock;

    @Test
    public void checkLimitingFactorsShouldSucceedWhenExpressionHasSupersetOfLimitingFactors() throws Exception {
        //given
        subject = new GeneExpressionPrecondition();
        subject.setLimitingFactors(Sets.newHashSet(factor1, factor2));
        given(expressionMock.getAllFactors()).willReturn(Sets.newHashSet(factor1, factor2, factor3));

        //then
        assertThat(subject.checkLimitingFactors(expressionMock), is(true));
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenNoLimitingFactorSetIsProvided() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        given(expressionMock.getAllFactors()).willReturn(Sets.newHashSet(factor1, factor2, factor3));

        //then
        assertThat(subject.checkLimitingFactors(expressionMock), is(true));
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenLimitingFactorSetIsNull() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        given(expressionMock.getAllFactors()).willReturn(Sets.newHashSet(factor1, factor2, factor3));

        //then
        assertThat(subject.checkLimitingFactors(expressionMock), is(true));
    }

    @Test
    public void applyShouldFailExpressionHasSubsetOfLimitingFactors() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        subject.setLimitingFactors(Sets.newHashSet(factor1, factor2));
        given(expressionMock.getAllFactors()).willReturn(Sets.newHashSet(factor1));

        //then
        assertThat(subject.apply(expressionMock), is(false));
    }

    @Test
    public void applyShouldSucceedIfLevelIsGreaterThanCutoff() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        subject.setLimitingFactors(Sets.newHashSet(factor1, factor2));
        subject.setCutoff(1d);

        given(expressionMock.getAllFactors()).willReturn(Sets.newHashSet(factor1, factor2));
        given(expressionMock.isGreaterThan(1d)).willReturn(true);

        //then
        assertThat(subject.apply(expressionMock), is(true));
    }
}
