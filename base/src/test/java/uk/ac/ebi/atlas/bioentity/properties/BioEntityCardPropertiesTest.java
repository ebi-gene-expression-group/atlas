package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName.*;

public class BioEntityCardPropertiesTest {

    private BioEntityCardProperties subject;

    String plantUrl = "http://plantreactome.gramene.org/content/detail/{0}";
    String reactomeUrl = "https://reactome.org/content/detail/{0}";
    String interProUrl = "http://www.ebi.ac.uk/interpro/entry/{0}";

    @Before
    public void setUp() throws Exception {
        subject = new BioEntityCardProperties();
    }

    @Test
    public void linkPlantPathwayWithPlantUrl() {
        assertThat(subject.getUrlTemplate(PATHWAYID,"plants"),is(plantUrl));
    }

    @Test
    public void linkPathwayWithReactomeUrl() {
        assertThat(subject.getUrlTemplate(PATHWAYID,"animals"),is(reactomeUrl));
    }

    @Test
    public void linkOtherBioentityPropertyWithUrl() {
        assertThat(subject.getUrlTemplate(INTERPRO),is(interProUrl));
    }

    @Test
    public void BioentityPropertyWithNoUrl() {
        assertThat(subject.getUrlTemplate(FLYBASE_GENE_ID),is(""));
    }

    @Test
    public void unknownSpeciesWithSpeciesSpecificProperties() {
        assertThat(subject.getUrlTemplate(PATHWAYID, ""), is(""));
        assertThat(subject.getUrlTemplate(PATHWAYID, null), is(""));
    }


    @Test
    public void linkTemplatesExceptGeneBiotypeHaveFormatElements() {
        for (Map.Entry<BioentityPropertyName, String> linkTemplate : BioEntityCardProperties.getAllUrlTemplates().entries()) {
            if (linkTemplate.getKey() != GENE_BIOTYPE) {
                assertThat(linkTemplate.getValue().matches(".+\\{\\d\\}.*"), is(true));
            }
        }
   }

}