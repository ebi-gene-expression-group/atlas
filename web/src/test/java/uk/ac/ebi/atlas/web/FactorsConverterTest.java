package uk.ac.ebi.atlas.web;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.Factor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FactorsConverterTest {

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
        assertThat(serializedFactors, is("TYPE1:value1,TYPE2:value2"));
    }

    @Test
    public void testDeserialize() throws Exception {

    }
}
