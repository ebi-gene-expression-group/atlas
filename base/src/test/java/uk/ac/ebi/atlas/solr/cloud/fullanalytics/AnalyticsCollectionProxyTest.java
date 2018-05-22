package uk.ac.ebi.atlas.solr.cloud.fullanalytics;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.model.ExpressionUnit;

import static org.assertj.core.api.Assertions.assertThat;

class AnalyticsCollectionProxyTest {
    @Test
    void tpmAndFpkmFieldsAreDifferent() {
        assertThat(AnalyticsCollectionProxy.getExpressionLevelFieldNames(ExpressionUnit.Absolute.Rna.TPM))
                .isNotEqualTo(AnalyticsCollectionProxy.getExpressionLevelFieldNames(ExpressionUnit.Absolute.Rna.FPKM));
    }

    @Test
    void proteomicUnitsAndTpmsAreTheSameField() {
        assertThat(AnalyticsCollectionProxy.getExpressionLevelFieldNames(ExpressionUnit.Absolute.Protein.ANY))
                .isEqualTo(AnalyticsCollectionProxy.getExpressionLevelFieldNames(ExpressionUnit.Absolute.Rna.TPM));
    }

    // TODO add tests for asAnalyticsGeneQuery
}