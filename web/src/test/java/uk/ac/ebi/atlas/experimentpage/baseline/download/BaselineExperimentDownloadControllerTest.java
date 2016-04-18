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

package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.baseline.PreferencesForBaselineExperiments;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.TreeSet;

import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    @Mock
    private BaselineRequestContextBuilder requestContextBuilderMock;

    @Mock
    private BaselineProfilesWriter baselineProfilesWriterMock;

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

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private PreferencesForBaselineExperiments preferencesForBaselineExperiments;

    @Mock
    private AssayGroups assayGroupsMock;

    private BaselineExperimentDownloadController subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentDownloadController(
                preferencesForBaselineExperiments, baselineProfilesWriterMock);
    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {
        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(baselineExperimentMock);
        when(preferencesMock.getQueryFactorType()).thenReturn("queryFactorType");
        when(preferencesMock.getSerializedFilterFactors()).thenReturn("TYPE:value");
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("factorValues")));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet("assayGroupIds")));
        when(baselineExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(baselineExperimentMock.getAssayGroups()).thenReturn(assayGroupsMock);
        when(baselineExperimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(baselineExperimentMock.getFirstOrganism()).thenReturn("some_organism");
        TreeSet<Factor> t = new TreeSet<>();
        t.add(new Factor("h1", "p1"));
        when(experimentalFactorsMock.getComplementFactors(anySet())).thenReturn(t);

        when(responseMock.getWriter()).thenReturn(printWriterMock);
        when(baselineProfilesWriterMock.write(Matchers.eq(printWriterMock), Matchers.any
                (BaselineRequestContext.class))).thenReturn
                (0L);

        subject.downloadGeneProfiles(requestMock, preferencesMock, responseMock);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

        verify(baselineProfilesWriterMock).write(Matchers.eq(printWriterMock), Matchers.any
                (BaselineRequestContext.class));
    }
}