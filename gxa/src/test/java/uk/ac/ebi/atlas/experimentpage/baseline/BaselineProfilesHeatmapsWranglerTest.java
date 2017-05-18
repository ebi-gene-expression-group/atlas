package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.RnaSeqBaselineRequestPreferences;

import java.util.TreeSet;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesHeatmapsWranglerTest {

    @Mock
    private BaselineProfilesHeatMap baselineProfilesHeatMapMock;

    @Mock
    private SolrQueryService solrQueryServiceMock;

    @Mock
    private CoexpressedGenesDao coexpressedGenesDaoMock;

    @Mock
    private ExperimentDisplayDefaults experimentDisplayDefaultsMock;

    @Mock
    private BaselineExperiment baselineExperimentMock;

    private BaselineProfilesHeatmapsWrangler subject;

    private BaselineRequestPreferences baselineRequestPreferences;

    private static final String ACCESSION = "E-MTAB-1337";
    private static final String GENE_WE_ASK_FOR = "T0";
    private static final String NAME_OF_THE_GENE_WE_ASK_FOR = "N0";

    @Before
    public void setUp(){
        when(baselineExperimentMock.getAccession()).thenReturn(ACCESSION);
        when(baselineExperimentMock.getSpecies()).thenReturn(new Species("some species", SpeciesProperties.UNKNOWN));

        when(experimentDisplayDefaultsMock.preserveColumnOrder()).thenReturn(true);
        when(baselineExperimentMock.getDisplayDefaults()).thenReturn(experimentDisplayDefaultsMock);

        baselineRequestPreferences = new RnaSeqBaselineRequestPreferences();
        baselineRequestPreferences.setGeneQuery(SemanticQuery.create(GENE_WE_ASK_FOR));

        TreeSet<Factor> ts = new TreeSet<>();
        ts.add(mock(Factor.class));

        subject = fakeWrangler(baselineRequestPreferences, baselineExperimentMock);
    }

    public BaselineProfilesHeatmapsWrangler fakeWrangler(BaselineRequestPreferences preferences,
                                                         BaselineExperiment experiment) {
        return new BaselineProfilesHeatmapsWrangler(
                baselineProfilesHeatMapMock,
                solrQueryServiceMock,
                new CoexpressedGenesService(coexpressedGenesDaoMock),
                preferences,
                experiment);
    }


    @Test
    public void rightQueriesToDataSources() throws Exception{
        GeneQueryResponse response = mock(GeneQueryResponse.class);
        when(solrQueryServiceMock.fetchResponse((SemanticQuery) Mockito.any(),anyString()))
                .thenReturn(response);
        when(baselineProfilesHeatMapMock.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                        .any(), eq(response),
                eq(false))).thenReturn(new BaselineProfilesList());

        subject.getJsonProfiles();

        verify(solrQueryServiceMock).fetchResponse(Mockito.any(SemanticQuery.class) ,anyString());
        verify(baselineProfilesHeatMapMock).fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                .any(), eq(response),
                eq(false));
    }

    @Test
    public void rightQueriesToDataSourcesForGeneSets() throws Exception{
        GeneQueryResponse response = mock(GeneQueryResponse.class);
        when(solrQueryServiceMock.fetchResponse(Mockito.any(SemanticQuery.class), anyString())).thenReturn(response);
        when(response.containsGeneSets()).thenReturn(true);
        when(baselineProfilesHeatMapMock.fetch(
                any(BaselineExperiment.class),
                (BaselineProfileStreamOptions) Mockito.any(),
                eq(response),
                eq(true)))
                .thenReturn(new BaselineProfilesList());

        BaselineProfilesHeatmapsWrangler subject =
                fakeWrangler(new RnaSeqBaselineRequestPreferences(), baselineExperimentMock);

        subject.getJsonProfilesAsGeneSets();

        verify(solrQueryServiceMock).fetchResponse(Mockito.any(SemanticQuery.class) ,anyString());
        verify(baselineProfilesHeatMapMock).fetch(any(BaselineExperiment.class), (BaselineProfileStreamOptions) Mockito
                .any(), eq(response),
                eq(true));
    }

    @Test
    public void cachingWorks() throws Exception{
        when(baselineProfilesHeatMapMock.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito.any()
                , any
                        (GeneQueryResponse.class),
                eq(false))).thenReturn(new BaselineProfilesList());

        for(int i = 0; i<5; i++) {
            subject.getJsonProfiles();
        }

        verify(solrQueryServiceMock).fetchResponse(Mockito.any(SemanticQuery.class) ,anyString());
        verify(baselineProfilesHeatMapMock).fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                .any(), any
                (GeneQueryResponse.class),
                eq(false));
    }

    @Test
    public void jsonCoexpressionsNotReturnedForWrongAmountOfResults() throws Exception {
        jsonCoexpressionsNotReturnedForTheseResultsBack(new BaselineProfilesList());

        BaselineProfilesList tooBigList = new BaselineProfilesList();
        tooBigList.add(mock(BaselineProfile.class));
        for(int i= 0 ; i<10; i++) {
            tooBigList.add(mock(BaselineProfile.class));
            jsonCoexpressionsNotReturnedForTheseResultsBack(tooBigList);
        }
    }

    private void jsonCoexpressionsNotReturnedForTheseResultsBack(BaselineProfilesList list) throws Exception {
        Mockito.reset(baselineProfilesHeatMapMock);
        when(baselineProfilesHeatMapMock.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito.any()
                , any
                        (GeneQueryResponse.class),
                eq(false))).thenReturn(list);
        BaselineProfilesHeatmapsWrangler subjectHere = fakeWrangler(baselineRequestPreferences, baselineExperimentMock);
        subjectHere.getJsonProfiles();

        verify(baselineProfilesHeatMapMock).fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                .any(), any(GeneQueryResponse.class), eq(false));

        subjectHere.getJsonCoexpressions();

        verifyZeroInteractions(coexpressedGenesDaoMock);
        verifyNoMoreInteractions(baselineProfilesHeatMapMock);
    }

    @Ignore
    @Test
    public void jsonCoexpressionsReturnedForOneResult() throws Exception {
        BaselineProfilesList rightList = new BaselineProfilesList();
        BaselineProfile profile = mock(BaselineProfile.class);
        when(profile.getName()).thenReturn(NAME_OF_THE_GENE_WE_ASK_FOR);
        when(profile.getId()).thenReturn(GENE_WE_ASK_FOR);
        rightList.add(profile);

        when(baselineProfilesHeatMapMock.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito.any()
                , any(GeneQueryResponse.class), eq(false))).thenReturn(rightList);

        when(coexpressedGenesDaoMock.coexpressedGenesFor(ACCESSION, GENE_WE_ASK_FOR)).thenReturn(ImmutableList.of("C1", "C2","C3"));

        BaselineProfilesHeatmapsWrangler subjectHere = fakeWrangler(baselineRequestPreferences, baselineExperimentMock);
        subjectHere.getJsonProfiles();

        subjectHere.getJsonCoexpressions();

        verify(coexpressedGenesDaoMock).coexpressedGenesFor(ACCESSION, GENE_WE_ASK_FOR);

    }

}