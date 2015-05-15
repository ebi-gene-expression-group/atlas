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
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class GeneIdSuggestionServiceIT {

    private static final String HOMO_SAPIENS_SPECIES = "homo sapiens";
    private static final String MUS_MUSCULUS_SPECIES = "mus musculus";

    @Inject
    private GeneIdSuggestionService subject;

    @Test
    public void findGeneNameSuggestionsForPartialGeneNames() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInName("mt-at", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(2));
        assertThat(properties.get(0).term, is("MT-ATP6"));
        assertThat(properties.get(0).source, is("symbol"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("mt-at", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneNameSuggestionsForFullGeneNames() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInName("mt-atp6", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).term, is("MT-ATP6"));
        assertThat(properties.get(0).source, is("symbol"));

        properties = subject.fetchGeneIdSuggestionsInName("mt-atp8", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).term, is("MT-ATP8"));
        assertThat(properties.get(0).source, is("symbol"));

    }

    @Test
    public void findSuggestionsForProteinCoding() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInIdentifier("protein_c", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).term, is("protein_coding"));
        assertThat(properties.get(0).source, is("gene_biotype"));
    }

    @Test
    public void findSuggestionsForGOTerm() {
        //GO:0016021
        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:0016", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(7).term, is("GO:0016568"));
        assertThat(properties.get(7).source, is("go"));
        assertThat(properties.get(5).term, is("GO:0016446"));
        assertThat(properties.get(5).source, is("go"));
        assertThat(properties.get(10).term, is("GO:0016888"));
        assertThat(properties.get(10).source, is("go"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:001602", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(5));
        assertThat(properties.get(1).term, is("GO:0016020"));
        assertThat(properties.get(1).source, is("go"));
        assertThat(properties.get(0).term, is("GO:0016021"));
        assertThat(properties.get(0).source, is("go"));
        assertThat(properties.get(4).term, is("GO:0016028"));
        assertThat(properties.get(4).source, is("go"));
    }

    @Test
    public void findSuggestionsForDesignElement() {
        //Hs2Affx.1.41.S1_3p_s_at
        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(0).term, is("Hs2Affx.1.318.S1_3p_at"));
        assertThat(properties.get(0).source, is("design_element"));
        assertThat(properties.get(4).term, is("Hs2Affx.1.193.S1_3p_at"));
        assertThat(properties.get(4).source, is("design_element"));
        assertThat(properties.get(14).term, is("Hs2Affx.1.207.S1_3p_at"));
        assertThat(properties.get(14).source, is("design_element"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx.1.41", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(7));
        assertThat(properties.get(0).term, is("Hs2Affx.1.419.S1_3p_at"));
        assertThat(properties.get(0).source, is("design_element"));
    }

    @Test
    public void findSomethingStupidShouldReturnEmpty() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx>", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.fetchGeneIdSuggestionsInName("mt-at$", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneSynonymSuggestions() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInSynonym("atpase-", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).term, is("ATPase-6"));
        assertThat(properties.get(0).source, is("synonym"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("mtatp", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(2));
        assertThat(properties.get(0).term, is("MTATP6"));
        assertThat(properties.get(0).source, is("synonym"));
        assertThat(properties.get(1).term, is("MTATP8"));
        assertThat(properties.get(1).source, is("synonym"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("su6", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).term, is("Su6m"));
        assertThat(properties.get(0).source, is("synonym"));

    }

    @Test
    public void findGeneNameSuggestionsShouldSupportSingleTermQueries() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInName("p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(0).term, is("PHF19"));
        assertThat(properties.get(0).source, is("symbol"));
        assertThat(properties.get(1).term, is("PTPN13"));
        assertThat(properties.get(1).source, is("symbol"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotContainSpeciesTerms() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInName("mus", MUS_MUSCULUS_SPECIES);

        assertThat(properties.size(), is(3));

        assertThat(properties.get(0).term, is("Musk"));
        assertThat(properties.get(0).source, is("symbol"));
        assertThat(properties.get(1).term, is("Mus81"));
        assertThat(properties.get(1).source, is("symbol"));
        assertThat(properties.get(2).term, is("Mustn1"));
        assertThat(properties.get(2).source, is("symbol"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotSupportMultitermQueries() {

        List<TermSourceSuggestion> properties = subject.fetchGeneIdSuggestionsInName("En p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));
    }

    @Test
    public void testGetSolrResultsForQuery() throws SolrServerException, GenesNotFoundException {
        // given
        List<TermSourceSuggestion> geneNames = subject.fetchGeneIdSuggestionsInName("aspm", "homo sapiens");

        assertThat(geneNames.get(0).term, is("ASPM"));
        assertThat(geneNames.get(0).source, is("symbol"));

    }

}
