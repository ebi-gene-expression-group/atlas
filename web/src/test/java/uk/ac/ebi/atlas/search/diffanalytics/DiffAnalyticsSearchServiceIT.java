package uk.ac.ebi.atlas.search.diffanalytics;


import autovalue.shaded.com.google.common.common.collect.Sets;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.search.ConditionQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsSearchServiceIT {

    DiffAnalyticsSearchService diffAnalyticsSearchService;

    @Inject
    DifferentialConditionsSearchService differentialConditionsSearchService;

    @Inject
    SolrQueryService solrQueryService;

    @Inject
    @Qualifier("dataSourceOracle")
    private DataSource dataSource;

    @Mock
    private ContrastTrader contrastTraderMock;

    @Mock
    private Contrast contrastMock;

    @Inject
    private OracleObjectFactory oracleObjectFactory;

    @Before
    public void mockOutContrastTrader() {
        //mock out contrast trader so as not to load experiments, so we don't need experiment data files and also for performance
        MockitoAnnotations.initMocks(this);
        when(contrastTraderMock.getContrast(anyString(), anyString())).thenReturn(contrastMock);
        DiffAnalyticsRowMapper dbeRowMapper = new DiffAnalyticsRowMapper(contrastTraderMock);
        DiffAnalyticsDao diffAnalyticsDao = new DiffAnalyticsDao(dataSource, dbeRowMapper, oracleObjectFactory);
        diffAnalyticsSearchService = new DiffAnalyticsSearchService(diffAnalyticsDao, differentialConditionsSearchService, solrQueryService);
    }

    private static List<String> getBioentityNames(DiffAnalyticsList bioentityExpressions) {
        List<String> names = Lists.newArrayList();
        for (DiffAnalytics bioentityExpression: bioentityExpressions) {
            names.add(bioentityExpression.getBioentityName());
        }
        return names;
    }


    @Test
    public void weHaveSomeResultsAfterAGeneralInexactGeneQueryWhenConditionQueryIsEmpty()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setExactMatch(false);
        requestParameters.setGeneQuery(GeneQuery.create("Os"));

        assertTrue(StringUtils.isBlank(requestParameters.getConditionQuery().asString()));

        String species = "";
        DiffAnalyticsList bioentityExpressions1 = fetch(requestParameters, species);

        assertThat(bioentityExpressions1.size(), greaterThan(0));


        requestParameters.setExactMatch(true);
        DiffAnalyticsList bioentityExpressions2 = fetch(requestParameters, species);

        assertEquals(new DiffAnalyticsList(), bioentityExpressions2);
    }

    private DiffAnalyticsList fetch(GeneQuerySearchRequestParameters requestParameters, String species){
        Optional<Set<String>> geneIdsFromGeneQuery = solrQueryService.expandGeneQueryIntoGeneIds
                (requestParameters.getGeneQuery().asString(), species, requestParameters.isExactMatch());
        return diffAnalyticsSearchService.fetchTop(requestParameters.getConditionQuery().asString(), species,
                geneIdsFromGeneQuery);
    }


    @Test
    public void kinaseIsAPopularKindOfProteinAndWeHaveSomeDifferentialResultsForIt()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("kinase"));
        requestParameters.setExactMatch(false);

        String species = "";
        DiffAnalyticsList bioentityExpressions = fetch(requestParameters,species);

        assertThat(bioentityExpressions, hasSize(50)); //Max
        assertThat(bioentityExpressions.getTotalNumberOfResults(), greaterThan(100));
    }


    @Test
    public void weHaveSomeDataAboutCancerAndCanAccessItInTwoDifferentWays()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("cancer");

        final List<String> names = Lists.newArrayList();

        int count = diffAnalyticsSearchService.visitEachExpression(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), requestParameters.getOrganism(), requestParameters.isExactMatch(), new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });


        assertThat(count, greaterThan(100));
        assertEquals(count, names.size());

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getConditionQuery().asString(), requestParameters.getOrganism(), Optional.<Set<String>>absent());

        assertThat(bioentityExpressions.size(), greaterThan(0));
        assertThat(bioentityExpressions.size(), lessThan(51));
        assertEquals(count, bioentityExpressions.getTotalNumberOfResults());
    }
    

    @Test
    public void weCanCheckAboutKinaseConnectedToCancer()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("kinase"));
        requestParameters.setExactMatch(false);
        requestParameters.setCondition("cancer");

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getConditionQuery().asString(), species, Optional.<Set<String>>absent());
        List<String> names = getBioentityNames(bioentityExpressions);

        assertThat(bioentityExpressions.size(), greaterThan(10));
        int matches = 0;
        for(String s: Lists.newArrayList(
                "Inmt","Egfl6","Napsa","Pon1","Clec2g","Cd36","Vsig2","St8sia6","Ctse","Sox9","Sgce","Wfdc18",
                "Esam","Pon1","Pax9","Syt5","Slfn4","Sox9","Phyhip","Irx2","Slc2a3","Syt5","S100a6","Mx1","Man1a","Gpa33","Slc9a3r2","Sema4f","Ssbp2","Txnrd3","Alox12","Vpreb3","Tspan33","Mmp14","Scnn1g","Sec14l2","Acp5","Col6a1","Kcnn3","Ndrg1","Stard3nl","Cp","Sema4f","Epdr1","S100a6","Cyp51","Ccdc65","Vpreb3","Gstt1"
                )){
            if(names.contains(s)){
                matches++;
            }
        }
        assertThat(matches, greaterThan(9));
    }

}

