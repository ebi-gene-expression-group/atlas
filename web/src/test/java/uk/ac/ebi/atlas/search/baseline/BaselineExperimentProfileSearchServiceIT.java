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

    private static final Factor LIVER = new Factor("ORGANISM_PART", "liver");

    private static final Factor APPENDIX = new Factor("ORGANISM_PART", "appendix");
    private static final Factor ENDOMETRIUM = new Factor("ORGANISM_PART", "endometrium");
    private static final Factor BONE_MARROW = new Factor("ORGANISM_PART", "bone marrow");
    private static final Factor STOMACH = new Factor("ORGANISM_PART", "stomach");
    private static final Factor PROSTATE = new Factor("ORGANISM_PART", "prostate");
    private static final String ORGANISM_PART = "ORGANISM_PART";

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
        builder.add(new Factor(ORGANISM_PART, "adipose tissue"));
        builder.add(new Factor(ORGANISM_PART, "adrenal gland"));
        builder.add(new Factor(ORGANISM_PART, "animal ovary"));
        builder.add(new Factor(ORGANISM_PART, "appendix"));
        builder.add(new Factor(ORGANISM_PART, "bladder"));
        builder.add(new Factor(ORGANISM_PART, "bone marrow"));
        builder.add(new Factor(ORGANISM_PART, "cerebral cortex"));
        builder.add(new Factor(ORGANISM_PART, "colon"));
        builder.add(new Factor(ORGANISM_PART, "duodenum"));
        builder.add(new Factor(ORGANISM_PART, "endometrium"));
        builder.add(new Factor(ORGANISM_PART, "esophagus"));
        builder.add(new Factor(ORGANISM_PART, "gall bladder"));
        builder.add(new Factor(ORGANISM_PART, "heart"));
        builder.add(new Factor(ORGANISM_PART, "kidney"));
        builder.add(new Factor(ORGANISM_PART, "liver"));
        builder.add(new Factor(ORGANISM_PART, "lung"));
        builder.add(new Factor(ORGANISM_PART, "lymph node"));
        builder.add(new Factor(ORGANISM_PART, "pancreas"));
        builder.add(new Factor(ORGANISM_PART, "placenta"));
        builder.add(new Factor(ORGANISM_PART, "prostate"));
        builder.add(new Factor(ORGANISM_PART, "salivary gland"));
        builder.add(new Factor(ORGANISM_PART, "skin"));
        builder.add(new Factor(ORGANISM_PART, "small intestine"));
        builder.add(new Factor(ORGANISM_PART, "spleen"));
        builder.add(new Factor(ORGANISM_PART, "stomach"));
        builder.add(new Factor(ORGANISM_PART, "testis"));
        builder.add(new Factor(ORGANISM_PART, "thyroid"));
        return builder.build();
    }

    private ImmutableSortedSet<Factor> getEMtab30352Tissues() {
        ImmutableSortedSet.Builder<Factor> builder = ImmutableSortedSet.naturalOrder();
        builder.add(new Factor(ORGANISM_PART, "cerebellum"));
        builder.add(new Factor(ORGANISM_PART, "frontal lobe"));
        builder.add(new Factor(ORGANISM_PART, "heart"));
        builder.add(new Factor(ORGANISM_PART, "kidney"));
        builder.add(new Factor(ORGANISM_PART, "liver"));
        builder.add(new Factor(ORGANISM_PART, "prefrontal cortex"));
        builder.add(new Factor(ORGANISM_PART, "temporal lobe"));
        builder.add(new Factor(ORGANISM_PART, "testis"));
        return builder.build();
    }

    private static final String GENE_IN_CELL_LINES_EXPERIMENT = "ENSG00000258081";

    @Test
    public void onlyTissueExperimentsReturned() {
        // test gene has expression in cell lines experiment (E-GEOD-26284)
        List<RnaSeqBslnExpression> expressions = rnaSeqBslnExpressionDao.fetchNonSpecificExpression(ImmutableSet.of(GENE_IN_CELL_LINES_EXPERIMENT));
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
