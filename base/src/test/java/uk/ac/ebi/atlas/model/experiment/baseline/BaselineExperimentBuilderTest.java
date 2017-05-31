package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroupsFake;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentBuilderTest {

    private static final String SPECIES_NAME = "Homo sapiens";
    private static final SpeciesProperties SPECIES_PROPERTIES = SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.<ImmutableMap<String, String>>of());

    private static final String EXPERIMENT_ACCESSION = "accession";
    private static final String DESCRIPTION = "description";
    private static final String DISPLAY_NAME = "displayName";

    private static final String PUBMEDID = "PUBMEDID";
    private static final List<String> PROVIDER_URL = Arrays.asList("http://www.provider.com","http://www.provider1.com");
    private static final List<String> PROVIDER_DESCRIPTION = Arrays.asList("Baseline experiment data provider","Another baseline experiment data provider");

    private BaselineExperimentBuilder subject = new BaselineExperimentBuilder();

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Test
    public void testCreate() throws Exception {

        BaselineExperiment experiment = subject
                .forSpecies(new Species(SPECIES_NAME, SPECIES_PROPERTIES))
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withAccession(EXPERIMENT_ACCESSION)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withExperimentDesign(experimentDesignMock)
                .withAssayGroups(AssayGroupsFake.get())
                .withDataProviderURL(PROVIDER_URL)
                .withDataProviderDescription(PROVIDER_DESCRIPTION)
                .create();

        Map<String, ?> attributes = experiment.getAttributes();

        assertEquals(EXPERIMENT_ACCESSION,attributes.get("experimentAccession"));
        assertEquals(DESCRIPTION,attributes.get("pageDescription"));
        assertEquals(PROVIDER_URL,attributes.get("dataProviderURL"));
        assertEquals(PROVIDER_DESCRIPTION,attributes.get("dataProviderDescription"));
        assertThat((Collection<String>) attributes.get("pubMedIds"), Matchers.contains(PUBMEDID));

    }
}