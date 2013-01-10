/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.commands;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;
import uk.ac.ebi.atlas.web.RequestPreferences;

import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WriteGeneProfilesCommandTest {

    @Mock
    private ObjectInputStream<GeneProfile> inputStreamMock;
    @Mock
    private RequestPreferences requestPreferencesMock;
    @Mock
    private CSVWriter csvWriterMock;
    @Mock
    private GeneProfile geneProfileMock1;
    @Mock
    private GeneProfile geneProfileMock2;
    @Mock
    private Experiment experimentMock;

    private WriteGeneProfilesCommand subject;

    @Before
    public void initMocks() {
        SortedSet<String> organismParts = Sets.newTreeSet(Sets.newHashSet("adipose", "brain", "breast", "liver", "lung"));

        when(requestPreferencesMock.getOrganismParts()).thenReturn(organismParts);

        when(inputStreamMock.readNext()).thenReturn(geneProfileMock1)
                .thenReturn(geneProfileMock2)
                .thenReturn(null);

        when(geneProfileMock1.getGeneName()).thenReturn("GN1");
        when(geneProfileMock1.getGeneId()).thenReturn("GI1");
        when(geneProfileMock1.getExpressionLevel("brain")).thenReturn(0.11d);
        when(geneProfileMock1.getExpressionLevel("lung")).thenReturn(9d);

        when(geneProfileMock2.getGeneName()).thenReturn("GN2");
        when(geneProfileMock2.getGeneId()).thenReturn("GI2");
        when(geneProfileMock2.getExpressionLevel("liver")).thenReturn(21.12d);

        when(experimentMock.getDefaultFactorValues()).thenReturn(Sets.newTreeSet(organismParts));
    }

    @Before
    public void initSubject() throws Exception {
        subject = new WriteGeneProfilesCommand(new NumberUtils());
        subject.setRequestPreferences(requestPreferencesMock);
        subject.setCsvWriter(csvWriterMock);
    }

    @Test
    public void applyShouldUseCsvWriter() throws Exception {

        long count = subject.apply(requestPreferencesMock, experimentMock, inputStreamMock);

        verify(csvWriterMock).writeNext(new String[]{"Gene name", "Gene Id", "adipose", "brain", "breast", "liver", "lung"});

        verify(csvWriterMock).writeNext(new String[]{"GN1", "GI1", "0", "0.11", "0", "0", "9"});

        verify(csvWriterMock).writeNext(new String[]{"GN2", "GI2", "0", "0", "0", "21.12", "0"});

        assertThat(count, is(2L));

    }

}
