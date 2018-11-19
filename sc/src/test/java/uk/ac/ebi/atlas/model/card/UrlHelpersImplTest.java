package uk.ac.ebi.atlas.model.card;

import com.google.common.net.UrlEscapers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomExperimentAccession;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomSpecies;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class UrlHelpersImplTest {
    @Mock
    Experiment experimentMock;

    private UrlHelpersImpl subject;

    @BeforeEach
    void setUp() {
        subject = new UrlHelpersImpl();
    }

    @Test
    void speciesUrl() throws MalformedURLException {
        Species species = generateRandomSpecies();
        URL url = new URL(subject.getExperimentsFilteredBySpeciesUrl(species.getReferenceName()));

        assertThat(url)
                .hasPath("/experiments")
                .hasQuery("species=" + UrlEscapers.urlPathSegmentEscaper().escape(species.getReferenceName()));
    }

    @Test
    void experimentUrl() throws MalformedURLException {
        String experimentAccession = generateRandomExperimentAccession();
        when(experimentMock.getAccession()).thenReturn(experimentAccession);

        URL url = new URL(subject.getExperimentUrl(experimentMock));

        assertThat(url)
                .hasPath("/experiments/" + UrlEscapers.urlPathSegmentEscaper().escape(experimentAccession));
    }

}