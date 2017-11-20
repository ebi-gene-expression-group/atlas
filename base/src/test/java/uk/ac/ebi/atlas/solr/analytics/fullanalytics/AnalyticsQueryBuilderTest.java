package uk.ac.ebi.atlas.solr.analytics.fullanalytics;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void queryBuiltFine() {

    }
}