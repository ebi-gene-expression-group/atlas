package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.SampleCharacteristic;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExperimentFilesCrossValidatorTest {


    @Mock
    ExperimentConfiguration experimentConfiguration;

    @Mock
    ExperimentDesign experimentDesign;

    AssayGroup assayGroup = new AssayGroup("g1", "r1", "r2");

    @Before
    public void setUp(){
        when(experimentConfiguration.getAssayGroups()).thenReturn(ImmutableList.of(
                assayGroup
        ));
    }

    @Test(expected = IllegalStateException.class)
    public void missingFactorsThrow(){

        when(experimentDesign.getFactorValues("r1")).thenReturn(ImmutableMap.<String, String>of());
        when(experimentDesign.getFactorValues("r2")).thenReturn(ImmutableMap.<String, String>of());

        new ExperimentFilesCrossValidator(experimentConfiguration, experimentDesign).factorsNotMissing();
    }

    @Test
    public void presentFactorsPass(){

        when(experimentDesign.getFactorValues("r1")).thenReturn(ImmutableMap.of("type", "value"));
        when(experimentDesign.getFactorValues("r2")).thenReturn(ImmutableMap.of("type", "value"));


        new ExperimentFilesCrossValidator(experimentConfiguration, experimentDesign).factorsNotMissing();
    }


    @Test(expected = IllegalStateException.class)
    public void missingCharacteristicsThrow(){

        when(experimentDesign.getSampleCharacteristics("r1")).thenReturn(ImmutableList.<SampleCharacteristic>of());
        when(experimentDesign.getSampleCharacteristics("r2")).thenReturn(ImmutableList.<SampleCharacteristic>of());

        new ExperimentFilesCrossValidator(experimentConfiguration, experimentDesign).sampleCharacteristicsNotMissing();
    }

    @Test
    public void presentCharacteristicsPass(){

        when(experimentDesign.getSampleCharacteristics("r1")).thenReturn(ImmutableList.of(SampleCharacteristic.create("", "")));
        when(experimentDesign.getSampleCharacteristics("r2")).thenReturn(ImmutableList.of(SampleCharacteristic.create("", "")));

        new ExperimentFilesCrossValidator(experimentConfiguration, experimentDesign).sampleCharacteristicsNotMissing();
    }

    @Test(expected = IllegalStateException.class)
    public void inconsistentFactorsFail()  {
        when(experimentDesign.getFactorValues("r1")).thenReturn(ImmutableMap.of("type", "value_1"));
        when(experimentDesign.getFactorValues("r2")).thenReturn(ImmutableMap.of("type", "value_2"));


        new ExperimentFilesCrossValidator(experimentConfiguration, experimentDesign).factorsConsistentWithinAssay();
    }

    @Test
    public void consistentFactorsPass(){
        when(experimentDesign.getFactorValues("r1")).thenReturn(ImmutableMap.of("type", "value"));
        when(experimentDesign.getFactorValues("r2")).thenReturn(ImmutableMap.of("type", "value"));


        new ExperimentFilesCrossValidator(experimentConfiguration, experimentDesign).factorsConsistentWithinAssay();
    }
}