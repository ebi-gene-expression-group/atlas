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
import org.junit.Ignore;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SolrSuggestionsServiceIT {

    private static final String HOMO_SAPIENS_SPECIES = "homo sapiens";
    private static final String MUS_MUSCULUS_SPECIES = "mus musculus";

    @Inject
    private SolrSuggestionsService subject;

    @Test
    public void findGeneNameSuggestionsForPartialGeneNames() {

        List<String> properties = subject.fetchGeneIdSuggestionsInName("mt-at", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(2));
        assertThat(properties, contains("MT-ATP6", "MT-ATP8"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("mt-at", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.fetchMultiTermSuggestions("MT-AT", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneNameSuggestionsForFullGeneNames() {

        List<String> properties = subject.fetchGeneIdSuggestionsInName("mt-atp6", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("MT-ATP6"));

        properties = subject.fetchGeneIdSuggestionsInName("mt-atp8", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("MT-ATP8"));
    }

    @Test
    public void findSuggestionsForProteinCoding() {

        List<String> properties = subject.fetchGeneIdSuggestionsInIdentifier("protein_c", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("protein_coding"));
    }

    @Test
    public void findSuggestionsForGOTerm() {
        //GO:0016021
        List<String> properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:0016", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("GO:0016021", "GO:0016020", "GO:0016032"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:001602", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(5));
        assertThat(properties, hasItems("GO:0016021", "GO:0016020", "GO:0016028"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:0016021", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties, contains("GO:0016021"));

    }

    @Test
    public void findSuggestionsForDesignElement() {
        //Hs2Affx.1.41.S1_3p_s_at
        List<String> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("Hs2Affx.1.41.S1_3p_s_at", "Hs2Affx.1.43.S1_3p_x_at", "Hs2Affx.1.52.S1_3p_at"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx.1.41", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(6));
        assertThat(properties, hasItems("Hs2Affx.1.413.S1_3p_at", "Hs2Affx.1.414.S1_3p_at", "Hs2Affx.1.415.S1_3p_at"));

    }

    @Test
    public void findSomethingStupidShouldReturnEmpty() {

        List<String> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx>", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.fetchGeneIdSuggestionsInName("mt-at$", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.fetchMultiTermSuggestions("prot%", HOMO_SAPIENS_SPECIES);
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
    public void findGeneSynonymSuggestions() {

        List<String> properties = subject.fetchGeneIdSuggestionsInSynonym("atpase-", HOMO_SAPIENS_SPECIES);
        assertThat(properties, contains("ATPase-6"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("mtatp", HOMO_SAPIENS_SPECIES);
        assertThat(properties, contains("MTATP6", "MTATP8"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("su6", HOMO_SAPIENS_SPECIES);
        assertThat(properties, contains("Su6m"));
    }

    @Test
    public void findGeneNameSuggestionsShouldSupportSingleTermQueries() {

        List<String> properties = subject.fetchGeneIdSuggestionsInName("p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("PTK2", "PDE4DIP", "PACRGL"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotContainSpeciesTerms() {

        List<String> properties = subject.fetchGeneIdSuggestionsInName("mus", MUS_MUSCULUS_SPECIES);

        assertThat(properties, containsInAnyOrder("Musk", "Mus81", "Mustn1"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotSupportMultitermQueries() {

        List<String> properties = subject.fetchGeneIdSuggestionsInName("En p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));
    }

    @Test
    @Ignore //TODO: fix me!
    public void findGenePropertySuggestionsShouldSupportMultiTermQueries() {

        List<String> properties = subject.fetchMultiTermSuggestions("p b", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties, hasItems("protein b", "p binding", "protein binding"));
    }

    @Test
    public void testGetSolrResultsForQuery() throws SolrServerException, GenesNotFoundException {

        // given
        List<String> geneNames = subject.fetchGeneIdSuggestionsInName("aspm", "homo sapiens");

        // then
        assertThat(geneNames, contains("ASPM"));

    }

}
