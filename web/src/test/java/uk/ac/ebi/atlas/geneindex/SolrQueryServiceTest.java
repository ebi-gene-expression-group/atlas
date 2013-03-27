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

package uk.ac.ebi.atlas.geneindex;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class SolrQueryServiceTest {

    private SolrQueryService subject;

    @Before
    public void setUp() throws Exception {
        subject = new SolrQueryService();
    }

    @Test
    public void testBuildSolrQuery() throws Exception {
        subject.buildGeneQuery("query_string", false, "sapiens");
    }

    @Test
    public void testBuildQueryAllTextString() {
        String query = "GO:0008134 \"p53 binding\"";
        assertThat(subject.buildGeneQuery(query, false, "sapiens"), containsString("(property_search:GO\\:0008134 \"p53 binding\")"));

        query = "GO:0008134 \"p53 binding";
        assertThat(subject.buildGeneQuery(query, false, "sapiens"), containsString("(property_search:GO\\:0008134 \"p53 binding)"));
    }
}
