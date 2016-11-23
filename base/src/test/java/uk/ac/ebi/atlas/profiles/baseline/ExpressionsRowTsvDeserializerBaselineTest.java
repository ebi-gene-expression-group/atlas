package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsRowTsvDeserializerBaselineTest {

    public static final String EXPRESSION_LEVEL_1 = "0";

    public static final String EXPRESSION_LEVEL_2 = "42.9134";
    public static final String EXPRESSION_LEVEL_3 = "0.0001";
    private static final String[] THREE_EXPRESSION_LEVELS = new String[]{EXPRESSION_LEVEL_1, EXPRESSION_LEVEL_2, EXPRESSION_LEVEL_3};

    private ExpressionsRowTsvDeserializerBaseline subject;


    @Before
    public void initializeSubject() {

        Factor factor1 = new Factor("ORGANISM_PART", "lung");
        Factor factor2 = new Factor("ORGANISM_PART", "liver");
        Factor factor3 = new Factor("ORGANISM_PART", "longue");

        // the only possible factor values here are the default ones
        List<FactorGroup> orderedAllFactorValues = new LinkedList<>();
        orderedAllFactorValues.add(new FactorSet().add(factor1));
        orderedAllFactorValues.add(new FactorSet().add(factor2));
        orderedAllFactorValues.add(new FactorSet().add(factor3));

        subject = new ExpressionsRowTsvDeserializerBaseline(orderedAllFactorValues);

    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        BaselineExpression expression = subject.next();
        //then we expect first expression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));

        //given we next again
        expression = subject.next();
        //then we expect second BaselineExpression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));

        //given we next again
        expression = subject.next();
        //then we expect second BaselineExpression
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_3)));

    }

    @Test
    public void bufferShouldReturnNullWhenExhausted() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when
        subject.next();
        subject.next();
        subject.next();
        //then we expect next to return null
        assertThat(subject.next(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadShouldRefillAnExhaustedBuffer() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);

        //when we next until exhaustion
        BaselineExpression run;
        do {
            run = subject.next();
        } while (run != null);
        //and we reload again with new values
        subject.reload("1", "2", "3");
        //and we next
        BaselineExpression expression = subject.next();
        //then we expect to find the new values
        assertThat(expression.getLevel(), is(1d));
    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(THREE_EXPRESSION_LEVELS);
        //and we next only a single expression
        subject.next();

        //when we reload again
        subject.reload(THREE_EXPRESSION_LEVELS);

        //then we expect an IllegalArgumentException
    }

    @Mock
    FactorGroup factorGroup;


    @Test(expected = IllegalArgumentException.class)
    public void checkValuesLengthEqualsHeaderLength() {
        ImmutableList<FactorGroup> factorGroups = ImmutableList.of(factorGroup, factorGroup);

        ExpressionsRowTsvDeserializerBaseline baselineExpressionsQueue = new ExpressionsRowTsvDeserializerBaseline(factorGroups);

        baselineExpressionsQueue.reload("1");
    }

    @Test
    public void testQuartiles() {
        String[] values = {"1,2,3,4,5", "0.1,0.2,0.3,0.4,0.5", "NA"};

        Factor factor1 = new Factor("ORGANISM_PART", "lung");
        Factor factor2 = new Factor("ORGANISM_PART", "liver");
        Factor factor3 = new Factor("ORGANISM_PART", "brain");


        FactorGroup factorGroup1 = new FactorSet(factor1);
        FactorGroup factorGroup2 = new FactorSet(factor2);
        FactorGroup factorGroup3 = new FactorSet(factor3);

        List<FactorGroup> orderedFactorGroups = ImmutableList.of(factorGroup1, factorGroup2, factorGroup3);
        subject = new ExpressionsRowTsvDeserializerBaseline(orderedFactorGroups);

        subject.reload(values);

        BaselineExpression baselineExpression1 = subject.next();
        BaselineExpression baselineExpression2 = subject.next();
        BaselineExpression baselineExpression3 = subject.next();
        assertThat(subject.next(), Is.is(nullValue()));

        assertThat(Arrays.equals(baselineExpression1.getQuartiles(), new double[]{1, 2, 3, 4, 5}), is(true));
        assertThat(baselineExpression1.getLevel(), is(3D));
        assertThat(baselineExpression1.getFactorGroup(), is(factorGroup1));

        assertThat(Arrays.equals(baselineExpression2.getQuartiles(), new double[]{0.1, 0.2, 0.3, 0.4, 0.5}), is(true));
        assertThat(baselineExpression2.getLevel(), is(0.3D));
        assertThat(baselineExpression2.getFactorGroup(), is(factorGroup2));

        assertThat(ArrayUtils.isEmpty(baselineExpression3.getQuartiles()), is(true));
        assertThat(baselineExpression3.getLevelAsString(), is("NA"));
        assertThat(baselineExpression3.getFactorGroup(), is(factorGroup3));
    }



}
