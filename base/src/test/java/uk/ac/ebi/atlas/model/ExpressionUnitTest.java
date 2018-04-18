package uk.ac.ebi.atlas.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ExpressionUnitTest {

    @Test
    @DisplayName("Available units for baseline RNA-Seq experiments are absolute TPM and FPKM")
    void testUnitsForBaselineRnaSeq() {
        assertThat(Arrays.stream(ExpressionUnit.Absolute.Rna.values()).map(ExpressionUnit.Absolute.Rna::toString))
                .containsExactlyInAnyOrder("FPKM", "TPM");
    }

    @Test
    @DisplayName("Available unit for proteomics experiments are absolute and has no name")
    void testUnitsForBaselineProteomics() {
        assertThat(Arrays.stream(ExpressionUnit.Absolute.Protein.values()).map(ExpressionUnit.Absolute.Protein::toString))
                .containsExactlyInAnyOrder("");
    }

    @Test
    @DisplayName("Relative unit for differential experiments is log2 fold change")
    void testUnitsForDifferentialExperiments() {
        assertThat(Arrays.stream(ExpressionUnit.Relative.values()).map(ExpressionUnit.Relative::toString))
                .containsExactlyInAnyOrder("Log2 fold change");
    }
}