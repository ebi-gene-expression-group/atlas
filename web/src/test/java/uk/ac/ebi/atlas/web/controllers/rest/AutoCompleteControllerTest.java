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
import uk.ac.ebi.atlas.solr.query.GeneIdSuggestionService;
import uk.ac.ebi.atlas.solr.query.MultiTermSuggestionService;

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
    private GeneIdSuggestionService geneIdSuggestionServiceMock;

    @Mock
    private MultiTermSuggestionService multiTermSuggestionServiceMock;

    @Before
    public void setUp() throws Exception {

        List<String> suggestions = Lists.newArrayList("Value1", "Value2");

        when(geneIdSuggestionServiceMock.fetchGeneIdSuggestionsInName(QUERY_STRING, HOMO_SAPIENS)).thenReturn(suggestions);
        when(multiTermSuggestionServiceMock.fetchMultiTermSuggestions(QUERY_STRING, HOMO_SAPIENS)).thenReturn(suggestions);

        when(requestContextMock.getFilteredBySpecies()).thenReturn(HOMO_SAPIENS);

        subject = new AutoCompleteController(geneIdSuggestionServiceMock, multiTermSuggestionServiceMock);

    }

    @Test
    public void fetchTopSuggestions() throws Exception {
        //given
        String jsonResponse = subject.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS);

        //then
        assertThat(jsonResponse, is("[\"Value1\",\"Value2\"]"));
    }
}
