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


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.utils.Files;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class MultiTermSuggestionServiceTest {

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private SolrQueryService solrQueryServiceMock;

    @Mock
    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizerMock;

    private MultiTermSuggestionService subject;

    private String jsonAutoCompleteResponse;
    private String jsonAutoCompleteEmptyResponse;

    @Before
    public void loadTestData() throws IOException {
        jsonAutoCompleteResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.json");
        jsonAutoCompleteEmptyResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.emptySuggestions.json");

        given(restTemplateMock.getForObject(anyString(), any(Class.class), anyVararg())).willReturn(jsonAutoCompleteResponse);

        subject = new MultiTermSuggestionService(restTemplateMock);
    }

    @Test
    public void shouldContainCollations(){
        List<SemanticQueryTerm> suggestions = subject.extractSuggestions(jsonAutoCompleteResponse);

        assertThat(suggestions.size(), is(29));

        assertThat(suggestions.get(0).value(), is("muscle organ development"));
        assertThat(suggestions.get(1).value(), is("muscle contraction"));
        assertThat(suggestions.get(2).value(), is("muscle cell differentiation"));
        assertThat(suggestions.get(3).value(), is("muscle cell homeostasis"));
        assertThat(suggestions.get(4).value(), is("muscle fiber development"));
        assertThat(suggestions.get(5).value(), is("mushroom body development"));
        assertThat(suggestions.get(6).value(), is("Muscle contraction"));
        assertThat(suggestions.get(7).value(), is("muscle filament sliding"));
        assertThat(suggestions.get(28).value(), is(" MUSCULAR DYSTROPHY-DYSTROGLYCANOPATHY (CONGENITAL WITH MENTAL RETARDATION),"));
    }

    @Test
    public void shouldContainCollationsIfSuggestionsElementIsEmpty(){
        List<SemanticQueryTerm> suggestions = subject.extractSuggestions(jsonAutoCompleteEmptyResponse);
        assertThat(suggestions, is(empty()));
    }

    @Test
    public void shouldReturnEmptyListIfResponseIsEmpty(){
        List<SemanticQueryTerm> suggestions = subject.extractSuggestions("{}");
        assertThat(suggestions, is(empty()));
    }

    @Test
    public void findGenePropertiesShouldReturnEmptyListWhenTermContainsNonSpellCheckableCharacters() {
        assertThat(subject.fetchMultiTermSuggestions("hello > boy"), Matchers.is(empty()));
    }

    @Test
    public void findGenePropertiesShouldNotReturnEmptyList() {
        assertThat(subject.fetchMultiTermSuggestions("p53"), Matchers.is(not(empty())));
    }

}
