package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.testutils.MockAssayGroups;
import uk.ac.ebi.atlas.testutils.MockExperiment;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BaselineExperimentBuilderIT {

    private static final String SPECIES_NAME = "Homo sapiens";
    private static final SpeciesProperties SPECIES_PROPERTIES = SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.<ImmutableMap<String, String>>of());

    private static final String EXPERIMENT_ACCESSION = "accession";
    private static final String DESCRIPTION = "description";
    private static final String DISPLAY_NAME = "displayName";

    private static final List<String> PUBMEDID = Arrays.asList("PUBMEDID");
    private static final List<String> DOI = Arrays.asList("00.1/DOI");
    private static final List<String> PROVIDER_URL = Arrays.asList("http://www.provider.com","http://www.provider1.com");
    private static final List<String> PROVIDER_DESCRIPTION = Arrays.asList("Baseline experiment data provider","Another baseline experiment data provider");

    private BaselineExperimentBuilder subject = new BaselineExperimentBuilder();

    @Inject
    private ExperimentAttributesService experimentAttributesService;

    @Test
    public void testCreate() {

        BaselineExperiment experiment = subject
                .forSpecies(new Species(SPECIES_NAME, SPECIES_PROPERTIES))
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withAccession(EXPERIMENT_ACCESSION)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withDois(Sets.newHashSet(DOI))
                .withExperimentDesign(MockExperiment.mockExperimentDesign(MockAssayGroups.create()))
                .withAssayGroups(MockAssayGroups.create())
                .withDataProviderURL(PROVIDER_URL)
                .withDataProviderDescription(PROVIDER_DESCRIPTION)
                .create();


        Map<String, ?> attributes = experimentAttributesService.getAttributes(experiment);

        assertThat(attributes)
                .extracting("experimentAccession", "pageDescription", "dataProviderURL", "dataProviderDescription",
                        "pubMedIds", "dois")
                .contains(EXPERIMENT_ACCESSION, DESCRIPTION, PROVIDER_URL, PROVIDER_DESCRIPTION, PUBMEDID, DOI);
    }
}