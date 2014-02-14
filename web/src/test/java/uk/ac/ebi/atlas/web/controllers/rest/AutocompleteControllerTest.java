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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.solr.query.SolrSuggestionsService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutocompleteControllerTest {

    private static final String QUERY_STRING = "This is a query";

    private static final String SPECIES = "homo sapiens";

    private AutocompleteController subject;

    @Mock
    private BaselineRequestContext requestContextMock;

    @Mock
    private SolrSuggestionsService solrSuggestionsServiceMock;

    @Before
    public void setUp() throws Exception {

        List<String> suggestions = Lists.newArrayList("Value1", "Value2");

        when(solrSuggestionsServiceMock.fetchGeneIdSuggestionsInName(QUERY_STRING, SPECIES)).thenReturn(suggestions);
        when(solrSuggestionsServiceMock.fetchGenePropertySpellingSuggestions(QUERY_STRING, SPECIES)).thenReturn(suggestions);

        when(requestContextMock.getFilteredBySpecies()).thenReturn(SPECIES);

        subject = new AutocompleteController(solrSuggestionsServiceMock);

    }

    @Test
    public void testGetTopSuggestions() throws Exception {
        //given
        String jsonResponse = subject.getTopSuggestions(QUERY_STRING, SPECIES);

        //then
        assertThat(jsonResponse, is("[\"Value1\",\"Value2\"]"));
    }
}
