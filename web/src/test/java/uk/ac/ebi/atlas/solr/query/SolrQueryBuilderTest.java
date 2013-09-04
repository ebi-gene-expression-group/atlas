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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SolrQueryBuilderTest {

    private SolrQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new SolrQueryBuilder();
    }

    @Test
    public void testBuildAutocompleteSuggestionQuery() throws Exception {
        // given
        String queryString = subject.withSpecies("species").withBioentityTypes("ensgene")
                .withPropertyNames(new String[]{"prototype1", "prototype2"}).buildAutocompleteSuggestionQuery("geneX");

        // then
        assertThat(queryString, is(SolrQueryService.PROPERTY_EDGENGRAM_FIELD + ":\"geneX\" AND " +
                SolrQueryService.SPECIES_FIELD + ":\"species\" AND (" +
                SolrQueryService.BIOENTITY_TYPE_FIELD + ":\"ensgene\") AND (" +
                SolrQueryService.PROPERTY_NAME_FIELD + ":\"prototype1\" OR " +
                SolrQueryService.PROPERTY_NAME_FIELD + ":\"prototype2\")"));
    }

    @Test
    public void testBuildBioentityQuery() throws Exception {
        // given
        String queryString = subject.withPropertyNames(new String[]{"prototype1", "prototype2"}).buildBioentityQuery("ENSMUS000000");

        // then
        assertThat(queryString, is(SolrQueryService.BIOENTITY_IDENTIFIER_FIELD + ":\"ENSMUS000000\" AND (" +
                SolrQueryService.PROPERTY_NAME_FIELD + ":\"prototype1\" OR " +
                SolrQueryService.PROPERTY_NAME_FIELD + ":\"prototype2\")"));

    }


}
