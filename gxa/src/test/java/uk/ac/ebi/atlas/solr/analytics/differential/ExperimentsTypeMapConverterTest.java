package uk.ac.ebi.atlas.solr.analytics.differential;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExperimentsTypeMapConverterTest {

    @Test
    public void testGetTypeExists() {
        assertThat(
                DifferentialFacetsReader.FacetFieldMapConverter.get("rnaseq_mrna_differential"),
                is("RNA-seq mRNA differential"));
    }

    @Test
    public void testGetTypeNotExists() {
        assertThat(
                DifferentialFacetsReader.FacetFieldMapConverter.get("microarray_5colour_microrna_differential"),
                is("Microarray_5colour_microrna_differential"));
    }
}
