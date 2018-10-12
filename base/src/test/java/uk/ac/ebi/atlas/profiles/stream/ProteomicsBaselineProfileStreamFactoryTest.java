package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.testutils.MockExperiment;
import uk.ac.ebi.atlas.testutils.MockDataFileHub;

import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProteomicsBaselineProfileStreamFactoryTest {
    private static final AssayGroup G1 = new AssayGroup("g1", "r1");
    private static final AssayGroup G2 = new AssayGroup("g2", "r2");


    private static final BaselineExperiment BASELINE_EXPERIMENT =
            MockExperiment.createBaselineExperiment(ImmutableList.of(G1, G2));

    private ProteomicsBaselineProfileStreamFactory.Impl subject;

    @Before
    public void setUp() throws Exception {
        subject = new ProteomicsBaselineProfileStreamFactory.Impl(MockDataFileHub.create());
    }

    @Test
    public void proteomicsHeadersCanHaveSpectraclCountStuff() {
        //gene name and gene id gets removed somewhere else
        //future proteomics headers will not even have the extra SpectralCount
        Function<String[], BaselineProfile> goThroughTsvLineAndPickUpExpressionsByIndex =
                subject.howToReadLine(BASELINE_EXPERIMENT, baselineExpression -> true)
                .apply(
                        "id name g1.SpectralCount g2.SpectralCount g1.WithInSampleAbundance g2.WithInSampleAbundance"
                                .split(" "));

        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(
                        new String[]{"id", "name", "_", "_", "1.0", "2.0"}).getExpression(G1),
                is(new BaselineExpression(1.0)));
    }
}
