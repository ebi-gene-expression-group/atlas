
package uk.ac.ebi.atlas.profiles.differential.microarray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.differential.ContrastTest;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExpression;
import uk.ac.ebi.atlas.profiles.tsv.MicroarrayExpressionsRowDeserializer;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExpressionsRowDeserializerTest {

    MicroarrayExpressionsRowDeserializer subject = new MicroarrayExpressionsRowDeserializer(ContrastTest.get(2));


    @Test
    public void shouldReadValuesRight() {
        String P_VAL_1 = "1";
        String T_VAL_1 = "0.5";
        String FOLD_CHANGE_1 = "0.474360080385946";

        String P_VAL_2 = "1";
        String T_VAL_2 = "-0.5";
        String FOLD_CHANGE_2 = "-Inf";

        String[] TWO_CONTRASTS = new String[]{P_VAL_1, T_VAL_1, FOLD_CHANGE_1, P_VAL_2, T_VAL_2, FOLD_CHANGE_2};

        Iterator<MicroarrayExpression> it = subject.deserializeRow(TWO_CONTRASTS).iterator();

        MicroarrayExpression expression = it.next();
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_1)));
        assertThat(expression.getTstatistic(), is(Double.valueOf(T_VAL_1)));
        assertThat(expression.getFoldChange(), is(Double.valueOf(FOLD_CHANGE_1)));

        expression = it.next();
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_2)));
        assertThat(expression.getTstatistic(), is(Double.valueOf(T_VAL_2)));
        assertThat(expression.getFoldChange(), is(Double.NEGATIVE_INFINITY));

        assertThat(it.hasNext(), is(false));

    }

    @Test
    public void skipNAValues(){
        String P_VAL_1 = "1";
        String T_VAL_1 = "0.5";
        String FOLD_CHANGE_1 = "0.474360080385946";
        assertThat(subject.deserializeRow(new String[]{P_VAL_1, T_VAL_1, FOLD_CHANGE_1}).size(), is(1));
        assertThat(subject.deserializeRow(new String[]{P_VAL_1, T_VAL_1, FOLD_CHANGE_1, "NA", "NA","NA"}).size(), is(1));
        assertThat(subject.deserializeRow(new String[]{"NA","NA","NA", P_VAL_1, T_VAL_1, FOLD_CHANGE_1}).size(), is(1));
        assertThat(subject.deserializeRow(new String[]{"NA","NA","NA", P_VAL_1, T_VAL_1, FOLD_CHANGE_1, "NA", "NA", "NA"})//Batman!
                 .size(), is(1));
    }
}
