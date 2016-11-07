package uk.ac.ebi.atlas.solr.query.conditions;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConditionsSolrQueryBuilderTest {

    private ConditionsSolrQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new ConditionsSolrQueryBuilder();
    }

    @Test
    public void singleTerm() throws Exception {
        String query = subject.buildQueryString("liver");
        assertThat(query, is("conditions_search:liver"));
    }

    @Test
    public void quotedTerm() throws Exception {
        String query = subject.buildQueryString("\"liver cancer\"");
        assertThat(query, is("conditions_search:\"liver cancer\""));
    }

    @Test
    public void multipleTerms() {
        String query = subject.buildQueryString("liver heart");
        assertThat(query, is("conditions_search:liver OR conditions_search:heart"));
    }

    @Test
    public void multipleTermsWithQuotes() {
        String query = subject.buildQueryString("\"wild type\" adipose");
        assertThat(query, is("conditions_search:\"wild type\" OR conditions_search:adipose"));
    }

    @Test
    public void multipleANDTerms() {
        String query = subject.buildQueryString("liver AND heart");
        assertThat(query, is("conditions_search:liver AND conditions_search:heart"));
    }

    @Test
    public void multipleANDTermsWithQuotes() {
        String query = subject.buildQueryString("\"Homo sapiens\" AND heart");
        assertThat(query, is("conditions_search:\"Homo sapiens\" AND conditions_search:heart"));
    }

    @Test
    public void termWithColonIsEscaped() {
        String query = subject.buildQueryString("EFO:0001265");
        assertThat(query, is("conditions_search:EFO\\:0001265"));
    }
}