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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.solr.query.builders.SolrQueryBuilderFactory;
import uk.ac.ebi.atlas.utils.Files;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SolrClientTest {

    private static final String IDENTIFIER = "ENSG00000132604";

    private static final String[] GENE_PAGE_PROPERTY_TYPES = new String[]{"synonym", "ortholog", "goterm", "interproterm", "ensfamily_description", "enstranscript", "mgi_description", "entrezgene", "uniprot", "mgi_id", "gene_biotype", "design_element"};

    private static final String[] TOOLTIP_PROPERTY_TYPES = new String[]{"synonym","goterm","interproterm"};

    private static final String MUS_MUSCULUS = "mus musculus";
    private static final HashSet<String> SPECIES = Sets.newHashSet(MUS_MUSCULUS);

    private static final String SYMBOL = "symbol";

    @Mock
    private SolrQueryService solrQueryServiceMock;

    @Mock
    private BioentityPropertyValueTokenizer bioentityPropertyValueTokenizerMock;

    private SolrClient subject;

    private String jsonAutocompleteResponse;

    @Mock
    private SolrQueryBuilderFactory solrQueryBuilderFactoryMock;

    @Before
    public void initSubject() throws Exception {

        SortedSetMultimap<String,String> mockMultimap = TreeMultimap.create();
        given(solrQueryServiceMock.fetchProperties(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES)).willReturn(mockMultimap);

        given(solrQueryServiceMock.getPropertyValuesForIdentifier(IDENTIFIER, SYMBOL)).willReturn(Lists.newArrayList(SYMBOL));
        given(solrQueryServiceMock.getSpeciesForPropertyValue(IDENTIFIER, SolrQueryService.BIOENTITY_IDENTIFIER_FIELD)).willReturn(SPECIES);

        given(bioentityPropertyValueTokenizerMock.split(IDENTIFIER)).willReturn(Lists.newArrayList(IDENTIFIER));

        jsonAutocompleteResponse = Files.readTextFileFromClasspath(getClass(), "solrAutocompleteResponse.json");

        subject = new SolrClient(TOOLTIP_PROPERTY_TYPES, solrQueryServiceMock, bioentityPropertyValueTokenizerMock, solrQueryBuilderFactoryMock);
    }

    @Test(expected = BioentityNotFoundException.class)
    public void testFetchGenePageProperties() throws Exception {
        subject.fetchGenePageProperties(IDENTIFIER, GENE_PAGE_PROPERTY_TYPES);
    }

    @Test
    public void testFindSpeciesForGeneId() throws Exception {
        assertThat(subject.findSpeciesForBioentityId(IDENTIFIER), is(MUS_MUSCULUS));
    }

    @Test
    public void testFindPropertyValuesForGeneId() throws Exception {
        assertThat(subject.findPropertyValuesForGeneId(IDENTIFIER, SYMBOL), hasItem(SYMBOL));
    }

}
