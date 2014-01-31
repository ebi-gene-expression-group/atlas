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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.streams.baseline.GeneSetProfilesBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneSetProfilesBuilderTest {

    private static final String IDENTIFIER_1 = "IDENTIFIER1";
    private static final String QUERY_TERM = "QUERY_TERM";
    public static final String IDENTIFIER_2 = "IDENTIFIER2";

    @Mock
    private GeneQueryResponse geneQueryResponseMock;

    @Mock
    private BaselineProfile baselineProfileMock1;

    @Mock
    private BaselineProfile baselineProfileMock2;

    @Mock
    private BaselineProfile averageProfileMock;

    @Mock
    private ObjectInputStream<BaselineProfile> inputStreamMock;

    @Mock
    private GeneSetFactory geneSetFactoryMock;

    @Mock
    private GeneSet geneSetMock;

    private GeneSetProfilesBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new GeneSetProfilesBuilder(geneSetFactoryMock);

        when(averageProfileMock.isEmpty()).thenReturn(false);
        when(geneSetFactoryMock.createGeneSet(QUERY_TERM)).thenReturn(geneSetMock);
        when(geneSetMock.getAverageProfile()).thenReturn(averageProfileMock);

        when(geneQueryResponseMock.getQueryTerms()).thenReturn(Sets.newHashSet(QUERY_TERM));
        when(geneQueryResponseMock.getRelatedQueryTerms(IDENTIFIER_1)).thenReturn(Sets.newHashSet(QUERY_TERM));
        when(geneQueryResponseMock.getRelatedQueryTerms(IDENTIFIER_2)).thenReturn(Sets.newHashSet(QUERY_TERM));

        when(baselineProfileMock1.getId()).thenReturn(IDENTIFIER_1);

        when(baselineProfileMock2.getId()).thenReturn(IDENTIFIER_2);

        when(inputStreamMock.readNext()).thenReturn(baselineProfileMock1).thenReturn(baselineProfileMock2).thenReturn(null);
    }


    @Test
    public void testBuild() throws Exception {
        BaselineProfilesList baselineProfilesList = subject.forGeneQueryResponse(geneQueryResponseMock)
                                                           .withInputStream(inputStreamMock).build();
        verify(geneQueryResponseMock).getRelatedQueryTerms(IDENTIFIER_1);
        assertThat(baselineProfilesList.getTotalResultCount(), is(2));

        assertThat(baselineProfilesList.size(), is(1));
        assertThat(baselineProfilesList.get(0), is(averageProfileMock));
    }
}