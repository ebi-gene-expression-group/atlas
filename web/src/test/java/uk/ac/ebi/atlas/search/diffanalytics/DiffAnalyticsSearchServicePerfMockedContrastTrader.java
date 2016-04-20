package uk.ac.ebi.atlas.search.diffanalytics;


import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.search.OracleObjectFactory;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.web.GeneQuery;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsSearchServicePerfMockedContrastTrader {

    DiffAnalyticsSearchService diffAnalyticsSearchService;

    @Mock
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

    @Mock
    JdbcTemplate jdbcTemplate;

    @Before
    public void mockOutContrastTrader() {
        //mock out contrast trader so as not to load experiments, so we don't need experiment data files and also for performance
        MockitoAnnotations.initMocks(this);
        when(contrastTraderMock.getContrast(anyString(), anyString())).thenReturn(contrastMock);
        DiffAnalyticsRowMapper dbeRowMapper = new DiffAnalyticsRowMapper(contrastTraderMock);


        DiffAnalyticsDao diffAnalyticsDao = new DiffAnalyticsDao(jdbcTemplate, dbeRowMapper, oracleObjectFactory);
        diffAnalyticsSearchService = new DiffAnalyticsSearchService(diffAnalyticsDao, differentialConditionsSearchService, solrQueryService);
    }

    @Test
    public void geneQueryKeywordProteinCoding()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("protein_coding"));
        String species = "";
        when(differentialConditionsSearchService.findContrasts(anyString())).thenReturn(new HashSet<IndexedAssayGroup>());
        diffAnalyticsSearchService.fetchTop(requestParameters.getConditionQuery().asString(), species, Optional.<Set<String>>absent());
    }


    @Test
    public void visitEachExpressionGeneQueryKeywordProteinCoding()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("protein_coding"));

        final List<String> names = Lists.newArrayList();

        diffAnalyticsSearchService.visitEachExpression(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), requestParameters.getOrganism(), requestParameters.isExactMatch(), new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });
    }

    @Test
    public void conditionHomoSapiens()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("Homo sapiens");
        String species = "";
        diffAnalyticsSearchService.fetchTop(requestParameters.getConditionQuery().asString(), species, Optional.<Set<String>>absent());
    }


    @Test
    public void geneQueryKeywordProteinCodingAndConditionHomoSapiens()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("protein_coding"));
        requestParameters.setCondition("Homo sapiens");
        String species = "";
        diffAnalyticsSearchService.fetchTop(requestParameters.getConditionQuery().asString(), species, Optional.<Set<String>>absent());
    }

}

