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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class MultiTermSuggestionServiceIT {

    private static final String HOMO_SAPIENS_SPECIES = "homo sapiens";
    private static final String MUS_MUSCULUS_SPECIES = "mus musculus";

    @Inject
    private MultiTermSuggestionService subject;

    @Test
    public void findGeneNameSuggestionsForPartialGeneNames() {

        List<String> properties = subject.fetchMultiTermSuggestions("MT-AT", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }


    @Test
    public void findSomethingStupidShouldReturnEmpty() {
        List<String> properties = subject.fetchMultiTermSuggestions("prot%", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void apop() {
        List<String> properties = subject.fetchMultiTermSuggestions("apop", null);
        assertThat(properties.size(), is(0));
    }

    @Test
    @Ignore //TODO: fix me!
    public void findGenePropertySuggestionsForPartialQuery() {

        //"mitochondrial enco
        List<String> properties = subject.fetchMultiTermSuggestions("mitochondrial enco", HOMO_SAPIENS_SPECIES);
        assertThat(properties, hasItems("mitochondrially encoded"));

    }

    @Test
    @Ignore //TODO: fix me!
    public void findGenePropertySuggestionsShouldSupportMultiTermQueries() {

        List<String> properties = subject.fetchMultiTermSuggestions("p b", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("protein b", "p binding", "protein binding"));
    }

}
