package uk.ac.ebi.atlas.search.analyticsindex.differential;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by barrera on 06/10/2015.
 *
 */
public class ExperimentsTypeMapConverterTest {

    @Test
    public void testGetTypeExists() throws Exception {
        assertThat(DifferentialAnalyticsFacetsReader.ExperimentsTypeMapConverter.get("rnaseq_mrna_differential"), is("RNA-seq mRNA differential"));
    }

    @Test
    public void testGetTypeNotExists() throws Exception {
        assertThat(DifferentialAnalyticsFacetsReader.ExperimentsTypeMapConverter.get("microarray_5colour_microrna_differential"), is("microarray_5colour_microrna_differential"));
    }

}