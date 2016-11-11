package uk.ac.ebi.atlas.solr.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
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
