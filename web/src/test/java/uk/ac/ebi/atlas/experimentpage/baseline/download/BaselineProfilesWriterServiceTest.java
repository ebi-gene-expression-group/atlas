package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class BaselineProfilesWriterServiceTest {

    @Mock
    ProfilesWriter<BaselineProfile, Factor, BaselineRequestContext> profilesWriter;

    @Mock
    BaselineProfileInputStreamFactory inputStreamFactory;

    @Mock
    SolrQueryService solrQueryService;

    @Mock
    CoexpressedGenesService coexpressedGenesService;

    BaselineProfilesWriterService subject;

    @Mock
    private AssayGroups assayGroupsMock;

    @Mock
    private BaselineRequestPreferences preferencesMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    GeneQuery geneQuery = GeneQuery.create("some_gene");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subject = new BaselineProfilesWriterService(inputStreamFactory,profilesWriter,solrQueryService,coexpressedGenesService);

        when(preferencesMock.getQueryFactorType()).thenReturn("queryFactorType");
        when(preferencesMock.getSerializedFilterFactors()).thenReturn("TYPE:value");
        when(preferencesMock.getQueryFactorValues()).thenReturn(Sets.newTreeSet(Sets.newHashSet("factorValues")));
        when(preferencesMock.getGeneQuery()).thenReturn(geneQuery);
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newTreeSet(Sets.newHashSet("assayGroupIds")));
        when(baselineExperimentMock.getAccession()).thenReturn("ACCESSION");
        when(baselineExperimentMock.getAssayGroups()).thenReturn(assayGroupsMock);
        when(baselineExperimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(baselineExperimentMock.getFirstOrganism()).thenReturn("some_organism");
        TreeSet<Factor> t = new TreeSet<>();
        t.add(new Factor("h1", "p1"));
        when(experimentalFactorsMock.getComplementFactors(anySet())).thenReturn(t);
    }

    @Test
    public void writeWithNoCoexpressionsDoesNotInteractWithCoexpressedGenesService() throws Exception {
        Writer writer = Mockito.mock(Writer.class);
        Map<String, Integer> coexpressions = new HashMap<>();

        subject.write(writer, preferencesMock, baselineExperimentMock,coexpressions );

        Mockito.verifyNoMoreInteractions(coexpressedGenesService);
    }

    @Test
    public void writeWithCoexpressionsDoesNotInteractWithCoexpressedGenesService() throws Exception {
        Writer writer = Mockito.mock(Writer.class);
        Map<String, Integer> coexpressions = ImmutableMap.of("some_gene", 3);
        GeneQuery enrichedGeneQuery = GeneQuery.create("some_gene","c1", "c2", "c3");

        when(coexpressedGenesService.extendGeneQueryWithCoexpressions(baselineExperimentMock,geneQuery,
                coexpressions)).thenReturn(enrichedGeneQuery);

        subject.write(writer, preferencesMock, baselineExperimentMock,coexpressions );

        Mockito.verify(coexpressedGenesService).extendGeneQueryWithCoexpressions(baselineExperimentMock,geneQuery,
                coexpressions);
    }

    @Test
    public void theDataFlowsLikeWeWant() throws Exception {
        theFlowOfTheDataIsRightForCoexpressions(ImmutableMap.<String, Integer>of());
        theFlowOfTheDataIsRightForCoexpressions(ImmutableMap.of("g1", 1));
        theFlowOfTheDataIsRightForCoexpressions(ImmutableMap.of("g1", 1, "g2", 10));
    }

    public void theFlowOfTheDataIsRightForCoexpressions(Map<String, Integer> coexpressions) throws Exception {
        Writer writer = Mockito.mock(Writer.class);

        when(coexpressedGenesService.extendGeneQueryWithCoexpressions(baselineExperimentMock,geneQuery,
                coexpressions)).thenReturn(GeneQuery.create("some_gene","c1", "c2", "c3"));

        ObjectInputStream<BaselineProfile> inputStream = Mockito.mock(ObjectInputStream.class);
        when(inputStreamFactory.create(any(BaselineProfileStreamOptions.class))).thenReturn(inputStream);

        GeneQueryResponse responseFromSolr = Mockito.mock(GeneQueryResponse.class);
        when(solrQueryService.fetchResponseBasedOnRequestContext(any(RequestContext.class), anyString()))
                .thenReturn(responseFromSolr);

        subject.write(writer, preferencesMock, baselineExperimentMock,coexpressions);

        //query solr, create a stream, pass the results on
        Mockito.verify(solrQueryService).fetchResponseBasedOnRequestContext(any(RequestContext.class), anyString());
        Mockito.verify(inputStreamFactory).create(any(BaselineProfileStreamOptions.class));
        Mockito.verify(profilesWriter).write(eq(writer), eq(inputStream), any(BaselineRequestContext.class), anySet(), eq
                (responseFromSolr), anyBoolean());
        Mockito.verifyNoMoreInteractions(solrQueryService, inputStreamFactory, profilesWriter);
        Mockito.reset(solrQueryService, inputStreamFactory, profilesWriter);
    }

    @Test
    public void returnValueComesFromTheWriter() throws Exception {
        Writer writer = Mockito.mock(Writer.class);
        Map<String, Integer> coexpressions = new HashMap<>();

        long expected = 123L;

        when(profilesWriter.write(eq(writer), any(ObjectInputStream.class), any(BaselineRequestContext.class), anySet()
                , any(GeneQueryResponse.class), anyBoolean())).thenReturn(expected);

        long returnValue = subject.write(writer, preferencesMock, baselineExperimentMock,coexpressions );

        assertEquals(expected, returnValue);
    }
}