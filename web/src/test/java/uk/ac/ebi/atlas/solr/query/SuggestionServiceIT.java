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
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SuggestionServiceIT {

    @Inject
    private SuggestionService suggestionService;

    @Test
    public void apop() {
        List<String> suggestions = suggestionService.fetchTopSuggestions("apop", null);
        System.out.println(Joiner.on(",\n").join(suggestions));
        assertThat(suggestions, hasItem("apoptotic process"));
    }

    @Test
    public void multiTermAutoComplete_ReturnedPhrasesMustStartWithSearchTerm() {
        List<String> suggestions = suggestionService.fetchTopSuggestions("G protein", null);
        //System.out.println(Joiner.on("\", \"").join(suggestions));
        assertThat(suggestions, everyItem(anyOf(startsWith("G protein"), startsWith("G-protein"))));

        suggestions = suggestionService.fetchTopSuggestions("G prot 1", null);
        assertThat(suggestions, not(hasItem("Gla-Rich Protein 1")));
    }

    @Test
    public void multiTermAutoComplete_HyphenIsTreatedSameAsWhiteSpace() {
        List<String> gSpaceProtein = suggestionService.fetchTopSuggestions("G protein", null);
        System.out.println(Joiner.on(",\n").join(gSpaceProtein));

        List<String> gHyphenProtein = suggestionService.fetchTopSuggestions("G-protein", null);
        System.out.println(Joiner.on(",\n").join(gHyphenProtein));

        assertThat(gHyphenProtein, contains(gSpaceProtein.toArray()));
    }


}
