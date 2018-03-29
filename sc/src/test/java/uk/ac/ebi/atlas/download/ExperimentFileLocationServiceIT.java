package uk.ac.ebi.atlas.download;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class ExperimentFileLocationServiceIT {

    private final String EXPERIMENT_ACCESSION = "TEST-SC";
    private final String EXPERIMENT_DESIGN_FILE_NAME = "ExpDesign-TEST-SC.tsv";

    @Inject
    private DataFileHub dataFileHub;

    private ExperimentFileLocationService subject;

    @Before
    public void setUp() {
        this.subject = new ExperimentFileLocationService(dataFileHub);
    }

    @Test
    public void existingExperimentDesignFile() {
        Path path = subject.getFilePath(EXPERIMENT_ACCESSION, ExperimentFileType.EXPERIMENT_DESIGN);
        File file = path.toFile();

        assertThat(file.getName(), is(EXPERIMENT_DESIGN_FILE_NAME));

        assertThat(file.exists(), is(true));
        assertThat(file.isDirectory(), is(false));
    }

    @Test
    public void invalidExperimentAccession() {
        Path path = subject.getFilePath("", ExperimentFileType.EXPERIMENT_DESIGN);
        File file = path.toFile();

        assertThat(file.exists(), is(false));
    }
}
