package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperimentTest;
import uk.ac.ebi.atlas.resource.MockDataFileHub;

import java.util.Map;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ProteomicsBaselineProfileStreamFactoryTest {

    ProteomicsBaselineProfileStreamFactory subject;

    AssayGroup g1 = new AssayGroup("g1", "r1");
    AssayGroup g2 = new AssayGroup("g2", "r2");


    BaselineExperiment baselineExperiment = BaselineExperimentTest.mockExperiment(ImmutableList.of(
            g1, g2
    ), "accession");

    @Before
    public void setUp() throws Exception {
        subject = new ProteomicsBaselineProfileStreamFactory(new MockDataFileHub());
    }

    @Test
    public void proteomicsHeaders(){
        //gene name and gene id gets removed somewhere else
        //future proteomics headers will not even have the extra SpectralCount
        String[] headers = "g1.SpectralCount g2.SpectralCount g1.WithInSampleAbundance g2.WithInSampleAbundance".split(" ");


        assertThat(
                subject.rowPositionsToDataColumns(baselineExperiment, headers),
                Matchers.<Map<Integer, AssayGroup>>is(ImmutableMap.of(2, g1, 3, g2)));
    }
}