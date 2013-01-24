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

    private FactorValue factorValue1 = new FactorValue("type1", "value1");
    private FactorValue factorValue2 = new FactorValue("type2", "value2");

    private FactorValue factorValue3 = new FactorValue("type3", "value3");

    @Mock
    private Expression expressionMock;

    @Test
    public void checkLimitingFactorsShouldSucceedWhenExpressionHasSupersetOfLimitingFactors() throws Exception {
        //given
        subject = new GeneExpressionPrecondition();
        subject.setLimitingFactorValues(Sets.newHashSet(factorValue1, factorValue2));
        given(expressionMock.getAllFactorValues()).willReturn(Sets.newHashSet(factorValue1, factorValue2, factorValue3));

        //then
        assertThat(subject.checkLimitingFactors(expressionMock), is(true));
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenNoLimitingFactorSetIsProvided() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        given(expressionMock.getAllFactorValues()).willReturn(Sets.newHashSet(factorValue1, factorValue2, factorValue3));

        //then
        assertThat(subject.checkLimitingFactors(expressionMock), is(true));
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenLimitingFactorSetIsNull() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        given(expressionMock.getAllFactorValues()).willReturn(Sets.newHashSet(factorValue1, factorValue2, factorValue3));

        //then
        assertThat(subject.checkLimitingFactors(expressionMock), is(true));
    }

    @Test
    public void applyShouldFailExpressionHasSubsetOfLimitingFactors() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        subject.setLimitingFactorValues(Sets.newHashSet(factorValue1, factorValue2));
        given(expressionMock.getAllFactorValues()).willReturn(Sets.newHashSet(factorValue1));

        //then
        assertThat(subject.apply(expressionMock), is(false));
    }

    @Test
    public void applyShouldSucceedIfLevelIsGreaterThanCutoff() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        subject.setLimitingFactorValues(Sets.newHashSet(factorValue1, factorValue2));
        subject.setCutoff(1d);

        given(expressionMock.getAllFactorValues()).willReturn(Sets.newHashSet(factorValue1, factorValue2));
        given(expressionMock.isGreaterThan(1d)).willReturn(true);

        //then
        assertThat(subject.apply(expressionMock), is(true));
    }
}
