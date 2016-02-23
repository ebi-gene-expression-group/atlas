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
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.solr.query.MultiTermSuggestionService;
import uk.ac.ebi.atlas.solr.query.SuggestionService;
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

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
        SemanticQueryTerm termSource1 = SemanticQueryTerm.create("Value1");
        SemanticQueryTerm termSource2 = SemanticQueryTerm.create("Value2");

        List<SemanticQueryTerm> suggestions = Lists.newArrayList(termSource1, termSource2);

        when(suggestionServiceMock.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS)).thenReturn(suggestions);

        when(requestContextMock.getFilteredBySpecies()).thenReturn(HOMO_SAPIENS);

        subject = new AutoCompleteController(suggestionServiceMock);

    }

    @Test
    public void fetchTopSuggestions() throws Exception {
        //given
        String jsonResponse = subject.fetchTopSuggestions(QUERY_STRING, HOMO_SAPIENS);

        //then
        assertThat(jsonResponse, is("[{\"term\":\"Value1\",\"source\":\"\"},{\"term\":\"Value2\",\"source\":\"\"}]"));
    }

    @Test
    public void fetchTermSource() throws Exception {

        SemanticQueryTerm termSource1 = SemanticQueryTerm.create("TERM1", "SOURCE1");
        SemanticQueryTerm termSource2 = SemanticQueryTerm.create("TERM2", "SOURCE2");

        SemanticQueryTerm termSource3 = SemanticQueryTerm.create("TERM3", "SOURCE3");
        SemanticQueryTerm termSource4 = SemanticQueryTerm.create("TERM4", "SOURCE4");

        List<SemanticQueryTerm> termSourceList = Lists.newArrayList(termSource1,termSource2,termSource3,termSource4);

        Gson gson = new Gson();

        String suggestions = gson.toJson(termSourceList);

        assertThat(suggestions, is("[{\"term\":\"TERM1\",\"source\":\"SOURCE1\"},{\"term\":\"TERM2\",\"source\":\"SOURCE2\"},{\"term\":\"TERM3\",\"source\":\"SOURCE3\"},{\"term\":\"TERM4\",\"source\":\"SOURCE4\"}]"));
    }

}
