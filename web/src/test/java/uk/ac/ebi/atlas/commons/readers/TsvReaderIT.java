package uk.ac.ebi.atlas.commons.readers;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;

public class TsvReaderIT {

    private static final String PATH_TEMPLATE = "web/src/test/resources/magetab/{0}/{0}-analysis-methods.tsv";
    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private TsvReader subject;

    @Before
    public void setUp() throws Exception {
        subject = new TsvReaderImpl(PATH_TEMPLATE);
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
        subject = new TsvReaderImpl("web/src/test/resources/magetab/{0}/ExpDesign-{0}.tsv");
        List<String[]> result = subject.readAll(EXPERIMENT_ACCESSION);
        String[] firstLine = result.get(0);
        String[] lastLine = result.get(result.size() - 1);

        // then
        assertThat(firstLine, arrayContaining("Assay", "Sample Characteristics[organism]", "Sample Characteristics[age]", "Sample Characteristics[sex]", "Sample Characteristics[biosource provider]", "Factor Values[organism part]"));
        assertThat(lastLine, arrayContaining("ERR030871", "Homo sapiens", "  ", "  ", "  ", "16 Tissues mixture "));
    }
}
