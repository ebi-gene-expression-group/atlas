package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.math.util.MathUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesEMTab513React71;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.*;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesHeatMapTest {

    public static final double POLR2B_LEUKOCYTE = 47D;
    public static final double SNRPA_LEUKOCYTE = 19D;
    public static final double CCNT2_LEUKOCYTE = 9D;
    public static final double ZKSCAN5_LEUKOCYTE = 5D;
    private static final double NUMBER_OF_GENES_IN_GENE_SET = 7;
    private static final int NUMBER_OF_FRACTIONAL_DIGITS = 0;
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");

    private BaselineProfileStreamPipelineBuilder baselineProfileStreamPipelineBuilder = new BaselineProfileStreamPipelineBuilder();
    private BaselineProfileComparatorFactory baselineProfileComparatorFactory = new BaselineProfileComparatorFactory();
    private BaselineProfilesListBuilder geneProfilesListBuilder = new BaselineProfilesListBuilder();
    private RankBaselineProfilesFactory rankProfilesFactory = new RankBaselineProfilesFactory(baselineProfileComparatorFactory, geneProfilesListBuilder);
    private BaselineProfilesHeatMap subject = new BaselineProfilesHeatMap(baselineProfileStreamPipelineBuilder, rankProfilesFactory, null);

    private BaselineProfilesEMTab513React71 eMTab513react71InputStream = new BaselineProfilesEMTab513React71(0.5);

    private ImmutableSetMultimap<String, String> react71GeneIds = ImmutableSetMultimap.<String, String>builder().putAll("react_71", "ENSG00000196652", "ENSG00000082258", "ENSG00000047315", "ENSG00000077312", "ENSG00000198939", "ENSG00000178665", "ENSG00000161547").build();

    @Mock
    GeneQueryResponse geneQueryResponse;

    @Mock
    BaselineProfileStreamOptions options;

    @Before
    public void before() {
        when(options.getHeatmapMatrixSize()).thenReturn(50);

        when(options.getAllQueryFactors()).thenReturn(eMTab513react71InputStream.getOrderedFactorGroups().extractFactors());
        when(geneQueryResponse.getQueryTermsToIds()).thenReturn(react71GeneIds);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true
    @Test
    public void eMTab513react71_Specific() {
        isSpecific();
        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.<GeneQueryResponse>absent());

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

        assertThat(srsf2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    private void isSpecific() {
        when(options.isSpecific()).thenReturn(true);
    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on
    @Test
    public void eMTab513react71_NotSpecific() {
        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.<GeneQueryResponse>absent());

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

        assertThat(srsf2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet() {
        isSpecific();

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.of(geneQueryResponse));

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);

    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet() {

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.of(geneQueryResponse));

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_Specific_QueryFactorLeukocyte() throws GenesNotFoundException {
        isSpecific();
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.<GeneQueryResponse>absent());

        assertThat(profiles.extractGeneNames(), contains("POLR2B"));

        BaselineProfile polr2b = profiles.get(0);

        checkAllPolr2bExpressionLevels(polr2b);
    }

    private void queryFactors(Set<Factor> factors) {
        when(options.getSelectedQueryFactors()).thenReturn(factors);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException {
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.<GeneQueryResponse>absent());

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        BaselineProfile polr2b = profiles.get(0);
        BaselineProfile snrpa = profiles.get(1);
        BaselineProfile ccnt2 = profiles.get(2);
        BaselineProfile zkscan5 = profiles.get(3);
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(polr2b.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException {
        isSpecific();
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.of(geneQueryResponse));

        // no results because Leukoctye is not specific to react_71
        assertThat(profiles, is(empty()));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=prostate&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorProstateGeneSet_CheckExpressionLevelsForReact71() throws GenesNotFoundException {
        isSpecific();
        queryFactors(Collections.singleton(factor("prostate")));

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.of(geneQueryResponse));

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException {
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(eMTab513react71InputStream, options, Optional.of(geneQueryResponse));

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        BaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    private void checkAllPolr2bExpressionLevels(BaselineProfile polr2b) {
        assertThat(polr2b.getKnownExpressionLevel(factor("adipose")), is(16D));
        assertThat(polr2b.getKnownExpressionLevel(factor("adrenal gland")), is(30D));
        assertThat(polr2b.getKnownExpressionLevel(factor("brain")), is(24D));
        assertThat(polr2b.getKnownExpressionLevel(factor("breast")), is(18D));
        assertThat(polr2b.getKnownExpressionLevel(factor("colon")), is(20D));
        assertThat(polr2b.getKnownExpressionLevel(factor("heart")), is(26D));
        assertThat(polr2b.getKnownExpressionLevel(factor("kidney")), is(nullValue()));
        assertThat(polr2b.getKnownExpressionLevel(factor("leukocyte")), is(47D));
        assertThat(polr2b.getKnownExpressionLevel(factor("liver")), is(12D));
        assertThat(polr2b.getKnownExpressionLevel(factor("lung")), is(nullValue()));
        assertThat(polr2b.getKnownExpressionLevel(factor("lymph node")), is(21D));
        assertThat(polr2b.getKnownExpressionLevel(factor("ovary")), is(25D));
        assertThat(polr2b.getKnownExpressionLevel(factor("prostate")), is(24D));
        assertThat(polr2b.getKnownExpressionLevel(factor("skeletal muscle")), is(28D));
        assertThat(polr2b.getKnownExpressionLevel(factor("testis")), is(33D));
        assertThat(polr2b.getKnownExpressionLevel(factor("thyroid")), is(38D));
    }


    private void checkAllReact71ExpressionLevels(BaselineProfile react71) {
        assertThat(react71.getKnownExpressionLevel(factor("adipose")), is(7D));
        assertThat(react71.getKnownExpressionLevel(factor("adrenal gland")), is(10D));
        assertThat(react71.getKnownExpressionLevel(factor("brain")), is(6D));
        assertThat(react71.getKnownExpressionLevel(factor("breast")), is(6D));
        assertThat(react71.getKnownExpressionLevel(factor("colon")), is(8D));
        assertThat(react71.getKnownExpressionLevel(factor("heart")), is(6D));
        assertThat(react71.getKnownExpressionLevel(factor("kidney")), is(4D));
        assertThat(react71.getKnownExpressionLevel(FACTOR_LEUKOCYTE), is(MathUtils.round((POLR2B_LEUKOCYTE + SNRPA_LEUKOCYTE + CCNT2_LEUKOCYTE + ZKSCAN5_LEUKOCYTE) / NUMBER_OF_GENES_IN_GENE_SET, NUMBER_OF_FRACTIONAL_DIGITS)));
        assertThat(react71.getKnownExpressionLevel(factor("liver")), is(4D));
        assertThat(react71.getKnownExpressionLevel(factor("lung")), is(6D));
        assertThat(react71.getKnownExpressionLevel(factor("lymph node")), is(9D));
        assertThat(react71.getKnownExpressionLevel(factor("ovary")), is(9D));
        assertThat(react71.getKnownExpressionLevel(factor("prostate")), is(17D));
        assertThat(react71.getKnownExpressionLevel(factor("skeletal muscle")), is(11D));
        assertThat(react71.getKnownExpressionLevel(factor("testis")), is(11D));
        assertThat(react71.getKnownExpressionLevel(factor("thyroid")), is(8D));
    }

    private Factor factor(String factorValue) {
        return new Factor("ORGANISM_PART", factorValue);
    }

}
