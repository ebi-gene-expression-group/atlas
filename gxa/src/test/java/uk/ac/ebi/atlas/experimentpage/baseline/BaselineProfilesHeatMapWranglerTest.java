package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.search.SemanticQuery;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeSet;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesHeatmapWranglerTest {

    @Mock
    private BaselineProfilesHeatMap baselineProfilesHeatMap;
    @Mock
    private SolrQueryService solrQueryService;
    @Mock
    private CoexpressedGenesDao coexpressedGenesDao;

    @Mock
    private BaselineExperiment experiment;

    HttpServletRequest httpServletRequest = new MockHttpServletRequest("GET", "https://www.ebi.ac.uk/gxa");

    private BaselineProfilesHeatmapWrangler subject;

    private BaselineRequestPreferences baselineRequestPreferences;

    private static final String ACCESSION = "E-MTAB-1337";
    private static final String GENE_WE_ASK_FOR = "T0";
    private static final String NAME_OF_THE_GENE_WE_ASK_FOR = "N0";

    @Before
    public void setUp(){
        when(experiment.getAccession()).thenReturn(ACCESSION);
        when(experiment.getSpecies()).thenReturn(new Species("some species", SpeciesProperties.UNKNOWN));

        baselineRequestPreferences = new BaselineRequestPreferences();
        baselineRequestPreferences.setGeneQuery(SemanticQuery.create(GENE_WE_ASK_FOR));

        TreeSet<Factor> ts = new TreeSet<>();
        ts.add(mock(Factor.class));

        subject = fakeWrangler(baselineRequestPreferences, experiment, httpServletRequest);
    }

    public BaselineProfilesHeatmapWrangler fakeWrangler(BaselineRequestPreferences preferences, BaselineExperiment
            experiment, HttpServletRequest httpServletRequest){
        return new BaselineProfilesHeatmapWrangler(baselineProfilesHeatMap,
                solrQueryService,new CoexpressedGenesService(coexpressedGenesDao),
                preferences,experiment);
    }


    @Test
    public void rightQueriesToDataSources() throws Exception{
        GeneQueryResponse response = mock(GeneQueryResponse.class);
        when(solrQueryService.fetchResponse((SemanticQuery) Mockito.any(),anyString()))
                .thenReturn(response);
        when(baselineProfilesHeatMap.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                        .any(), eq(response),
                eq(false))).thenReturn(new BaselineProfilesList());

        subject.getJsonProfiles();

        verify(solrQueryService).fetchResponse(Mockito.any(SemanticQuery.class) ,anyString());
        verify(baselineProfilesHeatMap).fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                .any(), eq(response),
                eq(false));
    }

    @Test
    public void rightQueriesToDataSourcesForGeneSets() throws Exception{
        GeneQueryResponse response = mock(GeneQueryResponse.class);
        when(solrQueryService.fetchResponse(Mockito.any(SemanticQuery.class) ,anyString()))
                .thenReturn(response);
        when(response.containsGeneSets()).thenReturn(true);
        when(baselineProfilesHeatMap.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                        .any(), eq(response),
                eq(true))).thenReturn(new BaselineProfilesList());

        BaselineProfilesHeatmapWrangler subject = fakeWrangler(new BaselineRequestPreferences(), experiment,httpServletRequest);

        subject.getJsonProfilesAsGeneSets();

        verify(solrQueryService).fetchResponse(Mockito.any(SemanticQuery.class) ,anyString());
        verify(baselineProfilesHeatMap).fetch(any(BaselineExperiment.class), (BaselineProfileStreamOptions) Mockito
                .any(), eq(response),
                eq(true));
    }

    @Test
    public void cachingWorks() throws Exception{
        when(baselineProfilesHeatMap.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito.any()
                , any
                        (GeneQueryResponse.class),
                eq(false))).thenReturn(new BaselineProfilesList());

        for(int i = 0; i<5; i++) {
            subject.getJsonProfiles();
        }

        verify(solrQueryService).fetchResponse(Mockito.any(SemanticQuery.class) ,anyString());
        verify(baselineProfilesHeatMap).fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
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
        Mockito.reset(baselineProfilesHeatMap);
        when(baselineProfilesHeatMap.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito.any()
                , any
                        (GeneQueryResponse.class),
                eq(false))).thenReturn(list);
        BaselineProfilesHeatmapWrangler subjectHere = fakeWrangler(baselineRequestPreferences, experiment,httpServletRequest);
        subjectHere.getJsonProfiles();

        verify(baselineProfilesHeatMap).fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito
                .any(), any(GeneQueryResponse.class), eq(false));

        subjectHere.getJsonCoexpressions();

        verifyZeroInteractions(coexpressedGenesDao);
        verifyNoMoreInteractions(baselineProfilesHeatMap);
    }

    @Ignore
    @Test
    public void jsonCoexpressionsReturnedForOneResult() throws Exception {
        BaselineProfilesList rightList = new BaselineProfilesList();
        BaselineProfile profile = mock(BaselineProfile.class);
        when(profile.getName()).thenReturn(NAME_OF_THE_GENE_WE_ASK_FOR);
        when(profile.getId()).thenReturn(GENE_WE_ASK_FOR);
        rightList.add(profile);

        when(baselineProfilesHeatMap.fetch(any(BaselineExperiment.class),(BaselineProfileStreamOptions) Mockito.any()
                , any(GeneQueryResponse.class), eq(false))).thenReturn(rightList);

        when(coexpressedGenesDao.coexpressedGenesFor(ACCESSION, GENE_WE_ASK_FOR)).thenReturn(ImmutableList.of("C1", "C2","C3"));

        BaselineProfilesHeatmapWrangler subjectHere = fakeWrangler(baselineRequestPreferences, experiment,httpServletRequest);
        subjectHere.getJsonProfiles();

        subjectHere.getJsonCoexpressions();

        verify(coexpressedGenesDao).coexpressedGenesFor(ACCESSION, GENE_WE_ASK_FOR);

    }

}