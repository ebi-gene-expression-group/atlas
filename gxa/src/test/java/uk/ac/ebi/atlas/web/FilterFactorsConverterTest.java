
package uk.ac.ebi.atlas.web;

import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FilterFactorsConverterTest {

    private static final String SERIALIZED_FACTOR1 = "TYPE1:value1";
    private static final String SERIALIZED_FACTOR2 = "TYPE2:value2";

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");


    @Test
    public void prettyPrint() {
        FactorSet factors = new FactorSet();

        factors.add(factor1);
        factors.add(factor2);

        assertThat(FilterFactorsConverter.prettyPrint(factors), is("value2, value1"));
    }

}

