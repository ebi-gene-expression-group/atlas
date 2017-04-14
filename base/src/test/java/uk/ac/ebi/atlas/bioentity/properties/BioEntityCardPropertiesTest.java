package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.util.Map.Entry;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.GENE_BIOTYPE;

public class BioEntityCardPropertiesTest {

    @Test
    public void linkTemplatesExceptGeneBiotypeHaveFormatElements() throws Exception {
        for (Entry<BioentityPropertyName, String> linkTemplate : BioEntityCardProperties.linkTemplates.entrySet()) {
            if (linkTemplate.getKey() != GENE_BIOTYPE) {
                assertThat(linkTemplate.getValue().matches(".+\\{\\d\\}.*"), is(true));
            }
        }
    }

}