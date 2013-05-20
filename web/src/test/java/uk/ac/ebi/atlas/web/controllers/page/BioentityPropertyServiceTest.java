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

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneindex.SolrClient;
import uk.ac.ebi.atlas.utils.UniProtClient;
import uk.ac.ebi.atlas.web.BioentityPageProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BioentityPropertyServiceTest {

    private static final String IDENTIFIER = "IDENTIFIER";
    private static final String SPECIES = "SPECIES";
    private static final String SYMBOL = "symbol";

    private BioentityPropertyService subject;

    @Mock
    private UniProtClient uniprotClientMock;

    @Mock
    private BioentityPageProperties bioentityPageProperties;

    @Mock
    private SolrClient solrClientMock;


    @Before
    public void setUp() throws Exception {
        when(solrClientMock.findSpeciesForGeneId(IDENTIFIER)).thenReturn(SPECIES);
        when(solrClientMock.findPropertyValuesForGeneId(IDENTIFIER, SYMBOL)).thenReturn(Lists.newArrayList(SYMBOL));

        subject = new BioentityPropertyService(solrClientMock, uniprotClientMock, bioentityPageProperties);
    }

    @Test
    public void testGetSpecies() throws Exception {

    }

    @Test
    public void testGetPropertyLinks() throws Exception {

    }

    @Test
    public void testTransformOrthologToSymbol() throws Exception {
        assertThat(subject.transformOrthologToSymbol(IDENTIFIER), is("symbol (SPECIES)"));

    }

    @Test
    public void testCreateLink() throws Exception {

    }

    @Test
    public void testGetLinkText() throws Exception {

    }

    @Test
    public void testGetEncodedString() throws Exception {

    }

    @Test
    public void testGetFirstValueOfProperty() throws Exception {

    }

    @Test
    public void testAddReactomePropertyValues() throws Exception {

    }
}
