package uk.ac.ebi.atlas.thirdpartyintegration;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class EBIGlobalSearchQueryBuilderTest {

    private EBIGlobalSearchQueryBuilder subject = new EBIGlobalSearchQueryBuilder();

    @Test
    public void buildGlobalSearchTerm2GeneQueryTermsAndCondition() throws Exception {
        String geneQuery = "id1 id2";
        String condition = "liver";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("(id1 OR id2) AND liver"));
    }

    @Test
    public void buildGlobalSearchTerm2GeneQueryTerms() throws Exception {
        String geneQuery = "id1 id2";
        String condition = null;
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("id1 OR id2"));
    }

    @Test
    public void buildGlobalSearchTerm1GeneQueryTerm() throws Exception {
        String geneQuery = "id1";
        String condition = null;
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("id1"));
    }

    @Test
    public void buildGlobalSearchTerm1GeneQueryTermWithQuotes() throws Exception {
        String geneQuery = "\"zinc finger\"";
        String condition = null;
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("\"zinc finger\""));
    }

    @Test
    public void buildGlobalSearchTerm1GeneQueryTermWithQuotesAndCondition() throws Exception {
        String geneQuery = "\"zinc finger\"";
        String condition = "liver";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("\"zinc finger\" AND liver"));
    }

    @Test
    public void buildGlobalSearchTermNullGeneQueryConditionOnly() throws Exception {
        String condition = "liver";
        assertThat(subject.buildGlobalSearchTerm(null, SemanticQuery.create(condition)), is("liver"));
    }

    @Test
    public void buildGlobalSearchTermEmptyStringGeneQueryConditionOnly() throws Exception {
        String condition = "liver";
        assertThat(subject.buildGlobalSearchTerm("", SemanticQuery.create(condition)), is("liver"));
    }

    @Test
    public void buildGlobalSearchTerm2Conditions() throws Exception {
        String condition = "liver heart";
        assertThat(subject.buildGlobalSearchTerm(null, SemanticQuery.create(condition)), is("liver OR heart"));
    }


    @Test
    public void buildGlobalSearchTerm2ConditionsWithAnd() throws Exception {
        String condition = "liver and heart";
        assertThat(subject.buildGlobalSearchTerm(null, SemanticQuery.create(condition)), is("liver AND heart"));
    }

    @Test
    public void buildGlobalSearchTerm2ConditionsQuoted() throws Exception {
        String condition = "\"wild type\" heart";
        assertThat(subject.buildGlobalSearchTerm(null, SemanticQuery.create(condition)), is("\"wild type\" OR heart"));
    }

    @Test
    public void buildGlobalSearchTerm1GeneQueryTermAnd2Conditions() throws Exception {
        String geneQuery = "id1";
        String condition = "liver heart";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("id1 AND (liver OR heart)"));
    }

    @Test
    public void buildGlobalSearchTerm1GeneQueryTermAnd2ConditionsWithAnd() throws Exception {
        String geneQuery = "id1";
        String condition = "liver and heart";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("id1 AND (liver AND heart)"));
    }


    @Test
    public void buildGlobalSearchTerm1GeneQueryTermAnd2ConditionsQuoted() throws Exception {
        String geneQuery = "\"zinc finger\"";
        String condition = "\"wild type\" heart";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("\"zinc finger\" AND (\"wild type\" OR heart)"));
    }

    @Test
    public void buildGlobalSearchTerm2GeneQueryTermsAnd2Conditions() throws Exception {
        String geneQuery = "id1 id2";
        String condition = "liver heart";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("(id1 OR id2) AND (liver OR heart)"));
    }

    @Test
    public void buildGlobalSearchTerm2GeneQueryTermsAnd2ConditionsWithAnd() throws Exception {
        String geneQuery = "id1 id2";
        String condition = "liver and heart";
        assertThat(subject.buildGlobalSearchTerm(geneQuery, SemanticQuery.create(condition)), is("(id1 OR id2) AND (liver AND heart)"));
    }

}
