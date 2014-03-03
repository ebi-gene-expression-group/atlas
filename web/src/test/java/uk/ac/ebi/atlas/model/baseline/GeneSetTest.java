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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneSetTest {

    private static final String GENE_SET_ID = "anId";

    @Mock
    private BaselineProfile averageProfileMock;
    @Mock
    private BaselineProfile baselineProfile1Mock;
    @Mock
    private BaselineProfile baselineProfile2Mock;
    @Mock
    private GeneSetBaselineProfileBuilder geneSetBaselineProfileBuilderMock;

    private GeneSet subject;

    @Before
    public void initSubject() throws Exception {
        when(geneSetBaselineProfileBuilderMock.withGeneName(GENE_SET_ID)).thenReturn(geneSetBaselineProfileBuilderMock);
        when(geneSetBaselineProfileBuilderMock.build()).thenReturn(averageProfileMock);

        subject = new GeneSet(GENE_SET_ID, geneSetBaselineProfileBuilderMock);
    }

    @Test
    public void testGetAverageProfile() throws Exception {
        subject.addBaselineProfile(baselineProfile1Mock);
        subject.addBaselineProfile(baselineProfile2Mock);

        subject.getAverageProfile();

        verify(geneSetBaselineProfileBuilderMock).withGeneName(GENE_SET_ID);
        verify(geneSetBaselineProfileBuilderMock).build();
        verify(averageProfileMock).sumProfile(baselineProfile1Mock);
        verify(averageProfileMock).sumProfile(baselineProfile2Mock);
        verify(averageProfileMock).foldProfile(2);

    }
}
