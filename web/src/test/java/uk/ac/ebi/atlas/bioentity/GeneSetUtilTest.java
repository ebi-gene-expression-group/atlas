package uk.ac.ebi.atlas.bioentity;

import com.google.common.collect.Sets;
import org.junit.Test;
import uk.ac.ebi.atlas.web.SemanticQuery;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import static org.junit.Assert.*;

/**
 * Created by Alfonso Muñoz-Pomer Fuentes <amunoz@ebi.ac.uk> on 15/02/2016.
 */
public class GeneSetUtilTest {

    private final SemanticQuery GENE_QUERY_WITH_GENE_SET_SOURCES =
            new SemanticQuery(SemanticQueryTerm.create("foo", "pathwayid"), SemanticQueryTerm.create("foo", "interpro"), SemanticQueryTerm.create("foo", "go"), SemanticQueryTerm.create("foo", "po"));
    private final SemanticQuery GENE_QUERY_WITH_GENE_SET_VALUES =
            new SemanticQuery(SemanticQueryTerm.create("R-HSA-123"), SemanticQueryTerm.create("IPR123"), SemanticQueryTerm.create("GO:123"), SemanticQueryTerm.create("PO:123"), SemanticQueryTerm.create("123456"));

    @Test
    public void testIsGeneSet() throws Exception {
        assertTrue(GeneSetUtil.isGeneSetSourceOrMatchesGeneSetAccession(GENE_QUERY_WITH_GENE_SET_SOURCES));
        assertTrue(GeneSetUtil.isGeneSetSourceOrMatchesGeneSetAccession(GENE_QUERY_WITH_GENE_SET_VALUES));
        assertTrue(GeneSetUtil.isGeneSetSourceOrMatchesGeneSetAccession(new SemanticQuery(Sets.union(GENE_QUERY_WITH_GENE_SET_SOURCES.terms(), GENE_QUERY_WITH_GENE_SET_VALUES.terms()))));
    }
}