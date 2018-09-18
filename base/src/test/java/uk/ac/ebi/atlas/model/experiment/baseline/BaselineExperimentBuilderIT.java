package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.utils.EuropePmcClient;

import javax.inject.Inject;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class BaselineExperimentBuilderIT {
    private static final String SPECIES_NAME = "Homo sapiens";
    private static final SpeciesProperties SPECIES_PROPERTIES =
            SpeciesProperties.create("Homo_sapiens", "ORGANISM_PART", "animals", ImmutableList.of());

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-5061";
    private static final String DESCRIPTION = "description";
    private static final String DISPLAY_NAME = "displayName";

    private static final List<String> PUBMEDID = singletonList("PUBMEDID");
    private static final List<String> DOI = singletonList("00.1/DOI");
    private static final List<String> PROVIDER_URL =
            Arrays.asList("http://www.provider.com", "http://www.provider1.com");
    private static final List<String> PROVIDER_DESCRIPTION =
            Arrays.asList("Baseline experiment data provider", "Another baseline experiment data provider");

    @Inject
    private Path dataFilesPath;

    @Inject
    private EuropePmcClient europePmcClient;

    private BaselineExperimentBuilder subject = new BaselineExperimentBuilder();
    private ExperimentAttributesService experimentAttributesService;

    @Test
    public void testCreateSingleCell() {
        IdfParser idfParser = new IdfParser(new DataFileHub(dataFilesPath.resolve("scxa")));

        experimentAttributesService = new ExperimentAttributesService(europePmcClient, idfParser);

        List<AssayGroup> assayGroups =
                ImmutableList.of(
                        AssayGroupFactory.create("g1", "run1"),
                        AssayGroupFactory.create("g2", "run2"));

        BaselineExperiment experiment = subject
                .forSpecies(new Species(SPECIES_NAME, SPECIES_PROPERTIES))
                .ofType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .withAccession(EXPERIMENT_ACCESSION)
                .withDescription(DESCRIPTION)
                .withDisplayName(DISPLAY_NAME)
                .withPubMedIds(Sets.newHashSet(PUBMEDID))
                .withDois(Sets.newHashSet(DOI))
                .withExperimentDesign(MockExperiment.mockExperimentDesign(assayGroups))
                .withAssayGroups(assayGroups)
                .withDataProviderURL(PROVIDER_URL)
                .withDataProviderDescription(PROVIDER_DESCRIPTION)
                .withLastUpdate(new Date())
                .create();

        Map<String, ?> attributes = experimentAttributesService.getAttributes(experiment);

        assertThat(attributes)
                .extracting("experimentAccession", "pageDescription", "dataProviderURL", "dataProviderDescription",
                        "pubMedIds", "dois")
                .contains(EXPERIMENT_ACCESSION, DESCRIPTION, PROVIDER_URL, PROVIDER_DESCRIPTION, PUBMEDID, DOI);
    }
}
