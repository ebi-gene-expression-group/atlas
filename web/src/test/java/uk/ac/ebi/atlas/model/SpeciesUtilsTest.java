package uk.ac.ebi.atlas.model;

import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SpeciesUtilsTest {

    @Test
    public void handleNullSpecies() {
        assertThat(SpeciesUtils.convertToEnsemblSpecies(null), is(nullValue()));
    }
}