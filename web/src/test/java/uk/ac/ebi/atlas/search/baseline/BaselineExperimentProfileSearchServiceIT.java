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
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExperimentProfileSearchServiceIT {

    public static final String ORGANISM_PART = "ORGANISM_PART";

    public static final Factor CD4 = new Factor(ORGANISM_PART, "CD4-positive T cell");
    public static final Factor CD8 = new Factor(ORGANISM_PART, "CD8-positive T cell");

    private static final FactorGroup EMPTY_FACTOR_SET = new FactorSet();
    private static final FactorGroup ORGANISM_HOMO_SAPIENS = new FactorSet(new Factor("ORGANISM", "Homo sapiens"));
    private static final FactorGroup STAGE_ADULT = new FactorSet(new Factor("DEVELOPMENTAL_STAGE", "adult"));
    private static final FactorGroup STAGE_FETAL = new FactorSet(new Factor("DEVELOPMENTAL_STAGE", "fetal"));

    private static final String GENE_IN_CELL_LINES_EXPERIMENT = "ENSG00000007062";

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

        assertThat(baselineProfilesList, hasSize(3));

        assertThat(baselineProfilesList.getTotalResultCount(), is(3));

        BaselineExperimentProfile baselineProfile1 = baselineProfilesList.get(0);
        assertThat(baselineProfile1.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile1.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile1.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile1.getConditions(), hasSize(32));
        assertThat(baselineProfile1.getMinExpressionLevel(), is(1.0));
        assertThat(baselineProfile1.getMaxExpressionLevel(), is(1670.0));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(1);
        assertThat(baselineProfile2.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile2.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile2.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile2.getConditions(), hasSize(46));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(1802.0));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(1802.0));

        BaselineExperimentProfile baselineProfile3 = baselineProfilesList.get(2);
        assertThat(baselineProfile3.getId(), is("E-PROT-3"));
        assertThat(baselineProfile3.getName(), is("Proteomics - Tissues - Human Protein Atlas"));
        assertThat(baselineProfile3.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile3.getConditions(), hasSize(30));
        assertThat(baselineProfile3.getMinExpressionLevel(), is(1.0));
        assertThat(baselineProfile3.getMaxExpressionLevel(), is(1.0));
    }

    private ImmutableSortedSet<Factor> getOrganismPartFactorsInBaselineExperiment(String experimentAccession) throws ExecutionException {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getFactors("ORGANISM_PART");
    }

    private SortedSet<Factor> getDefaultFilterFactorsInProteomicsExperiment(String experimentAccession) throws ExecutionException {
        ProteomicsBaselineExperiment experiment = proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getCoOccurringFactors(experiment.getExperimentalFactors().getDefaultFilterFactors().iterator().next());
    }

    private SortedSet<Factor> getOrganismPartFactorsInProteomicsBaselineExperiment(String experimentAccession) throws ExecutionException {
        ProteomicsBaselineExperiment experiment = proteomicsBaselineExperimentsCache.getExperiment(experimentAccession);
        return experiment.getExperimentalFactors().getFactors("ORGANISM_PART");
    }

    @Test
    public void singleSpeciesGeneSet() {

        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds("R-HSA-73887", "homo sapiens", true);

        BaselineExperimentSearchResult result = subject.query(geneIds.get());

        BaselineExperimentProfilesList baselineProfilesList = result.experimentProfiles;

        assertThat(baselineProfilesList, hasSize(7));

        assertThat(baselineProfilesList.getTotalResultCount(), is(7));

        BaselineExperimentProfile baselineProfile1 = baselineProfilesList.get(0);
        assertThat(baselineProfile1.getId(), is("E-MTAB-3358"));
        assertThat(baselineProfile1.getName(), is("Tissues - 68 FANTOM5 project - adult"));
        assertThat(baselineProfile1.getFilterFactors(), is(STAGE_ADULT));
        assertThat(baselineProfile1.getConditions(), hasSize(84));
        assertThat(baselineProfile1.getMinExpressionLevel(), is(0.3));
        assertThat(baselineProfile1.getMaxExpressionLevel(), is(4.0));

        BaselineExperimentProfile baselineProfile2 = baselineProfilesList.get(1);
        assertThat(baselineProfile2.getId(), is("E-MTAB-2836"));
        assertThat(baselineProfile2.getName(), is("Thirty two tissues"));
        assertThat(baselineProfile2.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile2.getConditions(), hasSize(100));
        assertThat(baselineProfile2.getMinExpressionLevel(), is(1.0));
        assertThat(baselineProfile2.getMaxExpressionLevel(), is(31.0));

        BaselineExperimentProfile baselineProfile3 = baselineProfilesList.get(2);
        assertThat(baselineProfile3.getId(), is("E-MTAB-1733"));
        assertThat(baselineProfile3.getName(), is("Twenty seven tissues"));
        assertThat(baselineProfile3.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile3.getConditions(), hasSize(100));
        assertThat(baselineProfile3.getMinExpressionLevel(), is(40.0));
        assertThat(baselineProfile3.getMaxExpressionLevel(), is(204.0));

        BaselineExperimentProfile baselineProfile4 = baselineProfilesList.get(3);
        assertThat(baselineProfile4.getId(), is("E-MTAB-3358"));
        assertThat(baselineProfile4.getName(), is("Tissues - 68 FANTOM5 project - fetal"));
        assertThat(baselineProfile4.getFilterFactors(), is(STAGE_FETAL));
        assertThat(baselineProfile4.getConditions(), hasSize(93));
        assertThat(baselineProfile4.getMinExpressionLevel(), is(0.5));
        assertThat(baselineProfile4.getMaxExpressionLevel(), is(2.0));

        BaselineExperimentProfile baselineProfile5 = baselineProfilesList.get(4);
        assertThat(baselineProfile5.getId(), is("E-GEOD-30352"));
        assertThat(baselineProfile5.getName(), is("Vertebrate tissues"));
        assertThat(baselineProfile5.getFilterFactors(), is(ORGANISM_HOMO_SAPIENS));
        assertThat(baselineProfile5.getConditions(), hasSize(100));
        assertThat(baselineProfile5.getMinExpressionLevel(), is(4.0));
        assertThat(baselineProfile5.getMaxExpressionLevel(), is(22.0));

        BaselineExperimentProfile baselineProfile6 = baselineProfilesList.get(5);
        assertThat(baselineProfile6.getId(), is("E-PROT-3"));
        assertThat(baselineProfile6.getName(), is("Proteomics - Tissues - Human Protein Atlas"));
        assertThat(baselineProfile6.getFilterFactors(), is(EMPTY_FACTOR_SET));
        assertThat(baselineProfile6.getConditions(), hasSize(96));
        assertThat(baselineProfile6.getMinExpressionLevel(), is(1.0));
        assertThat(baselineProfile6.getMaxExpressionLevel(), is(1.0));

        BaselineExperimentProfile baselineProfile7 = baselineProfilesList.get(6);
        assertThat(baselineProfile7.getId(), is("E-PROT-1"));
        assertThat(baselineProfile7.getName(), is("Human Proteome Map - adult"));
        assertThat(baselineProfile7.getFilterFactors(), is(STAGE_ADULT));
        assertThat(baselineProfile7.getConditions(), hasSize(87));
        assertThat(baselineProfile7.getMinExpressionLevel(), is(2.7E-6));
        assertThat(baselineProfile7.getMaxExpressionLevel(), is(1.745E7));
    }

    @Test
    public void onlyTissueExperimentsReturned() throws ExecutionException {
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
        //  E-PROT-3 doesn’t have default filter factors
        allFactorsBuilder.addAll(getOrganismPartFactorsInProteomicsBaselineExperiment("E-PROT-3"));

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

        assertThat(baselineProfilesList, hasSize(4));

        assertThat(baselineProfilesList.getTotalResultCount(), is(4));

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
