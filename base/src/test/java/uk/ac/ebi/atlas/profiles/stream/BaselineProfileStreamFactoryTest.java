package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;
import uk.ac.ebi.atlas.web.BaselineRequestPreferencesTest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class BaselineProfileStreamFactoryTest {
    private MockDataFileHub mockDataFileHub;
    private BaselineExperiment baselineExperiment =
            MockExperiment.createBaselineExperiment(ImmutableList.of(AssayGroupFactory.create("g1", "r1")));

    private RnaSeqBaselineProfileStreamFactory subject;

    @Before
    public void setUp() throws Exception {
        mockDataFileHub = MockDataFileHub.create();
        subject = new RnaSeqBaselineProfileStreamFactory(mockDataFileHub);
    }

    private int[] testHistogram(double[] values, double[] cutoffBins) {
        ImmutableList.Builder<String[]> b = ImmutableList.builder();
        b.add(new String[] {"Gene ID", "Gene name", "g1"});
        for (int i = 0; i < values.length; i++) {
            b.add(new String[] {"Gene ID " + i, "Gene name" + i, Double.toString(values[i])});
        }
        mockDataFileHub.addTpmsExpressionFile(baselineExperiment.getAccession(), b.build());

        int[] result =
                subject.histogram(
                        baselineExperiment,
                        new BaselineRequestContext<>(BaselineRequestPreferencesTest.get(), baselineExperiment),
                        cutoffBins);

        assertThat(result.length, is(cutoffBins.length));

        return result;
    }

    @Test
    public void emptyFileIsOkay() {
        testHistogram(new double[]{}, new double[]{});
        testHistogram(new double[]{}, new double[]{1.0});
        testHistogram(new double[]{}, new double[]{1.0, 2.0});
    }

    @Test
    public void oneCutoffIsOkay() {
        int fileSize = RandomUtils.nextInt(10, 100);
        double[] values = new double[fileSize];
        for (int i = 0; i < fileSize; i++) {
            values[i] = RandomUtils.nextDouble(0.0, 1000.0);
        }

        testHistogram(values, new double[]{0.0});
    }

    @Test
    public void valuesAssignedCorrectlyToBins() {
        assertArrayEquals(
                new int[]{1, 1, 1},
                testHistogram(new double[]{0.5, 1.5, 2.5}, new double[]{1.0, 2.0, 1000}));
        assertArrayEquals(
                new int[]{1, 2, 0},
                testHistogram(new double[]{0.5, 1.5, 2.5}, new double[]{1.0, 3.0, 1000}));
        assertArrayEquals(
                new int[]{0, 0, 3},
                testHistogram(new double[]{0.5, 1.5, 2.5}, new double[]{0.1, 0.2, 1000}));
        assertArrayEquals(
                new int[]{1, 2},
                testHistogram(new double[]{0.5, 1.5, 2.5}, new double[]{1.0, 1000}));
        assertArrayEquals(
                new int[]{3},
                testHistogram(new double[]{0.5, 1.5, 2.5}, new double[]{1.0}));
    }
}
