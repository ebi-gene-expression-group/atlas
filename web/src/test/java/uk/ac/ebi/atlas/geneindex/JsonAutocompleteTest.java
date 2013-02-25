/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.geneindex;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class JsonAutocompleteTest {


    private SolrClient subject;

    private String jsonAutocompleteResponse;
    private String jsonAutocompleteEmptyResponse;

    @Before
    public void loadTestData() throws IOException {
        jsonAutocompleteResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.json");
        jsonAutocompleteEmptyResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.emptySuggestions.json");

        subject = new SolrClient(mock(RestTemplate.class), mock(GenePropertyQueryBuilder.class));
    }

    @Test
    public void shouldContainSuggestionsForEachTerm(){
        List<String> suggestions = subject.extractSuggestions(jsonAutocompleteResponse, "b");
        assertThat(suggestions.size(), is(greaterThan(10)));
        assertThat(suggestions, hasItems("b", "binding", "beta"));

        suggestions = subject.extractSuggestions(jsonAutocompleteResponse, "p");
        assertThat(suggestions.size(), is(greaterThan(10)));
        assertThat(suggestions, hasItems("p", "protein"));
    }

    @Test
    public void shouldNotContainSuggestionsForUnknownTerms(){
        List<String> suggestions = subject.extractSuggestions(jsonAutocompleteResponse, "z");
        assertThat(suggestions, is(empty()));
    }

    @Test
    public void shouldNotContainSuggestionsIfSuggestionsElementIsEmpty(){
        List<String> suggestions = subject.extractSuggestions(jsonAutocompleteEmptyResponse, "z");
        assertThat(suggestions, is(empty()));
    }

    @Test
    public void shouldContainCollations(){
        List<String> suggestions = subject.extractCollations(jsonAutocompleteResponse);
        assertThat(suggestions.size(), is(10));
        assertThat(suggestions, hasItems("p b", "p binding"));
    }

    @Test
    public void shouldContainCollationsIfSuggestionsElementIsEmpty(){
        List<String> suggestions = subject.extractCollations(jsonAutocompleteEmptyResponse);
        assertThat(suggestions, is(empty()));
    }
}
