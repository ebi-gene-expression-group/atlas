package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationPropertiesTest {
    @Test
    public void getBaselineReferenceExperimentAccession() {
        SpeciesProperties homoSapiensProperties =
                SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of());

        Species species = new Species("Homo sapiens", homoSapiensProperties);

        assertThat(ApplicationProperties.getBaselineReferenceExperimentAccession(species), startsWith("E-"));
    }

    @Test
    public void getUnknownSpeciesBaselineReferenceExperimentAccession() {
        Species unknownSpecies = new Species("foobar", SpeciesProperties.UNKNOWN);

        assertThat(unknownSpecies.isUnknown(), is(true));
        assertThat(ApplicationProperties.getBaselineReferenceExperimentAccession(unknownSpecies), is(nullValue()));
    }
}
