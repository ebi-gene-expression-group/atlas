package uk.ac.ebi.atlas.search.diffanalytics;


import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.Contrast;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
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

    public static List<String> getBioentityNames(DiffAnalyticsList bioentityExpressions) {
        List<String> names = Lists.newArrayList();
        for (DiffAnalytics bioentityExpression: bioentityExpressions) {
            names.add(bioentityExpression.getBioentityName());
        }
        return names;
    }


    @Test
    public void geneQuery2IDsDifferentSpecies()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("ENSMUSG00000091366", "AT5G26220"));

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());
        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(2));
        assertThat(names, contains("Gm17040", "AT5G26220/T19G15_70"));
    }

    @Test
    public void geneQuery2IDsSameSpecies()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("ENSMUSG00000000278", "ENSMUSG00000002985"));

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());
        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(bioentityExpressions, hasSize(4));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(4));
        assertThat(names, contains("SCPEP1","SCPEP1","Apoe","Scpep1"));
    }

    @Test
    public void geneQueryMiRNA()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("hsa-mir-136"));

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());

        // System.out.println(Joiner.on("\"" + "\", \"").join(names) + "\"");
        assertThat(bioentityExpressions, hasSize(0));
    }

    @Test
    public void geneQueryPhrase_ApoptoticProcess()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("apoptotic process"));

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());
        List<String> names = getBioentityNames(bioentityExpressions);

        assertThat(bioentityExpressions, hasSize(50));
        // System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");
        assertThat(names, contains("BAG6", "IL1A", "IL1A", "TNF", "TNF", "CD14", "IL2RA", "CD14", "IL1B", "TNFSF15", "PYCARD", "IL1B", "PHLDA2", "GSN", "ITGB2", "TGFBR2", "LY86", "PYCARD", "TGFBR2", "CTSC", "RNF130", "IL24", "IFNG", "TRAF1", "PAK1", "DRAM2", "TNFRSF9", "Tnfrsf11b", "TRAF1", "PHLDA2", "ARHGEF6", "C5AR1", "RNF130", "PAK1", "ITGB2", "IL2RA", "GRAMD4", "ZFP36L1", "TNFSF15", "TRAF4", "IFNG", "TNFSF14", "TMEM219", "MAP3K8", "FAS", "RFK", "PKN2", "MST4", "MEF2C", "JAK2"));
    }


    @Test
    public void geneQueryKeywordKinase()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("kinase"));
        requestParameters.setExactMatch(false);

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());
        List<String> names = getBioentityNames(bioentityExpressions);

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(1647));
        // System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");
        assertThat(names, contains("IL12B", "IL6", "IL6", "IL1A", "IL23A", "CCL19", "IL1A", "CSF3", "CD36", "CD36", "IL23A", "F3", "TNF", "TNF", "CNKSR3", "CD14", "NKX3-1", "PDGFA", "CSF1R", "SORL1", "TNIP3", "CD14", "FCER1A", "png", "IL1B", "CSF1R", "TNFSF15", "PYCARD", "PFK3", "F1O17.1", "IL1B", "Hsp89.1", "H2AFY", "SORL1", "SLAMF8", "CG8173", "IL1RN", "AKN2", "TNIP3", "PKIB", "LRRK2", "KIAA1199", "NRG1", "LY96", "TLR7", "ITGB2", "SNX9", "CCL19", "PKIB", "TGFBR2"));
    }


    @Test
    public void geneQueryKeywordProteinCoding()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("protein_coding"));

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());

        List<String> names = getBioentityNames(bioentityExpressions);

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(12400));

        //System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        // match in any order because order differs between ATLAS3DEV and ATLAS3IT.
        // order is unpredictable in Oracle when rows have the same orderby value.
        assertThat(Iterables.limit(names, 5), containsInAnyOrder("Lactbl1", "Prok1", "Kdm5d", "Ddx3y", "Eif2s3y"));

        // match the remaining in order, which will be the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.skip(names, 5), contains("Uty", "MMP1", "HSP17.6C", "HSP26.5", "IL12B", "HSP21", "ATHSP22.0", "Hop3", "ATHSP23.6-MITO", "ATHSP17.4", "HSP23.5", "IL6", "Hsp70b", "F15H11.19", "ATHSP101", "PTGS2", "MMP10", "IL6", "BAG6", "APX2", "FN1", "CCR2", "AT-HSFA7A", "RNASE6", "CCR2", "CCL20", "HSP17.6II", "TFPI2", "Femcoat", "FN1", "ROF2", "DRG2", "HSP70T-2", "HSP17.6B", "ATHSFA2", "CD9", "FCN1", "FGL2", "PI3", "IL1A", "FPR3", "IL23A", "AT3G48131", "F10O3.11", "FCGR2B"));
    }


    @Test
    public void visitEachExpressionGeneQueryMiRNA()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("hsa-mir-136"));

        final List<String> names = Lists.newArrayList();

        int count = diffAnalyticsSearchService.visitEachExpression(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), requestParameters.getOrganism(), requestParameters.isExactMatch(), new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });

        assertThat(count, is(0));
    }

    @Test
    public void visitEachExpressionConditionAdultOrganismHomosapiens()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("adult");
        requestParameters.setOrganism("Homo sapiens");

        final List<String> names = Lists.newArrayList();

        int count = diffAnalyticsSearchService.visitEachExpression(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), requestParameters.getOrganism(), requestParameters.isExactMatch(), new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });

        //System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");
        assertThat(count, is(9859));
        assertThat(names, hasSize(9859));
    }

    @Test
    @Ignore //TODO: re-enable when performance fixed
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

        assertThat(names, hasSize(482));
        //System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");
        assertThat(names, contains("Arl8b", "Ddx3y", "Eif2s3y", "Uty", "Kdm5d", "Cldn8", "Lactbl1", "Tph1", "Ivd", "Fmo1", "Matn2", "Chgb", "Cish", "Lrrc55", "Neb", "Ogdhl", "Ehhadh", "Wipi1", "Rgs2", "Gpnmb", "Tmem255a", "Gpr26", "Gpx6", "Reg3b", "Vip", "Prlr", "Dnahc8", "Hsbp1", "Cst7", "Tnfrsf11b", "Npas4", "Dnajb1", "Enpp2", "Sftpd", "Reg3a", "Disp2", "Igfals", "Itgax", "Mpeg1", "B3galnt1", "Ikzf4", "Nr4a1", "Lgals3", "Dnase1", "Lpl", "Cspg5", "Dnaja1", "Ern1", "Ch25h", "Dhcr7"));

    }

    @Test
    public void conditionPregnant()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("pregnant");

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());
        List<String> names = getBioentityNames(bioentityExpressions);

       // System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(50));

        // match in any order because order differs between ATLAS3DEV and ATLAS3IT.
        // order is unpredictable in Oracle when rows have the same order by value.
        assertThat(Iterables.limit(names, 3), containsInAnyOrder("Gbp11", "Prok1", "Lactbl1"));

        // match the remaining in order, which will be the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.skip(names, 3), contains("Gpr26", "Lrrc55", "Tph1", "Cldn8", "Gm17040", "Fmo1", "Sftpd", "Lonrf3", "Neb", "Tnfrsf11b", "A130066N16Rik", "Fmo4", "Gm13716", "Mctp1", "Tph2", "Ivd", "Matn2", "Dnahc8", "Cspg5", "Gbp8", "Cish", "Ehhadh", "Reg3b", "Cartpt", "Grem2", "Chgb", "Reg3a", "Tmem255a", "Ikzf4", "Gm16314", "Grp", "Ovol2", "Cntn3", "mt-Rnr2", "Synpr", "Npas4", "Txnrd2", "Acvr1c", "Rnf182", "Syce2", "Aqp4", "Grhl1", "Wipi1", "Rgs2", "Ogdhl", "Gas2", "Dnmt3b"));

    }

    @Test
    public void conditionAdultOrganismHomosapiens()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("adult");
        requestParameters.setOrganism("Homo sapiens");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), requestParameters.getOrganism(), requestParameters.isExactMatch());

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(9859));
    }

    @Test
    public void conditionAND()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("\"Mus musculus\" AND \"wild type\"");

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());

        assertThat(bioentityExpressions, hasSize(50));
    }

    @Test
    public void geneQueryKinaseAndConditionPregnant()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("kinase"));
        requestParameters.setExactMatch(false);
        requestParameters.setCondition("pregnant");

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());
        List<String> names = getBioentityNames(bioentityExpressions);

        assertThat(bioentityExpressions, hasSize(11));
        // System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");
        assertThat(names, contains("Cish", "Acvr1c", "Rgs2", "Prlr", "Dnaja1", "Igfbp5", "Prkd1", "Ern1", "Lrp8", "Dusp1", "Prkar2b"));
    }

    @Test
    public void onlyShowTopGeneContrastCombination()  {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery(GeneQuery.create("Cct4"));

        String species = "";
        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters.getGeneQuery().asString(), requestParameters.getConditionQuery().asString(), species, requestParameters.isExactMatch());

        assertThat(bioentityExpressions, hasSize(1));

        DiffAnalytics cct4 = bioentityExpressions.get(0);

        assertThat(cct4.getBioentityName(), is("Cct4"));
        assertThat(cct4.getExpression().getFoldChange(), is(1.40000766666667));
    }

}

