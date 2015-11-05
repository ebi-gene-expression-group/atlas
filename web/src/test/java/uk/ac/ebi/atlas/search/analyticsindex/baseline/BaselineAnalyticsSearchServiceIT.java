package uk.ac.ebi.atlas.search.analyticsindex.baseline;

import com.google.common.collect.ImmutableSortedSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfile;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentProfilesList;
import uk.ac.ebi.atlas.search.baseline.BaselineExperimentSearchResult;
import uk.ac.ebi.atlas.web.GeneQuery;

import javax.inject.Inject;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsSearchServiceIT {

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();

    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose tissue");public static final Factor CD4 = new Factor(ORGANISM_PART, "CD4-positive T cell");
    public static final Factor ADRENAL_GLAND = new Factor(ORGANISM_PART, "adrenal gland");
    public static final Factor ANIMAL_OVARY = new Factor(ORGANISM_PART, "animal ovary");
    public static final Factor APPENDIX = new Factor(ORGANISM_PART, "appendix");
    public static final Factor BLADDER = new Factor(ORGANISM_PART, "bladder");
    public static final Factor BONE_MARROW = new Factor(ORGANISM_PART, "bone marrow");
    public static final Factor CD8 = new Factor(ORGANISM_PART, "CD8-positive T cell");
    public static final Factor CEREBRAL_CORTEX = new Factor(ORGANISM_PART, "cerebral cortex");
    public static final Factor COLON = new Factor(ORGANISM_PART, "colon");
    public static final Factor DUODENUM = new Factor(ORGANISM_PART, "duodenum");
    public static final Factor ENDOMETRIUM = new Factor(ORGANISM_PART, "endometrium");
    public static final Factor ESOPHAGUS = new Factor(ORGANISM_PART, "esophagus");
    public static final Factor FALLOPIAN_TUBE = new Factor(ORGANISM_PART, "fallopian tube");
    public static final Factor GALL_BLADDER = new Factor(ORGANISM_PART, "gall bladder");
    public static final Factor HEART = new Factor(ORGANISM_PART, "heart");
    public static final Factor KIDNEY = new Factor(ORGANISM_PART, "kidney");
    public static final Factor LIVER = new Factor(ORGANISM_PART, "liver");
    public static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    public static final Factor LYMPH_NODE = new Factor(ORGANISM_PART, "lymph node");
    public static final Factor PANCREAS = new Factor(ORGANISM_PART, "pancreas");
    public static final Factor PLACENTA = new Factor(ORGANISM_PART, "placenta");
    public static final Factor PROSTATE = new Factor(ORGANISM_PART, "prostate");
    public static final Factor RECTUM = new Factor(ORGANISM_PART, "rectum");
    public static final Factor SALIVARY_GLAND = new Factor(ORGANISM_PART, "salivary gland");
    public static final Factor SKELETAL_MUSCLE = new Factor(ORGANISM_PART, "skeletal muscle");
    public static final Factor SKIN = new Factor(ORGANISM_PART, "skin");
    public static final Factor SMALL_INTESTINE = new Factor(ORGANISM_PART, "small intestine");
    public static final Factor SMOOTH_MUSCLE = new Factor(ORGANISM_PART, "smooth muscle");
    public static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    public static final Factor STOMACH = new Factor(ORGANISM_PART, "stomach");
    public static final Factor TESTIS = new Factor(ORGANISM_PART, "testis");
    public static final Factor TONSIL = new Factor(ORGANISM_PART, "tonsil");
    public static final Factor THYROID = new Factor(ORGANISM_PART, "thyroid");

    private static final String CELL_LINE = "CELL_LINE";
    private static final Factor A549 = new Factor(CELL_LINE, "A549");
    private static final Factor AG445 = new Factor(CELL_LINE, "AG445");
    private static final Factor BJ = new Factor(CELL_LINE, "BJ");
    private static final Factor CD20_POSITIVE_B_CELL = new Factor(CELL_LINE, "CD20-positive B cell cell line");
    private static final Factor CD34_POSITIVE_MOBILIZED_CELL = new Factor(CELL_LINE, "CD34-positive mobilized cell cell line");
    private static final Factor GM12878 = new Factor(CELL_LINE, "GM12878");
    private static final Factor H1_hESC = new Factor(CELL_LINE, "H1-hESC");
    private static final Factor HFDPC = new Factor(CELL_LINE, "HFDPC cell line");
    private static final Factor HMEC = new Factor(CELL_LINE, "HMEC cell line");
    private static final Factor HPC_PL = new Factor(CELL_LINE, "HPC-PL cell line");
    private static final Factor HSMM = new Factor(CELL_LINE, "HSMM cell line");
    private static final Factor HUVEC = new Factor(CELL_LINE, "HUVEC cell line");
    private static final Factor HeLa_S3 = new Factor(CELL_LINE, "HeLa-S3");
    private static final Factor HepG2 = new Factor(CELL_LINE, "HepG2");
    private static final Factor hMSC_AT = new Factor(CELL_LINE, "hMSC-AT cell line");
    private static final Factor IMR_90 = new Factor(CELL_LINE, "IMR-90");
    private static final Factor K562 = new Factor(CELL_LINE, "K562");
    private static final Factor MCF_7 = new Factor(CELL_LINE, "MCF-7");
    private static final Factor NHEK = new Factor(CELL_LINE, "NHEK cell line");
    private static final Factor NHLF = new Factor(CELL_LINE, "NHLF cell line");
    private static final Factor SK_N_SH = new Factor(CELL_LINE, "SK-N-SH");
    private static final Factor SK_N_SH_RA = new Factor(CELL_LINE, "SK-N-SH_RA");


    @Inject
    private BaselineAnalyticsSearchService subject;

    @Test
    public void singleSpeciesGeneAccession_Tissues() {
        BaselineExperimentSearchResult result = subject.findExpressions(GeneQuery.create("ENSG00000006062"), "Homo sapiens", "ORGANISM_PART");

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertThat(baselineProfilesList, hasSize(2));
        assertThat(baselineProfilesList.getTotalResultCount(), is(2));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(0);
        assertThat(baselineProfile.getId(), is("E-MTAB-2836"));
        assertThat(baselineProfile.getName(), is("Thirty two tissues"));
        assertThat(baselineProfile.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile.getConditions(), hasSize(32));
        assertThat(baselineProfile.getMinExpressionLevel(), is(2.0));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(14.0));
        assertThat(baselineProfile.getKnownExpressionLevel(RECTUM), is(4.0));
        assertThat(baselineProfile.getKnownExpressionLevel(ESOPHAGUS), is(6.0));
        assertThat(baselineProfile.getKnownExpressionLevel(KIDNEY), is(2.0));
        assertThat(baselineProfile.getKnownExpressionLevel(BONE_MARROW), is(3.0));
        assertThat(baselineProfile.getKnownExpressionLevel(STOMACH), is(4.0));
        assertThat(baselineProfile.getKnownExpressionLevel(ADIPOSE), is(2.0));
        assertThat(baselineProfile.getKnownExpressionLevel(SMOOTH_MUSCLE), is(3.0));
        assertThat(baselineProfile.getKnownExpressionLevel(LYMPH_NODE), is(14.0));
        assertThat(baselineProfile.getKnownExpressionLevel(PLACENTA), is(2.0));
        assertThat(baselineProfile.getKnownExpressionLevel(FALLOPIAN_TUBE), is(5.0));

        SortedSet<Factor> factors = result.getFactorsAcrossAllExperiments();
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab2836Tissues()).build();
        assertThat(factors, containsInAnyOrder(allFactors.toArray()));
    }

    private static ImmutableSortedSet<Factor> getEMtab2836Tissues() {
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        builder.add(ADIPOSE);
        builder.add(ADRENAL_GLAND);
        builder.add(ANIMAL_OVARY);
        builder.add(APPENDIX);
        builder.add(BLADDER);
        builder.add(BONE_MARROW);
        builder.add(CEREBRAL_CORTEX);
        builder.add(COLON);
        builder.add(DUODENUM);
        builder.add(ENDOMETRIUM);
        builder.add(ESOPHAGUS);
        builder.add(FALLOPIAN_TUBE);
        builder.add(GALL_BLADDER);
        builder.add(HEART);
        builder.add(KIDNEY);
        builder.add(LIVER);
        builder.add(LUNG);
        builder.add(LYMPH_NODE);
        builder.add(PANCREAS);
        builder.add(PLACENTA);
        builder.add(PROSTATE);
        builder.add(RECTUM);
        builder.add(SALIVARY_GLAND);
        builder.add(SKELETAL_MUSCLE);
        builder.add(SKIN);
        builder.add(SMALL_INTESTINE);
        builder.add(SMOOTH_MUSCLE);
        builder.add(SPLEEN);
        builder.add(STOMACH);
        builder.add(TESTIS);
        builder.add(THYROID);
        builder.add(TONSIL);
        return builder.build();
    }

    private static ImmutableSortedSet<Factor> getEGeod26284CellLines() {
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        builder.add(A549);
        builder.add(AG445);
        builder.add(BJ);
        builder.add(CD20_POSITIVE_B_CELL);
        builder.add(CD34_POSITIVE_MOBILIZED_CELL);
        builder.add(GM12878);
        builder.add(H1_hESC);
        builder.add(HFDPC);
        builder.add(HMEC);
        builder.add(HPC_PL);
        builder.add(HSMM);
        builder.add(HUVEC);
        builder.add(HeLa_S3);
        builder.add(HepG2);
        builder.add(IMR_90);
        builder.add(K562);
        builder.add(MCF_7);
        builder.add(NHEK);
        builder.add(NHLF);
        builder.add(SK_N_SH);
        builder.add(SK_N_SH_RA);
        builder.add(hMSC_AT);
        return builder.build();
    }

    @Test
    public void geneQuery_CellLine() {
        BaselineExperimentSearchResult result = subject.findExpressions(GeneQuery.create("blood"), "Homo sapiens", CELL_LINE);

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertThat(baselineProfilesList, hasSize(48));
        assertThat(baselineProfilesList.getTotalResultCount(), is(48));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(45);
        assertThat(baselineProfile.getId(), is("E-GEOD-26284"));
        assertThat(baselineProfile.getName(), is("ENCODE cell lines - total RNA, nucleolus"));
        assertThat(baselineProfile.getFilterFactors(), is((FactorGroup) new FactorSet(new Factor("RNA", "total RNA"), new Factor("CELLULAR_COMPONENT", "nucleolus"))));
        assertThat(baselineProfile.getConditions(), hasSize(385));
        assertThat(baselineProfile.getMinExpressionLevel(), is(5.0));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(5.0));

        SortedSet<Factor> factors = result.getFactorsAcrossAllExperiments();
        assertThat(factors, hasItems(getEGeod26284CellLines().toArray(new Factor[getEGeod26284CellLines().size()])));

        assertThat(factors, hasSize(385));
    }

}