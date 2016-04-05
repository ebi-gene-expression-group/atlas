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

package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RnaSeqRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqExperimentDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    @Mock
    private RnaSeqRequestContextBuilder requestContextBuilderMock;

    @Mock
    private RnaSeqProfilesWriter profilesWriter;

    @Mock
    private DataWriterFactory dataWriterFactoryMock;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private DifferentialRequestPreferences preferencesMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private DifferentialExperiment experimentMock;

    @Mock
    private RnaSeqRequestContext requestContextMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private ExpressionsWriter expressionsWriterMock;

    private RnaSeqExperimentDownloadController subject;

    @Before
    public void setUp() throws Exception {
        subject = new RnaSeqExperimentDownloadController(requestContextBuilderMock, profilesWriter, dataWriterFactoryMock);

        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(experimentMock);
        when(experimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(requestContextBuilderMock.forExperiment(experimentMock)).thenReturn(requestContextBuilderMock);
        when(requestContextBuilderMock.withPreferences(preferencesMock)).thenReturn(requestContextBuilderMock);
        when(requestContextBuilderMock.build()).thenReturn(requestContextMock);
        when(responseMock.getWriter()).thenReturn(printWriterMock);
        when(profilesWriter.write(printWriterMock, requestContextMock)).thenReturn(0L);
    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {
        subject.downloadGeneProfiles(requestMock, preferencesMock, responseMock);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

        verify(profilesWriter).write(printWriterMock, requestContextMock);
    }

    @Test
    public void testDownloadRawCounts() throws Exception {
        when(dataWriterFactoryMock.getRnaSeqRawDataWriter(experimentMock, printWriterMock)).thenReturn(expressionsWriterMock);
        when(expressionsWriterMock.write()).thenReturn(0L);

        subject.downloadRawCounts(requestMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-raw-counts.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }

    @Test
    public void testDownloadAllAnalytics() throws Exception {

        when(dataWriterFactoryMock.getRnaSeqAnalyticsDataWriter(experimentMock, printWriterMock)).thenReturn(expressionsWriterMock);
        when(expressionsWriterMock.write()).thenReturn(0L);

        subject.downloadAllAnalytics(requestMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-analytics.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }
}