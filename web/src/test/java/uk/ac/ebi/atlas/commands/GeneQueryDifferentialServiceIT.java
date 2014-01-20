package uk.ac.ebi.atlas.commands;


import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialBioentityExpressions;
import uk.ac.ebi.atlas.utils.Visitor;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class GeneQueryDifferentialServiceIT {

    @Inject
    GeneQueryDifferentialService geneQueryDifferentialService;

    public static List<String> getBioentityNames(DifferentialBioentityExpressions bioentityExpressions) {
        List<String> names = Lists.newArrayList();
        for (DifferentialBioentityExpression bioentityExpression: bioentityExpressions) {
            names.add(bioentityExpression.getBioentityName());
        }
        return names;
    }


    @Test
    public void geneQuery2IDsDifferentSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSG00000161547 ENSMUSG00000030105");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(6));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("Arl8b", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "Arl8b"));
    }

    @Test
    public void geneQuery2IDsSameSpecies() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("ENSG00000161547 ENSG00000211855");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

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

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(4));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("MIMAT0003306", "MIMAT0003306", "MIMAT0003306", "MIMAT0003306"));
    }

    @Test
    public void geneQueryKeywordWithQuotesZincFinger() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("\"zinc finger\"");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(21));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("Zfp292", "Zfp503", "Zfp810", "Zfp758", "Zfp46", "Zfp68", "Zfp703", "Zfp146", "Zfp260", "Zfp948", "Zfp260", "Rlf", "Zfp568", "Zfp354a", "Zfp617", "Zfx", "Zfp109", "Zfp292", "Gli2", "Zfp68", "Zfp92"));
    }


    @Test
    public void geneQueryKeywordKinase() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("kinase");
        requestParameters.setExactMatch(false);

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(697));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        // match the first 10 only, because they are the only ones the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.limit(names, 10), contains("Cish", "Rgs2", "Ern1", "Cdkn1a", "Igfbp5", "Dusp1", "Kitl", "Apoe", "Tyrobp", "Hsp90ab1"));

    }


    // NB: this will fail on Atlas3Dev (but not Atlas3It) because order of "Tph1 and "Lactbl1" is reversed
    @Test
    public void geneQueryKeywordProteinCoding() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("protein_coding");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
        assertThat(bioentityExpressions.getTotalNumberOfResults(), is(4347));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        // NB: this will fail on Atlas3Dev (but not Atlas3It) because order of "Tph1 and "Lactbl1" is reversed
        assertThat(names, contains("Arl8b", "Ddx3y", "Eif2s3y", "Uty", "Kdm5d", "Cldn8", "Lactbl1", "Tph1", "Ivd", "Fmo1", "Matn2", "Chgb", "Cish", "Lrrc55", "Neb", "Ogdhl", "Ehhadh", "Wipi1", "Rgs2", "Gpnmb", "Tmem255a", "Gpr26", "Gpx6", "Reg3b", "Vip", "Prlr", "Dnahc8", "Hsbp1", "Cst7", "Tnfrsf11b", "Npas4", "Dnajb1", "Enpp2", "Sftpd", "Reg3a", "Disp2", "Igfals", "Itgax", "Mpeg1", "B3galnt1", "Ikzf4", "Nr4a1", "Lgals3", "Dnase1", "Lpl", "Cspg5", "Dnaja1", "Ern1", "Ch25h", "Dhcr7"));

    }


    @Test
    public void forEachExpressionGeneQueryMiRNA() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("hsa-mir-636");

        final List<String> names = Lists.newArrayList();

        int count = geneQueryDifferentialService.forEachExpression(requestParameters, new Visitor<DifferentialBioentityExpression>() {

            @Override
            public void visit(DifferentialBioentityExpression value) {
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

        geneQueryDifferentialService.forEachExpression(requestParameters, new Visitor<DifferentialBioentityExpression>() {

            int count = 0;

            @Override
            public void visit(DifferentialBioentityExpression value) {
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

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println(Joiner.on("\", \"").join(names));

        // match the first 11 only, because they are the only ones the same in both ATLAS3DEV and ATLAS3IT
        assertThat(Iterables.limit(names,11), contains("Gas2", "Psmb10", "Bax", "Vim", "Rock1", "Psmc6", "Ywhag", "Psmd6", "Psme1", "Bcap31", "Fnta"));
    }


    // NB: this will fail on Atlas3Dev (but not Atlas3It) because order of "Tph1 and "Lactbl1" is reversed
    @Test
    public void conditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("pregnant");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));

        List<String> names = getBioentityNames(bioentityExpressions);

        System.out.println(Joiner.on("\", \"").join(names));

        // match the first 46 only, because they are the only ones the same in both ATLAS3DEV and ATLAS3IT
        // NB: this will fail on Atlas3Dev (but not Atlas3It) because order of "Tph1 and "Lactbl1" is reversed
        assertThat(Iterables.limit(names, 46), contains("Cldn8", "Lactbl1", "Tph1", "Ivd", "Fmo1", "Matn2", "Chgb", "Cish", "Lrrc55", "Neb", "Ogdhl", "Ehhadh", "Wipi1", "Rgs2", "Tmem255a", "Gpr26", "Reg3b", "Vip", "Prlr", "Dnahc8", "Hsbp1", "Tnfrsf11b", "Npas4", "Dnajb1", "Enpp2", "Sftpd", "Reg3a", "Disp2", "Igfals", "B3galnt1", "Ikzf4", "Nr4a1", "Cspg5", "Dnaja1", "Ern1", "Gm13716", "Dhcr7", "Junb", "Gm16211", "Aqp4", "Ovol2", "Hspa1a", "Igfbp5", "Lonrf3", "Nupr1", "Dusp1"));
    }

    @Test
    public void conditionAND() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("\"Mus musculus\" AND \"wild type\"");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(50));
    }

    @Test
    public void geneQueryApoptosisAndConditionPregnant() throws GenesNotFoundException {
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("apoptosis");
        requestParameters.setCondition("pregnant");

        DifferentialBioentityExpressions bioentityExpressions = geneQueryDifferentialService.query(requestParameters);

        assertThat(bioentityExpressions, hasSize(2));

        List<String> names = getBioentityNames(bioentityExpressions);

        //System.out.println(Joiner.on("\", \"").join(names));

        assertThat(names, contains("Gas2", "Akt1"));
    }

}

