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

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class SolrSuggestionsServiceIT {

    private static final String HOMO_SAPIENS_SPECIES = "homo sapiens";
    private static final String MUS_MUSCULUS_SPECIES = "mus musculus";

    @Inject
    private SolrSuggestionsService subject;

    @Test
    public void findGeneNameSuggestionsForPartialGeneNames() {

        List<String> properties = subject.findGeneIdSuggestionsInName("mt-at", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(2));
        assertThat(properties, contains("mt-atp6", "mt-atp8"));

        properties = subject.findGeneIdSuggestionsInIdentifier("mt-at", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.findGenePropertySuggestions("mt-at", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneNameSuggestionsForFullGeneNames() {

        List<String> properties = subject.findGeneIdSuggestionsInName("mt-atp6", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("mt-atp6"));

        properties = subject.findGeneIdSuggestionsInName("mt-atp8", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("mt-atp8"));
    }

    @Test
    public void findSuggestionsForProteinCoding() {

        List<String> properties = subject.findGeneIdSuggestionsInIdentifier("protein_c", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("protein_coding"));
    }

    @Test
    public void findSuggestionsForGOTerm() {
        //GO:0016021
        List<String> properties = subject.findGeneIdSuggestionsInIdentifier("GO:0016", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("go:0016021", "go:0016020", "go:0016032"));

        properties = subject.findGeneIdSuggestionsInIdentifier("GO:001602", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(5));
        assertThat(properties, hasItems("go:0016021", "go:0016020", "go:0016028"));

        properties = subject.findGeneIdSuggestionsInIdentifier("GO:0016021", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("go:0016021"));

    }

    @Test
    public void findSuggestionsForDesignElement() {
        //Hs2Affx.1.41.S1_3p_s_at
        List<String> properties = subject.findGeneIdSuggestionsInIdentifier("Hs2Affx", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("hs2affx.1.41.s1_3p_s_at", "hs2affx.1.43.s1_3p_x_at", "hs2affx.1.52.s1_3p_at"));

        properties = subject.findGeneIdSuggestionsInIdentifier("Hs2Affx.1.41", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(6));
        assertThat(properties, hasItems("hs2affx.1.41.s1_3p_s_at", "hs2affx.1.413.s1_3p_at", "hs2affx.1.414.s1_3p_at"));

        properties = subject.findGeneIdSuggestionsInIdentifier("Hs2Affx.1.41.S1_3p_s", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("hs2affx.1.41.s1_3p_s_at"));

    }

    @Test
    public void findSomethingStupidShouldReturnEmpty() {

        List<String> properties = subject.findGeneIdSuggestionsInIdentifier("Hs2Affx>", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.findGeneIdSuggestionsInName("mt-at$", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.findGenePropertySuggestions("prot%", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGenePropertySuggestionsForPartialQuery() {

        //"mitochondrial enco
        List<String> properties = subject.findGenePropertySuggestions("mitochondrial enco", HOMO_SAPIENS_SPECIES);
        assertThat(properties, hasItems("mitochondrially encoded"));

    }

    @Test
    public void findGeneSynonymSuggestions() {

        List<String> properties = subject.findGeneIdSuggestionsInSynonym("atpase-", HOMO_SAPIENS_SPECIES);
        assertThat(properties, contains("atpase-6"));

        properties = subject.findGeneIdSuggestionsInSynonym("mtatp", HOMO_SAPIENS_SPECIES);
        assertThat(properties, contains("mtatp6", "mtatp8"));

        properties = subject.findGeneIdSuggestionsInSynonym("su6", HOMO_SAPIENS_SPECIES);
        assertThat(properties, contains("su6m"));
    }

    @Test
    public void findGeneNameSuggestionsShouldSupportSingleTermQueries() {

        List<String> properties = subject.findGeneIdSuggestionsInName("p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("ppt2", "pbx2", "prpf31"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotContainSpeciesTerms() {

        List<String> properties = subject.findGeneIdSuggestionsInName("mus", MUS_MUSCULUS_SPECIES);

        assertThat(properties, containsInAnyOrder("musk", "mus81", "mustn1"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotSupportMultitermQueries() {

        List<String> properties = subject.findGeneIdSuggestionsInName("En p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGenePropertySuggestionsShouldSupportMultiTermQueries() {

        List<String> properties = subject.findGenePropertySuggestions("p b", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("protein b", "p binding", "protein binding"));
    }

    @Test
    public void testGetSolrResultsForQuery() throws SolrServerException, GenesNotFoundException {

        // given
        List<String> geneNames = subject.getGeneIdSuggestionsInName("aspm", "homo sapiens");

        // then
        assertThat(geneNames, contains("aspm"));

    }

}
