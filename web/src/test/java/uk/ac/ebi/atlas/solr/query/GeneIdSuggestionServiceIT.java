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
import uk.ac.ebi.atlas.web.SemanticQueryTerm;

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

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("mt-at", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(2));
        assertThat(properties.get(0).value(), is("MT-ATP8"));
        assertThat(properties.get(0).source(), is("symbol"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("mt-at", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneNameSuggestionsForFullGeneNames() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("mt-atp6", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("MT-ATP6"));
        assertThat(properties.get(0).source(), is("symbol"));

        properties = subject.fetchGeneIdSuggestionsInName("mt-atp8", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("MT-ATP8"));
        assertThat(properties.get(0).source(), is("symbol"));

    }

    @Test
    public void findSuggestionsForProteinCoding() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("protein_c", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("protein_coding"));
        assertThat(properties.get(0).source(), is("gene_biotype"));
    }

    @Test
    public void findSuggestionsForGOTerm() {
        //GO:0016021
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:0016", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(7).value(), is("GO:0016323"));
        assertThat(properties.get(7).source(), is("go"));
        assertThat(properties.get(5).value(), is("GO:0016324"));
        assertThat(properties.get(5).source(), is("go"));
        assertThat(properties.get(10).value(), is("GO:0016884"));
        assertThat(properties.get(10).source(), is("go"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("GO:001602", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(5));
        assertThat(properties.get(1).value(), is("GO:0016021"));
        assertThat(properties.get(1).source(), is("go"));
        assertThat(properties.get(0).value(), is("GO:0016020"));
        assertThat(properties.get(0).source(), is("go"));
        assertThat(properties.get(4).value(), is("GO:0016028"));
        assertThat(properties.get(4).source(), is("go"));
    }

    @Test
    public void findSuggestionsForDesignElement() {
        //Hs2Affx.1.41.S1_3p_s_at
        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(0).value(), is("Hs2Affx.1.51.S1_3p_at"));
        assertThat(properties.get(0).source(), is("design_element"));
        assertThat(properties.get(4).value(), is("Hs2Affx.1.309.S1_3p_at"));
        assertThat(properties.get(4).source(), is("design_element"));
        assertThat(properties.get(14).value(), is("Hs2Affx.1.6.S1_3p_s_at"));
        assertThat(properties.get(14).source(), is("design_element"));

        properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx.1.41", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(7));
        assertThat(properties.get(0).value(), is("Hs2Affx.1.411.S1_3p_at"));
        assertThat(properties.get(0).source(), is("design_element"));
    }

    @Test
    public void findSomethingStupidShouldReturnEmpty() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInIdentifier("Hs2Affx>", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));

        properties = subject.fetchGeneIdSuggestionsInName("mt-at$", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(0));
    }

    @Test
    public void findGeneSynonymSuggestions() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInSynonym("atpase-", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("ATPase-6"));
        assertThat(properties.get(0).source(), is("synonym"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("mtatp", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(2));
        assertThat(properties.get(0).value(), is("MTATP8"));
        assertThat(properties.get(0).source(), is("synonym"));
        assertThat(properties.get(1).value(), is("MTATP6"));
        assertThat(properties.get(1).source(), is("synonym"));

        properties = subject.fetchGeneIdSuggestionsInSynonym("su6", HOMO_SAPIENS_SPECIES);
        assertThat(properties.size(), is(1));
        assertThat(properties.get(0).value(), is("Su6m"));
        assertThat(properties.get(0).source(), is("synonym"));

    }

    @Test
    public void findGeneNameSuggestionsShouldSupportSingleTermQueries() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(15));
        assertThat(properties.get(0).value(), is("PTRH2"));
        assertThat(properties.get(0).source(), is("symbol"));
        assertThat(properties.get(1).value(), is("PSMD8P1"));
        assertThat(properties.get(1).source(), is("symbol"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotContainSpeciesTerms() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("mus", MUS_MUSCULUS_SPECIES);

        assertThat(properties.size(), is(3));

        assertThat(properties.get(0).value(), is("Mustn1"));
        assertThat(properties.get(0).source(), is("symbol"));
        assertThat(properties.get(1).value(), is("Musk"));
        assertThat(properties.get(1).source(), is("symbol"));
        assertThat(properties.get(2).value(), is("Mus81"));
        assertThat(properties.get(2).source(), is("symbol"));
    }

    @Test
    public void findGeneNameSuggestionsShouldNotSupportMultitermQueries() {

        List<SemanticQueryTerm> properties = subject.fetchGeneIdSuggestionsInName("En p", HOMO_SAPIENS_SPECIES);

        assertThat(properties.size(), is(0));
    }

    @Test
    public void testGetSolrResultsForQuery() throws SolrServerException, GenesNotFoundException {
        // given
        List<SemanticQueryTerm> geneNames = subject.fetchGeneIdSuggestionsInName("aspm", "homo sapiens");

        assertThat(geneNames.get(0).value(), is("ASPM"));
        assertThat(geneNames.get(0).source(), is("symbol"));

    }

}
