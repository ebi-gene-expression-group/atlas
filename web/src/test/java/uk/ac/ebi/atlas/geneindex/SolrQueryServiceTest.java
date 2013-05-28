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

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SolrQueryServiceTest {

    private SolrQueryService subject;

    @Mock
    private GeneQueryTokenizer geneQueryTokenizerMock;

    @Before
    public void setUp() throws Exception {
        subject = new SolrQueryService();
    }

    @Test
    public void testCustomEscape() {
        assertThat(subject.customEscape("GO:12345"), is("GO\\:12345"));
    }

    @Test
    public void testBuildSolrQuery() {

        // given
        SolrQuery query = subject.buildSolrQuery("query", "facet", -1);

        // then
        assertThat(query.getFacetFields(), hasItemInArray("facet"));
        assertThat(query.getQuery(), is("query"));
        assertThat(query.getFacetLimit(), is(-1));

    }

    @Test
    public void testBuildCompositeQuery() {

        // given
        String s = subject.buildCompositeQuery("geneName", "ensgene", "species", new String[]{"prototype1", "prototype2"});

        // then
        assertThat(s, is("property_edgengram:\"geneName\" AND species:\"species\" AND type:\"ensgene\" AND (property_type:\"prototype1\" OR property_type:\"prototype2\")"));

    }

    @Test
    public void testBuildCompositeQueryIdentifier() {

        // given
        String s = subject.buildCompositeQueryIdentifier("ENSMUS000000", new String[]{"prototype1", "prototype2"});

        // then
        assertThat(s, is("identifier:\"ENSMUS000000\" AND (property_type:\"prototype1\" OR property_type:\"prototype2\")"));

    }

    @Test
    public void testBuildGeneQuery() throws Exception {

        // given
        String s = subject.buildGeneQuery("query_string", false, "ensgene", "sapiens");

        // then
        assertThat(s, is("{!lucene q.op=OR df=property_search} (property_search:query_string) AND species:\"sapiens\" AND type:\"ensgene\""));

    }

    @Test
    public void testBuildGeneQueryMultiTerms() {

        String query = "GO:0008134 \"p53 binding";
        assertThat(subject.buildGeneQuery(query, false, "ensgene", "sapiens"), containsString("(property_search:GO\\:0008134 \"p53 binding)"));

        query = query + "\"";

        assertThat(subject.buildGeneQuery(query, false, "ensgene", "sapiens"), containsString("(property_search:GO\\:0008134 \"p53 binding\")"));

    }
}
