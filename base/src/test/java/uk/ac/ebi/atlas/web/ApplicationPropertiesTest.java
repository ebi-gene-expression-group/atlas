package uk.ac.ebi.atlas.web;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationPropertiesTest {

    private ApplicationProperties subject;

    @Before
    public void setUp() throws Exception {
        subject = new ApplicationProperties();
    }

    @Test
    public void getBaselineReferenceExperimentAccession() throws Exception {
        SpeciesProperties homoSapiensProperties =
                SpeciesProperties.create(
                        "Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.<ImmutableMap<String, String>>of());

        Species species = new Species("Homo sapiens", homoSapiensProperties);

        assertThat(ApplicationProperties.getBaselineReferenceExperimentAccession(species), startsWith("E-"));
    }

    @Test
    public void getUnknownSpeciesBaselineReferenceExperimentAccession() throws Exception {
        Species unknownSpecies = new Species("foobar", SpeciesProperties.UNKNOWN);

        assertThat(unknownSpecies.isUnknown(), is (true));
        assertThat(ApplicationProperties.getBaselineReferenceExperimentAccession(unknownSpecies), is(nullValue()));
    }

}
