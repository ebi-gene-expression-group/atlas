package uk.ac.ebi.atlas.web.controllers;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.web.RequestPreferences;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RequestPreferencesTest {

    private RequestPreferences subject = new RequestPreferences();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetGeneIDs() throws Exception {

        //given
        subject.setGeneIDsString("g1, g2   g3 , g4 ,, g5 , , g6");

        //then
        assertThat(subject.getGeneIDs().size(), is(6));

        //and
        assertThat(subject.getGeneIDs(), hasItems("g1", "g2", "g3", "g4", "g5", "g6"));

    }
}
