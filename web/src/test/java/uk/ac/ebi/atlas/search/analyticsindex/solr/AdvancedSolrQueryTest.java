package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AdvancedSolrQuery.Operator.AND;
import static uk.ac.ebi.atlas.search.analyticsindex.solr.AdvancedSolrQuery.Operator.OR;

public class AdvancedSolrQueryTest {

    private AdvancedSolrQuery subject;

    @Test
    public void simpleQuery() {
        subject = new AdvancedSolrQuery(
                "identifierSearch",
                SemanticQuery.create(SemanticQueryTerm.create("GO:0008150", "go")));

        assertThat(subject.toString(), is("identifierSearch:(\"go:{GO:0008150}\")"));
    }

    @Test
    public void notSoSimpleQuery() {
        subject = new AdvancedSolrQuery(
                "identifierSearch",
                SemanticQuery.create(
                        SemanticQueryTerm.create("GO:0008150", "go"),
                        SemanticQueryTerm.create("PIM1", "synonym"),
                        SemanticQueryTerm.create("zinc finger")));

        assertThat(subject.toString(), is("identifierSearch:(\"go:{GO:0008150}\" OR \"synonym:{PIM1}\" OR \"zinc finger\")"));
    }

    @Test
    public void complexQuery() {
        subject = new AdvancedSolrQuery(
                OR,
                new AdvancedSolrQuery(
                        "identifierSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("GO:0008150", "go"),
                                SemanticQueryTerm.create("PIM1", "synonym"),
                                SemanticQueryTerm.create("zinc finger"))
                ),
                new AdvancedSolrQuery(
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
        subject = new AdvancedSolrQuery(
                OR,
                new AdvancedSolrQuery(
                        "identifierSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("GO:0008150", "go"),
                                SemanticQueryTerm.create("PIM1", "synonym"),
                                SemanticQueryTerm.create("zinc finger"))
                ),
                new AdvancedSolrQuery(
                        "conditionsSearch",
                        SemanticQuery.create(
                                SemanticQueryTerm.create("liver", "efo"),
                                SemanticQueryTerm.create("cancer"))
                ),
                new AdvancedSolrQuery(
                        AND,
                        new AdvancedSolrQuery(
                                "species",
                                SemanticQuery.create("homo sapiens")),
                        new AdvancedSolrQuery(
                                "defaultQueryFactorType",
                                SemanticQuery.create("CELL_LINE")),
                        new AdvancedSolrQuery(
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