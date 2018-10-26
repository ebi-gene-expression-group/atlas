package uk.ac.ebi.atlas.controllers.rest;

import com.google.common.collect.ImmutableMap;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.search.suggester.SuggesterService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutoCompleteControllerTest {

    private static final String QUERY_STRING = "This is a query";
    private static final String HOMO_SAPIENS = "Homo sapiens";

    @Mock
    private SuggesterService suggesterServiceMock;

    private AutoCompleteController subject;

    @Before
    public void setUp() {
        subject = new AutoCompleteController(suggesterServiceMock);
    }

    @Test
    public void fetchTopSuggestions() {
        Map<String, String> queryTerm1 = ImmutableMap.of("term", "Value1", "category", "");
        Map<String, String> queryTerm2 = ImmutableMap.of("term", "Value2", "category", "");

        when(suggesterServiceMock.fetchPropertiesWithHighlighting(QUERY_STRING, "Homo sapiens"))
                .thenReturn(Stream.of(queryTerm1, queryTerm2));

        String jsonResponse = subject.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS);
        ReadContext ctx = JsonPath.parse(jsonResponse);

        assertThat(ctx.<List<Map<String, String>>>read("$")).hasSize(2);
        assertThat(ctx.<List<Map<String, String>>>read("$"))
                .extracting("term", "category")
                .containsExactly(
                        tuple("Value1", ""),
                        tuple("Value2", ""));
    }
}
