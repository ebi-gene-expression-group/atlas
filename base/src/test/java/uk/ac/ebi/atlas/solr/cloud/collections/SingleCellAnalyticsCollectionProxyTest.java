package uk.ac.ebi.atlas.solr.cloud.collections;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.solr.cloud.collections.SingleCellAnalyticsCollectionProxy.SingleCellAnalyticsSchemaField;

import static org.assertj.core.api.Assertions.assertThat;

class SingleCellAnalyticsCollectionProxyTest {

    @Test
    void testCharacteristicFieldCreation() {
        SingleCellAnalyticsSchemaField result =
                SingleCellAnalyticsCollectionProxy.characteristicAsSchemaField("inferred_cell_type");

        assertThat(result.displayName()).isEqualTo("Inferred cell type");
        assertThat(result.name()).isEqualTo("characteristic_inferred_cell_type");
    }

    @Test
    void testFactorFieldCreation() {
        SingleCellAnalyticsSchemaField result = SingleCellAnalyticsCollectionProxy.factorAsSchemaField("species");

        assertThat(result.displayName()).isEqualTo("Species");
        assertThat(result.name()).isEqualTo("factor_species");
    }

    @Test
    void validFactorFieldToDisplayName() {
        assertThat(SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName("factor_biopsy_site"))
                .isEqualToIgnoringCase("Biopsy site");
    }

    @Test
    void emptyFactorFieldToDisplayName() {
        assertThat(SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName("")).isEmpty();
    }

    @Test
    void validCharacteristicFieldToDisplayName() {
        assertThat(
                SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName("characteristic_inferred_cell_type"))
                .isEqualToIgnoringCase("Inferred cell type");
    }

    @Test
    void emptyCharacteristicFieldToDisplayName() {
        assertThat(SingleCellAnalyticsCollectionProxy.metadataFieldNameToDisplayName("")).isEmpty();
    }

    @Test
    void validAttributeNameToDisplayName() {
        assertThat(SingleCellAnalyticsCollectionProxy.attributeNameToFieldName("FACS marker"))
                .isEqualToIgnoringCase("facs_marker");
    }

    @Test
    void emptyAttributeNameToDisplayName() {
        assertThat(SingleCellAnalyticsCollectionProxy.attributeNameToFieldName("")).isEmpty();
    }

    @Test
    void validFactorNameToSchemaField() {
        assertThat(SingleCellAnalyticsCollectionProxy.metadataAsSchemaField("factor_biopsy_site"))
                .isInstanceOf(SingleCellAnalyticsSchemaField.class)
                .extracting("fieldName", "displayName")
                .contains("factor_biopsy_site", "Biopsy site");
    }

    @Test
    void validCharacteristicNameToSchemaField() {
        assertThat(SingleCellAnalyticsCollectionProxy.metadataAsSchemaField("characteristic_cell_type"))
                .isInstanceOf(SingleCellAnalyticsSchemaField.class)
                .extracting("fieldName", "displayName")
                .contains("characteristic_cell_type", "Cell type");
    }
}
