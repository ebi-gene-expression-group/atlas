package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Transactional
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LatestExperimentsServiceIT {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Inject
    private ExperimentTrader experimentTrader;

    @Inject
    private LatestExperimentsDao latestExperimentsDao;

    @Inject
    private DataSource dataSource;

    private LatestExperimentsService subject;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    public void setUp() {
        subject =
                new LatestExperimentsService(
                        latestExperimentsDao, experimentTrader,
                        ImmutableSet.of(
                                ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                                ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                                ExperimentType.MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL,
                                ExperimentType.RNASEQ_MRNA_DIFFERENTIAL,
                                ExperimentType.PROTEOMICS_BASELINE,
                                ExperimentType.RNASEQ_MRNA_BASELINE
                        ));
    }

    public void assertThatExperimentsAreSortedByDate(List<ExperimentInfo> listOfExperimentInfo) throws Exception {
        Iterator<ExperimentInfo> iterator = listOfExperimentInfo.iterator();

        while (iterator.hasNext()) {
            ExperimentInfo thisElement = iterator.next();
            ExperimentInfo nextElement = iterator.hasNext() ? iterator.next() : null;

            if (nextElement != null) {
                Date thisDate = DATE_FORMAT.parse(thisElement.getLastUpdate());
                Date nextDate = DATE_FORMAT.parse(nextElement.getLastUpdate());

                if (!thisDate.equals(nextDate)) {
                    assertThat(
                            DATE_FORMAT.parse(thisElement.getLastUpdate())
                                    .after(DATE_FORMAT.parse(nextElement.getLastUpdate())),
                            is(true));
                }
            }
        }
    }

    @Test
    public void fetchLatestExperimentsAttributes() throws Exception {
        assertThat(subject.fetchLatestExperimentsAttributes().get("latestExperiments"), instanceOf(List.class));
        assertThatExperimentsAreSortedByDate(
                (List<ExperimentInfo>) subject.fetchLatestExperimentsAttributes().get("latestExperiments")
        );
    }
}
