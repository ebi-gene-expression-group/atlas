package uk.ac.ebi.atlas.experimentpage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.model.download.ExternallyAvailableContent.ContentType.SUPPLEMENTARY_INFORMATION;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomPrideExperimentAccession;

@ExtendWith(MockitoExtension.class)
class LinkToPrideTest {
    private LinkToPride subject;

    @Mock
    BaselineExperiment baselineExperimentMock;

    @BeforeEach
    void setUp() {
        subject = new LinkToPride();
    }

    @Test
    void iconAndLinkPointAtPride() {
        String accession = generateRandomPrideExperimentAccession();
        when(baselineExperimentMock.getSecondaryAccession()).thenReturn(accession);
        assertThat(subject.get(baselineExperimentMock))
                .hasSize(1)
                .first()
                .hasFieldOrProperty("uri")
                .hasFieldOrProperty("description");

        assertThat(subject.get(baselineExperimentMock).iterator().next().uri)
                .hasToString("redirect:https://www.ebi.ac.uk/pride/archive/projects/" + accession);

        assertThat(subject.get(baselineExperimentMock).iterator().next().description)
                .hasFieldOrPropertyWithValue("type", "icon-pride")
                .hasFieldOrPropertyWithValue("description", "PRIDE Archive: project " + accession);
    }

    @Test
    void goesIntoSupplementaryInformationTab() {
        assertThat(subject.contentType())
                .isEqualTo(SUPPLEMENTARY_INFORMATION);
    }
}