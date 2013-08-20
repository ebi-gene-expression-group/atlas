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

package uk.ac.ebi.atlas.web.controllers.rest.experimentquery;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.WriteBaselineProfilesCommand;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.controllers.ExperimentDispatcher;
import uk.ac.ebi.atlas.web.controllers.rest.experimentquery.BaselineQueryDownloadController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineQueryDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    @Mock
    private BaselineRequestContextBuilder requestContextBuilderMock;

    @Mock
    private FilterFactorsConverter filterFactorsConverterMock;

    @Mock
    private WriteBaselineProfilesCommand writeBaselineProfilesCommandMock;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private BaselineRequestPreferences preferencesMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private BaselineRequestContext baselineRequestContextMock;

    @Mock
    private PrintWriter printWriterMock;

    private BaselineQueryDownloadController subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineQueryDownloadController(requestContextBuilderMock, filterFactorsConverterMock, writeBaselineProfilesCommandMock);
    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {

        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(baselineExperimentMock);
        when(preferencesMock.getQueryFactorType()).thenReturn("queryFactorType");
        when(preferencesMock.getSerializedFilterFactors()).thenReturn("serializedFilterFactors");
        when(baselineExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);

        when(requestContextBuilderMock.forExperiment(baselineExperimentMock)).thenReturn(requestContextBuilderMock);
        when(requestContextBuilderMock.withPreferences(preferencesMock)).thenReturn(requestContextBuilderMock);
        when(requestContextBuilderMock.build()).thenReturn(baselineRequestContextMock);

        when(responseMock.getWriter()).thenReturn(printWriterMock);
        when(writeBaselineProfilesCommandMock.execute(EXPERIMENT_ACCESSION)).thenReturn(0L);

        subject.downloadGeneProfiles(requestMock, preferencesMock, responseMock);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

        verify(writeBaselineProfilesCommandMock).setResponseWriter(printWriterMock);
        verify(writeBaselineProfilesCommandMock).execute(EXPERIMENT_ACCESSION);
    }
}