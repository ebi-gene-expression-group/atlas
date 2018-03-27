package uk.ac.ebi.atlas.bioentity.properties;

import org.junit.Test;
import uk.ac.ebi.atlas.solr.BioentityPropertyName;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.FLYBASE_GENE_ID;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.GENE_BIOTYPE;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.INTERPRO;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.PATHWAYID;

public class BioEntityCardPropertiesTest {
    private static final String PLANT_REACTOME_URL_TEMPLATE = "http://plantreactome.gramene.org/content/detail/{0}";
    private static final String REACTOME_URL_TEMPLATE = "https://reactome.org/content/detail/{0}";
    private static final String INTERPRO_URL_TEMPLATE = "http://www.ebi.ac.uk/interpro/entry/{0}";

    @Test
    public void linkPlantPathwayWithPlantUrl() {
        assertThat(BioEntityCardProperties.getUrlTemplate(PATHWAYID,"plants"),is(PLANT_REACTOME_URL_TEMPLATE));
    }

    @Test
    public void linkPathwayWithReactomeUrl() {
        assertThat(BioEntityCardProperties.getUrlTemplate(PATHWAYID,"animals"),is(REACTOME_URL_TEMPLATE));
    }

    @Test
    public void linkOtherBioentityPropertyWithUrl() {
        assertThat(BioEntityCardProperties.getUrlTemplate(INTERPRO),is(INTERPRO_URL_TEMPLATE));
    }

    @Test
    public void BioentityPropertyWithNoUrl() {
        assertThat(BioEntityCardProperties.getUrlTemplate(FLYBASE_GENE_ID),is(""));
    }

    @Test
    public void unknownSpeciesWithSpeciesSpecificProperties() {
        assertThat(BioEntityCardProperties.getUrlTemplate(PATHWAYID, ""), is(""));
        assertThat(BioEntityCardProperties.getUrlTemplate(PATHWAYID, null), is(""));
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