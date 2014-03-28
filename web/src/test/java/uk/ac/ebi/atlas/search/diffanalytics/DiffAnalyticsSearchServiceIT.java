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
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.DifferentialConditionsSearchService;
import uk.ac.ebi.atlas.trader.ContrastTrader;
import uk.ac.ebi.atlas.utils.Visitor;
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

    @Before
    public void mockOutContrastTrader() {
        //mock out contrast trader so as not to load experiments, so we don't need experiment data files and also for performance
        MockitoAnnotations.initMocks(this);
        when(contrastTraderMock.getContrast(anyString(), anyString())).thenReturn(contrastMock);
        DiffAnalyticsRowMapper dbeRowMapper = new DiffAnalyticsRowMapper(contrastTraderMock);
        DiffAnalyticsDao diffAnalyticsDao = new DiffAnalyticsDao(dataSource, dbeRowMapper);
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
    public void geneQuery2IDsDifferentSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSMUSG00000091366 AT5G26220");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(2));
        assertThat(names, contains("Gm17040", "AT5G26220/T19G15_70"));
    }

    @Test
    public void geneQuery2IDsSameSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSMUSG00000000278 ENSMUSG00000002985");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(bioentityExpressions, hasSize(2));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(2));
        assertThat(names, contains("Apoe","Scpep1"));
    }

    @Test
    public void geneQueryMiRNA() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("hsa-mir-136");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println(Joiner.on("\", \"").join(names));

        assertThat(bioentityExpressions, hasSize(1));
        assertThat(names, contains("MIMAT0000448"));
    }

    @Test
    public void geneQueryKeywordWithQuotesApoptoticProcess() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("\"apoptotic process\"");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(23));
        assertThat(names, contains("Tnfrsf11b", "Lcn2", "Dnase1", "Tnfrsf12a", "Trp53inp1", "Gas2", "Apip", "Gja1", "Comp", "Srgn", "Eif2ak3", "Mknk2", "Kras", "Nr4a1", "Arf6", "Tgfbr1", "Sgms1", "Prkd1", "Mef2a", "Ern1", "Slc40a1", "Csrnp1", "Dab2"));
    }


    @Test
    public void geneQueryKeywordKinase() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("kinase");
        requestParameters.setExactMatch(false);

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(120));
        assertThat(names, contains("Ccl3", "Cish", "Cdkn1a", "AT5G24240", "Thbs1", "Jun", "Pdk4", "Kitl", "Acvr1c", "Rgs2", "Irs1", "Apoe", "FRK1", "AT4G18250", "Dusp1", "CPK29", "Gja1", "Errfi1", "T7B11.18", "AT1G51890", "Tyrobp", "F19C24.15", "Ddr1", "Plek", "S100a4", "F13E7.26", "LECRK18", "AT1G74360", "ATMPK11", "AT1G51790", "Eif2ak3", "Mknk2", "Igbp1", "AT4G11890/T26M18_100", "Fos", "Chp1", "LRR-RLK", "Kras", "Igfbp5", "CPK6", "MZB10.4", "T14L22.6", "Igf1", "CRK34", "Hsp90ab1", "F15B8.110", "WAKL8", "CRK29", "AT1G35710", "CIPK23"));

    }


    @Test
    public void geneQueryKeywordProteinCoding() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("protein_coding");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);

        List<String> names = getBioentityNames(bioentityExpressions);
        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(482));

        // match in any order because order differs between ATLAS3DEV and ATLAS3IT.
        // order is unpredictable in Oracle when rows have the same order by value.
        assertThat(Iterables.limit(names, 5), containsInAnyOrder("Lactbl1", "Prok1", "Kdm5d", "Eif2s3y", "Ddx3y"));

        // match the remaining in order, which will be the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.skip(names, 5), contains("Uty", "Gpr26", "Lrrc55", "Tph1", "Scgb1b27", "Cldn8", "Hmgcs2", "Acot1", "Fmo1", "Scgb2b27", "Sftpd", "Cst7", "Lonrf3", "Itgax", "Neb", "Tnfrsf11b", "Lcn2", "Fmo4", "Ccl4", "Ccl3", "Mctp1", "1700017B05Rik", "Ch25h", "Tph2", "Ivd", "Matn2", "Dnahc8", "Serpinf2", "Abca1", "Cspg5", "Gpnmb", "Gbp8", "Cish", "Lgals3", "Ang", "Cdkn1a", "Ehhadh", "Gal", "Reg3b", "Cartpt", "Socs2", "Thbs1", "Arg1", "Wisp2", "Lgals3"));

    }


    @Test
    public void visitEachExpressionGeneQueryMiRNA() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("hsa-mir-136");

        final List<String> names = Lists.newArrayList();

        int count = diffAnalyticsSearchService.visitEachExpression(requestParameters, new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(count, is(1));
        assertThat(names, hasSize(1));
        assertThat(names, contains("MIMAT0000448"));

    }

    @Test
    @Ignore //TODO: re-enable when performance fixed
    public void visitEachExpressionGeneQueryKeywordProteinCoding() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("protein_coding");

        final List<String> names = Lists.newArrayList();

        diffAnalyticsSearchService.visitEachExpression(requestParameters, new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });

        assertThat(names, hasSize(482));

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("Arl8b", "Ddx3y", "Eif2s3y", "Uty", "Kdm5d", "Cldn8", "Lactbl1", "Tph1", "Ivd", "Fmo1", "Matn2", "Chgb", "Cish", "Lrrc55", "Neb", "Ogdhl", "Ehhadh", "Wipi1", "Rgs2", "Gpnmb", "Tmem255a", "Gpr26", "Gpx6", "Reg3b", "Vip", "Prlr", "Dnahc8", "Hsbp1", "Cst7", "Tnfrsf11b", "Npas4", "Dnajb1", "Enpp2", "Sftpd", "Reg3a", "Disp2", "Igfals", "Itgax", "Mpeg1", "B3galnt1", "Ikzf4", "Nr4a1", "Lgals3", "Dnase1", "Lpl", "Cspg5", "Dnaja1", "Ern1", "Ch25h", "Dhcr7"));

    }

    @Test
    public void conditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("pregnant");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(50));

        // match in any order because order differs between ATLAS3DEV and ATLAS3IT.
        // order is unpredictable in Oracle when rows have the same order by value.
        assertThat(Iterables.limit(names, 3), containsInAnyOrder("Gbp11", "Prok1", "Lactbl1"));

        // match the remaining in order, which will be the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.skip(names, 3), contains("Gpr26", "Lrrc55", "Tph1", "Cldn8", "Gm17040", "Fmo1", "Sftpd", "Lonrf3", "Neb", "Tnfrsf11b", "A130066N16Rik", "Fmo4", "Gm13716", "Mctp1", "Tph2", "Ivd", "Matn2", "Dnahc8", "Cspg5", "Gbp8", "Cish", "Ehhadh", "Reg3b", "Cartpt", "Grem2", "Chgb", "Reg3a", "Tmem255a", "Ikzf4", "Gm16314", "Grp", "Ovol2", "Cntn3", "mt-Rnr2", "Synpr", "Npas4", "Txnrd2", "Acvr1c", "Rnf182", "Syce2", "Aqp4", "Grhl1", "Wipi1", "Rgs2", "Ogdhl", "Gas2", "Dnmt3b"));

    }


    @Test
    public void conditionAND() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("\"Mus musculus\" AND \"wild type\"");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
    }

    @Test
    public void geneQueryKinaseAndConditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("kinase");
        requestParameters.setExactMatch(false);
        requestParameters.setCondition("pregnant");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);
        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(bioentityExpressions, hasSize(9));
        assertThat(names, contains("Cish", "Acvr1c", "Rgs2", "Igfbp5", "Prkd1", "Ern1", "Lrp8", "Dusp1", "Prkar2b"));
    }

    @Test
    public void onlyShowTopGeneContrastCombination() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("Cct4");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.fetchTop(requestParameters);

        assertThat(bioentityExpressions, hasSize(1));

        DiffAnalytics cct4 = bioentityExpressions.get(0);

        assertThat(cct4.getBioentityName(), is("Cct4"));
        assertThat(cct4.getExpression().getFoldChange(), is(1.40000766666667));
    }

}

