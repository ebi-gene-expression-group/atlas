package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileBuilderTest {

    public GeneProfile.Builder subject;

    @Mock
    private Expression expressionMock1;
    @Mock
    private Expression expressionMock2;


    @Before
    public void initSubject() {
        subject = new GeneProfile.Builder();
    }

    @Test(expected = IllegalStateException.class)
    public void createShouldFailWhenSetGeneIdIsNotInvoked() {
        subject.addExpression(expressionMock1).create();
    }

    @Test(expected = IllegalStateException.class)
    public void addExpressionShouldFailBeforeSetGeneIdIsNotInvoked() {
        subject.addExpression(expressionMock1);
    }

    @Test
    public void createShouldReturnNullIfNoneOfTheExpressionsSatisfyPreconditionWithoutSelectedQueryFactors() {
        //given
        GeneExpressionPrecondition geneExpressionPrecondition = mock(GeneExpressionPrecondition.class);
        given(geneExpressionPrecondition.apply(Matchers.any(Expression.class))).willReturn(false);
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);

        //when
        subject.setGeneExpressionPrecondition(geneExpressionPrecondition);
        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create(), is(nullValue()));

    }

    @Test
    public void createShouldReturnGeneProfileIfAtLeastOneExpressionSatisfiesPreconditionWithoutSelectedQueryFactors() {
        //given
        GeneExpressionPrecondition geneExpressionPrecondition = mock(GeneExpressionPrecondition.class);
        given(geneExpressionPrecondition.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPrecondition.getQueryFactorType()).willReturn("factor_type");
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getFactor("factor_type")).willReturn(mock(Factor.class));
        //when
        subject.setGeneExpressionPrecondition(geneExpressionPrecondition);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create().getGeneId(), is("Gene1"));

    }

    @Test
    public void createShouldReturnGeneProfileIfOnlyOneExpressionPresentForPreconditionWithSelectedQueryFactors() {
        //given
        Factor selectedFactorMock = new Factor("factor_type", "value1");

        GeneExpressionPrecondition geneExpressionPrecondition = mock(GeneExpressionPrecondition.class);
        given(geneExpressionPrecondition.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPrecondition.apply(expressionMock2)).willReturn(false);
        given(geneExpressionPrecondition.getQueryFactorType()).willReturn("factor_type");
        given(geneExpressionPrecondition.isSpecific()).willReturn(true);
        given(geneExpressionPrecondition.getSelectedQueryFactors()).willReturn(Sets.newHashSet(selectedFactorMock));
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock1.getFactor("factor_type")).willReturn(selectedFactorMock);

        //when
        subject.setGeneExpressionPrecondition(geneExpressionPrecondition);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create().getGeneId(), is("Gene1"));

    }

    @Test
    public void createShouldReturnGeneProfileIfAtLeastOneExpressionSatisfiesPreconditionWithSelectedQueryFactors() {
        //given
        Factor selectedFactorMock = new Factor("factor_type", "value1");
        Factor otherFactorMock = new Factor("factor_type", "value2");

        GeneExpressionPrecondition geneExpressionPrecondition = mock(GeneExpressionPrecondition.class);
        given(geneExpressionPrecondition.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPrecondition.apply(expressionMock2)).willReturn(true);
        given(geneExpressionPrecondition.getQueryFactorType()).willReturn("factor_type");
        given(geneExpressionPrecondition.isSpecific()).willReturn(true);
        given(geneExpressionPrecondition.getSelectedQueryFactors()).willReturn(Sets.newHashSet(selectedFactorMock));
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.getLevel()).willReturn(3d);
        given(expressionMock1.getFactor("factor_type")).willReturn(selectedFactorMock);
        given(expressionMock2.getFactor("factor_type")).willReturn(otherFactorMock);
        //when
        subject.setGeneExpressionPrecondition(geneExpressionPrecondition);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create().getGeneId(), is("Gene1"));

    }

    @Test
    public void createShouldReturnNullIfExpressionAverageOfSelectedQueryFactorIsSmallerThanRest() {
        //given
        Factor selectedFactorMock = new Factor("factor_type", "value1");
        Factor otherFactorMock = new Factor("factor_type", "value2");

        GeneExpressionPrecondition geneExpressionPrecondition = mock(GeneExpressionPrecondition.class);
        given(geneExpressionPrecondition.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPrecondition.apply(expressionMock2)).willReturn(true);
        given(geneExpressionPrecondition.getQueryFactorType()).willReturn("factor_type");
        given(geneExpressionPrecondition.isSpecific()).willReturn(true);
        given(geneExpressionPrecondition.getSelectedQueryFactors()).willReturn(Sets.newHashSet(selectedFactorMock));
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.getLevel()).willReturn(7d);
        given(expressionMock1.getFactor("factor_type")).willReturn(selectedFactorMock);
        given(expressionMock2.getFactor("factor_type")).willReturn(otherFactorMock);
        //when
        subject.setGeneExpressionPrecondition(geneExpressionPrecondition);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create(), is(nullValue()));

    }

}
