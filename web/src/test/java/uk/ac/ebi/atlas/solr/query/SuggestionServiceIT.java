package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SuggestionServiceIT {

    @Inject
    private SuggestionService suggestionService;

    @Test
    public void apop() {
        List<TermSourceSuggestion> suggestions = suggestionService.fetchTopSuggestions("apop", null);
        System.out.println(Joiner.on(",\n").join(suggestions));

        assertThat(suggestions.get(0).term, is("APOPT1"));
        assertThat(suggestions.get(0).source, is("symbol"));
        assertThat(suggestions.get(13).term, is("apoptotic process"));
        assertThat(suggestions.get(13).source, is(""));
    }

    @Test
    public void multiTermAutoComplete_ReturnedPhrasesMustStartWithSearchTerm() {
        List<TermSourceSuggestion> suggestions = suggestionService.fetchTopSuggestions("G protein", null);
        //System.out.println(Joiner.on("\", \"").join(suggestions));
        assertThat(suggestions.get(0).term, is("G-protein coupled receptor signaling pathway"));
        assertThat(suggestions.get(0).source, is(""));
        assertThat(suggestions.get(1).term, is("G-protein coupled receptor activity"));
        assertThat(suggestions.get(1).source, is(""));

        suggestions = suggestionService.fetchTopSuggestions("G prot 1", null);

        assertThat(suggestions.size(), is(0));

    }

    @Test
    public void multiTermAutoComplete_HyphenIsTreatedSameAsWhiteSpace() {
        List<TermSourceSuggestion> gSpaceProtein = suggestionService.fetchTopSuggestions("G protein", null);
        System.out.println(Joiner.on(",\n").join(gSpaceProtein));

        assertThat(gSpaceProtein.size(), is(15));

        List<TermSourceSuggestion> gHyphenProtein = suggestionService.fetchTopSuggestions("G-protein", null);
        System.out.println(Joiner.on(",\n").join(gHyphenProtein));

        assertThat(gHyphenProtein.size(), is(15));
    }


}
