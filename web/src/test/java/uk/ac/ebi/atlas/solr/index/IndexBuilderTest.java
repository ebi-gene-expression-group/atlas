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

package uk.ac.ebi.atlas.solr.index;

import org.apache.solr.client.solrj.SolrServer;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IndexBuilderTest {

    @Mock
    private SolrServer solrServerMock;

    @Mock
    private BioentityPropertyStreamBuilder bioentityPropertyStreamBuilderMock;

    @Mock
    private BioentityPropertyStream bioentityPropertyStreamMock;

    private IndexBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new IndexBuilder(solrServerMock, bioentityPropertyStreamBuilderMock);
    }
/*
    @Test
    public void testBuild() throws Exception {
        given(propertyStreamMock.next()).willReturn(mock(PropertyDocument.class))
                                        .willReturn(mock(PropertyDocument.class))
                                        .willReturn(null);
        subject.build();

        verify(solrServerMock, times(2)).addBean(any(PropertyDocument.class));
        verify(propertyStreamMock, times(3)).next();
    }*/
}
