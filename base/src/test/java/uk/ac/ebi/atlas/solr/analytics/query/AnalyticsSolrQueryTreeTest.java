package uk.ac.ebi.atlas.solr.analytics.query;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;

import static org.assertj.core.api.Assertions.assertThat;

public class AnalyticsSolrQueryTreeTest {
    @Test
    public void freeTextIsSearchedFirstInBioentityIdentifierAndSymbol() {
        AnalyticsSolrQueryTree subject =
                AnalyticsSolrQueryTree.createForIdentifierSearch(SemanticQuery.create("SLC4A4"));

        assertThat(subject.toQueryPlan())
                .hasSize(4);
        assertThat(subject.toQueryPlan().subList(0, 2))
                .containsExactly("bioentity_identifier_search:\"SLC4A4\"", "keyword_symbol:\"SLC4A4\"");
    }
}
