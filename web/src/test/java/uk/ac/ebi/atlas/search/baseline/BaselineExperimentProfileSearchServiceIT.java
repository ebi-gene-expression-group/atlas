package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.trader.cache.ProteomicsBaselineExperimentsCache;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentProfileSearchServiceIT {

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
    public static final Factor FALLOPIAN_TUBE = new Factor(ORGANISM_PART, "fallopian tube");
    public static final Factor SKELETAL_MUSCLE = new Factor(ORGANISM_PART, "skeletal muscle");
    public static final Factor TONSIL = new Factor(ORGANISM_PART, "tonsil");

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();
    private static final FactorGroup ORGANISM_HOMO_SAPIENS = new FactorSet(new Factor("ORGANISM", "Homo sapiens"));
    private static final FactorGroup STAGE_ADULT = new FactorSet(new Factor("DEVELOPMENTAL_STAGE", "adult"));
    private static final Factor SMOOTH_MUSCLE = new Factor(ORGANISM_PART, "smooth muscle");

    @Inject
    private BaselineExperimentProfileSearchService subject;

    @Inject
    private BaselineExpressionDao baselineExpressionDao;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    private ProteomicsBaselineExperimentsCache proteomicsBaselineExperimentsCache;


    @Inject
    private SolrQueryService solrQueryService;

    @Test
    public void singleGeneInMultipleExperiments() {
        BaselineExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of("ENSG00000228278")));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(2));

        assertThat(baselineProfilesList.getTotalResultCount(), is(2));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(1);
        assertThat(baselineProfile.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile.getConditions(), hasSize(24));
        assertThat(baselineProfile.getMinExpressionLevel(), is(1802.0));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(1802.0));
        assertThat(baselineProfile.getKnownExpressionLevel(LIVER), is(1802.0));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(0);
        assertThat(baselineProfile2.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile2.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile2.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile2.getConditions(), hasSize(10));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(1.0));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1670.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(APPENDIX), is(2.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(BONE_MARROW), is(12.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(ENDOMETRIUM), is(1.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(LIVER), is(1670.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(PROSTATE), is(8.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(STOMACH), is(10.0));

        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab1733Tissues()).addAll(getEMtab30352Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }

    private ImmutableSortedSet<Factor> getOrganismPartFactorsInBaselineExperiment(String experimentAccession) {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getFactors("ORGANISM_PART");
    }

    private SortedSet<Factor> getDefaultFilterFactorsInProteomicsExperiment(String experimentAccession) {
        ProteomicsBaselineExperiment experiment = proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getCoOccurringFactors(experiment.getExperimentalFactors().getDefaultFilterFactors().iterator().next());
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
        builder.add(FALLOPIAN_TUBE);
        builder.add(ESOPHAGUS);
        builder.add(GALL_BLADDER);
        builder.add(HEART);
        builder.add(KIDNEY);
        builder.add(LIVER);
        builder.add(LUNG);
        builder.add(LYMPH_NODE);
        builder.add(PANCREAS);
        builder.add(PLACENTA);
        builder.add(RECTUM);
        builder.add(PROSTATE);
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

    private static ImmutableSortedSet<Factor> getEMtab30352Tissues() {
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        builder.add(CEREBELLUM);
        builder.add(FRONTAL_LOBE);
        builder.add(HEART);
        builder.add(KIDNEY);
        builder.add(LIVER);
        builder.add(PREFRONTAL_CORTEX);
        builder.add(TEMPORAL_LOBE);
        builder.add(TESTIS);
        return builder.build();
    }

    private static ImmutableSortedSet<Factor> getEProt1Tissues() {
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        builder.add(B_CELL);
        builder.add(CD4);
        builder.add(CD8);
        builder.add(FRONTAL_CORTEX);
        builder.add(GALLBLADDER);
        builder.add(MONOCYTE);
        builder.add(NATURAL_KILLER);
        builder.add(OVARY);
        builder.add(PLATELET);
        builder.add(RECTUM);
        builder.add(RETINA);
        builder.add(SPINAL_CORD);
        builder.add(URINARY_BLADDER);
        return builder.build();
    }

    @Test
    public void singleSpeciesGeneSet() {

        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds("R-HSA-73887", "homo sapiens", true);

        BaselineExperimentSearchResult result = subject.query(geneIds.get());

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(4));

        assertThat(baselineProfilesList.getTotalResultCount(), is(4));

        BaselineExperimentProfile baselineProfile0 = baselineProfilesList.get(0);
        assertThat(baselineProfile0.getId(), is("E-MTAB-2836"));
        assertThat(baselineProfile0.getName(), is("Thirty two tissues"));
        assertThat(baselineProfile0.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile0.getConditions(), hasSize(48));
        assertThat(baselineProfile0.getMinExpressionLevel(), is(0.7));
        assertThat(baselineProfile0.getMaxExpressionLevel(), is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADIPOSE), is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADRENAL_GLAND), is(8.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ANIMAL_OVARY), is(7.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(APPENDIX), is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(BLADDER), is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(BONE_MARROW), is(11.0));
        assertThat(baselineProfile0.getExpression(CEREBELLUM).isKnown(), is(false));
        assertThat(baselineProfile0.getKnownExpressionLevel(CEREBRAL_CORTEX), is(2.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(COLON), is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(DUODENUM), is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ENDOMETRIUM), is(8.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(ESOPHAGUS), is(7.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(FALLOPIAN_TUBE), is(9.0));
        assertThat(baselineProfile0.getExpression(FRONTAL_CORTEX).isKnown(), is(false));
        assertThat(baselineProfile0.getExpression(FRONTAL_LOBE).isKnown(), is(false));
        assertThat(baselineProfile0.getKnownExpressionLevel(GALL_BLADDER), is(10.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(HEART), is(5.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(KIDNEY), is(5.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(LIVER), is(6.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(LUNG), is(12.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(LYMPH_NODE), is(12.0));
        assertThat(baselineProfile0.getExpression(MONOCYTE).isKnown(), is(false));
        assertThat(baselineProfile0.getExpression(NATURAL_KILLER).isKnown(), is(false));
        assertThat(baselineProfile0.getExpression(OVARY).isKnown(), is(false));
        assertThat(baselineProfile0.getKnownExpressionLevel(PANCREAS), is(0.7));
        assertThat(baselineProfile0.getKnownExpressionLevel(PLACENTA), is(7.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(PROSTATE), is(7.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(RECTUM), is(13.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SALIVARY_GLAND), is(3.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SKELETAL_MUSCLE), is(2.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SKIN), is(6.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SMALL_INTESTINE), is(9.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SMOOTH_MUSCLE), is(7.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(SPLEEN), is(12.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(STOMACH), is(6.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(TESTIS), is(3.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(THYROID), is(6.0));
        assertThat(baselineProfile0.getKnownExpressionLevel(TONSIL), is(10.0));

        BaselineExperimentProfile baselineProfile1 = baselineProfilesList.get(1);
        assertThat(baselineProfile1.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile1.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile1.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile1.getConditions(), hasSize(48));
        assertThat(baselineProfile1.getMinExpressionLevel(), is(3.0));
        assertThat(baselineProfile1.getMaxExpressionLevel(), is(30.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(ADIPOSE), is(14.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(ADRENAL_GLAND), is(15.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(ANIMAL_OVARY), is(13.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(APPENDIX), is(27.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(BLADDER), is(24.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(BONE_MARROW), is(30.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(CEREBRAL_CORTEX), is(7.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(COLON), is(16.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(DUODENUM), is(19.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(ENDOMETRIUM), is(14.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(ESOPHAGUS), is(15.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(GALL_BLADDER), is(21.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(HEART), is(9.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(KIDNEY), is(14.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(LIVER), is(12.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(LUNG), is(25.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(LYMPH_NODE), is(21.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(PANCREAS), is(3.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(PLACENTA), is(18.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(PROSTATE), is(13.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(SALIVARY_GLAND), is(9.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(SKIN), is(13.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(SMALL_INTESTINE), is(19.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(SPLEEN), is(19.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(STOMACH), is(13.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(TESTIS), is(12.0));
        assertThat(baselineProfile1.getKnownExpressionLevel(THYROID), is(12.0));


        BaselineExperimentProfile baselineProfile3 = baselineProfilesList.get(2);
        assertThat(baselineProfile3.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile3.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile3.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile3.getConditions(), hasSize(48));
        assertThat(baselineProfile3.getMinExpressionLevel(), is(4.0));
        assertThat(baselineProfile3.getMaxExpressionLevel(), is(22.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(CEREBELLUM), is(4.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(FRONTAL_LOBE), is(4.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(HEART), is(12.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(KIDNEY), is(22.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(LIVER), is(17.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(PREFRONTAL_CORTEX), is(4.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(TEMPORAL_LOBE), is(6.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(TESTIS), is(14.0));
        assertThat(baselineProfile3.getKnownExpressionLevel(THYROID), is(nullValue()));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(3);
        assertThat(baselineProfile2.getId(), is("E-PROT-1"));
        assertThat(baselineProfile2.getName(), is("Human Proteome Map - adult"));
        assertThat(baselineProfile2.getFilterFactors(), is(STAGE_ADULT));
        assertThat(baselineProfile2.getConditions(), hasSize(35));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(2.7E-6));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1.745E7));
        assertThat(baselineProfile2.getKnownExpressionLevel(ADIPOSE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(APPENDIX), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(ANIMAL_OVARY), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(B_CELL), is(2505000.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(BLADDER), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(BONE_MARROW), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(CEREBELLUM), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(CEREBRAL_CORTEX), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(DUODENUM), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(ENDOMETRIUM), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(FRONTAL_LOBE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(GALL_BLADDER), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(LYMPH_NODE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(PLACENTA), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(PREFRONTAL_CORTEX), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(PROSTATE), is(2400000.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(RETINA), is(17450000.0));
        assertThat(baselineProfile2.getKnownExpressionLevel(SALIVARY_GLAND), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(SKIN), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(SMALL_INTESTINE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(SPLEEN), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(STOMACH), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(TEMPORAL_LOBE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(THYROID), is(nullValue()));


        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab2836Tissues()).addAll(getEMtab30352Tissues()).addAll(getEMtab1733Tissues()).addAll(getEProt1Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }

    private static final String GENE_IN_CELL_LINES_EXPERIMENT = "ENSG00000007062";

    @Test
    public void onlyTissueExperimentsReturned() {
        // test gene has expression in cell lines experiment (E-GEOD-26284)
        List<BaselineExperimentExpression> expressions = baselineExpressionDao.fetchAverageExpressionByExperimentAssayGroup(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT));
        assertThat(expressions,  hasItem(hasExperimentAccession("E-GEOD-26284")));

        // test that cell lines experiment is not returned
        BaselineExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT)));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        Matcher twentySevenTissuesExperimentProfile = Matchers.<BaselineExperimentProfile>hasProperty("id", is("E-MTAB-1733"));
        Matcher encodeCellLinesExperimentProfile = Matchers.<BaselineExperimentProfile>hasProperty("id", is("E-GEOD-26284"));

        assertThat(baselineProfilesList, hasItem(twentySevenTissuesExperimentProfile));
        assertThat(baselineProfilesList, not(hasItem(encodeCellLinesExperimentProfile)));

        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;

        ImmutableSortedSet.Builder<Factor> allFactorsBuilder = ImmutableSortedSet.naturalOrder();
        allFactorsBuilder.addAll(getOrganismPartFactorsInBaselineExperiment("E-MTAB-1733"));
        allFactorsBuilder.addAll(getOrganismPartFactorsInBaselineExperiment("E-MTAB-2836"));
        allFactorsBuilder.addAll(getDefaultFilterFactorsInProteomicsExperiment("E-PROT-1"));

        assertThat(factors, containsInAnyOrder(allFactorsBuilder.build().toArray()));
    }

    private Matcher<BaselineExperimentExpression> hasExperimentAccession(final String expectedExperimentAccession) {
        return new BaseMatcher<BaselineExperimentExpression>() {
            @Override
            public boolean matches(Object o) {
                String actualExperimentAccession = ((BaselineExperimentExpression)o).experimentAccession();
                return expectedExperimentAccession.equals(actualExperimentAccession);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("experiment accession ").appendValue(expectedExperimentAccession);
            }
        };
    }

    @Test
    public void sortExperimentsByNonFilterFactors() {
        BaselineExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of("ENSG00000187003")));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(3));

        assertThat(baselineProfilesList.getTotalResultCount(), is(3));

        BaselineExperimentProfile baselineProfile1 = baselineProfilesList.get(0);
        assertThat(baselineProfile1.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile1.getId(), is("E-MTAB-1733"));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(1);
        assertThat(baselineProfile2.getName(), is("Illumina Body Map"));
        assertThat(baselineProfile2.getId(), is("E-MTAB-513"));

        BaselineExperimentProfile baselineProfile3 = baselineProfilesList.get(2);
        assertThat(baselineProfile3.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile3.getId(), is("E-GEOD-30352"));

    }

    @Test
    public void nonOrganismFactorsAppendedToExperimentName() {
        BaselineExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of("ENSRNOG00000015557")));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(3));

        assertThat(baselineProfilesList.getTotalResultCount(), is(3));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(0);
        assertThat(baselineProfile.getId(), is("E-MTAB-2800"));
        assertThat(baselineProfile.getName(), is("Nine rat tissues - BN/SsNHsd"));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(1);
        assertThat(baselineProfile2.getId(), is("E-MTAB-2800"));
        assertThat(baselineProfile2.getName(), is("Nine rat tissues - F344/Cr1"));

        BaselineExperimentProfile baselineProfile3 = baselineProfilesList.get(2);
        assertThat(baselineProfile3.getId(), is("E-MTAB-2800"));
        assertThat(baselineProfile3.getName(), is("Nine rat tissues - Sprague Dawley"));
    }

    @Test
    public void ignoreGeneInExperimentWithOrganismPartButOrganismPartIsNotDefaultQueryType() {
        BaselineExperimentSearchResult result = subject.query(ImmutableSet.of("WBGene00194914"));

        assertThat(result.isEmpty(), is(true));
    }
}
