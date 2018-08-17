package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeneSetUtilTest {
    private static final SemanticQuery GENE_QUERY_WITH_REACTOME_CATEGORY =
            SemanticQuery.create(SemanticQueryTerm.create("foo", "pathwayid"));
    private static final SemanticQuery GENE_QUERY_WITH_INTERPRO_CATEGORY =
            SemanticQuery.create(SemanticQueryTerm.create("foo", "interpro"));
    private static final SemanticQuery GENE_QUERY_WITH_GO_CATEGORY =
            SemanticQuery.create(SemanticQueryTerm.create("foo", "go"));
    private static final SemanticQuery GENE_QUERY_WITH_PO_CATEGORY =
            SemanticQuery.create(SemanticQueryTerm.create("foo", "po"));

    private static final SemanticQuery GENE_QUERY_WITH_REACTOME_VALUE =
            SemanticQuery.create(SemanticQueryTerm.create("R-HSA-123"));
    private static final SemanticQuery GENE_QUERY_WITH_INTERPRO_VALUE =
            SemanticQuery.create(SemanticQueryTerm.create("IPR123"));
    private static final SemanticQuery GENE_QUERY_WITH_GO_VALUE =
            SemanticQuery.create(SemanticQueryTerm.create("GO:123"));
    private static final SemanticQuery GENE_QUERY_WITH_PO_VALUE =
            SemanticQuery.create(SemanticQueryTerm.create("PO:123"));
    private static final SemanticQuery GENE_QUERY_WITH_PLANT_REACTOME_VALUE =
            SemanticQuery.create(SemanticQueryTerm.create("123456"));

    private static final SemanticQuery GENE_QUERY_WITH_GENE_SET_CATEGORIES =
            SemanticQuery.create(
                    SemanticQueryTerm.create("foo", "pathwayid"),
                    SemanticQueryTerm.create("foo", "interpro"),
                    SemanticQueryTerm.create("foo", "go"),
                    SemanticQueryTerm.create("foo", "po"));
    private static final SemanticQuery GENE_QUERY_WITH_GENE_SET_VALUES =
            SemanticQuery.create(
                    SemanticQueryTerm.create("R-HSA-123"),
                    SemanticQueryTerm.create("IPR123"),
                    SemanticQueryTerm.create("GO:123"),
                    SemanticQueryTerm.create("PO:123"),
                    SemanticQueryTerm.create("123456"));

    private static final SemanticQuery GENERIC_GENE_QUERY = SemanticQuery.create("zinc finger");
    private static final SemanticQuery TRICKY_GENE_QUERY =
            SemanticQuery.create(SemanticQueryTerm.create("IPR9000", "symbol"));
    private static final SemanticQuery EMPTY_QUERY = SemanticQuery.create();

    @Test
    public void testIsGeneSet() {
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_REACTOME_CATEGORY), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_INTERPRO_CATEGORY), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_GO_CATEGORY), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_PO_CATEGORY), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_REACTOME_VALUE), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_INTERPRO_VALUE), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_GO_VALUE), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_PO_VALUE), is(true));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_PLANT_REACTOME_VALUE), is(true));
    }

    @Test
    public void multiTermQuery() {
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_GENE_SET_CATEGORIES), is(false));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENE_QUERY_WITH_GENE_SET_VALUES), is(false));
    }

    @Test
    public void emptyQuery() {
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(GENERIC_GENE_QUERY), is(false));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(TRICKY_GENE_QUERY), is(false));
        assertThat(GeneSetUtil.matchesGeneSetCategoryOrGeneSetValue(EMPTY_QUERY), is(false));
    }
}
