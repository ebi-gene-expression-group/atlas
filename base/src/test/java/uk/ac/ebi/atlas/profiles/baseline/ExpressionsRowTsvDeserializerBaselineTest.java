package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsRowTsvDeserializerBaselineTest {

    private AssayGroup g1 = new AssayGroup("g1", "run11","run12","run13");
    private AssayGroup g2 = new AssayGroup("g2", "run21");
    private AssayGroup g3 = new AssayGroup("g3", "run31","run32");

    private List<AssayGroup> headers = ImmutableList.of(g1, g2, g3);

    private ExpressionsRowTsvDeserializerBaseline subject = new ExpressionsRowTsvDeserializerBaseline(headers);

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        String EXPRESSION_LEVEL_1 = "0";
        String EXPRESSION_LEVEL_2 = "42.9134";
        String EXPRESSION_LEVEL_3 = "0.0001";
        String[] THREE_EXPRESSION_LEVELS = new String[]{EXPRESSION_LEVEL_1, EXPRESSION_LEVEL_2, EXPRESSION_LEVEL_3};

        Iterator<BaselineExpression> it = subject.deserializeRow(THREE_EXPRESSION_LEVELS).iterator();


        BaselineExpression expression = it.next();
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));

        expression = it.next();
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));

        expression = it.next();
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_3)));

        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void canIgnoreColumns() throws Exception {
        ExpressionsRowTsvDeserializerBaseline subject = new ExpressionsRowTsvDeserializerBaseline(headers, new int[]{0,2,4});
        String EXPRESSION_LEVEL_1 = "0";
        String EXPRESSION_LEVEL_2 = "42.9134";
        String EXPRESSION_LEVEL_3 = "0.0001";
        String[] THREE_EXPRESSION_LEVELS = new String[]{EXPRESSION_LEVEL_1, "_", EXPRESSION_LEVEL_2, "_", EXPRESSION_LEVEL_3, "_"};

        Iterator<BaselineExpression> it = subject.deserializeRow(THREE_EXPRESSION_LEVELS).iterator();


        BaselineExpression expression = it.next();
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_1)));

        expression = it.next();
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_2)));

        expression = it.next();
        assertThat(expression.getLevel(), is(Double.valueOf(EXPRESSION_LEVEL_3)));

        assertThat(it.hasNext(), is(false));
    }

    @Test
    public void testQuartiles() {
        String[] values = {"1,2,3,4,5", "0.1,0.2,0.3,0.4,0.5", "NA"};

        Iterator<BaselineExpression> it = subject.deserializeRow(values).iterator();

        BaselineExpression baselineExpression1 = it.next();
        BaselineExpression baselineExpression2 = it.next();
        BaselineExpression baselineExpression3 = it.next();
        assertThat(it.hasNext(), is(false));

        assertThat(Arrays.equals(baselineExpression1.getQuartiles(), new double[]{1, 2, 3, 4, 5}), is(true));
        assertThat(baselineExpression1.getLevel(), is(3D));

        assertThat(Arrays.equals(baselineExpression2.getQuartiles(), new double[]{0.1, 0.2, 0.3, 0.4, 0.5}), is(true));
        assertThat(baselineExpression2.getLevel(), is(0.3D));

        assertThat(ArrayUtils.isEmpty(baselineExpression3.getQuartiles()), is(true));
        assertThat(baselineExpression3.getLevel(), is(0.0D));
    }

}
