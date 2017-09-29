package uk.ac.ebi.atlas.profiles.stream;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.util.function.Function;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProteomicsBaselineProfileStreamFactoryTest {

    ProteomicsBaselineProfileStreamFactory.Impl subject;

    AssayGroup g1 = new AssayGroup("g1", "r1");
    AssayGroup g2 = new AssayGroup("g2", "r2");


    BaselineExperiment baselineExperiment =
            BaselineExperimentTest.mockExperiment(ImmutableList.of(g1, g2), "accession");

    @Before
    public void setUp() throws Exception {
        subject = new ProteomicsBaselineProfileStreamFactory.Impl(MockDataFileHub.create());
    }

    @Test
    public void proteomicsHeadersCanHaveSpectraclCountStuff(){
        //gene name and gene id gets removed somewhere else
        //future proteomics headers will not even have the extra SpectralCount
        Function<String[], BaselineProfile> goThroughTsvLineAndPickUpExpressionsByIndex =
                subject.howToReadLine(baselineExperiment, baselineExpression -> true)
                .apply(
                        "id name g1.SpectralCount g2.SpectralCount g1.WithInSampleAbundance g2.WithInSampleAbundance"
                                .split(" "));

        assertThat(
                goThroughTsvLineAndPickUpExpressionsByIndex.apply(new String[]{"id", "name", "_", "_", "1.0", "2.0"}).getExpression(g1),
                Matchers.is(new BaselineExpression(1.0)));
    }

}