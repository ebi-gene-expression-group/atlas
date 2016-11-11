package uk.ac.ebi.atlas.solr.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class MultiTermSuggestionServiceIT {

    @Inject
    private MultiTermSuggestionService subject;

    @Test
    public void apopSuggestsMultiTermSuggestion() {
        List<SemanticQueryTerm> suggestions = subject.fetchMultiTermSuggestions("apop");

        assertThat(suggestions.get(0).value(), is("apoptotic process"));
        assertThat(suggestions.get(0).category(), is(""));
    }

    @Test
    public void apoptotic_pSuggestsApototicProcess() {
        List<SemanticQueryTerm> suggestions = subject.fetchMultiTermSuggestions("apoptotic p");

        assertThat(suggestions.get(0).value(), is("apoptotic process"));
        assertThat(suggestions.get(0).category(), is(""));
    }

    @Test
    public void mitochondrial_encSuggestsMitochondrialEncoded() {
        List<SemanticQueryTerm> suggestions = subject.fetchMultiTermSuggestions("mitochondrial enc");

        assertThat(suggestions.get(0).value(), is("Mitochondrial-encoded proline-accepting tRNA. [Source:TAIR;Acc:ATMG00350]"));
        assertThat(suggestions.get(0).category(), is(""));

    }

    @Test
    public void ifContainsNonWordCharactersReturnNoSuggestions() {
        List<SemanticQueryTerm> properties = subject.fetchMultiTermSuggestions("prot%");
        assertThat(properties.size(), is(0));
    }

    @Test
    public void searchTermContainingHyphen() {
        List<SemanticQueryTerm> properties = subject.fetchMultiTermSuggestions("G-protein");
        assertThat(properties.size(), is(30));

        assertThat(properties.get(0).value(), is("G-protein coupled receptor signaling pathway"));
        assertThat(properties.get(0).category(), is(""));
    }

}
