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

import com.google.common.collect.Lists;
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
        String s = subject.buildCompositeQuery("geneName", "species", new String[]{"prototype1", "prototype2"}, "ensgene");

        // then
        assertThat(s, is(SolrQueryService.PROPERTY_EDGENGRAM_FIELD + ":\"geneName\" AND " +
                SolrQueryService.SPECIES_FIELD + ":\"species\" AND (" +
                SolrQueryService.BIOENTITY_TYPE + ":\"ensgene\") AND (" +
                SolrQueryService.PROPERTY_TYPE_FIELD + ":\"prototype1\" OR " +
                SolrQueryService.PROPERTY_TYPE_FIELD + ":\"prototype2\")"));
    }

    @Test
    public void testBuildCompositeQueryIdentifier() {

        // given
        String s = subject.buildCompositeQueryIdentifier("ENSMUS000000", Lists.newArrayList("prototype1", "prototype2"));

        // then
        assertThat(s, is(SolrQueryService.IDENTIFIER_FIELD + ":\"ENSMUS000000\" AND (" +
                SolrQueryService.PROPERTY_TYPE_FIELD + ":\"prototype1\" OR " +
                SolrQueryService.PROPERTY_TYPE_FIELD + ":\"prototype2\")"));
    }

    @Test
    public void testBuildGeneQuery() throws Exception {

        // given
        String s = subject.buildGeneQuery("query_string", false, "sapiens", "ensgene");

        // then
        assertThat(s, is("{!lucene q.op=OR df=" + SolrQueryService.PROPERTY_SEARCH_FIELD + "} (" +
                SolrQueryService.PROPERTY_SEARCH_FIELD + ":query_string) AND " +
                SolrQueryService.SPECIES_FIELD + ":\"sapiens\" AND (" +
                SolrQueryService.BIOENTITY_TYPE + ":\"ensgene\")"));

    }

    @Test
    public void testBuildGeneQueryMultiTerms() {

        String query = "GO:0008134 \"p53 binding";
        assertThat(subject.buildGeneQuery(query, false, "sapiens", "ensgene"), containsString("(" + SolrQueryService.PROPERTY_SEARCH_FIELD + ":GO\\:0008134 \"p53 binding)"));

        query = query + "\"";

        assertThat(subject.buildGeneQuery(query, false, "sapiens", "ensgene"), containsString("(" + SolrQueryService.PROPERTY_SEARCH_FIELD + ":GO\\:0008134 \"p53 binding\")"));

    }
}
