package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AnalyticsSolrQueryTree.Operator.OR;

public class AnalyticsSolrQueryTreeTest {

    private AnalyticsSolrQueryTree subject;

    @Test
    public void simpleQuery() {
        subject = new AnalyticsSolrQueryTree(
                "identifierSearch",
                SemanticQuery.create(SemanticQueryTerm.create("GO:0008150", "go")));

        assertThat(subject.toQueryPlan().get(0), is("identifierSearch:(\"go:{GO:0008150}\")"));
    }

    @Test
    public void notSoSimpleQuery() {
        subject = new AnalyticsSolrQueryTree(
                "identifierSearch",
                SemanticQuery.create(
                        SemanticQueryTerm.create("GO:0008150", "go"),
                        SemanticQueryTerm.create("PIM1", "synonym"),
                        SemanticQueryTerm.create("zinc finger")));

        assertThat(subject.toQueryPlan().get(0), is("identifierSearch:(\"go:{GO:0008150}\" OR \"synonym:{PIM1}\" OR \"zinc finger\")"));
    }

    @Test
    public void complexQuery() {
        subject = new AnalyticsSolrQueryTree(
                OR,
                new AnalyticsSolrQueryTree(
                        "identifierSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("GO:0008150", "go"),
                                SemanticQueryTerm.create("PIM1", "synonym"),
                                SemanticQueryTerm.create("zinc finger"))
                ),
                new AnalyticsSolrQueryTree(
                        "conditionsSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("liver", "efo"),
                                SemanticQueryTerm.create("cancer"))
                )
        );

        assertThat(subject.toQueryPlan().get(0), is(
                "identifierSearch:(\"go:{GO:0008150}\" OR \"synonym:{PIM1}\" OR \"zinc finger\")" +
                " OR " +
                "conditionsSearch:(\"efo:{liver}\" OR \"cancer\")"
        ));
    }

    @Test
    public void moreComplexQuery() {
        subject = new AnalyticsSolrQueryTree(
                OR,
                new AnalyticsSolrQueryTree(
                        "identifierSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("GO:0008150", "go"),
                                SemanticQueryTerm.create("PIM1", "synonym"),
                                SemanticQueryTerm.create("zinc finger"))
                ),
                new AnalyticsSolrQueryTree(
                        "conditionsSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("liver", "efo"),
                                SemanticQueryTerm.create("cancer"))
                ),
                new AnalyticsSolrQueryTree(
                        AND,
                        new AnalyticsSolrQueryTree(
                                "species",
                                SemanticQuery.create("homo sapiens")),
                        new AnalyticsSolrQueryTree(
                                "defaultQueryFactorType",
                                SemanticQuery.create("CELL_LINE")),
                        new AnalyticsSolrQueryTree(
                                "experimentType",
                                SemanticQuery.create("rnaseq_mrna_baseline"))
                )
        );

        assertThat(subject.toQueryPlan().get(0), is(
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