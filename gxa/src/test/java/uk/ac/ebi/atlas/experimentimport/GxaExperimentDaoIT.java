package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;

import javax.inject.Inject;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static uk.ac.ebi.atlas.model.experiment.ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GxaExperimentDaoIT {
    private static final ExperimentType TYPE_MICROARRAY = MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL;
    private static final String SECRET_111 = "Secret_111";

    @Inject
    private GxaExperimentDao subject;

    private UUID createSecret111(boolean isPrivate) {
        UUID randomUUID = UUID.randomUUID();
        ExperimentDTO mtab =
                ExperimentDTO.create(
                        SECRET_111,
                        TYPE_MICROARRAY,
                        "cow",
                        Sets.newHashSet("1"),
                        Sets.newHashSet("doi"),
                        "diff",
                        isPrivate);
        subject.addExperiment(mtab, randomUUID);
        return randomUUID;
    }

    private void deleteSecret111() {
        try {
            subject.deleteExperiment(SECRET_111);
        } catch (Exception e) {
            //yum!
        }
    }

    @AfterEach
    void tearDown() {
        deleteSecret111();
    }

    @Test
    void testFindExperimentByType() {
        createSecret111(false);
        Set<String> experimentAccessions = subject.findPublicExperimentAccessions(TYPE_MICROARRAY);
        assertThat(experimentAccessions).contains(SECRET_111);
    }

    @Test
    void findExperimentShouldFailForUnknownExperiment() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> subject.findExperiment("UNKNOWN", ""));
    }

    @Test
    void testAddExperiment() {
        int size = subject.getAllExperimentsAsAdmin().size();
        createSecret111(false);
        assertThat(subject.getAllExperimentsAsAdmin().size()).isEqualTo(size + 1);
        subject.deleteExperiment(SECRET_111);
        assertThat(subject.getAllExperimentsAsAdmin().size()).isEqualTo(size);
    }

    @Test
    void testDeleteExperiment() {
        createSecret111(false);
        int size = subject.getAllExperimentsAsAdmin().size();
        deleteSecret111();
        assertThat(subject.getAllExperimentsAsAdmin().size()).isEqualTo(size - 1);
    }

    @Test
    void updateExperimentShouldChangePrivateState() {
        createSecret111(false);
        subject.setExperimentPrivacyStatus(SECRET_111, true);
        assertThat(subject.getExperimentAsAdmin(SECRET_111).isPrivate()).isTrue();
        subject.setExperimentPrivacyStatus(SECRET_111, false);
        assertThat(subject.getExperimentAsAdmin(SECRET_111).isPrivate()).isFalse();
    }

    @Test
    void cannotCreateExperimentTwice() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    createSecret111(false);
                    createSecret111(false);
                });
    }

    @Test
    void cannotCreateExperimentTwice2() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    createSecret111(false);
                    createSecret111(true);
                });
    }

    @Test
    void forPublicExperimentsAccessKeyIsIgnored() {
        UUID id = createSecret111(false);
        assertThat(subject.findExperiment(SECRET_111, id.toString()))
                .isEqualTo(subject.findExperiment(SECRET_111, "different id"));
    }

    @Test
    void forPrivateExperimentsAccessKeyIsRequired() {
        UUID id = createSecret111(true);

        assertThat(subject.findExperiment(SECRET_111, id.toString()))
                .hasFieldOrPropertyWithValue("experimentAccession", SECRET_111);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> subject.findExperiment(SECRET_111, "foobar"));
    }

    @Test
    void youCanGetPrivateExperimentIfYouAreAdmin() {
        createSecret111(true);
        subject.getExperimentAsAdmin(SECRET_111);
    }

    @Test
    void countExperiments() {
        createSecret111(true);
        int count = subject.countExperiments();
        deleteSecret111();
        assertThat(subject.countExperiments()).isEqualTo(count - 1);
    }
}
