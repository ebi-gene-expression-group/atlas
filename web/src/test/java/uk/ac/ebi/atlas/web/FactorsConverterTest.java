package uk.ac.ebi.atlas.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.Factor;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class FactorsConverterTest {

    private static final String SERIALIZED_FACTOR1 = "TYPE1:value1";
    private static final String SERIALIZED_FACTOR2 = "TYPE2:value2";
    private static final String SERIALIZED_FACTORS = SERIALIZED_FACTOR1 + "," + SERIALIZED_FACTOR2;
    private FactorsConverter subject;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    @Before
    public void setUp() throws Exception {
        subject = new FactorsConverter();
    }

    @Test
    public void testSerializeFactors() throws Exception {
        //given
        String serializedFactors = subject.serializeFactors(Lists.newArrayList(factor1, factor2));
        //then
        assertThat(serializedFactors, is(SERIALIZED_FACTORS));
    }

    @Test
    public void testDeserialize() throws Exception {
        //given
        Set<Factor> factors = subject.deserialize(Sets.newHashSet(SERIALIZED_FACTOR1, SERIALIZED_FACTOR2));
        //then
        assertThat(factors, containsInAnyOrder(factor1, factor2));
    }
}
