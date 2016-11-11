package uk.ac.ebi.atlas.solr.query;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.web.GenesNotFoundException;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class GeneIdSuggestionServiceIT {

    private static final String HOMO_SAPIENS_SPECIES = "homo sapiens";
    private static final String MUS_MUSCULUS_SPECIES = "mus musculus";

    @Inject
    private GeneIdSuggestionService subject;

    @Test
    public void findGeneNameSuggestionsForPartialGeneNames() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("mt-at", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(2));
        assertThat(properties.get(0).value().toLowerCase(), startsWith("mt-at"));
        assertThat(properties.get(0).category(), is("symbol"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("mt-at", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneNameSuggestionsForFullGeneNames() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("mt-atp6", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value().toLowerCase(), startsWith("mt-atp6"));
        assertThat(properties.get(0).category(), is("symbol"));

        properties = subject.fetchGeneIdSuggestionsInName("mt-atp8", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("MT-ATP8"));
        assertThat(properties.get(0).category(), is("symbol"));

    }

    @Test
    public void findSuggestionsForProteinCoding() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("protein_c", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("protein_coding"));
        assertThat(properties.get(0).category(), is("gene_biotype"));
    }

    @Test
    public void findSuggestionsForGOTerm() {
        //GO:0016021
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:0016", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(7).value(), startsWith("GO:0016"));
        assertThat(properties.get(7).category(), is("go"));
        assertThat(properties.get(5).value(), startsWith("GO:0016"));
        assertThat(properties.get(5).category(), is("go"));
        assertThat(properties.get(10).value(), startsWith("GO:0016"));
        assertThat(properties.get(10).category(), is("go"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:001602", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(5));
        assertThat(properties.get(1).value(), startsWith("GO:0016"));
        assertThat(properties.get(1).category(), is("go"));
        assertThat(properties.get(0).value(), startsWith("GO:0016"));
        assertThat(properties.get(0).category(), is("go"));
        assertThat(properties.get(4).value(), startsWith("GO:0016"));
        assertThat(properties.get(4).category(), is("go"));
    }

    @Test
    public void findSuggestionsForDesignElement() {
        //Hs2Affx.1.41.S1_3p_s_at
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(0).value(), is("Hs2Affx.1.6.S1_3p_s_at"));
        assertThat(properties.get(0).category(), is("design_element"));
        assertThat(properties.get(4).value(), is("Hs2Affx.1.212.S1_3p_s_at"));
        assertThat(properties.get(4).category(), is("design_element"));
        assertThat(properties.get(14).value(), is("Hs2Affx.1.414.S1_3p_at"));
        assertThat(properties.get(14).category(), is("design_element"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx.1.41", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(7));
        assertThat(properties.get(0).value(), is("Hs2Affx.1.413.S1_3p_at"));
        assertThat(properties.get(0).category(), is("design_element"));
    }

    @Test
    public void findSomethingStupidShouldReturnEmpty() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx>", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));

        properties = subject.fetchGeneIdSuggestionsInName("mt-at$", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneSynonymSuggestions() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInSynonym("atpase-", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("ATPase-6"));
        assertThat(properties.get(0).category(), is("synonym"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("mtatp", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(2));
        assertThat(properties.get(0).value().toLowerCase(), startsWith("mtatp"));
        assertThat(properties.get(0).category(), is("synonym"));
        assertThat(properties.get(1).value().toLowerCase(), startsWith("mtatp"));
        assertThat(properties.get(1).category(), is("synonym"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("su6", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("Su6m"));
        assertThat(properties.get(0).category(), is("synonym"));

    }

    @Test
    public void findGeneNameSuggestionsShouldSupportSingleTermQueries() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(0).value().toLowerCase(), startsWith("p"));
        assertThat(properties.get(0).category(), is("symbol"));
        assertThat(properties.get(1).value().toLowerCase(), startsWith("p"));
        assertThat(properties.get(1).category(), is("symbol"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotContainSpeciesTerms() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("mus", MUS_MUSCULUS_SPECIES);

        assertThat(properties.size(), is(3));

        assertThat(properties.get(0).value(), is("Musk"));
        assertThat(properties.get(0).category(), is("symbol"));
        assertThat(properties.get(1).value(), is("Mustn1"));
        assertThat(properties.get(1).category(), is("symbol"));
        assertThat(properties.get(2).value(), is("Mus81"));
        assertThat(properties.get(2).category(), is("symbol"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotSupportMultitermQueries() {
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("En p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));
    }

    @Test
    public void testGetSolrResultsForQuery() throws SolrServerException, GenesNotFoundException {
        // given
        List<SemanticQueryTerm> geneNames = subject.fetchGeneIdSuggestionsInName("aspm", "homo sapiens");

        assertThat(geneNames.get(0).value(), is("ASPM"));
        assertThat(geneNames.get(0).category(), is("symbol"));

    }

}
