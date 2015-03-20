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
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
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
        assertThat(baselineProfile.getMinExpressionLevel(), is(1802D));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(1802D));
        assertThat(baselineProfile.getKnownExpressionLevel(LIVER), is(1802D));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(0);
        assertThat(baselineProfile2.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile2.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile2.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile2.getConditions(), hasSize(10));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(1D));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1670D));
        assertThat(baselineProfile2.getKnownExpressionLevel(APPENDIX), is(2D));
        assertThat(baselineProfile2.getKnownExpressionLevel(BONE_MARROW), is(12D));
        assertThat(baselineProfile2.getKnownExpressionLevel(ENDOMETRIUM), is(1D));
        assertThat(baselineProfile2.getKnownExpressionLevel(LIVER), is(1670D));
        assertThat(baselineProfile2.getKnownExpressionLevel(PROSTATE), is(8D));
        assertThat(baselineProfile2.getKnownExpressionLevel(STOMACH), is(10D));

        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab1733Tissues()).addAll(getEMtab30352Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }

    private ImmutableSortedSet<Factor> getOrganismPartFactors(String experimentAccession) {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getFactors("ORGANISM_PART");
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

        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds("REACT_1619", "homo sapiens", true);

        BaselineExperimentSearchResult result = subject.query(geneIds.get());

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(3));

        assertThat(baselineProfilesList.getTotalResultCount(), is(3));

        BaselineExperimentProfile baselineProfile0 = baselineProfilesList.get(0);
        assertThat(baselineProfile0.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile0.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile0.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile0.getConditions(), hasSize(44));
        assertThat(baselineProfile0.getMinExpressionLevel(), is(3D));
        assertThat(baselineProfile0.getMaxExpressionLevel(), is(32D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADIPOSE), is(12D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADRENAL_GLAND), is(19D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ANIMAL_OVARY), is(12D));
        assertThat(baselineProfile0.getKnownExpressionLevel(APPENDIX), is(25D));
        assertThat(baselineProfile0.getKnownExpressionLevel(BLADDER), is(30D));
        assertThat(baselineProfile0.getKnownExpressionLevel(BONE_MARROW), is(16D));
        assertThat(baselineProfile0.getKnownExpressionLevel(CEREBRAL_CORTEX), is(5D));
        assertThat(baselineProfile0.getKnownExpressionLevel(COLON), is(20D));
        assertThat(baselineProfile0.getKnownExpressionLevel(DUODENUM), is(25D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ENDOMETRIUM), is(14D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ESOPHAGUS), is(16D));
        assertThat(baselineProfile0.getKnownExpressionLevel(GALL_BLADDER), is(25D));
        assertThat(baselineProfile0.getKnownExpressionLevel(HEART), is(6D));
        assertThat(baselineProfile0.getKnownExpressionLevel(KIDNEY), is(16D));
        assertThat(baselineProfile0.getKnownExpressionLevel(LIVER), is(15D));
        assertThat(baselineProfile0.getKnownExpressionLevel(LUNG), is(32D));
        assertThat(baselineProfile0.getKnownExpressionLevel(LYMPH_NODE), is(21D));
        assertThat(baselineProfile0.getKnownExpressionLevel(PANCREAS), is(3D));
        assertThat(baselineProfile0.getKnownExpressionLevel(PLACENTA), is(21D));
        assertThat(baselineProfile0.getKnownExpressionLevel(PROSTATE), is(15D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SALIVARY_GLAND), is(11D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SKIN), is(15D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SMALL_INTESTINE), is(24D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SPLEEN), is(20D));
        assertThat(baselineProfile0.getKnownExpressionLevel(STOMACH), is(15D));
        assertThat(baselineProfile0.getKnownExpressionLevel(TESTIS), is(7D));
        assertThat(baselineProfile0.getKnownExpressionLevel(THYROID), is(13D));

        BaselineExperimentProfile baselineProfile1 = baselineProfilesList.get(1);
        assertThat(baselineProfile1.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile1.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile1.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile1.getConditions(), hasSize(44));
        assertThat(baselineProfile1.getMinExpressionLevel(), is(4D));
        assertThat(baselineProfile1.getMaxExpressionLevel(), is(22D));
        assertThat(baselineProfile1.getKnownExpressionLevel(CEREBELLUM), is(4D));
        assertThat(baselineProfile1.getKnownExpressionLevel(FRONTAL_LOBE), is(4D));
        assertThat(baselineProfile1.getKnownExpressionLevel(HEART), is(12D));
        assertThat(baselineProfile1.getKnownExpressionLevel(KIDNEY), is(22D));
        assertThat(baselineProfile1.getKnownExpressionLevel(LIVER), is(17D));
        assertThat(baselineProfile1.getKnownExpressionLevel(PREFRONTAL_CORTEX), is(4D));
        assertThat(baselineProfile1.getKnownExpressionLevel(TEMPORAL_LOBE), is(6D));
        assertThat(baselineProfile1.getKnownExpressionLevel(TESTIS), is(14D));
        assertThat(baselineProfile1.getKnownExpressionLevel(THYROID), is(nullValue()));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(2);
        assertThat(baselineProfile2.getId(), is("E-PROT-1"));
        assertThat(baselineProfile2.getName(), is("Human Proteome Map - adult"));
        assertThat(baselineProfile2.getFilterFactors(), is(STAGE_ADULT));
        assertThat(baselineProfile2.getConditions(), hasSize(31));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(2.7E-6));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1.745E7D));
        assertThat(baselineProfile2.getKnownExpressionLevel(ADIPOSE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(APPENDIX), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(ANIMAL_OVARY), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(B_CELL), is(2505000D));
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
        assertThat(baselineProfile2.getKnownExpressionLevel(PROSTATE), is(2400000D));
        assertThat(baselineProfile2.getKnownExpressionLevel(RETINA), is(17450000D));
        assertThat(baselineProfile2.getKnownExpressionLevel(SALIVARY_GLAND), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(SKIN), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(SMALL_INTESTINE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(SPLEEN), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(STOMACH), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(TEMPORAL_LOBE), is(nullValue()));
        assertThat(baselineProfile2.getKnownExpressionLevel(THYROID), is(nullValue()));

        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab30352Tissues()).addAll(getEMtab1733Tissues()).addAll(getEProt1Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }

    private static final String GENE_IN_CELL_LINES_EXPERIMENT = "ENSG00000007062";

    @Test
    public void onlyTissueExperimentsReturned() {
        // test gene has expression in cell lines experiment (E-MTAB-1733)
        List<BaselineExpression> expressions = baselineExpressionDao.fetchAverageExpressionByExperimentAssayGroup(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT));
        assertThat(expressions,  hasItem(hasExperimentAccession("E-MTAB-1733")));

        // test that cell lines experiment is not returned
        BaselineExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT)));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        Matcher twentySevenTissuesExperimentProfile = Matchers.<BaselineExperimentProfile>hasProperty("id", is("E-MTAB-1733"));
        Matcher thirtyTwoTissuesExperimentProfile = Matchers.<BaselineExperimentProfile>hasProperty("id", is("E-MTAB-2836"));

        assertThat(baselineProfilesList, hasItem(twentySevenTissuesExperimentProfile));
        assertThat(baselineProfilesList, not(hasItem(thirtyTwoTissuesExperimentProfile)));

        SortedSet<Factor> factors = result.factorsAcrossAllExperiments;
        ImmutableSortedSet<Factor> allFactors = getOrganismPartFactors("E-MTAB-1733");

        //TODO: CHANGE THIS FOR A METHOD THAT ALSO RETURNS PROTEOMICS FACTORS IN THE getOrganismPartFactors
        OntologyTerm ontologyTerm1 = OntologyTerm.create("CL_0000236", "http://purl.obolibrary.org/obo/");
        Factor factor1 = new Factor(ORGANISM_PART, "B cell", ontologyTerm1);
        Factor factor2 = new Factor(ORGANISM_PART, "CD4-positive T cell");
        Factor factor3 = new Factor(ORGANISM_PART, "CD8-positive T cell");

        OntologyTerm ontologyTerm2 = OntologyTerm.create("UBERON_0001870", "http://purl.obolibrary.org/obo/");
        Factor factor4 = new Factor(ORGANISM_PART, "frontal cortex", ontologyTerm2);
        Factor factor5 = new Factor(ORGANISM_PART, "gallbladder");
        OntologyTerm ontologyTerm3 = OntologyTerm.create("CL_0000576", "http://purl.obolibrary.org/obo/");
        Factor factor6 = new Factor(ORGANISM_PART, "monocyte", ontologyTerm3);
        OntologyTerm ontologyTerm4 = OntologyTerm.create("CL_0000623", "http://purl.obolibrary.org/obo/");
        Factor factor7 = new Factor(ORGANISM_PART, "natural killer cell", ontologyTerm4);
        OntologyTerm ontologyTerm5 = OntologyTerm.create("EFO_0000973", "http://purl.obolibrary.org/obo/");
        Factor factor8 = new Factor(ORGANISM_PART, "ovary", ontologyTerm5);
        OntologyTerm ontologyTerm6 = OntologyTerm.create("CL_0000233", "http://purl.obolibrary.org/obo/");
        Factor factor9 = new Factor(ORGANISM_PART, "platelet", ontologyTerm6);
        OntologyTerm ontologyTerm7 = OntologyTerm.create("UBERON_0001052", "http://purl.obolibrary.org/obo/");
        Factor factor10 = new Factor(ORGANISM_PART, "rectum", ontologyTerm7);
        OntologyTerm ontologyTerm8 = OntologyTerm.create("UBERON_0000966", "http://purl.obolibrary.org/obo/");
        Factor factor11 = new Factor(ORGANISM_PART, "retina", ontologyTerm8);
        OntologyTerm ontologyTerm9 = OntologyTerm.create("UBERON_0002240", "http://purl.obolibrary.org/obo/");
        Factor factor12 = new Factor(ORGANISM_PART, "spinal cord", ontologyTerm9);
        Factor factor13 = new Factor(ORGANISM_PART, "urinary bladder");

        ImmutableSortedSet.Builder<Factor> protFactorBuilder = ImmutableSortedSet.naturalOrder();
        protFactorBuilder.add(factor1);
        protFactorBuilder.add(factor2);
        protFactorBuilder.add(factor3);
        protFactorBuilder.add(factor4);
        protFactorBuilder.add(factor5);
        protFactorBuilder.add(factor6);
        protFactorBuilder.add(factor7);
        protFactorBuilder.add(factor8);
        protFactorBuilder.add(factor9);
        protFactorBuilder.add(factor10);
        protFactorBuilder.add(factor11);
        protFactorBuilder.add(factor12);
        protFactorBuilder.add(factor13);

        protFactorBuilder.build();

        protFactorBuilder.addAll(allFactors);

        assertThat(factors, containsInAnyOrder(protFactorBuilder.build().toArray()));

    }

    private Matcher<BaselineExpression> hasExperimentAccession(final String expectedExperimentAccession) {
        return new BaseMatcher<BaselineExpression>() {
            @Override
            public boolean matches(Object o) {
                String actualExperimentAccession = ((BaselineExpression)o).experimentAccession();
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
