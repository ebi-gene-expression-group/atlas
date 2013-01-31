package uk.ac.ebi.atlas.streams;

import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.Expression;
import uk.ac.ebi.atlas.model.Factor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ExpressionBufferTest {

    private static final String GENE_ID = "ENST00000000233";

    public static final String EXPRESSION_LEVEL_1 = "0";

    public static final String EXPRESSION_LEVEL_2 = "42.9134";
    public static final String EXPRESSION_LEVEL_3 = "0.0001";
    private static final String[] THREE_EXPRESSION_LEVELS = new String[]{GENE_ID, EXPRESSION_LEVEL_1, EXPRESSION_LEVEL_2, EXPRESSION_LEVEL_3};

    private ExpressionsBuffer subject;


    @Before
    public void initializeSubject() {

        Factor factor1 = new Factor("ORGANISM_PART", "org", "lung");
        Factor factor2 = new Factor("ORGANISM_PART", "org", "liver");
        Factor factor3 = new Factor("ORGANISM_PART", "org", "longue");

        // the only possible factor values here are the default ones
        List<Set<Factor>> orderedAllFactorValues = new LinkedList<>();
        orderedAllFactorValues.add(Sets.newHashSet(factor1));
        orderedAllFactorValues.add(Sets.newHashSet(factor2));
        orderedAllFactorValues.add(Sets.newHashSet(factor3));

        subject = new ExpressionsBuffer(orderedAllFactorValues);

    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        Expression expression = subject.poll();
        //then we expect first expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));

        //given we poll again
        expression = subject.poll();
        //then we expect second Expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));

        //given we poll again
        expression = subject.poll();
        //then we expect second Expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_3)));

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


}
