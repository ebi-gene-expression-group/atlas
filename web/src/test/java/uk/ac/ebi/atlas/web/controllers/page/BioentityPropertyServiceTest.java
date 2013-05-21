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

package uk.ac.ebi.atlas.web.controllers.page;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.utils.UniProtClient;
import uk.ac.ebi.atlas.web.BioentityPageProperties;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BioentityPropertyServiceTest {

    private static final String PROPERTY_TYPES = "symbol,description,synonym,ortholog,goterm,interproterm,ensfamily_description,ensgene,entrezgene,uniprot,mgi_id,gene_biotype,designelement_accession";

    private static final String SYNONYMS = "Synonyms";
    private static final String GENE_ONTOLOGY = "Gene Ontology";
    private static final String IDENTIFIER = "IDENTIFIER";
    private static final String SPECIES = "Mus mus";
    private static final String SYMBOL = "symbol";
    private static final String DESCRIPTION = "description";
    private static final String SYNONYM = "synonym";
    private static final String GOTERM = "goterm";
    private static final String ORTHOLOG = "ortholog";
    private static final String PROTEIN = "ensprotein";
    private static final String GENE = "ensgene";
    private static final String TRANSCRIPT = "enstranscript";

    private BioentityPropertyService subject;

    @Mock
    private UniProtClient uniprotClientMock;

    @Mock
    private BioentityPageProperties bioentityPagePropertiesMock;

    @Mock
    private SolrClient solrClientMock;

    private Multimap<String, String> genePageProperties = HashMultimap.create();

    @Before
    public void setUp() throws Exception {
        genePageProperties.put(SYMBOL, SYMBOL);
        genePageProperties.put(DESCRIPTION, DESCRIPTION);
        genePageProperties.put(SYNONYM, SYNONYM);
        genePageProperties.put(GOTERM, GOTERM);
        genePageProperties.put(ORTHOLOG, ORTHOLOG);
        genePageProperties.put(PROTEIN, PROTEIN);
        genePageProperties.put(GENE, GENE);
        genePageProperties.put(TRANSCRIPT, TRANSCRIPT);

        when(solrClientMock.findSpeciesForGeneId(IDENTIFIER)).thenReturn(SPECIES);
        when(solrClientMock.findPropertyValuesForGeneId(IDENTIFIER, SYMBOL)).thenReturn(Lists.newArrayList(SYMBOL));
        when(solrClientMock.fetchGenePageProperties(IDENTIFIER, PROPERTY_TYPES.split(","))).thenReturn(genePageProperties);


        subject = new BioentityPropertyService(solrClientMock, uniprotClientMock, bioentityPagePropertiesMock);
        subject.init(IDENTIFIER, PROPERTY_TYPES.split(","));
    }

    @Test
    public void testGetSpecies() throws Exception {
        assertThat(subject.getSpecies(), is(SPECIES));
    }

    @Test
    public void testGetPropertyLinks() throws Exception {

    }

    @Test
    public void testTransformOrthologToSymbol() throws Exception {
        assertThat(subject.transformOrthologToSymbol(IDENTIFIER), is("symbol (Mus Mus)"));
    }

    @Test
    public void testCreateLinkProtein() throws Exception {
        when(bioentityPagePropertiesMock.getLinkTemplate(PROTEIN)).thenReturn("http://www.ensembl.org/{0}/Transcript/ProteinSummary?db=core;g={1};t={2}");

        PropertyLink link = subject.createLink(PROTEIN, "prot1", SPECIES);

        assertThat(link.getText(), is("prot1"));
        assertThat(link.getUrl(), is("http://www.ensembl.org/Mus_mus/Transcript/ProteinSummary?db=core;g=" + GENE + ";t=" + TRANSCRIPT));
    }

    @Test
    public void testCreateLinkEmpty() throws Exception {
        when(bioentityPagePropertiesMock.getLinkTemplate("type")).thenReturn("");

        PropertyLink link = subject.createLink("type", "acc", SPECIES);

        assertThat(link.getText(), is("acc"));
        assertThat(link.getUrl(), is(""));
    }

    @Test
    public void testGetLinkText() throws Exception {

    }

    @Test
    public void testGetFirstValueOfProperty() throws Exception {


    }

    @Test
    public void testAddReactomePropertyValues() throws Exception {
        //given
        genePageProperties.put("uniprot", "uniprotId");
        when(uniprotClientMock.fetchReactomeIds("uniprotId")).thenReturn(Lists.newArrayList("r1"));

        //when
        subject.addReactomePropertyValues();

        //then
        List<PropertyLink> reactome = subject.getPropertyLinks("reactome");
        assertThat(reactome.get(0).getText(), is("r1"));
    }
}
