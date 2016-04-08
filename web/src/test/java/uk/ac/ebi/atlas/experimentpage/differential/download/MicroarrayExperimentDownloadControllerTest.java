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

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.MicroarrayRequestContextBuilder;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
    public static final String ARRAY_DESIGN = "arrayDesign";

    @Mock
    private MicroarrayRequestContextBuilder requestContextBuilderMock;

    @Mock
    private MicroarrayProfilesWriter profilesWriter;

    @Mock
    private DataWriterFactory dataWriterFactoryMock;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private MicroarrayExperiment experimentMock;

    @Mock
    private MicroarrayRequestPreferences preferencesMock;

    @Mock
    private MicroarrayRequestContext requestContextMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    private ExpressionsWriter expressionsWriterMock;

    private MicroarrayExperimentDownloadController subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperimentDownloadController(requestContextBuilderMock, profilesWriter, dataWriterFactoryMock);

        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(experimentMock);
        when(experimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(experimentMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAY_DESIGN)));
        when(requestContextBuilderMock.forExperiment(experimentMock)).thenReturn(requestContextBuilderMock);
        when(requestContextBuilderMock.withPreferences(preferencesMock)).thenReturn(requestContextBuilderMock);
        when(requestContextBuilderMock.build()).thenReturn(requestContextMock);
        when(responseMock.getWriter()).thenReturn(printWriterMock);
        when(profilesWriter.write(any(PrintWriter.class), any(ObjectInputStream.class), any
                (DifferentialProfileStreamOptions.class), anySet(), any(Optional.class))).thenReturn(0L);
        when(preferencesMock.getArrayDesignAccession()).thenReturn(ARRAY_DESIGN);

    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {
        subject.downloadGeneProfiles(requestMock, preferencesMock, responseMock);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "_" + ARRAY_DESIGN + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

        verify(profilesWriter).write(printWriterMock, requestContextMock, ARRAY_DESIGN);
    }

    @Test
    public void testDownloadNormalizedData() throws Exception {
        when(expressionsWriterMock.write()).thenReturn(0L);
        when(dataWriterFactoryMock.getMicroarrayRawDataWriter(experimentMock, printWriterMock, ARRAY_DESIGN)).thenReturn(expressionsWriterMock);

        subject.downloadNormalizedData(requestMock, preferencesMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "_" + ARRAY_DESIGN + "-normalized-expressions.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }

    @Test
    public void testDownloadLogFoldData() throws Exception {
        when(expressionsWriterMock.write()).thenReturn(0L);
        when(dataWriterFactoryMock.getMicroarrayLogFoldDataWriter(experimentMock, printWriterMock, ARRAY_DESIGN)).thenReturn(expressionsWriterMock);

        subject.downloadLogFoldData(requestMock, preferencesMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "_" + ARRAY_DESIGN + "-log-fold-changes.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }

    @Test
    public void testDownloadAllAnalytics() throws Exception {
        when(expressionsWriterMock.write()).thenReturn(0L);
        when(dataWriterFactoryMock.getMicroarrayAnalyticsDataWriter(experimentMock, printWriterMock, ARRAY_DESIGN)).thenReturn(expressionsWriterMock);

        subject.downloadAllAnalytics(requestMock, preferencesMock, responseMock);

        verify(expressionsWriterMock).write();
        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "_" + ARRAY_DESIGN + "-analytics.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");
    }
}