package uk.ac.ebi.atlas.streams.baseline;

import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.math.util.MathUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.*;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(MockitoJUnitRunner.class)
public class RankAndPipeBaselineProfilesTest {

    public static final double POLR2B_LEUKOCYTE = 47D;
    public static final double SNRPA_LEUKOCYTE = 19D;
    public static final double CCNT2_LEUKOCYTE = 9D;
    public static final double ZKSCAN5_LEUKOCYTE = 5D;
    private static final double NUMBER_OF_GENES_IN_GENE_SET = 7;
    private static final int NUMBER_OF_FRACTIONAL_DIGITS = 0;
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");

    private GeneSetBaselineProfilesBuilder geneSetBaselineProfilesBuilder = new GeneSetBaselineProfilesBuilder(new GeneSetFactory(new GeneSetBaselineProfileBuilder()));
    private RankAndPipeBaselineProfiles subject = new RankAndPipeBaselineProfiles(new BaselineProfilesPipelineBuilder(geneSetBaselineProfilesBuilder), new RankBaselineProfiles());

    private GeneProfileInputStreamEMTab513React71 eMTab513react71InputStream = new GeneProfileInputStreamEMTab513React71(0.5);

    private ImmutableSetMultimap<String, String> react71GeneIds = ImmutableSetMultimap.<String, String>builder().putAll("react_71", "ENSG00000196652", "ENSG00000082258", "ENSG00000047315", "ENSG00000077312", "ENSG00000198939", "ENSG00000178665", "ENSG00000161547").build();

    @Before
    public void before() {
        subject.inputStream(eMTab513react71InputStream);
        subject.allQueryFactors(eMTab513react71InputStream.getOrderedFactorGroups().extractFactors());
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true
    @Test
    public void eMTab513react71_Specific() {
        subject.isSpecific();
        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("SRSF2", "ZNF713", "ZFP2", "POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        BaselineProfile srsf2 = profiles.get(0);
        BaselineProfile znf713 = profiles.get(1);
        BaselineProfile zfp2 = profiles.get(2);
        BaselineProfile polr2b = profiles.get(3);
        BaselineProfile snrpa = profiles.get(4);
        BaselineProfile ccnt2 = profiles.get(5);
        BaselineProfile zkscan5 = profiles.get(6);
        assertThat(srsf2.getName(), is("SRSF2"));
        assertThat(znf713.getName(), is("ZNF713"));
        assertThat(zfp2.getName(), is("ZFP2"));
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(srsf2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on
    @Test
    public void eMTab513react71_NotSpecific() {
        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5", "ZFP2", "ZNF713", "SRSF2"));

        BaselineProfile srsf2 = profiles.get(6);
        BaselineProfile znf713 = profiles.get(5);
        BaselineProfile zfp2 = profiles.get(4);
        BaselineProfile polr2b = profiles.get(0);
        BaselineProfile snrpa = profiles.get(1);
        BaselineProfile ccnt2 = profiles.get(2);
        BaselineProfile zkscan5 = profiles.get(3);
        assertThat(srsf2.getName(), is("SRSF2"));
        assertThat(znf713.getName(), is("ZNF713"));
        assertThat(zfp2.getName(), is("ZFP2"));
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(srsf2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet() {
        subject.isSpecific();
        subject.averageIntoGeneSets(react71GeneIds);

        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);

    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet() {
        subject.averageIntoGeneSets(react71GeneIds);

        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_Specific_QueryFactorLeukocyte() throws GenesNotFoundException {
        subject.isSpecific();
        subject.queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("POLR2B"));

        BaselineProfile polr2b = profiles.get(0);

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException {
        subject.queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        BaselineProfile polr2b = profiles.get(0);
        BaselineProfile snrpa = profiles.get(1);
        BaselineProfile ccnt2 = profiles.get(2);
        BaselineProfile zkscan5 = profiles.get(3);
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException {
        subject.isSpecific();
        subject.queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));
        subject.averageIntoGeneSets(react71GeneIds);

        BaselineProfilesList profiles = subject.fetch();

        // no results because Leukoctye is not specific to react_71
        assertThat(profiles, is(empty()));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=prostate&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorProstateGeneSet_CheckExpressionLevelsForReact71() throws GenesNotFoundException {
        subject.isSpecific();
        subject.queryFactors(Collections.singleton(factor("prostate")));
        subject.averageIntoGeneSets(react71GeneIds);

        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException {
        subject.queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));
        subject.averageIntoGeneSets(react71GeneIds);

        BaselineProfilesList profiles = subject.fetch();

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    private void checkAllPolr2bExpressionLevels(BaselineProfile polr2b) {
        assertThat(polr2b.getExpressionLevel(factor("adipose")), is(16D));
        assertThat(polr2b.getExpressionLevel(factor("adrenal gland")), is(30D));
        assertThat(polr2b.getExpressionLevel(factor("brain")), is(24D));
        assertThat(polr2b.getExpressionLevel(factor("breast")), is(18D));
        assertThat(polr2b.getExpressionLevel(factor("colon")), is(20D));
        assertThat(polr2b.getExpressionLevel(factor("heart")), is(26D));
        assertThat(polr2b.getExpressionLevel(factor("kidney")), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(factor("leukocyte")), is(47D));
        assertThat(polr2b.getExpressionLevel(factor("liver")), is(12D));
        assertThat(polr2b.getExpressionLevel(factor("lung")), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(factor("lymph node")), is(21D));
        assertThat(polr2b.getExpressionLevel(factor("ovary")), is(25D));
        assertThat(polr2b.getExpressionLevel(factor("prostate")), is(24D));
        assertThat(polr2b.getExpressionLevel(factor("skeletal muscle")), is(28D));
        assertThat(polr2b.getExpressionLevel(factor("testis")), is(33D));
        assertThat(polr2b.getExpressionLevel(factor("thyroid")), is(38D));
    }


    private void checkAllReact71ExpressionLevels(BaselineProfile react71) {
        assertThat(react71.getExpressionLevel(factor("adipose")), is(7D));
        assertThat(react71.getExpressionLevel(factor("adrenal gland")), is(10D));
        assertThat(react71.getExpressionLevel(factor("brain")), is(6D));
        assertThat(react71.getExpressionLevel(factor("breast")), is(6D));
        assertThat(react71.getExpressionLevel(factor("colon")), is(8D));
        assertThat(react71.getExpressionLevel(factor("heart")), is(6D));
        assertThat(react71.getExpressionLevel(factor("kidney")), is(4D));
        assertThat(react71.getExpressionLevel(FACTOR_LEUKOCYTE), is(MathUtils.round((POLR2B_LEUKOCYTE + SNRPA_LEUKOCYTE + CCNT2_LEUKOCYTE + ZKSCAN5_LEUKOCYTE) / NUMBER_OF_GENES_IN_GENE_SET, NUMBER_OF_FRACTIONAL_DIGITS)));
        assertThat(react71.getExpressionLevel(factor("liver")), is(4D));
        assertThat(react71.getExpressionLevel(factor("lung")), is(6D));
        assertThat(react71.getExpressionLevel(factor("lymph node")), is(9D));
        assertThat(react71.getExpressionLevel(factor("ovary")), is(9D));
        assertThat(react71.getExpressionLevel(factor("prostate")), is(17D));
        assertThat(react71.getExpressionLevel(factor("skeletal muscle")), is(11D));
        assertThat(react71.getExpressionLevel(factor("testis")), is(11D));
        assertThat(react71.getExpressionLevel(factor("thyroid")), is(8D));
    }

    private Factor factor(String factorValue) {
        return new Factor("ORGANISM_PART", factorValue);
    }

}
