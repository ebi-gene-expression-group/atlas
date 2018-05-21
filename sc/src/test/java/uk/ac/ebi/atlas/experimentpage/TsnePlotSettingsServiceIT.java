package uk.ac.ebi.atlas.experimentpage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.io.UncheckedIOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class TsnePlotSettingsServiceIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private IdfParser idfParser;

    private TsnePlotSettingsService subject;

    @Before
    public void setUp() {
        this.subject = new TsnePlotSettingsService(dataFileHub, idfParser);
    }


    @Test
    public void getClustersForValidAccession() {
        List<Integer> result = subject.getAvailableClusters(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());

        assertThat(result)
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }

    @Test(expected = UncheckedIOException.class)
    public void getClustersForInvalidAccessionThrowsException() {
        subject.getAvailableClusters("FOO");
    }

    @Test
    public void getPerplexitiesForValidAccession() {
        List<Integer> result = subject.getAvailablePerplexities(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());

        assertThat(result)
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }
}
