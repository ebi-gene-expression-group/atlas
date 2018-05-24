package uk.ac.ebi.atlas.solr.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SchemaFieldNameUtilsTest {

    @Test
    public void validFactorFieldToDisplayName() {
        assertThat(SchemaFieldNameUtils.factorFieldNameToDisplayName("factor_biopsy_site")).isEqualToIgnoringCase("Biopsy site");
    }

    @Test
    public void emptyFactorFieldToDisplayName() {
        assertThat(SchemaFieldNameUtils.factorFieldNameToDisplayName("")).isEmpty();
    }

    @Test
    public void validCharacteristicFieldToDisplayName() {
        assertThat(SchemaFieldNameUtils.characteristicFieldNameToDisplayName("characteristic_inferred_cell_type")).isEqualToIgnoringCase("Inferred cell type");
    }

    @Test
    public void emptyCharacteristicFieldToDisplayName() {
        assertThat(SchemaFieldNameUtils.characteristicFieldNameToDisplayName("")).isEmpty();
    }

    @Test
    public void validAttributeNameToDisplayName() {
        assertThat(SchemaFieldNameUtils.attributeNameToFieldName("FACS marker")).isEqualToIgnoringCase("facs_marker");
    }

    @Test
    public void emptyAttributeNameToDisplayName() {
        assertThat(SchemaFieldNameUtils.attributeNameToFieldName("")).isEmpty();
    }
}
