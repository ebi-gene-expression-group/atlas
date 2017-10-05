package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AnalyticsSolrQueryTreeTest {

    @Test
    public void testSearchValueNeedsQuotes() throws Exception {

        assertThat(
                AnalyticsSolrQueryTree.searchValueNeedsQuotes("AT4G10920"),
                is(false)
        );
        assertThat(
                AnalyticsSolrQueryTree.searchValueNeedsQuotes("AT4G10920 "),
                is(false)
        );
        assertThat(
                AnalyticsSolrQueryTree.searchValueNeedsQuotes("GO:0005667"),
                is(true)
        );
        assertThat(
                AnalyticsSolrQueryTree.searchValueNeedsQuotes("zinc finger"),
                is(true)
        );
        assertThat(
                AnalyticsSolrQueryTree.searchValueNeedsQuotes("2-(4-carboxyphenyl)-4,4,5,5-tetramethylimidazoline-1-oxyl-3-oxide"),
                is(true)
        );
    }
}