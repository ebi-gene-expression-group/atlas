package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.FactorAcrossExperiments;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentDisplayDefaults;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentSearchResultProducerTest {

    @Mock
    ExperimentTrader experimentTrader;

    BaselineExperimentSearchResultProducer subject;

    BaselineExperiment singleFactorExperiment;
    String factorHeader = "type1";
    String factorType = Factor.normalize(factorHeader);

    @Before
    public void setUp(){
        subject = new BaselineExperimentSearchResultProducer(experimentTrader);

        List<AssayGroup> singleFactorExperimentAssayGroups = ImmutableList.of(new AssayGroup("g1", "run11"), new AssayGroup("g2", "run21"), new AssayGroup("g3", "run31"));
        ExperimentDesign singleFactorExperimentExperimentDesign = new ExperimentDesign();
        singleFactorExperimentExperimentDesign.putFactor("run11", factorHeader, "value1");
        singleFactorExperimentExperimentDesign.putFactor("run21", factorHeader, "value2");
        singleFactorExperimentExperimentDesign.putFactor("run31", factorHeader, "value3");

        singleFactorExperiment = BaselineExperimentTest.mockExperiment(singleFactorExperimentExperimentDesign, singleFactorExperimentAssayGroups, ExperimentDisplayDefaults.create(), "singleFactorExperiment");
        when(experimentTrader.getPublicExperiment("singleFactorExperiment")).thenReturn(singleFactorExperiment);
    }

    @Test
    public void threeDataPointsGiveThreeConditions(){
        Map<String, Map<String, Double>> dataInSolr = ImmutableMap.of(singleFactorExperiment.getAccession(), (Map<String, Double>)
                ImmutableMap.of("g1", 1.0, "g2", 2.0, "g3", 3.0));
        Collection<BaselineExperimentProfile> result = subject.profilesForExpressions(dataInSolr, factorType);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getSpecificity(), is(3));
    }

    @Test
    public void twoDataPointsGiveThreeConditionsButLastOneIsEmpty(){
        Map<String, Map<String, Double>> dataInSolr = ImmutableMap.of(singleFactorExperiment.getAccession(), (Map<String, Double>)
                ImmutableMap.of("g1", 1.0, "g2", 2.0));
        Collection<BaselineExperimentProfile> result = subject.profilesForExpressions(dataInSolr, factorType);
        assertThat(result.size(), is(1));
        assertThat(result.iterator().next().getSpecificity(), is(2));
        assertThat(result.iterator().next().getConditions().size(), is(3));

    }
}