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

package uk.ac.ebi.atlas.solr.query.conditions;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.solr.query.BioentityPropertyValueTokenizer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConditionsSolrQueryBuilderTest {

    private ConditionsSolrQueryBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new ConditionsSolrQueryBuilder(new BioentityPropertyValueTokenizer());
    }

    @Test
    public void singleTerm() throws Exception {
        String query = subject.buildQueryString("liver");
        assertThat(query, is("conditions_search:liver"));
    }

    @Test
    public void quotedTerm() throws Exception {
        String query = subject.buildQueryString("\"liver cancer\"");
        assertThat(query, is("conditions_search:\"liver cancer\""));
    }

    @Test
    public void multipleTerms() {
        String query = subject.buildQueryString("liver heart");
        assertThat(query, is("conditions_search:liver OR conditions_search:heart"));
    }

    @Test
    public void multipleTermsWithQuotes() {
        String query = subject.buildQueryString("\"wild type\" adipose");
        assertThat(query, is("conditions_search:\"wild type\" OR conditions_search:adipose"));
    }

    @Test
    public void multipleANDTerms() {
        String query = subject.buildQueryString("liver AND heart");
        assertThat(query, is("conditions_search:liver AND conditions_search:heart"));
    }

    @Test
    public void multipleANDTermsWithQuotes() {
        String query = subject.buildQueryString("\"Homo sapiens\" AND heart");
        assertThat(query, is("conditions_search:\"Homo sapiens\" AND conditions_search:heart"));
    }
}