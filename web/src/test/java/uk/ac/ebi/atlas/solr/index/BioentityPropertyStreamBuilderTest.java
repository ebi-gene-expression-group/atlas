package uk.ac.ebi.atlas.solr.index;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BioentityPropertyStreamBuilderTest {

    private BioentityPropertyStreamBuilder subject;

    @Before
    public void initSubject() {

        subject = new BioentityPropertyStreamBuilder();

    }

    @Test
    public void testGetFileName() throws Exception {
        //when
        Path path = Paths.get("directory", "anopheles_gambiae.A-AFFY-102.tsv");
        subject.forPath(path);

        String fileName = subject.getFileName();
        assertThat(fileName, is("anopheles_gambiae.A-AFFY-102.tsv"));
    }

    @Test
    public void testGetSpecies() throws Exception {
        //when
        Path path = Paths.get("directory", "anopheles_gambiae.A-AFFY-102.tsv");
        subject.forPath(path);

        String fileName = subject.getSpecies();
        assertThat(fileName, is("anopheles_gambiae"));
    }
}
