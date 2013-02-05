package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Factor;

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
        Factor f1 = new Factor("hello", "world");
        Factor f2 = new Factor("hello1", "world1");

        assertThat(subject.getJsonUrl("aType", f1, f2),
                is("{\"queryFactorType\":\"aType\",\"filterFactorsURL\":\"HELLO:world,HELLO1:world1\"}"));
    }

}
