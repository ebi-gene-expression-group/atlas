package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.solr.bioentities.query.SolrBioentitiesSuggesterService;
import uk.ac.ebi.atlas.search.SemanticQueryTerm;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesFactory;
import uk.ac.ebi.atlas.species.SpeciesProperties;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.utils.GsonProvider.GSON;

@RunWith(MockitoJUnitRunner.class)
public class AutoCompleteControllerTest {

    private static final String QUERY_STRING = "This is a query";
    private static final String HOMO_SAPIENS = "Homo sapiens";

    @Mock
    private SolrBioentitiesSuggesterService suggesterServiceMock;

    private AutoCompleteController subject;

    @Before
    public void setUp() {
        SemanticQueryTerm queryTerm1 = SemanticQueryTerm.create("Value1");
        SemanticQueryTerm queryTerm2 = SemanticQueryTerm.create("Value2");

        List<SemanticQueryTerm> suggestions = Lists.newArrayList(queryTerm1, queryTerm2);

        when(suggesterServiceMock.fetchPropertySuggestions(QUERY_STRING, 15, "Homo sapiens")).thenReturn(suggestions);

        subject = new AutoCompleteController(suggesterServiceMock);
    }

    @Test
    public void fetchTopSuggestions() {
        //given
        String jsonResponse = subject.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS, 15);

        //then
        assertThat(jsonResponse, is("[{\"value\":\"Value1\"},{\"value\":\"Value2\"}]"));
    }

    @Test
    public void fetchTermSource() {

        SemanticQueryTerm queryTerm1 = SemanticQueryTerm.create("TERM1", "CATEGORY1");
        SemanticQueryTerm queryTerm2 = SemanticQueryTerm.create("TERM2", "CATEGORY2");

        SemanticQueryTerm queryTerm3 = SemanticQueryTerm.create("TERM3", "CATEGORY3");
        SemanticQueryTerm queryTerm4 = SemanticQueryTerm.create("TERM4", "CATEGORY4");

        List<SemanticQueryTerm> termSourceList = Lists.newArrayList(queryTerm1, queryTerm2, queryTerm3, queryTerm4);

        String suggestions = GSON.toJson(termSourceList);

        assertThat(suggestions, is(
                "[{\"value\":\"TERM1\",\"category\":\"CATEGORY1\"},{\"value\":\"TERM2\",\"category\":\"CATEGORY2\"}," +
                 "{\"value\":\"TERM3\",\"category\":\"CATEGORY3\"},{\"value\":\"TERM4\",\"category\":\"CATEGORY4\"}]"));
    }

}
