package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.Sets;
import org.junit.Test;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import static org.junit.Assert.*;

public class GeneSetUtilTest {

    private final SemanticQuery GENE_QUERY_WITH_GENE_SET_SOURCES =
            SemanticQuery.create(SemanticQueryTerm.create("foo", "pathwayid"), SemanticQueryTerm.create("foo", "interpro"), SemanticQueryTerm.create("foo", "go"), SemanticQueryTerm.create("foo", "po"));
    private final SemanticQuery GENE_QUERY_WITH_GENE_SET_VALUES =
            SemanticQuery.create(SemanticQueryTerm.create("R-HSA-123"), SemanticQueryTerm.create("IPR123"), SemanticQueryTerm.create("GO:123"), SemanticQueryTerm.create("PO:123"), SemanticQueryTerm.create("123456"));

    @Test
    public void testIsGeneSet() throws Exception {
        assertTrue(GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(GENE_QUERY_WITH_GENE_SET_SOURCES));
        assertTrue(GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(GENE_QUERY_WITH_GENE_SET_VALUES));
        assertTrue(GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(SemanticQuery.create(Sets.union(GENE_QUERY_WITH_GENE_SET_SOURCES.terms(), GENE_QUERY_WITH_GENE_SET_VALUES.terms()))));
    }
}