package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import java.util.Set;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineExpressionSearchResultIT {

    public static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_GEOD_26284 = "E-GEOD-26284";
    private static final String ORGANISM_PART = "ORGANISM_PART";
    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Test
    public void e_mtab_513_TwoSpecificAssayGroups() {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(E_MTAB_513);

        BaselineExperimentAssayGroup subject = new BaselineExperimentAssayGroup(E_MTAB_513, E_MTAB_513, "Homo sapiens", ORGANISM_PART, true);

        Set<String> assayGroupIdsWithExpression = ImmutableSet.of("g1","g2");
        subject.setAssayGroupsWithCondition(assayGroupIdsWithExpression, experiment);

        SortedSet<Factor> factors = subject.getDefaultFactorsForSpecificAssayGroupsWithCondition();

        Factor factor1 = new Factor("ORGANISM_PART", "leukocyte", new OntologyTerm("CL:0000738"));
        Factor factor2 = new Factor("ORGANISM_PART", "skeletal muscle", new OntologyTerm("CL:0000188"));

        assertThat(factors, hasSize(2));
        assertThat(factors, containsInAnyOrder(factor1, factor2));
    }

    @Test
    public void e_mtab_513_AllAssayGroups() {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(E_MTAB_513);

        BaselineExperimentAssayGroup subject = new BaselineExperimentAssayGroup(E_MTAB_513, E_MTAB_513, "Homo sapiens", ORGANISM_PART, true);

        Set<String> assayGroupIdsWithExpression = ImmutableSet.<String>builder().add("g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15", "g16").build();
        subject.setAssayGroupsWithCondition(assayGroupIdsWithExpression, experiment);

        SortedSet<Factor> factors = subject.getDefaultFactorsForSpecificAssayGroupsWithCondition();

        assertThat(factors, empty());
    }

    @Test
    public void multiFactor_TwoSpecificAssayGroups_InSlice() {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(E_GEOD_26284);

        BaselineExperimentAssayGroup subject = new BaselineExperimentAssayGroup(E_MTAB_513, E_MTAB_513, "Homo sapiens", ORGANISM_PART, true);

        Set<String> assayGroupIdsWithExpression = ImmutableSet.<String>builder().add("g11", "g59").build();
        Factor factor1 = new Factor("RNA", "total RNA");
        Factor factor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        FactorSet filterFactors = new FactorSet(ImmutableSet.of(factor1, factor2));

        subject.setAssayGroupsWithCondition(assayGroupIdsWithExpression, experiment);
        subject.setFilterFactors(filterFactors);

        Factor assayGroupFactor1 = new Factor("CELL_LINE", "HFDPC cell line");
        Factor assayGroupFactor2 = new Factor("CELL_LINE", "IMR-90");
        assertThat(subject.getDefaultFactorsForSpecificAssayGroupsWithCondition(), containsInAnyOrder(assayGroupFactor1, assayGroupFactor2));
    }

    @Test
    public void multiFactor_AllAssayGroups_InSlice() {
        BaselineExperiment experiment = baselineExperimentsCache.getExperiment(E_GEOD_26284);

        BaselineExperimentAssayGroup subject = new BaselineExperimentAssayGroup(E_MTAB_513, E_MTAB_513, "Homo sapiens", ORGANISM_PART, true);

        Set<String> assayGroupIdsWithExpression = ImmutableSet.<String>builder().add("g11", "g59", "g54", "g71", "g9").build();
        Factor factor1 = new Factor("RNA", "total RNA");
        Factor factor2 = new Factor("CELLULAR_COMPONENT", "whole cell");

        FactorSet filterFactors = new FactorSet(ImmutableSet.of(factor1, factor2));

        subject.setAssayGroupsWithCondition(assayGroupIdsWithExpression, experiment);
        subject.setFilterFactors(filterFactors);

        Factor assayGroupFactor1 = new Factor("CELL_LINE", "HFDPC cell line");
        Factor assayGroupFactor2 = new Factor("CELL_LINE", "IMR-90");
        Factor assayGroupFactor3 = new Factor("CELL_LINE", "CD34-positive mobilized cell cell line");
        Factor assayGroupFactor4 = new Factor("CELL_LINE", "HPC-PL cell line");
        Factor assayGroupFactor5 = new Factor("CELL_LINE", "hMSC-AT cell line");
        assertThat(subject.getDefaultFactorsForSpecificAssayGroupsWithCondition(), containsInAnyOrder(assayGroupFactor1, assayGroupFactor2, assayGroupFactor3, assayGroupFactor4, assayGroupFactor5));
    }

}
