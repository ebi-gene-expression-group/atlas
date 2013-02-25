package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneindex.SolrClient;

import java.util.List;
import java.util.NavigableSet;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutocompleteControllerTest {

    private static final String QUERY_STRING = "This is a query";

    private AutocompleteController subject;

    @Mock
    private SolrClient solrClientMock;

    @Before
    public void setUp() throws Exception {

        List<String> suggestions = Lists.newArrayList("Value1", "Value2");

        when(solrClientMock.findGeneNameSuggestions(QUERY_STRING)).thenReturn(suggestions);
        when(solrClientMock.findGenePropertySuggestions(QUERY_STRING)).thenReturn(suggestions);

        subject = new AutocompleteController(solrClientMock);

    }

    @Test
    public void testGetTopSuggestions() throws Exception {
        //given
        String jsonResponse = subject.getTopSuggestions(QUERY_STRING);

        //then
        assertThat(jsonResponse, is("[\"Value1\",\"Value2\"]"));
    }
}
