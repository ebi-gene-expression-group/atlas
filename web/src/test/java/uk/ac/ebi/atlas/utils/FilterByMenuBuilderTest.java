package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.FactorValue;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterByMenuBuilderTest {
    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String CELL_LINE = "CELL_LINE";


    private FilterByMenuBuilder subject = new FilterByMenuBuilder();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetJsonUrl() throws Exception {
        FactorValue firstFactorValueMock = mock(FactorValue.class);
        FactorValue secondFactorValueMock = mock(FactorValue.class);
        when(firstFactorValueMock.getType()).thenReturn("firstFactorValueType");
        when(firstFactorValueMock.getName()).thenReturn(null);
        when(firstFactorValueMock.getValue()).thenReturn("firstFactorValueValue");
        when(secondFactorValueMock.getType()).thenReturn("secondFactorValueType");
        when(firstFactorValueMock.getName()).thenReturn(null);
        when(firstFactorValueMock.getValue()).thenReturn("firstFactorValueValue");
        FactorValue f1 = new FactorValue("hello","world");
        FactorValue f2 = new FactorValue("hello1","world1");

        assertThat(subject.getJsonUrl("aType", f1, f2),
                        is("{\"queryFactorType\":\"aType\",\"filterFactorValuesURL\":\"HELLO:world,HELLO1:world1\"}"));
    }


    @Test
    public void testIndexFactorValuesByName() {

        FactorValue factorValue1 = new FactorValue(ORGANISM_PART, ORGANISM_PART, "liver");
        FactorValue factorValue2 = new FactorValue(ORGANISM_PART, ORGANISM_PART, "heart");
        FactorValue factorValue3 = new FactorValue(CELL_LINE, CELL_LINE, "imr-90");
        Set<FactorValue> factorValues = Sets.newHashSet(factorValue1, factorValue2, factorValue3);

        assertThat(subject.factorValuesByName(factorValues), notNullValue());
        assertThat(subject.factorValuesByName(factorValues).keySet(), hasItems(ORGANISM_PART, CELL_LINE));
        assertThat(subject.factorValuesByName(factorValues).get(ORGANISM_PART), hasItems(factorValue1, factorValue2));
        assertThat(subject.factorValuesByName(factorValues).get(CELL_LINE), hasItems(factorValue3));
    }

}
