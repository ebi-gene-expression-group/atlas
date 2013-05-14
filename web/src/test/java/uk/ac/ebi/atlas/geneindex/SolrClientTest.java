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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.utils.Files;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SolrClientTest {

    private static final String IDENTIFIER = "ENSG00000132604";

    private static final String GENE_PAGE_PROPERTY_TYPES = "synonym,ortholog,goterm,interproterm,ensfamily_description,enstranscript,mgi_description,entrezgene,uniprot,mgi_id,gene_biotype,designelement_accession";

    private static final String[] GENE_PAGE_PROPERTY_TYPES_ARRAY = GENE_PAGE_PROPERTY_TYPES.split(",");

    private static final String EXPECTED_GENE_PAGE_QUERY = "identifier:\"ENSG00000132604\" AND (property_type:\"synonym\" OR property_type:\"ortholog\" OR property_type:\"goterm\" OR property_type:\"interproterm\" OR property_type:\"ensfamily_description\" OR property_type:\"enstranscript\" OR property_type:\"mgi_description\" OR property_type:\"entrezgene\" OR property_type:\"uniprot\" OR property_type:\"mgi_id\" OR property_type:\"gene_biotype\" OR property_type:\"designelement_accession\")";

    private static final String TOOLTIP_PROPERTY_TYPES = "synonym,goterm,interproterm";

    private static final String[] TOOLTIP_PROPERTY_TYPES_ARRAY = TOOLTIP_PROPERTY_TYPES.split(",");

    private static final String EXPECTED_TOOLTIP_QUERY = "identifier:\"ENSG00000132604\" AND (property_type:\"synonym\" OR property_type:\"goterm\" OR property_type:\"interproterm\")";

    private static final String SPECIES = "mus musculus";

    private static final String SYMBOL = "symbol";

    private static final String GENE_QUERY = "A QUERY";

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private SolrQueryService solrQueryServiceMock;

    @Mock
    private GeneQueryTokenizer geneQueryTokenizerMock;

    private SolrClient subject;

    private Multimap<String, String> results = HashMultimap.create();

    private String jsonAutocompleteResponse;

    @Before
    public void initSubject() throws Exception {

        doCallRealMethod().when(solrQueryServiceMock).fetchProperties(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES_ARRAY);
        doCallRealMethod().when(solrQueryServiceMock).fetchProperties(IDENTIFIER, TOOLTIP_PROPERTY_TYPES_ARRAY);
        doCallRealMethod().when(solrQueryServiceMock).buildCompositeQueryIdentifier(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES_ARRAY);
        doCallRealMethod().when(solrQueryServiceMock).buildCompositeQueryIdentifier(IDENTIFIER, TOOLTIP_PROPERTY_TYPES_ARRAY);

        when(solrQueryServiceMock.querySolrForProperties(anyString(), anyInt())).thenReturn(results);
        when(solrQueryServiceMock.getSpeciesForIdentifier(IDENTIFIER)).thenReturn(SPECIES);
        when(solrQueryServiceMock.getPropertyValuesForIdentifier(IDENTIFIER, SYMBOL)).thenReturn(Lists.newArrayList(SYMBOL));

        jsonAutocompleteResponse = Files.readTextFileFromClasspath(this.getClass(), "solrAutocompleteResponse.json");
        when(restTemplateMock.getForObject(anyString(), any(Class.class), anyVararg())).thenReturn(jsonAutocompleteResponse);

        subject = new SolrClient(restTemplateMock, solrQueryServiceMock, geneQueryTokenizerMock);
    }

    @Test
    public void testExtractSuggestion() {
        //given
        String suggestion = subject.extractSuggestion("\"musk\" AND species:\"mus musculus\"");

        assertThat(suggestion, is("musk"));
    }

    @Test
    public void findGenePropertiesShouldReturnEmptyListWhenTermContainsNonSpellCheckableCharacters() {
        assertThat(subject.findGenePropertySuggestions("hello > boy", "mus mus"), is(empty()));
    }

    @Test
    public void findGenePropertiesShouldNotReturnEmptyList() {
        assertThat(subject.findGenePropertySuggestions("p53", "mus mus"), is(not(empty())));
    }

    @Test
    public void testFetchGenePageProperties() throws Exception {
        Multimap<String, String> multimap = subject.fetchGenePageProperties(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES.split(","));
        assertThat(multimap, is(results));

        verify(solrQueryServiceMock).querySolrForProperties(EXPECTED_GENE_PAGE_QUERY, 1000);
    }

    @Test
    public void testFetchTooltipProperties() throws Exception {
        subject.setTooltipPropertyTypes(TOOLTIP_PROPERTY_TYPES);

        Multimap<String, String> multimap = subject.fetchTooltipProperties(IDENTIFIER);
        assertThat(multimap, is(results));

        verify(solrQueryServiceMock).querySolrForProperties(EXPECTED_TOOLTIP_QUERY, 1000);
    }

    @Test
    public void testFindSpeciesForGeneId() throws Exception {
        assertThat(subject.findSpeciesForGeneId(IDENTIFIER), is(SPECIES));
    }

    @Test
    public void testFindPropertyValuesForGeneId() throws Exception {
        assertThat(subject.findPropertyValuesForGeneId(IDENTIFIER, SYMBOL), hasItem(SYMBOL));
    }

    @Test
    public void testGetSelectedGeneIdsPerQueryToken() throws GenesNotFoundException, SolrServerException {

        when(solrQueryServiceMock.getGeneIds("A", false, SPECIES)).thenReturn(Sets.newHashSet(IDENTIFIER));
        when(solrQueryServiceMock.getGeneIds("QUERY", false, SPECIES)).thenReturn(Sets.newHashSet(IDENTIFIER));

        when(geneQueryTokenizerMock.split(GENE_QUERY)).thenReturn(Lists.newArrayList("A", "QUERY"));

        Multimap<String, String> geneIds = subject.getGeneSets(GENE_QUERY, false, SPECIES);

        verify(geneQueryTokenizerMock).split("A QUERY");

        verify(solrQueryServiceMock).getGeneIds("A", false, SPECIES);
        verify(solrQueryServiceMock).getGeneIds("QUERY", false, SPECIES);

        assertThat(geneIds.keySet(), containsInAnyOrder("A", "QUERY"));
    }


}
