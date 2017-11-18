package uk.ac.ebi.atlas.search.analyticsindex.solr.query;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.solr.analytics.query.AnalyticsQueryBuilder;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

public class AnalyticsQueryBuilderTest {
    AnalyticsQueryBuilder subject;

    @Before
    public void setUp() {
        subject = new AnalyticsQueryBuilder();
    }

    @Test
    public void buildsASolrQuery() {
        assertThat(subject.build(), isA(SolrQuery.class));
    }
}