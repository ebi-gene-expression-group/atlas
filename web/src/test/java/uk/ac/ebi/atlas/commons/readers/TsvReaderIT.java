package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TsvReaderIT {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    @Value("#{configuration['experiment.analysis-method.path.template']}")
    private String analysisMethodsTemplate;

    @Value("#{configuration['experiment.experiment-design.path.template']}")
    private String experimentDesignTemplate;

    @Inject
    private TsvReaderBuilder tsvReaderBuilder;

    private TsvReader subject;

    @Before
    public void setUp() throws Exception {
        subject = tsvReaderBuilder.forTsvFilePathTemplate(analysisMethodsTemplate).build();
    }

    @Test
    public void readLine() {
        String[] firstLine = subject.readLine(EXPERIMENT_ACCESSION, 0L);
        assertThat(firstLine, arrayContaining("# Pipeline version", "0.1.6"));
    }

    @Test
    public void readAll() {

        // given
        List<String[]> result = subject.readAll(EXPERIMENT_ACCESSION);
        String[] firstLine = result.get(0);
        String[] lastLine = result.get(result.size() - 1);

        // then
        assertThat(firstLine, arrayContaining("Analyzed Libraries", "<a href=\"experiments/E-MTAB-513/experiment-design\">Paired-end only</a>"));
        assertThat(lastLine, arrayContaining("Normalized Counts per Gene", "Obtained from the <a href=\"http://cufflinks.cbcb.umd.edu/manual.html#fpkm_tracking_format\">genes.fpkm_tracking files</a>, then averaged for all biological replicates (if any)"));
    }

    @Test
    public void readExpDesignAll() {

        // given
        subject = tsvReaderBuilder.forTsvFilePathTemplate(experimentDesignTemplate).build();
        List<String[]> result = subject.readAll(EXPERIMENT_ACCESSION);
        String[] firstLine = result.get(0);
        String[] lastLine = result.get(result.size() - 1);

        // then
        assertThat(firstLine, arrayContaining("Run", "Sample Characteristics[Organism]", "Sample Characteristics[age]", "Sample Characteristics[ethnic group]", "Sample Characteristics[organism part]", "Sample Characteristics[sex]", "Factor Values[organism part]"));
        assertThat(lastLine, arrayContaining("ERR030903", "Homo sapiens", "60", "Caucasian", "thyroid", "female", "thyroid"));
    }
}
