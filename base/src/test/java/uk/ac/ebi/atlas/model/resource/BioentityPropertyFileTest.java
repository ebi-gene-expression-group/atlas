package uk.ac.ebi.atlas.model.resource;

import org.junit.Test;
import uk.ac.ebi.atlas.solr.bioentities.BioentityProperty;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BioentityPropertyFileTest {
    private BioentityPropertyFile testFile = new BioentityPropertyFile(null, null) {
        @Override
        public Stream<BioentityProperty> get() {
            return null;
        }
    };

    @Test
    public void testCleanUpPropertyValue() {
        assertThat(
                testFile.cleanUpPropertyValue("BRCA1"),
                is("BRCA1")
        );
        assertThat(
                testFile.cleanUpPropertyValue("zinc finger"),
                is("zinc finger")
        );
        assertThat(
                testFile.cleanUpPropertyValue("words with space "),
                is("words with space")
        );
        assertThat(
                testFile.cleanUpPropertyValue(
                        "shoot apex {http://www.co-ode.org/patterns#createdBy=" +
                                "\\\"http://www.ebi.ac.uk/ontology/webulous#OPPL_pattern\\\"}"),
                is("shoot apex")
        );

        assertThat(
                testFile.cleanUpPropertyValue(
                        "Homeodomain-like/winged-helix DNA-binding family protein [Source:TAIR;Acc:AT1G17520]"),
                is("Homeodomain-like/winged-helix DNA-binding family protein")
        );
    }
}
