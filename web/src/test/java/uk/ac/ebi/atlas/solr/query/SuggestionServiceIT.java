package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

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
        List<SemanticQueryTerm> suggestions = suggestionService.fetchTopSuggestions("apop", null);
        System.out.println(Joiner.on(",\n").join(suggestions));

        assertThat(suggestions.get(0).value(), is("Apopt1"));
        assertThat(suggestions.get(0).source(), is("symbol"));
        assertThat(suggestions.get(13).value(), is("apoplast"));
        assertThat(suggestions.get(13).source(), is(""));
    }

}
