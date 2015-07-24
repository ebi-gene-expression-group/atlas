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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineAnalyticsSearchServiceIT {

    public static final String ORGANISM_PART = "ORGANISM_PART";

    public static final Factor B_CELL = new Factor(ORGANISM_PART, "B cell");
    public static final Factor CD4 = new Factor(ORGANISM_PART, "CD4-positive T cell");
    public static final Factor CD8 = new Factor(ORGANISM_PART, "CD8-positive T cell");
    public static final Factor FRONTAL_CORTEX = new Factor(ORGANISM_PART, "frontal cortex");
    public static final Factor GALLBLADDER = new Factor(ORGANISM_PART, "gallbladder");
    public static final Factor MONOCYTE = new Factor(ORGANISM_PART, "monocyte");
    public static final Factor NATURAL_KILLER = new Factor(ORGANISM_PART, "natural killer cell");
    public static final Factor OVARY = new Factor(ORGANISM_PART, "ovary");
    public static final Factor PLATELET = new Factor(ORGANISM_PART, "platelet");
    public static final Factor RECTUM = new Factor(ORGANISM_PART, "rectum");
    public static final Factor RETINA = new Factor(ORGANISM_PART, "retina");
    public static final Factor SPINAL_CORD = new Factor(ORGANISM_PART, "spinal cord");
    public static final Factor URINARY_BLADDER = new Factor(ORGANISM_PART, "urinary bladder");

    public static final Factor LIVER = new Factor(ORGANISM_PART, "liver");
    public static final Factor APPENDIX = new Factor(ORGANISM_PART, "appendix");
    public static final Factor ENDOMETRIUM = new Factor(ORGANISM_PART, "endometrium");
    public static final Factor STOMACH = new Factor(ORGANISM_PART, "stomach");
    public static final Factor PROSTATE = new Factor(ORGANISM_PART, "prostate");

    public static final Factor TEMPORAL_LOBE = new Factor(ORGANISM_PART, "temporal lobe");
    public static final Factor PREFRONTAL_CORTEX = new Factor(ORGANISM_PART, "prefrontal cortex");
    public static final Factor FRONTAL_LOBE = new Factor(ORGANISM_PART, "frontal lobe");
    public static final Factor CEREBELLUM = new Factor(ORGANISM_PART, "cerebellum");

    public static final Factor THYROID = new Factor(ORGANISM_PART, "thyroid");
    public static final Factor TESTIS = new Factor(ORGANISM_PART, "testis");
    public static final Factor SPLEEN = new Factor(ORGANISM_PART, "spleen");
    public static final Factor SMALL_INTESTINE = new Factor(ORGANISM_PART, "small intestine");
    public static final Factor SKIN = new Factor(ORGANISM_PART, "skin");
    public static final Factor SALIVARY_GLAND = new Factor(ORGANISM_PART, "salivary gland");
    public static final Factor PLACENTA = new Factor(ORGANISM_PART, "placenta");
    public static final Factor PANCREAS = new Factor(ORGANISM_PART, "pancreas");
    public static final Factor LYMPH_NODE = new Factor(ORGANISM_PART, "lymph node");
    public static final Factor LUNG = new Factor(ORGANISM_PART, "lung");
    public static final Factor KIDNEY = new Factor(ORGANISM_PART, "kidney");
    public static final Factor HEART = new Factor(ORGANISM_PART, "heart");
    public static final Factor GALL_BLADDER = new Factor(ORGANISM_PART, "gall bladder");
    public static final Factor ESOPHAGUS = new Factor(ORGANISM_PART, "esophagus");
    public static final Factor DUODENUM = new Factor(ORGANISM_PART, "duodenum");
    public static final Factor COLON = new Factor(ORGANISM_PART, "colon");
    public static final Factor CEREBRAL_CORTEX = new Factor(ORGANISM_PART, "cerebral cortex");
    public static final Factor BONE_MARROW = new Factor(ORGANISM_PART, "bone marrow");
    public static final Factor BLADDER = new Factor(ORGANISM_PART, "bladder");
    public static final Factor ANIMAL_OVARY = new Factor(ORGANISM_PART, "animal ovary");
    public static final Factor ADRENAL_GLAND = new Factor(ORGANISM_PART, "adrenal gland");
    public static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose tissue");

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();
    private static final FactorGroup ORGANISM_HOMO_SAPIENS = new FactorSet(new Factor("ORGANISM", "Homo sapiens"));
    private static final FactorGroup STAGE_ADULT = new FactorSet(new Factor("DEVELOPMENTAL_STAGE", "adult"));
    private static final String CELL_LINE = "CELL_LINE";
    private static final Factor AG445 = new Factor(CELL_LINE, "AG445");
    private static final Factor H1_hESC = new Factor(CELL_LINE, "H1-hESC");

    @Inject
    private BaselineAnalyticsSearchService subject;

    @Test
    public void singleSpeciesGeneAccession_Tissues() {
        BaselineExperimentSearchResult result = subject.findExpressions(GeneQuery.create("ENSG00000126549"), "Homo sapiens", "ORGANISM_PART");

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertThat(baselineProfilesList, hasSize(1));
        assertThat(baselineProfilesList.getTotalResultCount(), is(1));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(0);
        assertThat(baselineProfile.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile.getConditions(), hasSize(6));
        assertThat(baselineProfile.getMinExpressionLevel(), is(0.5D));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(80452D));
        assertThat(baselineProfile.getKnownExpressionLevel(ANIMAL_OVARY), is(37D));
        assertThat(baselineProfile.getKnownExpressionLevel(BONE_MARROW), is(3D));
        assertThat(baselineProfile.getKnownExpressionLevel(ESOPHAGUS), is(0.5D));
        assertThat(baselineProfile.getKnownExpressionLevel(PANCREAS), is(33D));
        assertThat(baselineProfile.getKnownExpressionLevel(PLACENTA), is(36D));
        assertThat(baselineProfile.getKnownExpressionLevel(SALIVARY_GLAND), is(80452D));

        SortedSet<Factor> factors = result.getFactorsAcrossAllExperiments();
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab1733Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }

    private static ImmutableSortedSet<Factor> getEMtab1733Tissues() {
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
        builder.add(GALL_BLADDER);
        builder.add(HEART);
        builder.add(KIDNEY);
        builder.add(LIVER);
        builder.add(LUNG);
        builder.add(LYMPH_NODE);
        builder.add(PANCREAS);
        builder.add(PLACENTA);
        builder.add(PROSTATE);
        builder.add(SALIVARY_GLAND);
        builder.add(SKIN);
        builder.add(SMALL_INTESTINE);
        builder.add(SPLEEN);
        builder.add(STOMACH);
        builder.add(TESTIS);
        builder.add(THYROID);
        return builder.build();
    }

    @Test
    public void geneQuery_CellLine() {
        BaselineExperimentSearchResult result = subject.findExpressions(GeneQuery.create("blood"), "Homo sapiens", CELL_LINE);

        BaselineExperimentProfilesList baselineProfilesList = result.getExperimentProfiles();

        assertThat(baselineProfilesList, hasSize(13));
        assertThat(baselineProfilesList.getTotalResultCount(), is(13));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(0);
        assertThat(baselineProfile.getId(), is("E-GEOD-26284"));
        assertThat(baselineProfile.getName(), is("ENCODE cell lines - long polyA RNA, whole cell"));
        assertThat(baselineProfile.getFilterFactors(), is((FactorGroup) new FactorSet(new Factor("RNA", "long polyA RNA"), new Factor("CELLULAR_COMPONENT", "whole cell"))));
        assertThat(baselineProfile.getConditions(), hasSize(32));
        assertThat(baselineProfile.getMinExpressionLevel(), is(7D));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(7D));
        assertThat(baselineProfile.getKnownExpressionLevel(H1_hESC), is(7D));

        SortedSet<Factor> factors = result.getFactorsAcrossAllExperiments();
        assertThat(factors, hasSize(49));
//        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
//        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab1733Tissues()).build();
//        assertThat(factors, contains(allFactors.toArray()));
    }

}