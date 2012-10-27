package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentRun;
import uk.ac.ebi.atlas.model.Expression;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ExpressionBufferTest {

    private static final String RUN_ACCESSION_1 = "ERR030872";
    private static final String RUN_ACCESSION_2 = "ERR030873";
    private static final String RUN_ACCESSION_3 = "ERR030874";

    private static final String GENE_ID = "ENST00000000233";

    public static final String EXPRESSION_LEVEL_1 = "0";

    public static final String EXPRESSION_LEVEL_2 = "42.9134";
    public static final String EXPRESSION_LEVEL_3 = "0.0001";
    private static final String[] THREE_EXPRESSION_LEVELS = new String[]{GENE_ID, EXPRESSION_LEVEL_1, EXPRESSION_LEVEL_2, EXPRESSION_LEVEL_3};

    private ExpressionsBuffer subject;

    private static List<ExperimentRun> experimentRuns;

    @Before
    public void initializeSubject() {
        ExperimentRun experimentRun1 = new ExperimentRun(RUN_ACCESSION_1);
        ExperimentRun experimentRun2 = new ExperimentRun(RUN_ACCESSION_2);
        ExperimentRun experimentRun3 = new ExperimentRun(RUN_ACCESSION_3);

        experimentRuns = Lists.newArrayList(experimentRun1, experimentRun2, experimentRun3);

        subject = new ExpressionsBuffer(experimentRuns);

    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        Expression expression = subject.poll();
        //then we expect first expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));
        assertThat(expression.getFactorValues(), is(experimentRuns.get(0).getFactorValues()));

        //given we poll again
        expression = subject.poll();
        //then we expect second Expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));
        assertThat(expression.getFactorValues(), is(experimentRuns.get(1).getFactorValues()));

        //given we poll again
        expression = subject.poll();
        //then we expect second Expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_3)));
        assertThat(expression.getFactorValues(), is(experimentRuns.get(2).getFactorValues()));

    }

    @Test
    public void bufferShouldReturnNullWhenExhausted() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        subject.poll();
        subject.poll();
        subject.poll();
        //then we expect next poll to return null
        assertThat(subject.poll(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadShouldRefillAnExhaustedBuffer() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when we poll until exhaustion
        Expression run;
        do {
            run = subject.poll();
        } while (run != null);
        //and we reload again with new values
        subject.reload("T1", "1", "2", "3");
        //and we poll
        Expression expression = subject.poll();
        //then we expect to find the new values
        assertThat(expression.getLevel(), is(1d));
        assertThat(expression.getRunAccession(), is(experimentRuns.get(0).getRunAccession()));
    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);
        //and we poll only a single expression
        subject.poll();

        //when we reload again
        subject.reload(THREE_EXPRESSION_LEVELS);

        //then we expect an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfWeProvideMoreValuesThanRuns() throws Exception {
        //given that we built a buffer suitable for three experiment runs

        //when we reload with 4 expressions per line
        subject.reload(GENE_ID, "0", "42.9134", "0.0001", "666");

        //then we expect an IllegalArgumentException
    }

    @Test(expected = IllegalArgumentException.class)
    public void reloadShouldThrowExceptionIfWeProvideLessValuesThanRuns() throws Exception {
        //given that we built a buffer suitable for three experiment runs

        //when we reload with 2 expressions per line
        subject.reload(GENE_ID, "0", "42.9134");

        //then we expect an IllegalArgumentException
    }


}
