package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.web.BaselineRequestPreferencesTest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class BaselineProfileStreamFactoryTest {

    MockDataFileHub mockDataFileHub;

    RnaSeqBaselineProfileStreamFactory subject;

    BaselineExperiment baselineExperiment = BaselineExperimentTest.mockExperiment(ImmutableList.of(new AssayGroup("g1", "r1")), "accession");

    @Before
    public void setUp() throws Exception {
        mockDataFileHub = new MockDataFileHub();
        subject = new RnaSeqBaselineProfileStreamFactory(mockDataFileHub);
    }

    int[] testHistogram(double[] values, double[] cutoffBins){
        ImmutableList.Builder<String[]> b = ImmutableList.builder();
        b.add(new String[]{"Gene ID", "Gene name", "g1"});
        for(int i = 0; i< values.length ; i++){
            b.add(new String[]{"Gene ID "+i,"Gene name"+i, new Double(values[i]).toString()});
        }
        mockDataFileHub.addProteomicsExpressionFile(baselineExperiment.getAccession(), b.build());

        int [] result = subject.histogram(baselineExperiment, new BaselineRequestContext(BaselineRequestPreferencesTest.get(), baselineExperiment), cutoffBins);

        assertThat(result.length, is(cutoffBins.length));

        return result;
    }

    @Test
    public void emptyFileIsOkay(){
        testHistogram(new double[]{}, new double[]{});
        testHistogram(new double[]{}, new double[]{1.0});
        testHistogram(new double[]{}, new double[]{1.0, 2.0});
    }

    @Test
    public void oneCutoffIsOkay(){
        int fileSize = RandomUtils.nextInt(10,100);
        double[] values = new double[fileSize];
        for(int i = 0; i< fileSize ; i++){
            values[i] = RandomUtils.nextDouble(0.0, 1000.0);
        }

        testHistogram(values, new double[]{0.0});
    }

    @Test
    public void valuesAssignedCorrectlyToBins(){
        assertArrayEquals(
                new int[]{},
                testHistogram(new double[]{0.5,1.5,2.5}, new double[]{}));
        assertArrayEquals(
                new int[]{2},
                testHistogram(new double[]{0.5,1.5,2.5}, new double[]{1.0}));

        assertArrayEquals(
                new int[]{1,1},
                testHistogram(new double[]{0.5,1.5,2.5}, new double[]{1.0, 2.0}));
    }

}