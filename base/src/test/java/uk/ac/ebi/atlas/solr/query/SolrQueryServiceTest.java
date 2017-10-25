package uk.ac.ebi.atlas.solr.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BioentityPropertyName;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.inject.Inject;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SolrQueryServiceTest {

    @Inject
    SolrQueryService subject;

    @Test
    public void getKnownSpeciesWithCategory() throws Exception {
        assertThat(
                subject.fetchSpecies(SemanticQueryTerm.create("ENSMUSG00000019082", BioentityPropertyName.ENSGENE.name())),
                hasItem(equalToIgnoringCase("mus_musculus")));

        assertThat(
                subject.fetchSpecies(SemanticQueryTerm.create("FBgn0260743", BioentityPropertyName.FLYBASE_GENE_ID.name())),
                hasItem(equalToIgnoringCase("drosophila_melanogaster")));
    }

    @Test
    public void getKnownSpeciesWithoutCategory() throws Exception {
        assertThat(
                subject.fetchSpecies(SemanticQueryTerm.create("ENSMUSG00000019082")),
                hasItem(equalToIgnoringCase("mus_musculus")));
    }

    @Test
    public void queryWithoutCategoryFallsBackToProperties() throws Exception {
        assertThat(
                subject.fetchSpecies(SemanticQueryTerm.create("OBP3-responsive gene 4")),
                hasItem(equalToIgnoringCase("arabidopsis_lyrata")));
    }

    @Test
    public void unknownSpeciesReturnsEmpty() throws Exception {
        // Escherichia coli
        assertThat(
                subject.fetchSpecies(SemanticQueryTerm.create(BioentityPropertyName.ENSGENE.name(), "ECBD_0176")),
                is(empty()));
    }
}