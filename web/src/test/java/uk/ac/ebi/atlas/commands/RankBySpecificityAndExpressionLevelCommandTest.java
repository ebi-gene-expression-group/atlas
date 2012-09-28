package uk.ac.ebi.atlas.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneExpression;
import uk.ac.ebi.atlas.model.GeneProfile;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class RankBySpecificityAndExpressionLevelCommandTest {

    private static final int QUEUE_SIZE = 3;

    private ObjectInputStream<GeneProfile> largeInputStream;

    private ObjectInputStream<GeneProfile> smallInputStream;

    private RankBySpecificityAndExpressionLevelCommand subject;

    public RankBySpecificityAndExpressionLevelCommandTest() {
    }

    @Before
    public void initializeSubject() throws Exception {

        //a stream with 5 profile of 2 expressions
        largeInputStream = new GeneProfileInputStreamMock(5);

        //a stream with 1 profile of 2 expressions
        smallInputStream = new GeneProfileInputStreamMock(1);


        subject = new RankBySpecificityAndExpressionLevelCommand().setRankingSize(QUEUE_SIZE);
    }

    @Test
    public void givenAStreamWithLessExpressionsThanRankSizeTheCommandShouldReturnAllTheExpressions() throws Exception {
        //when
        List<GeneExpression> top3Objects = subject.apply(smallInputStream);

        //then
        assertThat(top3Objects.size(), is(1));

    }

    @Test
    public void givenAStreamWithManyExpressionsTheCommandShouldReturnThreeExpressionExpressions() throws Exception {
        //when
        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {
        //when
        List<GeneExpression> top3Objects = subject.apply(largeInputStream);

        //and
        assertThat(top3Objects.get(0).getSpecificity(), is(1));
        //and
        assertThat(top3Objects.get(0).getLevel(), is(1D));
        //then
        assertThat(top3Objects.get(0).getGeneId(), is("1"));

        //and
        assertThat(top3Objects.get(2).getSpecificity(), is(2));
        //and
        assertThat(top3Objects.get(2).getLevel(), is(1D));
        //and
        assertThat(top3Objects.get(2).getGeneId(), is("2"));

    }

}
