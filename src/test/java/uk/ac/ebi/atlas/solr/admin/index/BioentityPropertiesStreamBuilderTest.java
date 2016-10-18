
package uk.ac.ebi.atlas.solr.admin.index;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class BioentityPropertiesStreamBuilderTest {
    private static final String ANOPHELES_GAMBIAE = "anopheles gambiae";

    private BioentityPropertiesStreamBuilder subject;

    @Mock
    private BioentityPropertiesBuilder bioentityPropertiesBuilderMock;

    @Before
    public void initSubject() {

        subject = new BioentityPropertiesStreamBuilder(bioentityPropertiesBuilderMock);

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
        assertThat(fileName, is(ANOPHELES_GAMBIAE));
    }
}
