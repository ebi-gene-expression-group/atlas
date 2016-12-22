package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.search.SemanticQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqBaselineExperimentDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "experimentAccession";
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
    private PrintWriter printWriterMock;

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Mock
    private AssayGroups assayGroupsMock;

    @Mock
    private BaselineProfileInputStreamFactory inputStreamFactoryMock;

    @Mock
    private ExperimentTrader experimentTraderMock;

    @Mock
    private BaselineProfilesWriterServiceFactory baselineProfilesWriterServiceFactory;

    @Mock
    private BaselineProfilesWriterService baselineProfilesWriterService;

    private BaselineExperimentDownloadService<BaselineRequestPreferences> subject;

    @Before
    public void setUp() throws Exception {
        when(baselineProfilesWriterServiceFactory.create(inputStreamFactoryMock))
                .thenReturn(baselineProfilesWriterService);
        subject = new BaselineExperimentDownloadService<>(inputStreamFactoryMock, baselineProfilesWriterServiceFactory,experimentTraderMock);

    }

    @Test
    public void testDownloadGeneProfiles() throws Exception {
        when(experimentTraderMock.getExperiment(eq(EXPERIMENT_ACCESSION), Matchers.anyString())).thenReturn
                (baselineExperimentMock);
        when(preferencesMock.getQueryFactorType()).thenReturn("queryFactorType");
        when(preferencesMock.getSerializedFilterFactors()).thenReturn("TYPE:value");
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("factorValues")));
        when(preferencesMock.getGeneQuery()).thenReturn(SemanticQuery.create());
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet("assayGroupIds")));
        when(baselineExperimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(baselineExperimentMock.getAssayGroups()).thenReturn(assayGroupsMock);
        when(baselineExperimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(baselineExperimentMock.getSpecies()).thenReturn(new Species("some species", SpeciesProperties.UNKNOWN));
        TreeSet<Factor> t = new TreeSet<>();
        t.add(new Factor("h1", "p1"));
        when(experimentalFactorsMock.getComplementFactors(anySetOf(Factor.class))).thenReturn(t);

        when(responseMock.getWriter()).thenReturn(printWriterMock);
        when(profilesWriterMock.write(eq(printWriterMock), Matchers.<ObjectInputStream<BaselineProfile>>any(),
                any(BaselineRequestContext.class), anySetOf(Factor.class), any(GeneQueryResponse.class))).thenReturn
                (0L);

        subject.download(EXPERIMENT_ACCESSION,requestMock, preferencesMock,responseMock,"");

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-query-results.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

        verify(baselineProfilesWriterService)
                .write(eq(printWriterMock), any(BaselineRequestPreferences.class), any
                (BaselineExperiment.class), anyMapOf(String.class, Integer.class));
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