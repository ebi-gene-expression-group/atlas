package uk.ac.ebi.atlas.experimentpage.baseline;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesDao;
import uk.ac.ebi.atlas.experimentpage.baseline.coexpression.CoexpressedGenesService;
import uk.ac.ebi.atlas.experimentpage.context.RequestContext;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.viewmodel.BaselineProfilesViewModelBuilder;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.search.GeneQuery;

import java.util.*;

import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesHeatMapWranglerTest {

    @Mock
    private BaselineProfilesHeatMap baselineProfilesHeatMap;
    @Mock
    private BaselineProfilesViewModelBuilder baselineProfilesViewModelBuilder;
    @Mock
    private SolrQueryService solrQueryService;
    @Mock
    private CoexpressedGenesDao coexpressedGenesDao;

    @Mock
    private BaselineExperiment experiment;

    @Mock
    private ExperimentalFactors experimentalFactors;

    JsonObject resultObject = new JsonObject();

    BaselineProfilesHeatMapWrangler subject;

    BaselineRequestPreferences baselineRequestPreferences;

    static String ACCESSION = "E-MTAB-1337";
    static String GENE_WE_ASK_FOR = "T0";
    static String NAME_OF_THE_GENE_WE_ASK_FOR = "N0";

    @Before
    public void setUp(){
        when(experiment.getAccession()).thenReturn(ACCESSION);
        when(experiment.getSpecies()).thenReturn("SPECIES");
        when(experiment.getSpeciesToEnsemblMapping()).thenReturn(new HashMap<String, String>());
        when(experiment.getExperimentalFactors()).thenReturn(experimentalFactors);

        baselineRequestPreferences = new BaselineRequestPreferences();
        baselineRequestPreferences.setGeneQuery(GeneQuery.create(GENE_WE_ASK_FOR));

        TreeSet<Factor> ts = new TreeSet<>();
        ts.add(mock(Factor.class));
        when(experimentalFactors.getComplementFactors(anySet())).thenReturn(ts);

        when(baselineProfilesViewModelBuilder.build(Matchers.any(BaselineProfilesList.class), Matchers.any(List
                .class))).thenReturn(resultObject);

        subject = fakeWrangler(baselineRequestPreferences, experiment);
    }

    public BaselineProfilesHeatMapWrangler fakeWrangler(BaselineRequestPreferences preferences, BaselineExperiment
            experiment){
        return new BaselineProfilesHeatMapWrangler(baselineProfilesHeatMap, baselineProfilesViewModelBuilder,
                solrQueryService,new CoexpressedGenesService(coexpressedGenesDao),
                preferences,experiment);
    }


    @Test
    public void rightQueriesToDataSources() throws Exception{
        GeneQueryResponse response = Mockito.mock(GeneQueryResponse.class);
        when(solrQueryService.fetchResponseBasedOnRequestContext((RequestContext) Mockito.any(),anyString()))
                .thenReturn(response);

        subject.getJsonProfiles();

        verify(solrQueryService).fetchResponseBasedOnRequestContext((RequestContext) Mockito.any(),anyString());
        verify(baselineProfilesHeatMap).fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.eq(response),
                Matchers.eq(false));
    }

    @Test
    public void rightQueriesToDataSourcesForGeneSets() throws Exception{
        GeneQueryResponse response = Mockito.mock(GeneQueryResponse.class);
        when(solrQueryService.fetchResponseBasedOnRequestContext((RequestContext) Mockito.any(),anyString()))
                .thenReturn(response);

        when(response.containsGeneSets()).thenReturn(true);
        BaselineProfilesHeatMapWrangler subject = fakeWrangler(new BaselineRequestPreferences(), experiment);

        subject.getJsonProfilesAsGeneSets();

        verify(solrQueryService).fetchResponseBasedOnRequestContext((RequestContext) Mockito.any(),anyString());
        verify(baselineProfilesHeatMap).fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.eq(response),
                Matchers.eq(true));
    }

    @Test
    public void cachingWorks() throws Exception{
        when(baselineProfilesHeatMap.fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.any
                        (GeneQueryResponse.class),
                Matchers.eq(false))).thenReturn(new BaselineProfilesList());

        for(int i = 0; i<5; i++) {
            subject.getJsonProfiles();
        }

        verify(solrQueryService).fetchResponseBasedOnRequestContext((RequestContext) Mockito.any(),anyString());
        verify(baselineProfilesHeatMap).fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.any
                (GeneQueryResponse.class),
                Matchers.eq(false));
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
        when(baselineProfilesHeatMap.fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.any
                        (GeneQueryResponse.class),
                Matchers.eq(false))).thenReturn(list);
        BaselineProfilesHeatMapWrangler subjectHere = fakeWrangler(baselineRequestPreferences, experiment);
        subjectHere.getJsonProfiles();

        verify(baselineProfilesHeatMap).fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.any
                        (GeneQueryResponse.class),
                Matchers.eq(false));

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

        when(baselineProfilesHeatMap.fetch((BaselineProfileStreamOptions) Mockito.any(), Matchers.any
                        (GeneQueryResponse.class),
                Matchers.eq(false))).thenReturn(rightList);

        when(coexpressedGenesDao.coexpressedGenesFor(ACCESSION, GENE_WE_ASK_FOR)).thenReturn(ImmutableList.of("C1", "C2","C3"));

        BaselineProfilesHeatMapWrangler subjectHere = fakeWrangler(baselineRequestPreferences, experiment);
        subjectHere.getJsonProfiles();

        subjectHere.getJsonCoexpressions();

        verify(coexpressedGenesDao).coexpressedGenesFor(ACCESSION, GENE_WE_ASK_FOR);

    }

}