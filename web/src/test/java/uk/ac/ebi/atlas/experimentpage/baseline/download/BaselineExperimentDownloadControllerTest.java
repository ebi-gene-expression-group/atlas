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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.ExperimentDispatcher;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";

    @Mock
    private BaselineRequestContextBuilder requestContextBuilderMock;

    @Mock
    private ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> profilesWriterMock;

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
    private AssayGroups assayGroupsMock;

    private BaselineExperimentDownloadController subject;

    @Mock
    BaselineProfileInputStreamFactory inputStreamFactory;

    @Mock
    BaselineProfilesWriterServiceFactory baselineProfilesWriterServiceFactory;

    BaselineProfilesWriterService baselineProfilesWriterService;

    @Mock
    CoexpressedGenesService coexpressedGenesService;

    @Mock
    SolrQueryService solrQueryService;


    @Before
    public void setUp() throws Exception {
        BaselineProfilesWriterServiceFactory baselineProfilesWriterServiceFactory = new
                BaselineProfilesWriterServiceFactory(profilesWriterMock, solrQueryService,coexpressedGenesService );
        subject = new BaselineExperimentDownloadController(inputStreamFactory, baselineProfilesWriterServiceFactory);

    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {
        when(requestMock.getAttribute(ExperimentDispatcher.EXPERIMENT_ATTRIBUTE)).thenReturn(baselineExperimentMock);
        when(preferencesMock.getQueryFactorType()).thenReturn("queryFactorType");
        when(preferencesMock.getSerializedFilterFactors()).thenReturn("TYPE:value");
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("factorValues")));
        when(preferencesMock.getGeneQuery()).thenReturn(GeneQuery.EMPTY);
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet("assayGroupIds")));
        when(baselineExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(baselineExperimentMock.getAssayGroups()).thenReturn(assayGroupsMock);
        when(baselineExperimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(baselineExperimentMock.getFirstOrganism()).thenReturn("some_organism");
        TreeSet<Factor> t = new TreeSet<>();
        t.add(new Factor("h1", "p1"));
        when(experimentalFactorsMock.getComplementFactors(anySet())).thenReturn(t);

        when(responseMock.getWriter()).thenReturn(printWriterMock);
        when(profilesWriterMock.write(eq(printWriterMock),any(ObjectInputStream.class),
                any(BaselineRequestContext.class), anySet(), any(GeneQueryResponse.class), anyBoolean())).thenReturn
                (0L);

        subject.downloadGeneProfiles(requestMock, preferencesMock, responseMock);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

        verify(profilesWriterMock).write(eq(printWriterMock), any(ObjectInputStream.class), any
                (BaselineRequestContext.class), anySet(), any(GeneQueryResponse.class), anyBoolean());
    }


    @Test
    public void parseGoodCoexpressionArgument(){
        testParse("{\\\"AC190623.3_FG001\\\":10}", ImmutableMap.of("AC190623.3_FG001", 10));
        testParse("{\"AC190623.3_FG001\":10}", ImmutableMap.of("AC190623.3_FG001", 10));
    }

    @Test
    public void returnEmptyForMalformedCoexpressionArgument(){
        Map<String, Integer> empty = ImmutableMap.of();
        testParse("", empty);
        testParse("\"banana\"", empty);
        testParse("notJson:[", empty);
        testParse("{\"AC190623.3_FG001\":notAnInteger}", empty);
    }

    public void testParse(String input, Map<String, Integer> expected) {
        when(requestMock.getParameterMap()).thenReturn(ImmutableMap.of("geneQuery", new String[] {"idOfGeneGoesHere"},
                "coexpressions", new String[]{input}));
        when(requestMock.getParameter("coexpressions")).thenReturn(input);

        Map<String, Integer> result = subject.readCoexpressionsRequested(requestMock);
        assertEquals(expected, result);
    }
}