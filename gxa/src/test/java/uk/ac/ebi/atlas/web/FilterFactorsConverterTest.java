
package uk.ac.ebi.atlas.web;

import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class FilterFactorsConverterTest {

    private static final String SERIALIZED_FACTOR1 = "TYPE1:value1";
    private static final String SERIALIZED_FACTOR2 = "TYPE2:value2";
    private static final String SERIALIZED_FACTORS = SERIALIZED_FACTOR1 + "," + SERIALIZED_FACTOR2;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    @Before
    public void setUp() throws Exception {
        BaselineExperiment experimentMock = mock(BaselineExperiment.class);
    }

    @Test
    public void prettyPrint() {
        FactorSet factors = new FactorSet();

        factors.add(factor1);
        factors.add(factor2);

        assertThat(FilterFactorsConverter.prettyPrint(factors), is("value2, value1"));
    }

    @Test
    public void testPrettyPrintWithEmptyFactors() {
        FactorSet factors = new FactorSet();

        assertThat(FilterFactorsConverter.prettyPrint(factors), is(""));
    }

    @Test
    public void testSerializeFactors() throws Exception {
        //given
        String serializedFactors = FilterFactorsConverter.serialize(Lists.newArrayList(factor1, factor2));
        //then
        assertThat(serializedFactors, containsString(factor1.getType()));
        assertThat(serializedFactors, containsString(factor2.getValue()));
    }

    @Test
    public void testDeserialize() throws Exception {
        for(int i = 0 ; i<100000; i++){
            //given
            Set<Factor> factors = randomFactors();
            //then

            try{
                Set<Factor> result = FilterFactorsConverter.deserialize(FilterFactorsConverter.serialize(factors));
                assertEquals(factors.toString(), result, factors);
            } catch (Exception e){
                fail("Bad: "+ factors.toString()+" exception:"+e.getMessage() );
            }


        }
    }

    private Set<Factor> randomFactors() {
        Random random = new Random();

        Set<Factor> result = new HashSet<>();
        result.add(randomFactor());
        result.add(randomFactor());

        while (random.nextBoolean()){
            result.add(randomFactor());
        }

        return result;
    }

    private Factor randomFactor() {
        return new Factor(RandomStringUtils.randomAscii(6), RandomStringUtils.randomAscii(10));
    }
}

