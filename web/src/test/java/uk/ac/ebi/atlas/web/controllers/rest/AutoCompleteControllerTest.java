package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.solr.query.MultiTermSuggestionService;
import uk.ac.ebi.atlas.solr.query.SuggestionService;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutoCompleteControllerTest {

    private static final String QUERY_STRING = "This is a query";

    private static final String HOMO_SAPIENS = "homo sapiens";

    private AutoCompleteController subject;

    @Mock
    private BaselineRequestContext requestContextMock;

    @Mock
    private SuggestionService suggestionServiceMock;

    @Mock
    private MultiTermSuggestionService multiTermSuggestionServiceMock;

    @Before
    public void setUp() throws Exception {
        SemanticQueryTerm queryTerm1 = SemanticQueryTerm.create("Value1");
        SemanticQueryTerm queryTerm2 = SemanticQueryTerm.create("Value2");

        List<SemanticQueryTerm> suggestions = Lists.newArrayList(queryTerm1, queryTerm2);

        when(suggestionServiceMock.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS)).thenReturn(suggestions);

        when(requestContextMock.getFilteredBySpecies()).thenReturn(HOMO_SAPIENS);

        subject = new AutoCompleteController(suggestionServiceMock);

    }

    @Test
    public void fetchTopSuggestions() throws Exception {
        //given
        String jsonResponse = subject.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS);

        //then
        assertThat(jsonResponse, is("[{\"value\":\"Value1\",\"category\":\"\"},{\"value\":\"Value2\",\"category\":\"\"}]"));
    }

    @Test
    public void fetchTermSource() throws Exception {

        SemanticQueryTerm queryTerm1 = SemanticQueryTerm.create("TERM1", "CATEGORY1");
        SemanticQueryTerm queryTerm2 = SemanticQueryTerm.create("TERM2", "CATEGORY2");

        SemanticQueryTerm queryTerm3 = SemanticQueryTerm.create("TERM3", "CATEGORY3");
        SemanticQueryTerm queryTerm4 = SemanticQueryTerm.create("TERM4", "CATEGORY4");

        List<SemanticQueryTerm> termSourceList = Lists.newArrayList(queryTerm1, queryTerm2, queryTerm3, queryTerm4);

        Gson gson = new Gson();

        String suggestions = gson.toJson(termSourceList);

        assertThat(suggestions, is(
                "[{\"value\":\"TERM1\",\"category\":\"CATEGORY1\"},{\"value\":\"TERM2\",\"category\":\"CATEGORY2\"}," +
                 "{\"value\":\"TERM3\",\"category\":\"CATEGORY3\"},{\"value\":\"TERM4\",\"category\":\"CATEGORY4\"}]"));
    }

}
