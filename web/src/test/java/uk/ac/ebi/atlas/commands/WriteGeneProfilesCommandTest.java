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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.GeneProfile;
import uk.ac.ebi.atlas.utils.NumberUtils;

import java.io.PrintWriter;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WriteGeneProfilesCommandTest {

    @Mock
    private ObjectInputStream<GeneProfile> inputStreamMock;
    @Mock
    private SessionContext sessionContextMock;
    @Mock
    private PrintWriter printWriterMock;
    @Mock
    private GeneProfile geneProfileMock1;
    @Mock
    private GeneProfile geneProfileMock2;
    @Mock
    private Experiment experimentMock;
    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    private WriteGeneProfilesCommand subject;

    private SortedSet<Factor> organismParts;

    @Before
    public void initMocks() {
        organismParts = Sets.newTreeSet(Sets.newHashSet(
                createFactorValue("adipose"),
                createFactorValue("brain"),
                createFactorValue("breast"),
                createFactorValue("liver"),
                createFactorValue("lung")));

        when(sessionContextMock.getSelectedQueryFactors()).thenReturn(organismParts);

        when(inputStreamMock.readNext()).thenReturn(geneProfileMock1)
                .thenReturn(geneProfileMock2)
                .thenReturn(null);

        when(geneProfileMock1.getGeneName()).thenReturn("GN1");
        when(geneProfileMock1.getGeneId()).thenReturn("GI1");
        when(geneProfileMock1.getExpressionLevel(createFactorValue("brain"))).thenReturn(0.11d);
        when(geneProfileMock1.getExpressionLevel(createFactorValue("lung"))).thenReturn(9d);

        when(geneProfileMock2.getGeneName()).thenReturn("GN2");
        when(geneProfileMock2.getGeneId()).thenReturn("GI2");
        when(geneProfileMock2.getExpressionLevel(createFactorValue("liver"))).thenReturn(21.12d);

        when(experimentalFactorsMock.getFactorsByType(anyString())).thenReturn(Sets.newTreeSet(organismParts));
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
    }

    @Before
    public void initSubject() throws Exception {
        subject = new WriteGeneProfilesCommand(new NumberUtils());
        subject.setFilteredParameters(sessionContextMock);
        subject.setResponseWriter(printWriterMock);
    }

    @Test
    public void applyShouldUseCsvWriter() throws Exception {

        long count = subject.apply(organismParts, null ,inputStreamMock);

        verify(printWriterMock).write("Gene name\tGene Id\tadipose\tbrain\tbreast\tliver\tlung\n", 0, 50);

        verify(printWriterMock).write("GN1\tGI1\t0\t0.11\t0\t0\t9\n", 0, 21);

        verify(printWriterMock).write("GN2\tGI2\t0\t0\t0\t21.12\t0\n", 0, 22);

        assertThat(count, is(2L));

    }

    @Test
    public void buildCsvHeadersTest() {
        String[] headers = subject.buildCsvHeaders(Sets.newTreeSet(Lists.newArrayList("adipose", "brain")));
        assertThat(headers, is(new String[]{"Gene name", "Gene Id", "adipose", "brain"}));
    }

    @Test
    public void buildCsvRowTest() {
        String[] headers = subject.buildCsvRow(new String[]{"A", "B"}, new String[]{"C", "D"});
        assertThat(headers, is(new String[]{"A", "B", "C", "D"}));
    }

    private Factor createFactorValue(String value) {
        return new Factor("ORG", null, value);
    }


}
