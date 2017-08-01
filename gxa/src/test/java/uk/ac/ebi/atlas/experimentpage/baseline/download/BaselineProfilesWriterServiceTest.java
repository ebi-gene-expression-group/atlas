package uk.ac.ebi.atlas.experimentpage.baseline.download;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.download.ExternallyAvailableContent;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.stream.BaselineProfileStreamFactory;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesWriterFactory;
import uk.ac.ebi.atlas.profiles.writer.ProfilesWriter;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesWriterServiceTest {

    @Mock
    BaselineProfilesWriterFactory baselineProfilesWriterFactory;

    @Mock
    ProfilesWriter<BaselineProfile> profilesWriter;

    @Mock
    BaselineProfileStreamFactory inputStreamFactory;

    @Mock
    SolrQueryService solrQueryService;

    @Mock
    CoexpressedGenesService coexpressedGenesService;

    @Mock
    BaselineRequestPreferences preferencesMock;

    @Mock
    BaselineExperiment baselineExperimentMock;

    @Mock
    ExperimentDisplayDefaults experimentDisplayDefaultsMock;

    BaselineRequestContext baselineRequestContext;

    BaselineProfilesWriterService subject;

    String geneName = "some_gene";
    String geneId = "some_gene_id";
    SemanticQuery geneQuery = SemanticQuery.create(geneName);

    @Before
    public void setUp() {
        AssayGroup assayGroupMock = mock(AssayGroup.class);

        baselineRequestContext = new BaselineRequestContext(preferencesMock, baselineExperimentMock);

        when(baselineProfilesWriterFactory.create(any(), any(), 0)).thenReturn(profilesWriter);

        subject = new BaselineProfilesWriterService<ExpressionUnit.Absolute>(inputStreamFactory, baselineProfilesWriterFactory, solrQueryService,
                coexpressedGenesService){
            @Override
            public Collection<ExternallyAvailableContent> get(BaselineExperiment experiment) {
                return ImmutableList.of();
            }
        };

        when(experimentDisplayDefaultsMock.preserveColumnOrder()).thenReturn(true);

        when(preferencesMock.getGeneQuery()).thenReturn(geneQuery);
        when(baselineExperimentMock.getDataColumnDescriptors()).thenReturn(ImmutableList.of(assayGroupMock));
        when(baselineExperimentMock.getDisplayDefaults()).thenReturn(experimentDisplayDefaultsMock);
        Species species = new Species("some species", SpeciesProperties.UNKNOWN);
        when(baselineExperimentMock.getSpecies()).thenReturn(species);
        TreeSet<Factor> t = new TreeSet<>();
        t.add(new Factor("h1", "p1"));

        when(solrQueryService.fetchResponse(geneQuery, species)).thenReturn(new GeneQueryResponse());
    }

    @Test
    public void writeWithNoCoexpressionsDoesNotInteractWithCoexpressedGenesService() throws Exception {
        Writer writer = mock(Writer.class);
        Map<String, Integer> coexpressions = new HashMap<>();

        subject.write(writer, preferencesMock, baselineExperimentMock, coexpressions);

        Mockito.verifyNoMoreInteractions(coexpressedGenesService);
    }

    @Test
    public void writeWithCoexpressionsDoesInteractWithCoexpressedGenesService() throws Exception {
        Writer writer = mock(Writer.class);

        Map<String, Integer> coexpressions = ImmutableMap.of(geneId, 3);

        GeneQueryResponse response = new GeneQueryResponse();
        response.addGeneIds(geneName, ImmutableSet.of(geneId));

        GeneQueryResponse extendedResponse = new GeneQueryResponse();
        extendedResponse.addGeneIds(geneName, ImmutableSet.of(geneId));

        Set<String> range = new HashSet<>();
        for (int i = 0; i < coexpressions.get(geneId); i++) {
            range.add("coexpression_" + i);
        }
        extendedResponse.addGeneIds(geneName + ":coexpressions", range);

        when(solrQueryService.fetchResponse(eq(geneQuery), any(Species.class))).thenReturn(response);

        when(coexpressedGenesService.extendGeneQueryResponseWithCoexpressions(baselineExperimentMock, response,
                coexpressions)).thenReturn(extendedResponse);

        subject.write(writer, preferencesMock, baselineExperimentMock, coexpressions);

        Mockito.verify(coexpressedGenesService).extendGeneQueryResponseWithCoexpressions(baselineExperimentMock, response, coexpressions);
    }

    @Test
    public void theDataFlowsLikeWeWant() throws Exception {
        theFlowOfTheDataIsRightForCoexpressions(ImmutableMap.<String, Integer>of());
        theFlowOfTheDataIsRightForCoexpressions(ImmutableMap.of(geneId, 1));
    }

    //TODO make this test reasonable and useful again
    void theFlowOfTheDataIsRightForCoexpressions(Map<String, Integer> coexpressions) throws Exception {
        Writer writer = mock(Writer.class);

        GeneQueryResponse response = new GeneQueryResponse();
        response.addGeneIds(geneName, ImmutableSet.of(geneId));

        GeneQueryResponse extendedResponse = new GeneQueryResponse();
        extendedResponse.addGeneIds(geneName, ImmutableSet.of(geneId));

        if (coexpressions.containsKey(geneId)) {
            Set<String> range = new HashSet<>();
            for (int i = 0; i < coexpressions.get(geneId); i++) {
                range.add("coexpresion_" + i);
            }
            extendedResponse.addGeneIds(geneName + ":coexpressions", range);
        }

        when(coexpressedGenesService.extendGeneQueryResponseWithCoexpressions(
                baselineExperimentMock, response, coexpressions)).thenReturn(extendedResponse);

        when(solrQueryService.fetchResponse(any(SemanticQuery.class), any(Species.class))).thenReturn(response);

        subject.write(writer, preferencesMock, baselineExperimentMock, coexpressions);
    }

}