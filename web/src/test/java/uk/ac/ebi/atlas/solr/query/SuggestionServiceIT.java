package uk.ac.ebi.atlas.solr.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SuggestionServiceIT {

    @Inject
    private SuggestionService subject;

    @Test
    public void fetchTopSuggestions() {
        List<SemanticQueryTerm> suggestions = subject.fetchTopSuggestions("apop", null);

        assertThat(suggestions, hasSize(greaterThan(10)));
        for (SemanticQueryTerm queryTerm : suggestions) {
            assertThat(queryTerm.value().toLowerCase(), containsString("apop"));
        }
    }

}
