package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.Sets;
import org.junit.Test;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import static org.junit.Assert.*;

public class GeneSetUtilTest {

    private final GeneQuery GENE_QUERY_WITH_GENE_SET_SOURCES =
            GeneQuery.create(SemanticQueryTerm.create("foo", "pathwayid"), SemanticQueryTerm.create("foo", "interpro"), SemanticQueryTerm.create("foo", "go"), SemanticQueryTerm.create("foo", "po"));
    private final GeneQuery GENE_QUERY_WITH_GENE_SET_VALUES =
            GeneQuery.create(SemanticQueryTerm.create("R-HSA-123"), SemanticQueryTerm.create("IPR123"), SemanticQueryTerm.create("GO:123"), SemanticQueryTerm.create("PO:123"), SemanticQueryTerm.create("123456"));

    @Test
    public void testIsGeneSet() throws Exception {
        assertTrue(GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(GENE_QUERY_WITH_GENE_SET_SOURCES));
        assertTrue(GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(GENE_QUERY_WITH_GENE_SET_VALUES));
        assertTrue(GeneSetUtil.isGeneSetCategoryOrMatchesGeneSetAccession(GeneQuery.create(Sets.union(GENE_QUERY_WITH_GENE_SET_SOURCES.terms(), GENE_QUERY_WITH_GENE_SET_VALUES.terms()))));
    }
}