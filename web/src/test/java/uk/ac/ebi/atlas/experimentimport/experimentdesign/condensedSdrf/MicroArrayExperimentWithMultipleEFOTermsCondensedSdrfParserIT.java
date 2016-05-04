package uk.ac.ebi.atlas.experimentimport.experimentdesign.condensedSdrf;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MicroArrayExperimentWithMultipleEFOTermsCondensedSdrfParserIT {

    private static final String MICROARRAY_EXPERIMENT_ACCESSION = "E-GEOD-2507";

    @Inject
    private CondensedSdrfParser subject;

    @Test
    public void testGetSpeciesForAssays() throws IOException {
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();
        Set<String> species = experimentDesign.getSpeciesForAssays(Sets.newHashSet("N/LV1", "S/SM5"));
        assertThat(species, containsInAnyOrder("Mus musculus"));

    }

    @Test
    public void sampleHasMultipleOntologyTerms() throws IOException{
        ExperimentDesign experimentDesign = subject.parse(MICROARRAY_EXPERIMENT_ACCESSION, ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL).getExperimentDesign();
        assertThat(experimentDesign.getAllOntologyTermIdsByAssayAccession().get("N/LV1").size(), is(5));
        assertThat(experimentDesign.getFactors("N/LV1").getFactorByType("ORGANISM_PART").getValueOntologyTerms().size(), is(2));
    }

}