package uk.ac.ebi.atlas.search.diffanalytics;


import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsSearchServiceIT {

    @Inject
    DiffAnalyticsSearchService diffAnalyticsSearchService;

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
        requestParameters.setGeneQuery("ENSG00000161547 ENSMUSG00000030105");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(6));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(names, contains("Arl8b", "Arl8b", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306"));
    }

    @Test
    public void geneQuery2IDsSameSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSG00000161547 ENSG00000211855");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(4));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(4));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306"));
    }

    @Test
    public void geneQueryMiRNA() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("hsa-mir-636");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(4));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306"));
    }

    @Test
    public void geneQueryKeywordWithQuotesZincFinger() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("\"zinc finger\"");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(21));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(names, contains("Zfp260", "Zfp503", "Zfp758", "Zfp292", "Zfp948", "Zfp68", "Zfp292", "Zfp260", "Zfp810", "Zfp703", "Zfp617", "Zfp568", "Gli2", "Zfp46", "Zfp109", "Zfp354a", "Zfp146", "Zfp68", "Zfx", "Rlf", "Zfp92"));
    }


    @Test
    public void geneQueryKeywordKinase() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("kinase");
        requestParameters.setExactMatch(false);

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(697));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(names, contains("Ccl3", "Cish", "Cdkn1a", "AT5G24240", "Thbs1", "Jun", "Pdk4", "Kitl", "Acvr1c", "Rgs2", "Irs1", "Apoe", "FRK1", "AT4G18250", "Dusp1", "CPK29", "Gja1", "Errfi1", "T7B11.18", "AT1G51890", "Tyrobp", "F19C24.15", "Ddr1", "Jun", "Plek", "S100a4", "F13E7.26", "LECRK18", "AT1G74360", "ATMPK11", "AT1G51790", "Eif2ak3", "Mknk2", "Igbp1", "AT4G11890/T26M18_100", "Fos", "Chp1", "LRR-RLK", "Kras", "Igfbp5", "CPK6", "MZB10.4", "T14L22.6", "Igf1", "CRK34", "Hsp90ab1", "F15B8.110", "WAKL8", "CRK29", "AT1G35710"));

    }


    @Test
    public void geneQueryKeywordProteinCoding() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("protein_coding");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(4347));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        // match in any order because order differs between ATLAS3DEV and ATLAS3IT.
        // order is unpredictable in Oracle when rows have the same order by value.
        assertThat(Iterables.limit(names, 5), containsInAnyOrder("Lactbl1", "Prok1", "Kdm5d", "Eif2s3y", "Ddx3y"));

        // match the remaining in order, which will be the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.skip(names, 5), contains("Uty", "Gpr26", "Lrrc55", "Tph1", "Scgb1b27", "Cldn8", "Hmgcs2", "Acot1", "Fmo1", "Scgb2b27", "Sftpd", "Cst7", "Lonrf3", "Itgax", "Neb", "Tnfrsf11b", "Lcn2", "Fmo4", "Ccl4", "Hmgcs2", "Ccl3", "Mctp1", "1700017B05Rik", "Ch25h", "Tph2", "Ivd", "Matn2", "Dnahc8", "Serpinf2", "Abca1", "Cspg5", "Gpnmb", "Gbp8", "Cish", "Lgals3", "Ang", "Cdkn1a", "Ehhadh", "Gal", "Reg3b", "Cartpt", "Socs2", "Thbs1", "Arg1", "Wisp2"));

    }


    @Test
    public void forEachExpressionGeneQueryMiRNA() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("hsa-mir-636");

        final List<String> names = Lists.newArrayList();

        int count = diffAnalyticsSearchService.forEachExpression(requestParameters, new Visitor<DiffAnalytics>() {

            @Override
            public void visit(DiffAnalytics value) {
                names.add(value.getBioentityName());
            }
        });

        assertThat(count, is(4));
        assertThat(names, hasSize(4));
        assertThat(names, contains("MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306"));

    }

    @Test
    @Ignore //TODO: re-enable when performance fixed
    public void forEachExpressionGeneQueryKeywordProteinCoding() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("protein_coding");

        final List<String> names = Lists.newArrayList();

        diffAnalyticsSearchService.forEachExpression(requestParameters, new Visitor<DiffAnalytics>() {

            int count = 0;

            @Override
            public void visit(DiffAnalytics value) {
                System.out.print(++count + "\t");
                names.add(value.getBioentityName());
            }
        });

        assertThat(names, hasSize(4347));

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("Arl8b", "Ddx3y", "Eif2s3y", "Uty", "Kdm5d", "Cldn8", "Lactbl1", "Tph1", "Ivd", "Fmo1", "Matn2", "Chgb", "Cish", "Lrrc55", "Neb", "Ogdhl", "Ehhadh", "Wipi1", "Rgs2", "Gpnmb", "Tmem255a", "Gpr26", "Gpx6", "Reg3b", "Vip", "Prlr", "Dnahc8", "Hsbp1", "Cst7", "Tnfrsf11b", "Npas4", "Dnajb1", "Enpp2", "Sftpd", "Reg3a", "Disp2", "Igfals", "Itgax", "Mpeg1", "B3galnt1", "Ikzf4", "Nr4a1", "Lgals3", "Dnase1", "Lpl", "Cspg5", "Dnaja1", "Ern1", "Ch25h", "Dhcr7"));

    }

    @Test
    public void geneQueryKeywordApoptosis() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("apoptosis");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

        assertThat(names, contains("Gas2", "Psme1", "Bax", "Psmb10", "Lmna", "Psmd11", "Akt1", "Rock1", "Vim", "Rock1", "Psmc6", "Ywhaz", "Gsn", "Stk24", "Psmd6", "Ywhag", "Fnta", "Bcap31", "Sptan1", "Kpnb1", "Psmd7", "Mapt", "Mapt", "Psmc4", "Prkcd", "Xiap", "Dynll2", "Psmd11", "Akt1", "Psmd4", "PSMA7", "Psmd4", "Vim", "Vim", "Bcl2l11", "Gsn", "Psmc4", "Lmnb1", "Rock1", "Bcl2l11", "Gsn", "Ywhab", "Tradd", "Ywhab", "Ywhab", "Apc", "Bcl2", "Psmd14", "Sptan1", "Tnf"));
    }


    @Test
    public void conditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("pregnant");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println("\"" + Joiner.on("\", \"").join(names) + "\"");

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

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
    }

    @Test
    public void geneQueryApoptosisAndConditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("apoptosis");
        requestParameters.setCondition("pregnant");

        DiffAnalyticsList bioentityExpressions = diffAnalyticsSearchService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(2));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("Gas2", "Akt1"));
    }

}

