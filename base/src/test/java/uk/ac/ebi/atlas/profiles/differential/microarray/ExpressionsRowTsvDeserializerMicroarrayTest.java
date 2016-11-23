
package uk.ac.ebi.atlas.profiles.differential.microarray;

import com.google.common.collect.Lists;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsRowTsvDeserializerMicroarrayTest {

    public static final String P_VAL_1 = "1";
    public static final String T_VAL_1 = "0.5";
    public static final String FOLD_CHANGE_1 = "0.474360080385946";

    public static final String P_VAL_2 = "1";
    public static final String T_VAL_2 = "-0.5";
    public static final String FOLD_CHANGE_2 = "-Inf";

    private static final String[] TWO_CONTRASTS = new String[]{P_VAL_1, T_VAL_1, FOLD_CHANGE_1, P_VAL_2, T_VAL_2, FOLD_CHANGE_2};

    private ExpressionsRowTsvDeserializerMicroarray subject;

    @Mock
    private Contrast contrast1Mock;
    @Mock
    private Contrast contrast2Mock;

    @Before
    public void initializeSubject() {
        subject = new ExpressionsRowTsvDeserializerMicroarray(Lists.newArrayList(contrast1Mock, contrast2Mock));

    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);

        //when
        MicroarrayExpression expression = subject.next();
        //then we expect first expression
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_1)));
        assertThat(expression.getTstatistic(), is(Double.valueOf(T_VAL_1)));
        assertThat(expression.getFoldChange(), is(Double.valueOf(FOLD_CHANGE_1)));
        assertThat(expression.getContrast(), is(contrast1Mock));

        //given we next again
        expression = subject.next();
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_2)));
        assertThat(expression.getTstatistic(), is(Double.valueOf(T_VAL_2)));
        assertThat(expression.getFoldChange(), is(Double.NEGATIVE_INFINITY));
        assertThat(expression.getContrast(), is(contrast2Mock));


        assertThat(subject.next(), is(nullValue()));
    }

    @Test
    public void bufferShouldReturnNullWhenExhausted() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);

        //when
        subject.next();
        subject.next();
        subject.next();
        //then we expect next next to return null
        assertThat(subject.next(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadShouldRefillAnExhaustedBuffer() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);

        //when we next until exhaustion
        MicroarrayExpression run;
        do {
            run = subject.next();
        } while (run != null);
        //and we reload again with new values
        subject.reload("1", "0.5", "2");
        //and we next
        MicroarrayExpression expression = subject.next();
        //then we expect to find the new values
        assertThat(expression.getPValue(), is(1d));
        assertThat(expression.getTstatistic(), is(0.5d));
    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);
        //and we next only a single expression
        subject.next();

        //when we reload again
        subject.reload(TWO_CONTRASTS);

        //then we expect an IllegalArgumentException
    }

    @Test
    public void skipNALines() {

        subject.reload("NA", "NA", "NA");

        MicroarrayExpression expression = subject.next();

        assertThat(expression, is(CoreMatchers.nullValue()));

        subject.reload("1", "NA", "NA");

        expression = subject.next();

        assertThat(expression, is(CoreMatchers.nullValue()));

        subject.reload("NA", "1", "NA");

        expression = subject.next();

        assertThat(expression, is(CoreMatchers.nullValue()));
    }

    @Test
    public void skipNALinesKeepsCorrespondingContrasts() {

        subject.reload("NA", "NA", "1", P_VAL_2, T_VAL_2, "-Inf");

        MicroarrayExpression expression = subject.next();

        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_2)));
        assertThat(expression.getTstatistic(), is(Double.valueOf(T_VAL_2)));
        assertThat(expression.getFoldChange(), is(Double.NEGATIVE_INFINITY));
        assertThat(expression.getContrast(), is(contrast2Mock));
    }

}
