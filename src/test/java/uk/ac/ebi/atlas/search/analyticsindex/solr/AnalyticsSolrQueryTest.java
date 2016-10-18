package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQuery.Operator.OR;

public class AnalyticsSolrQueryTest {

    private AnalyticsSolrQuery subject;

    @Test
    public void simpleQuery() {
        subject = new AnalyticsSolrQuery(
                "identifierSearch",
                SemanticQuery.create(SemanticQueryTerm.create("GO:0008150", "go")));

        assertThat(subject.toString(), is("identifierSearch:(\"go:{GO:0008150}\")"));
    }

    @Test
    public void notSoSimpleQuery() {
        subject = new AnalyticsSolrQuery(
                "identifierSearch",
                SemanticQuery.create(
                        SemanticQueryTerm.create("GO:0008150", "go"),
                        SemanticQueryTerm.create("PIM1", "synonym"),
                        SemanticQueryTerm.create("zinc finger")));

        assertThat(subject.toString(), is("identifierSearch:(\"go:{GO:0008150}\" OR \"synonym:{PIM1}\" OR \"zinc finger\")"));
    }

    @Test
    public void complexQuery() {
        subject = new AnalyticsSolrQuery(
                OR,
                new AnalyticsSolrQuery(
                        "identifierSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("GO:0008150", "go"),
                                SemanticQueryTerm.create("PIM1", "synonym"),
                                SemanticQueryTerm.create("zinc finger"))
                ),
                new AnalyticsSolrQuery(
                        "conditionsSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("liver", "efo"),
                                SemanticQueryTerm.create("cancer"))
                )
        );

        assertThat(subject.toString(), is(
                "identifierSearch:(\"go:{GO:0008150}\" OR \"synonym:{PIM1}\" OR \"zinc finger\")" +
                " OR " +
                "conditionsSearch:(\"efo:{liver}\" OR \"cancer\")"
        ));
    }

    @Test
    public void moreComplexQuery() {
        subject = new AnalyticsSolrQuery(
                OR,
                new AnalyticsSolrQuery(
                        "identifierSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("GO:0008150", "go"),
                                SemanticQueryTerm.create("PIM1", "synonym"),
                                SemanticQueryTerm.create("zinc finger"))
                ),
                new AnalyticsSolrQuery(
                        "conditionsSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("liver", "efo"),
                                SemanticQueryTerm.create("cancer"))
                ),
                new AnalyticsSolrQuery(
                        AND,
                        new AnalyticsSolrQuery(
                                "species",
                                SemanticQuery.create("homo sapiens")),
                        new AnalyticsSolrQuery(
                                "defaultQueryFactorType",
                                SemanticQuery.create("CELL_LINE")),
                        new AnalyticsSolrQuery(
                                "experimentType",
                                SemanticQuery.create("rnaseq_mrna_baseline"))
                )
        );

        assertThat(subject.toString(), is(
                "identifierSearch:(\"go:{GO:0008150}\" OR \"synonym:{PIM1}\" OR \"zinc finger\")" +
                " OR " +
                "conditionsSearch:(\"efo:{liver}\" OR \"cancer\")" +
                " OR " +
                "(" +
                        "species:(\"homo sapiens\")" +
                        " AND " +
                        "defaultQueryFactorType:(\"CELL_LINE\")" +
                        " AND " +
                        "experimentType:(\"rnaseq_mrna_baseline\")" +
                ")"
        ));
    }
}