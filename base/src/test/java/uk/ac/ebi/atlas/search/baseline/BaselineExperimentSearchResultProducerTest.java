package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentSearchResultProducerTest {

    @Mock
    ExperimentTrader experimentTrader;

    BaselineExperimentSearchResultProducer subject;

    String factorHeader = "type1";
    String factorType = Factor.normalize(factorHeader);
    String secondFactorHeader = "type2";
    String secondFactorType = Factor.normalize(secondFactorHeader);

    BaselineExperiment singleFactorExperiment;
    BaselineExperiment twoFactorExperimentWithThreeDifferentValuesForEachFactor;

    BaselineExperiment twoFactorExperimentWithOverlappingFactors;
    

    @Before
    public void setUp() {
        subject = new BaselineExperimentSearchResultProducer(experimentTrader);

        List<AssayGroup> singleFactorExperimentAssayGroups =
                ImmutableList.of(
                        new AssayGroup("g1", "run11"), new AssayGroup("g2", "run21"), new AssayGroup("g3", "run31"));
        ExperimentDesign singleFactorExperimentExperimentDesign = new ExperimentDesign();
        singleFactorExperimentExperimentDesign.putFactor("run11", factorHeader, "value1");
        singleFactorExperimentExperimentDesign.putFactor("run21", factorHeader, "value2");
        singleFactorExperimentExperimentDesign.putFactor("run31", factorHeader, "value3");

        singleFactorExperiment = MockExperiment.createBaselineExperiment(
                        "singleFactorExperiment",
                        singleFactorExperimentExperimentDesign,
                        singleFactorExperimentAssayGroups);
        when(experimentTrader.getPublicExperiment("singleFactorExperiment")).thenReturn(singleFactorExperiment);

        List<AssayGroup> twoFactorExperimentAllDifferentValuesAssayGroups =
                ImmutableList.of(
                        new AssayGroup("x1", "R11"), new AssayGroup("x2", "R21"), new AssayGroup("x3", "R31"));
        ExperimentDesign twoFactorExperimentAllDifferentValuesExperimentDesign = new ExperimentDesign();
        twoFactorExperimentAllDifferentValuesExperimentDesign.putFactor("R11", factorHeader, "value1");
        twoFactorExperimentAllDifferentValuesExperimentDesign.putFactor("R21", factorHeader, "value2");
        twoFactorExperimentAllDifferentValuesExperimentDesign.putFactor("R31", factorHeader, "value3");
        twoFactorExperimentAllDifferentValuesExperimentDesign.putFactor("R11", secondFactorHeader, "V1");
        twoFactorExperimentAllDifferentValuesExperimentDesign.putFactor("R21", secondFactorHeader, "V2");
        twoFactorExperimentAllDifferentValuesExperimentDesign.putFactor("R31", secondFactorHeader, "V3");

        twoFactorExperimentWithThreeDifferentValuesForEachFactor = MockExperiment.createBaselineExperiment(
                "twoFactorExperimentWithThreeDifferentValuesForEachFactor",
                twoFactorExperimentAllDifferentValuesExperimentDesign,
                twoFactorExperimentAllDifferentValuesAssayGroups,
                ExperimentDisplayDefaults.create(
                        ImmutableList.of(new Factor(factorHeader, "value1")),
                        secondFactorType,
                        ImmutableList.of(factorType, secondFactorType), false));

        when(experimentTrader.getPublicExperiment("twoFactorExperimentWithThreeDifferentValuesForEachFactor"))
                .thenReturn(twoFactorExperimentWithThreeDifferentValuesForEachFactor);

        List<AssayGroup> twoFactorExperimentWithOverlappingFactorsAssayGroups =
                ImmutableList.of(
                        new AssayGroup("a1", "r1"), new AssayGroup("a2", "r2"), new AssayGroup("a3", "r3"),
                        new AssayGroup("a4", "r4"));
        ExperimentDesign twoFactorExperimentWithOverlappingFactorsExperimentDesign = new ExperimentDesign();
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r1", factorHeader, "aa");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r2", factorHeader, "aa");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r3", factorHeader, "ab");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r4", factorHeader, "ab");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r1", secondFactorHeader, "ba");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r2", secondFactorHeader, "bb");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r3", secondFactorHeader, "ba");
        twoFactorExperimentWithOverlappingFactorsExperimentDesign.putFactor("r4", secondFactorHeader, "bb");

        twoFactorExperimentWithOverlappingFactors = MockExperiment.createBaselineExperiment(
                "twoFactorExperimentWithOverlappingFactors",
                twoFactorExperimentWithOverlappingFactorsExperimentDesign,
                twoFactorExperimentWithOverlappingFactorsAssayGroups,
                ExperimentDisplayDefaults.create(
                        ImmutableList.of(new Factor(factorHeader, "value1")),
                        secondFactorType,
                        ImmutableList.of(factorType, secondFactorType),
                        false));

        when(experimentTrader.getPublicExperiment("twoFactorExperimentWithOverlappingFactors"))
                .thenReturn(twoFactorExperimentWithOverlappingFactors);
    }

    @Test
    public void threeDataPointsGiveThreeConditions() {
        Map<String, Map<String, Double>> dataInSolr =
                ImmutableMap.of(
                        singleFactorExperiment.getAccession(), ImmutableMap.of("g1", 1.0, "g2", 2.0, "g3", 3.0));
        Collection<BaselineExperimentProfile> result = subject.buildProfilesForExperiments(dataInSolr, factorType);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getSpecificity(), is(3L));
    }

    @Test
    public void twoDataPointsGiveThreeConditionsButLastOneIsEmpty(){
        Map<String, Map<String, Double>> dataInSolr =
                ImmutableMap.of(singleFactorExperiment.getAccession(), ImmutableMap.of("g1", 1.0, "g2", 2.0));
        Collection<BaselineExperimentProfile> result = subject.buildProfilesForExperiments(dataInSolr, factorType);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getSpecificity(), is(2L));
        assertThat(result.iterator().next().getConditions().size(), is(3));
    }

    @Test
    public void sameDataPointsWhetherYouQueryByFirstOrSecondTypeForTwoFactorExperimentWithThreeDifferentValuesForEachFactor() {
        sameDataPointsWhetherYouQueryByFirstOrSecondTypeForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of("x1", 1.0, "x2", 2.0, "x3", 3.0));

        sameDataPointsWhetherYouQueryByFirstOrSecondTypeForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of("x1", 1.0, "x2", 2.0));

        sameDataPointsWhetherYouQueryByFirstOrSecondTypeForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of("x1", 1.0));

        sameDataPointsWhetherYouQueryByFirstOrSecondTypeForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of());
    }

    private void sameDataPointsWhetherYouQueryByFirstOrSecondTypeForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
            Map<String, Double> data) {
        assertThat(
                resultShape(twoFactorExperimentWithThreeDifferentValuesForEachFactor, factorType, data),
                is(resultShape(twoFactorExperimentWithThreeDifferentValuesForEachFactor, secondFactorType, data))
        );
    }

    @Test
    public void oneDataPointPerRowForTwoFactorExperimentWithThreeDifferentValuesForEachFactor() {
        oneDataPointPerRowForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of("x1", 1.0, "x2", 2.0));
        oneDataPointPerRowForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of("x1", 1.0, "x2", 2.0, "x3", 3.0));
        oneDataPointPerRowForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of("x1", 1.0));
        oneDataPointPerRowForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(
                ImmutableMap.of());
    }

    private void oneDataPointPerRowForTwoFactorExperimentWithThreeDifferentValuesForEachFactor(Map<String, Double> data) {
        assertThat(
                resultShape(twoFactorExperimentWithThreeDifferentValuesForEachFactor, factorType, data),
                Matchers.is(Collections.nCopies(data.size(), 1L))
        );
    }

    @Test
    public void twoFactorExperimentWithOverlappingValuesResultsHaveRightShape() {
        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                factorType,
                ImmutableMap.of("a1", 1.0, "a2", 2.0, "a3", 3.0, "a4", 4.0),
                ImmutableList.of(2L, 2L));

        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                secondFactorType,
                ImmutableMap.of("a1", 1.0, "a2", 2.0, "a3", 3.0, "a4", 4.0),
                ImmutableList.of(2L, 2L));

        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                factorType,
                ImmutableMap.of("a1", 1.0, "a2", 2.0),
                ImmutableList.of(1L, 1L));

        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                secondFactorType,
                ImmutableMap.of("a1", 1.0, "a2", 2.0),
                ImmutableList.of(2L));

        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                factorType,
                ImmutableMap.of("a1", 1.0, "a3", 3.0),
                ImmutableList.of(2L));

        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                secondFactorType,
                ImmutableMap.of("a1", 1.0, "a3", 3.0),
                ImmutableList.of(1L, 1L));

        testDataHasShape(
                twoFactorExperimentWithOverlappingFactors,
                factorType,
                ImmutableMap.of("a1", 1.0),
                ImmutableList.of(1L));
    }

    private List<Long> resultShape(BaselineExperiment experiment,String factorType, Map<String, Double> data) {
        Map<String, Map<String, Double>> dataInSolr = ImmutableMap.of(experiment.getAccession(), data);

        List<BaselineExperimentProfile> result = subject.buildProfilesForExperiments(dataInSolr, factorType);

        return result.stream().map(Profile::getSpecificity).collect(Collectors.toList());
    }

    private void testDataHasShape(BaselineExperiment experiment,
                                  String factorType,
                                  Map<String, Double> data,
                                  List<Long> shape){
        assertThat(resultShape(experiment, factorType, data), is(shape));
    }

}