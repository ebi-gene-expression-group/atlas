package uk.ac.ebi.atlas.utils;

import com.google.common.net.UrlEscapers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.species.Species;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomSpecies;
import static uk.ac.ebi.atlas.utils.UrlHelpers.getCustomUrl;
import static uk.ac.ebi.atlas.utils.UrlHelpers.getExperimentSetUrl;
import static uk.ac.ebi.atlas.utils.UrlHelpers.getExperimentUrl;
import static uk.ac.ebi.atlas.utils.UrlHelpers.getExperimentsFilteredBySpeciesAndExperimentType;
import static uk.ac.ebi.atlas.utils.UrlHelpers.getExperimentsFilteredBySpeciesUrl;
import static uk.ac.ebi.atlas.utils.UrlHelpers.getExperimentsSummaryImageUrl;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class UrlHelpersIT {
    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @Mock
    Experiment experimentMock;

    @Test
    void speciesUrl() throws MalformedURLException {
        Species species = generateRandomSpecies();

        assertThat(new URL(getExperimentsFilteredBySpeciesUrl(species.getReferenceName())))
                .hasPath("/experiments")
                .hasParameter("species", species.getReferenceName());
    }

    @Test
    void experimentUrl() throws MalformedURLException {
        String experimentAccession = generateRandomExperimentAccession();
        when(experimentMock.getAccession()).thenReturn(experimentAccession);

        assertThat(new URL(getExperimentUrl(experimentMock)))
                .hasPath("/experiments/" + UrlEscapers.urlPathSegmentEscaper().escape(experimentAccession));
    }

    @Test
    void speciesAndTypeUrl() throws MalformedURLException {
        Species species = generateRandomSpecies();

        ExperimentType type = ExperimentType.MICROARRAY_ANY;
        while (type == ExperimentType.MICROARRAY_ANY) {
            type = ExperimentType.values()[RNG.nextInt(0, ExperimentType.values().length)];
        }

        assertThat(new URL(getExperimentsFilteredBySpeciesAndExperimentType(species.getReferenceName(), type.name())))
                .hasPath("/experiments")
                .hasParameter("species", species.getReferenceName())
                .hasParameter("experimentType", type.name());
    }

    @Test
    void imageUrl() throws MalformedURLException {
        String imageFileName = randomAlphabetic(5, 20);

        assertThat(new URL(getExperimentsSummaryImageUrl(imageFileName)))
                .hasPath("/resources/images/experiments-summary/" + imageFileName + ".png");
    }

    @Test
    void customUrl() throws MalformedURLException {
        String path = randomAlphabetic(5, 20);

        assertThat(new URL(getCustomUrl(path)))
                .hasPath("/" + path);
    }

    @Test
    void experimentSetUrl() throws MalformedURLException {
        String keyword = randomAlphabetic(5, 20);

        assertThat(new URL(getExperimentSetUrl(keyword)))
                .hasPath("/experiments")
                .hasParameter("experimentSet", keyword);
    }
}