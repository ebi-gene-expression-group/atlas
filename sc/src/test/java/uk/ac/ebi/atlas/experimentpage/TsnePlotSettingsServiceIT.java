package uk.ac.ebi.atlas.experimentpage;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.WebConfig;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.testutils.JdbcUtils;

import javax.inject.Inject;
import java.io.UncheckedIOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class})
public class TsnePlotSettingsServiceIT {
    @Inject
    private JdbcUtils jdbcTestUtils;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private IdfParser idfParser;

    private TsnePlotSettingsService subject;

    @BeforeEach
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

    @Test()
    public void getClustersForInvalidAccessionThrowsException() {
        assertThatExceptionOfType(UncheckedIOException.class).isThrownBy(() -> subject.getAvailableClusters("FOO"));
    }

    @Test
    public void getPerplexitiesForValidAccession() {
        List<Integer> result = subject.getAvailablePerplexities(jdbcTestUtils.fetchRandomSingleCellExperimentAccession());

        assertThat(result)
                .isNotEmpty()
                .doesNotHaveDuplicates();
    }
}
