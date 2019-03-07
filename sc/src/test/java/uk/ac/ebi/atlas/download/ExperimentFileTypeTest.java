package uk.ac.ebi.atlas.download;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ExperimentFileTypeTest {
    @ParameterizedTest
    @MethodSource("stringAndExperimentFileTypeProvider")
    void fileTypeFromValidId(String fileTypeId, ExperimentFileType fileType) {
        assertThat(ExperimentFileType.fromId(fileTypeId)).isEqualByComparingTo(fileType);
    }

    @Test
    void fileTypeFromEmptyIdThrowsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class).isThrownBy(() -> ExperimentFileType.fromId(""));

    }

    @Test
    void fileTypeFromNonexistentIdThrowsException() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> ExperimentFileType.fromId("foo-bar"));

    }

    private static Stream<Arguments> stringAndExperimentFileTypeProvider() {
        return Stream.of(
                Arguments.of("experiment-design", ExperimentFileType.EXPERIMENT_DESIGN),
                Arguments.of("sdrf", ExperimentFileType.SDRF),
                Arguments.of("cluster", ExperimentFileType.CLUSTERING),
                Arguments.of("quantification-raw", ExperimentFileType.QUANTIFICATION_RAW),
                Arguments.of("quantification-filtered", ExperimentFileType.QUANTIFICATION_FILTERED),
                Arguments.of("normalised", ExperimentFileType.NORMALISED)
        );
    }
}
