package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Factor;

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
        Factor firstFactorMock = mock(Factor.class);
        Factor secondFactorMock = mock(Factor.class);
        when(firstFactorMock.getType()).thenReturn("firstFactorValueType");
        when(firstFactorMock.getName()).thenReturn(null);
        when(firstFactorMock.getValue()).thenReturn("firstFactorValueValue");
        when(secondFactorMock.getType()).thenReturn("secondFactorValueType");
        when(firstFactorMock.getName()).thenReturn(null);
        when(firstFactorMock.getValue()).thenReturn("firstFactorValueValue");
        Factor f1 = new Factor("hello","world");
        Factor f2 = new Factor("hello1","world1");

        assertThat(subject.getJsonUrl("aType", f1, f2),
                        is("{\"queryFactorType\":\"aType\",\"filterFactorsURL\":\"HELLO:world,HELLO1:world1\"}"));
    }


    @Test
    public void testIndexFactorValuesByName() {

        Factor factor1 = new Factor(ORGANISM_PART, ORGANISM_PART, "liver");
        Factor factor2 = new Factor(ORGANISM_PART, ORGANISM_PART, "heart");
        Factor factor3 = new Factor(CELL_LINE, CELL_LINE, "imr-90");
        Set<Factor> factors = Sets.newHashSet(factor1, factor2, factor3);

        assertThat(subject.factorsByName(factors), notNullValue());
        assertThat(subject.factorsByName(factors).keySet(), hasItems(ORGANISM_PART, CELL_LINE));
        assertThat(subject.factorsByName(factors).get(ORGANISM_PART), hasItems(factor1, factor2));
        assertThat(subject.factorsByName(factors).get(CELL_LINE), hasItems(factor3));
    }

}
