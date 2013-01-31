package uk.ac.ebi.atlas.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.FactorValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterByMenuBuilderTest {

    @Mock
    private FactorValueUtils factorValueUtilsMock;

    private FilterByMenuBuilder subject = new FilterByMenuBuilder(factorValueUtilsMock);

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

}
