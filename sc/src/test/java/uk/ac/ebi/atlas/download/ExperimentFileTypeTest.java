package uk.ac.ebi.atlas.download;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ExperimentFileTypeTest {

    @Parameterized.Parameter
    public String fileTypeId;

    @Parameterized.Parameter(1)
    public ExperimentFileType fileType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "experiment-design", ExperimentFileType.EXPERIMENT_DESIGN },
                { "sdrf", ExperimentFileType.SDRF },
                { "cluster", ExperimentFileType.CLUSTERING },
                { "quantification-raw", ExperimentFileType.QUANTIFICATION_RAW },
                { "quantification-filtered", ExperimentFileType.QUANTIFICATION_FILTERED }
        });
    }

    @Test
    public void fileTypeFromValidId() {
        assertThat(ExperimentFileType.fromId(fileTypeId), is(fileType));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fileTypeFromEmptyIdThrowsException() {
        ExperimentFileType.fromId("");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void fileTypeFromNonexistentIdThrowsException() {
        ExperimentFileType.fromId("foo-bar");
    }
}
