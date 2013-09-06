package uk.ac.ebi.atlas.solr.query.conditions;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConditionsSolrQueryBuilderTest {

    private ConditionsSolrQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new ConditionsSolrQueryBuilder();

    }

    @Test
    public void testBuildQueryString() throws Exception {
        String query = subject.buildQueryString("liver");
        assertThat(query, is("property_value_search:liver"));
    }

    @Test
    public void testBuildQueryStringMultiTerms() throws Exception {
        String query = subject.buildQueryString("\"liver cancer\"");
        assertThat(query, is("property_value_search:\"liver cancer\""));
    }


}
