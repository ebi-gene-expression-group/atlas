/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.solr.query;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SolrSuggestionsServiceTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private SolrQueryService solrQueryServiceMock;

    @Mock
    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizerMock;

    private SolrSuggestionsService subject;

    private String jsonAutocompleteResponse;
    private String jsonAutocompleteEmptyResponse;

    @Before
    public void loadTestData() throws IOException {
        jsonAutocompleteResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.json");
        jsonAutocompleteEmptyResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.emptySuggestions.json");

        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyVararg())).willReturn(jsonAutocompleteResponse);

        subject = new SolrSuggestionsService(restTemplateMock, null, null);
    }

    @Test
    public void shouldContainCollations(){
        List<String> suggestions = subject.extractCollations(jsonAutocompleteResponse);
        assertThat(suggestions.size(), is(5));
        assertThat(suggestions, contains("mus", "mus81", "musculus", "musk", "mustn1"));
    }

    @Test
    public void shouldContainCollationsIfSuggestionsElementIsEmpty(){
        List<String> suggestions = subject.extractCollations(jsonAutocompleteEmptyResponse);
        assertThat(suggestions, is(empty()));
    }

    @Test
    public void shouldReturnEmptyListIfResponseIsEmpty(){
        List<String> suggestions = subject.extractCollations("{}");
        assertThat(suggestions, is(empty()));
    }

    @Test
    public void testExtractSuggestion() {
        //given
        String suggestion = subject.extractSuggestion("\"musk\" AND species:\"mus musculus\"");

        MatcherAssert.assertThat(suggestion, Matchers.is("musk"));
    }

    @Test
    public void findGenePropertiesShouldReturnEmptyListWhenTermContainsNonSpellCheckableCharacters() {
        assertThat(subject.findGenePropertySpellingSuggestions("hello > boy", "mus mus"), Matchers.is(empty()));
    }

    @Test
    public void findGenePropertiesShouldNotReturnEmptyList() {
        assertThat(subject.findGenePropertySpellingSuggestions("p53", "mus mus"), Matchers.is(not(empty())));
    }

}
