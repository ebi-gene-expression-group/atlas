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
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.utils.Files;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    private static final List<String> GENE_PAGE_PROPERTY_TYPES = Lists.newArrayList("synonym", "ortholog", "goterm", "interproterm", "ensfamily_description", "enstranscript", "mgi_description", "entrezgene", "uniprot", "mgi_id", "gene_biotype", "design_element");

    private static final String CSV_TOOLTIP_PROPERTY_TYPES = "synonym,goterm,interproterm";

    private static final List<String> TOOLTIP_PROPERTY_TYPES = Arrays.asList(CSV_TOOLTIP_PROPERTY_TYPES.split(","));

    private static final String EXPECTED_TOOLTIP_QUERY = SolrQueryService.IDENTIFIER_FIELD + ":\"ENSG00000132604\" AND (" + SolrQueryService.PROPERTY_TYPE_FIELD + ":\"synonym\" OR " + SolrQueryService.PROPERTY_TYPE_FIELD + ":\"goterm\" OR " + SolrQueryService.PROPERTY_TYPE_FIELD + ":\"interproterm\")";

    private static final String MUS_MUSCULUS = "mus musculus";
    private static final HashSet<String> SPECIES = Sets.newHashSet(MUS_MUSCULUS);

    private static final String SYMBOL = "symbol";

    private static final String GENE_QUERY = "A QUERY";

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private SolrQueryService solrQueryServiceMock;

    @Mock
    private GeneQueryTokenizer geneQueryTokenizerMock;

    private SolrClient subject;

    private SortedSetMultimap<String, String> results = TreeMultimap.create();

    private String jsonAutocompleteResponse;

    @Before
    public void initSubject() throws Exception {

        doCallRealMethod().when(solrQueryServiceMock).fetchProperties(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES);
        doCallRealMethod().when(solrQueryServiceMock).fetchProperties(IDENTIFIER, TOOLTIP_PROPERTY_TYPES);
        doCallRealMethod().when(solrQueryServiceMock).buildCompositeQueryIdentifier(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES);
        doCallRealMethod().when(solrQueryServiceMock).buildCompositeQueryIdentifier(IDENTIFIER, TOOLTIP_PROPERTY_TYPES);

        when(solrQueryServiceMock.querySolrForProperties(anyString(), anyInt())).thenReturn(results);
//        when(solrQueryServiceMock.getSpeciesForIdentifier(IDENTIFIER)).thenReturn(SPECIES);
        when(solrQueryServiceMock.getPropertyValuesForIdentifier(IDENTIFIER, SYMBOL)).thenReturn(Lists.newArrayList(SYMBOL));
        when(solrQueryServiceMock.getSpeciesForPropertyValue(IDENTIFIER, SolrQueryService.IDENTIFIER_FIELD)).thenReturn(SPECIES);

        when(geneQueryTokenizerMock.split(IDENTIFIER)).thenReturn(Lists.newArrayList(IDENTIFIER));

        jsonAutocompleteResponse = Files.readTextFileFromClasspath(getClass(), "solrAutocompleteResponse.json");
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

    @Test(expected = ResultNotFoundException.class)
    public void testFetchGenePageProperties() throws Exception {
        subject.fetchGenePageProperties(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES);
    }

    @Test
    public void testFetchTooltipProperties() throws Exception {
        subject.setTooltipPropertyTypes(CSV_TOOLTIP_PROPERTY_TYPES);

        SortedSetMultimap<String, String> multimap = subject.fetchTooltipProperties(IDENTIFIER);
        assertThat(multimap, is(results));

        verify(solrQueryServiceMock).querySolrForProperties(EXPECTED_TOOLTIP_QUERY, 1000);
    }

    @Test
    public void testFindSpeciesForGeneId() throws Exception {
        assertThat(subject.findSpeciesForGeneId(IDENTIFIER), containsInAnyOrder(MUS_MUSCULUS));
    }

    @Test
    public void testFindPropertyValuesForGeneId() throws Exception {
        assertThat(subject.findPropertyValuesForGeneId(IDENTIFIER, SYMBOL), hasItem(SYMBOL));
    }

    @Test
    public void testGetSelectedGeneIdsPerQueryToken() throws GenesNotFoundException, SolrServerException {

        when(solrQueryServiceMock.getGeneIds("A", false, MUS_MUSCULUS)).thenReturn(Sets.newHashSet(IDENTIFIER));
        when(solrQueryServiceMock.getGeneIds("QUERY", false, MUS_MUSCULUS)).thenReturn(Sets.newHashSet(IDENTIFIER));

        when(geneQueryTokenizerMock.split(GENE_QUERY)).thenReturn(Lists.newArrayList("A", "QUERY"));

        GeneQueryResponse geneQueryResponse = subject.findGeneSets(GENE_QUERY, false, MUS_MUSCULUS, true);

        verify(geneQueryTokenizerMock).split("A QUERY");

        verify(solrQueryServiceMock).getGeneIds("A", false, MUS_MUSCULUS);
        verify(solrQueryServiceMock).getGeneIds("QUERY", false, MUS_MUSCULUS);

        assertThat(geneQueryResponse.getQueryTerms(), containsInAnyOrder("A", "QUERY"));
    }


}
