package uk.ac.ebi.atlas.model.arraydesign;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

class ArrayDesignTest {
    @Test
    void unknownArrayDesignsArePopulatedWithTheirAccession() {
        assertThat(ArrayDesign.createForUnknownName(randomAlphanumeric(20)))
                .satisfies(arrayDesign -> assertThat(arrayDesign.accession()).isEqualTo(arrayDesign.name()));
    }
}