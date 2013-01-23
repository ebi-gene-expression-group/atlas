package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileBuilderTest {

    public GeneProfile.Builder subject;

    @Mock
    private Expression expressionMock;

    @Before
    public void initSubject(){
        subject = new GeneProfile.Builder();
    }

    @Test(expected = IllegalStateException.class)
    public void createShouldFailWhenSetGeneIdIsNotInvoked(){
        subject.addExpression(expressionMock).create();
    }

    @Test(expected = IllegalStateException.class)
    public void addExpressionShouldFailBeforeSetGeneIdIsNotInvoked(){
        subject.addExpression(expressionMock);
    }

}
