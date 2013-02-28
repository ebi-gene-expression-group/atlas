//ToDo: this test is too complex. It's not really unit test on GeneProfile because it tests all the chain of builder , preconditions , etc....

/*
package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.RequestContext;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileBuilderTest {

    private GeneProfile.Builder subject;

    @Mock
    private GeneProfilePrecondition geneProfilePreconditionMock;
    @Mock
    private GeneExpressionPrecondition geneExpressionPreconditionMock;

    @Mock
    private RequestContext requestContextMock;

    @Mock
    private Expression expressionMock1;
    @Mock
    private Expression expressionMock2;



    @Before
    public void initSubject() {
        subject = new GeneProfile.Builder();
        subject.setGeneProfilePrecondition(geneProfilePreconditionMock);
        subject.setGeneExpressionPrecondition(geneExpressionPreconditionMock);
        subject.setRequestContext(requestContextMock);

        when(requestContextMock.getCutoff()).thenReturn(0d);
        when(requestContextMock.isSpecific()).thenReturn(true);
        when(requestContextMock.getQueryFactorType()).thenReturn("ORGANISM_PART");
        when(requestContextMock.getAllQueryFactors()).thenReturn(Collections.EMPTY_SET);
        when(requestContextMock.getFilteredBySpecies()).thenReturn("homo");
        when(requestContextMock.getSelectedFilterFactors()).thenReturn(Collections.EMPTY_SET);

        when(geneExpressionPreconditionMock.setCutoff(anyDouble())).thenReturn(geneExpressionPreconditionMock);
        when(geneExpressionPreconditionMock.setFilterFactors(anySet())).thenReturn(geneExpressionPreconditionMock);
        when(geneProfilePreconditionMock.setAllQueryFactors(anySet())).thenReturn(geneProfilePreconditionMock);
        when(geneProfilePreconditionMock.setSelectedQueryFactors(anySet())).thenReturn(geneProfilePreconditionMock);
        when(geneProfilePreconditionMock.setSpecific(anyBoolean())).thenReturn(geneProfilePreconditionMock);
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
        given(geneExpressionPreconditionMock.apply(Matchers.any(Expression.class))).willReturn(false);
        given(geneProfilePreconditionMock.apply(Matchers.any(GeneProfile.class))).willReturn(false);
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);

        //when
        subject.setGeneExpressionPrecondition(geneExpressionPreconditionMock);
        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create(), is(nullValue()));

    }

    @Test
    public void createShouldReturnGeneProfileIfAtLeastOneExpressionSatisfiesPreconditionWithoutSelectedQueryFactors() {
        //given
        given(geneExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getFactor("factor_type")).willReturn(mock(Factor.class));

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

        given(geneExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPreconditionMock.apply(expressionMock2)).willReturn(false);

        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock1.getFactor("factor_type")).willReturn(selectedFactorMock);

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
        given(geneExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPreconditionMock.apply(expressionMock2)).willReturn(true);
        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.getLevel()).willReturn(3d);
        given(expressionMock1.getFactor("factor_type")).willReturn(selectedFactorMock);
        given(expressionMock2.getFactor("factor_type")).willReturn(otherFactorMock);

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

        given(geneExpressionPreconditionMock.apply(expressionMock1)).willReturn(true);
        given(geneExpressionPreconditionMock.apply(expressionMock2)).willReturn(true);

        //and
        given(expressionMock1.isGreaterThan(0d)).willReturn(true);
        given(expressionMock1.getLevel()).willReturn(5d);
        given(expressionMock2.isGreaterThan(0d)).willReturn(true);
        given(expressionMock2.getLevel()).willReturn(7d);
        given(expressionMock1.getFactor("factor_type")).willReturn(selectedFactorMock);
        given(expressionMock2.getFactor("factor_type")).willReturn(otherFactorMock);

        subject.forGeneId("Gene1")
                .addExpression(expressionMock1)
                .addExpression(expressionMock2);

        //then
        assertThat(subject.create(), is(nullValue()));

    }

}
*/