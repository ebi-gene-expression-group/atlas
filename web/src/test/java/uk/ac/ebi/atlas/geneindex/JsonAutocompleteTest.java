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


import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
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

        subject = new SolrClient(mock(RestTemplate.class));
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
}
