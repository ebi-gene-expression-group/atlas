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

import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SolrClientIT {

    private static final String SPECIES = "homo sapiens";

    private static final String SOLR_REST_GENENAMES_QUERY_TEMPLATE = "suggest_genename?q={0}&wt=json&omitHeader=true";

    @Inject
    private SolrClient subject;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetJsonResponseForGeneNameSuggestions() {

        String jsonString = subject.getJsonResponse(SOLR_REST_GENENAMES_QUERY_TEMPLATE, "asp");

        List<String> list = JsonPath.read(jsonString, SolrClient.JSON_PATH_LAST_TERM_SUGGESTION);
        assertThat(list, is(not(empty())));
        assertThat(list, hasItems("asp", "aspm"));
    }

    @Test
    public void testGetJsonForMultiTermSuggestions() {

        List<String> properties = subject.findGenePropertySuggestions("p b", SPECIES);

        assertThat(properties.size(), is(10));
        assertThat(properties, hasItems("p b", "p binding", "putative b"));
    }
}
