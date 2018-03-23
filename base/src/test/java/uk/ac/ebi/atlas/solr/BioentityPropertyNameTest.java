package uk.ac.ebi.atlas.solr;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.ac.ebi.atlas.solr.BioentityPropertyName.UNKNOWN;

public class BioentityPropertyNameTest {

    @Test
    public void getByNameIsSafe() {
        assertThat(BioentityPropertyName.getByName("¯\\_(ツ)_/¯")).isEqualTo(UNKNOWN);
        assertThat(BioentityPropertyName.getByName("")).isEqualTo(UNKNOWN);
        assertThat(BioentityPropertyName.getByName(null)).isEqualTo(UNKNOWN);
    }
}