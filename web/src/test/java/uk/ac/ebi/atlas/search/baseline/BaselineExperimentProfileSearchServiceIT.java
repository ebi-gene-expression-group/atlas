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
import uk.ac.ebi.atlas.commands.GenesNotFoundException;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import java.util.List;
import java.util.SortedSet;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentProfileSearchServiceIT {

    public static final String ORGANISM_PART = "ORGANISM_PART";

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
    public static final Factor CELEBRAL_CORTEX = new Factor(ORGANISM_PART, "cerebral cortex");
    public static final Factor BONE_MARROW = new Factor(ORGANISM_PART, "bone marrow");
    public static final Factor BLADDER = new Factor(ORGANISM_PART, "bladder");
    public static final Factor ANIMAL_OVARY = new Factor(ORGANISM_PART, "animal ovary");
    public static final Factor ADRENAL_GLAND = new Factor(ORGANISM_PART, "adrenal gland");
    public static final Factor ADIPOSE = new Factor(ORGANISM_PART, "adipose tissue");

    @Inject
    private BaselineExperimentProfileSearchService subject;

    @Inject
    private RnaSeqBslnExpressionDao rnaSeqBslnExpressionDao;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();

    private static final FactorGroup ORGANISM_HOMO_SAPIENS = new FactorSet(new Factor("ORGANISM", "Homo sapiens"));


    @Test
    public void singleGeneInMultipleExperiments() {
        BaselineTissueExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of("ENSG00000228278")));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(2));

        assertThat(baselineProfilesList.getTotalResultCount(), is(2));

        BaselineExperimentProfile baselineProfile = baselineProfilesList.get(1);
        assertThat(baselineProfile.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile.getConditions(), hasSize(1));
        assertThat(baselineProfile.getMinExpressionLevel(), is(1802D));
        assertThat(baselineProfile.getMaxExpressionLevel(), is(1802D));
        assertThat(baselineProfile.getKnownExpressionLevel(LIVER), is(1802D));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(0);
        assertThat(baselineProfile2.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile2.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile2.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile2.getConditions(), hasSize(6));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(1D));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1670D));
        assertThat(baselineProfile2.getKnownExpressionLevel(APPENDIX), is(2D));
        assertThat(baselineProfile2.getKnownExpressionLevel(BONE_MARROW), is(12D));
        assertThat(baselineProfile2.getKnownExpressionLevel(ENDOMETRIUM), is(1D));
        assertThat(baselineProfile2.getKnownExpressionLevel(LIVER), is(1670D));
        assertThat(baselineProfile2.getKnownExpressionLevel(PROSTATE), is(8D));
        assertThat(baselineProfile2.getKnownExpressionLevel(STOMACH), is(10D));

        SortedSet<Factor> factors = result.tissueFactorsAcrossAllExperiments;
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab1733Tissues()).addAll(getEMtab30352Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }

    private ImmutableSortedSet<Factor> getOrganismPartFactors(String experimentAccession) {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getFactorsByType("ORGANISM_PART");
    }

    private ImmutableSortedSet<Factor> getEMtab1733Tissues() {
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        builder.add(ADIPOSE);
        builder.add(ADRENAL_GLAND);
        builder.add(ANIMAL_OVARY);
        builder.add(APPENDIX);
        builder.add(BLADDER);
        builder.add(BONE_MARROW);
        builder.add(CELEBRAL_CORTEX);
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

    private ImmutableSortedSet<Factor> getEMtab30352Tissues() {
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

    @Test
    public void singleSpeciesGeneSet() throws GenesNotFoundException {
        BaselineTissueExperimentSearchResult result = subject.query("REACT_1619", "homo sapiens", true);

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(2));

        assertThat(baselineProfilesList.getTotalResultCount(), is(2));

        BaselineExperimentProfile baselineProfile0 = baselineProfilesList.get(0);
        assertThat(baselineProfile0.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile0.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile0.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile0.getConditions(), hasSize(27));
        assertThat(baselineProfile0.getMinExpressionLevel(), is(2D));
        assertThat(baselineProfile0.getMaxExpressionLevel(), is(26D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADIPOSE), is(10D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ADRENAL_GLAND), is(15D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ANIMAL_OVARY), is(10D));
        assertThat(baselineProfile0.getKnownExpressionLevel(APPENDIX), is(20D));
        assertThat(baselineProfile0.getKnownExpressionLevel(BLADDER), is(24D));
        assertThat(baselineProfile0.getKnownExpressionLevel(BONE_MARROW), is(13D));
        assertThat(baselineProfile0.getKnownExpressionLevel(CELEBRAL_CORTEX), is(4D));
        assertThat(baselineProfile0.getKnownExpressionLevel(COLON), is(16D));
        assertThat(baselineProfile0.getKnownExpressionLevel(DUODENUM), is(20D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ENDOMETRIUM), is(11D));
        assertThat(baselineProfile0.getKnownExpressionLevel(ESOPHAGUS), is(13D));
        assertThat(baselineProfile0.getKnownExpressionLevel(GALL_BLADDER), is(20D));
        assertThat(baselineProfile0.getKnownExpressionLevel(HEART), is(5D));
        assertThat(baselineProfile0.getKnownExpressionLevel(KIDNEY), is(13D));
        assertThat(baselineProfile0.getKnownExpressionLevel(LIVER), is(12D));
        assertThat(baselineProfile0.getKnownExpressionLevel(LUNG), is(26D));
        assertThat(baselineProfile0.getKnownExpressionLevel(LYMPH_NODE), is(17D));
        assertThat(baselineProfile0.getKnownExpressionLevel(PANCREAS), is(2D));
        assertThat(baselineProfile0.getKnownExpressionLevel(PLACENTA), is(17D));
        assertThat(baselineProfile0.getKnownExpressionLevel(PROSTATE), is(12D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SALIVARY_GLAND), is(9D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SKIN), is(12D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SMALL_INTESTINE), is(19D));
        assertThat(baselineProfile0.getKnownExpressionLevel(SPLEEN), is(16D));
        assertThat(baselineProfile0.getKnownExpressionLevel(STOMACH), is(12D));
        assertThat(baselineProfile0.getKnownExpressionLevel(TESTIS), is(6D));
        assertThat(baselineProfile0.getKnownExpressionLevel(THYROID), is(10D));

        BaselineExperimentProfile baselineProfile1 = baselineProfilesList.get(1);
        assertThat(baselineProfile1.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile1.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile1.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile1.getConditions(), hasSize(8));
        assertThat(baselineProfile1.getMinExpressionLevel(), is(2D));
        assertThat(baselineProfile1.getMaxExpressionLevel(), is(14D));
        assertThat(baselineProfile1.getKnownExpressionLevel(CEREBELLUM), is(2D));
        assertThat(baselineProfile1.getKnownExpressionLevel(FRONTAL_LOBE), is(3D));
        assertThat(baselineProfile1.getKnownExpressionLevel(HEART), is(8D));
        assertThat(baselineProfile1.getKnownExpressionLevel(KIDNEY), is(14D));
        assertThat(baselineProfile1.getKnownExpressionLevel(LIVER), is(12D));
        assertThat(baselineProfile1.getKnownExpressionLevel(PREFRONTAL_CORTEX), is(3D));
        assertThat(baselineProfile1.getKnownExpressionLevel(TEMPORAL_LOBE), is(4D));
        assertThat(baselineProfile1.getKnownExpressionLevel(TESTIS), is(9D));

        SortedSet<Factor> factors = result.tissueFactorsAcrossAllExperiments;
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        ImmutableSortedSet<Factor> allFactors = builder.addAll(getEMtab1733Tissues()).addAll(getEMtab30352Tissues()).build();
        assertThat(factors, contains(allFactors.toArray()));
    }


    private static final String GENE_IN_CELL_LINES_EXPERIMENT = "ENSG00000258081";

    @Test
    public void onlyTissueExperimentsReturned() {
        // test gene has expression in cell lines experiment (E-GEOD-26284)
        List<RnaSeqBslnExpression> expressions = rnaSeqBslnExpressionDao.fetchAverageExpressionByExperimentAssayGroup(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT));
        assertThat(expressions,  hasItem(hasExperimentAccession("E-GEOD-26284")));

        // test that cell lines experiment is not returned
        BaselineTissueExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT)));

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        Matcher cellLinesExperimentProfile = Matchers.<BaselineExperimentProfile>hasProperty("id", is("E-GEOD-26284"));
        Matcher illuminaBodyMapExperimentProfile = Matchers.<BaselineExperimentProfile>hasProperty("id", is("E-MTAB-513"));

        assertThat(baselineProfilesList, hasItem(illuminaBodyMapExperimentProfile));
        assertThat(baselineProfilesList, not(hasItem(cellLinesExperimentProfile)));

        SortedSet<Factor> factors = result.tissueFactorsAcrossAllExperiments;
        ImmutableSortedSet<Factor> allFactors = getOrganismPartFactors("E-MTAB-513");
        assertThat(factors, contains(allFactors.toArray()));

    }

    private Matcher<RnaSeqBslnExpression> hasExperimentAccession(final String expectedExperimentAccession) {
        return new BaseMatcher<RnaSeqBslnExpression>() {
            @Override
            public boolean matches(Object o) {
                String actualExperimentAccession = ((RnaSeqBslnExpression)o).experimentAccession();
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
        BaselineTissueExperimentSearchResult result = subject.fetchTissueExperimentProfiles(Optional.of(ImmutableSet.of("ENSG00000187003")));

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


}
