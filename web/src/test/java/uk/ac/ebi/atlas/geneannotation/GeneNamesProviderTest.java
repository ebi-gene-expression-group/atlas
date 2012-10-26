package uk.ac.ebi.atlas.geneannotation;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeneNamesProviderTest {

    private GeneNamesProvider subject;

    private ConcurrentMap<String, String> map = new ConcurrentHashMap();

    @Before
    public void initializeSubject() {
        map.put("e1", "g1");

        subject = new GeneNamesProvider();
        subject.setGeneNames(map);
    }

    @Test
    public void testGetGeneName() throws Exception {
        assertThat(subject.getGeneName("e1"), is("g1"));

        assertThat(subject.getGeneName("not there"), is("not there"));
    }


}
