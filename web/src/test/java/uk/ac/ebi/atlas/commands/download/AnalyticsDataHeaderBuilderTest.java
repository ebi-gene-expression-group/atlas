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

package uk.ac.ebi.atlas.commands.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnalyticsDataHeaderBuilderTest {

    @Mock
    private DifferentialExperiment experimentMock;

    private AnalyticsDataHeaderBuilder subject;

    private final static String[] HEADER = {"Gene ID", "Gene Name", "c1.p-value", "c1.t-stat", "c2.p-value", "c2.t-stat"};

    @Before
    public void initSubject() throws Exception {
        Contrast contrastMock1 = mock(Contrast.class);
        Contrast contrastMock2 = mock(Contrast.class);

        when(contrastMock1.getDisplayName()).thenReturn("contrast1");
        when(contrastMock2.getDisplayName()).thenReturn("contrast2");

        when(experimentMock.getContrast("c1")).thenReturn(contrastMock1);
        when(experimentMock.getContrast("c2")).thenReturn(contrastMock2);

        subject = new AnalyticsDataHeaderBuilder();
        subject.setExperiment(experimentMock);

    }

    @Test
    public void testBuildHeader() throws Exception {
        String[] newHeader = subject.buildHeader(HEADER);
        assertThat(newHeader, is(new String[]{"Gene ID"
                , "Gene Name"
        , "contrast1.p-value", "contrast1.t-stat", "contrast2.p-value", "contrast2.t-stat"}));
    }

    @Test
    public void testReplaceContrastIdWithName() throws Exception {
        String columnName = subject.replaceContrastIdWithName("c1.p-value");
        assertThat(columnName, is("contrast1.p-value"));
    }
}
