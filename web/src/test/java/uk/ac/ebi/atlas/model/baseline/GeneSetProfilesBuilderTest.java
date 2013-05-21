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
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.geneindex.GeneQueryResponse;

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
    private BaselineRequestContext baselineRequestContextMock;

    @Mock
    private GeneQueryResponse geneQueryResponseMock;

    @Mock
    private BaselineProfile baselineProfileMock1;

    @Mock
    private BaselineProfile baselineProfileMock2;

    private GeneSetProfilesBuilder subject;

    @Before
    public void setUp() throws Exception {
        subject = new GeneSetProfilesBuilder(baselineRequestContextMock);
        when(baselineRequestContextMock.getGeneQueryResponse()).thenReturn(geneQueryResponseMock);
        when(geneQueryResponseMock.getRelatedTerms(IDENTIFIER_1)).thenReturn(Sets.newHashSet(QUERY_TERM));
        when(geneQueryResponseMock.getRelatedTerms(IDENTIFIER_2)).thenReturn(Sets.newHashSet(QUERY_TERM));
        when(baselineProfileMock1.getId()).thenReturn(IDENTIFIER_1);
        when(baselineProfileMock2.getId()).thenReturn(IDENTIFIER_2);
    }

    @Test
    public void testSumProfile() throws Exception {
        assertThat(subject.sumProfile(baselineProfileMock1), is(subject));

        verify(baselineProfileMock1).getId();
        verify(baselineRequestContextMock).getGeneQueryResponse();
        verify(geneQueryResponseMock).getRelatedTerms(IDENTIFIER_1);
    }

    @Test
    public void testBuild() throws Exception {
        BaselineProfilesList baselineProfilesList = subject.sumProfile(baselineProfileMock1).sumProfile(baselineProfileMock2).build();
        assertThat(baselineProfilesList.size(), is(1));
        assertThat(baselineProfilesList.getTotalResultCount(), is(2));

        BaselineProfile baselineProfile = baselineProfilesList.get(0);
        assertThat(baselineProfile.getMinExpressionLevel(), is(Double.MAX_VALUE));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(0D));
    }
}