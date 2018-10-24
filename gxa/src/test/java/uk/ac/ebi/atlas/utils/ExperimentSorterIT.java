package uk.ac.ebi.atlas.utils;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExperimentSorterIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private DataFileHub dataFileHub;

    @Inject
    private ExperimentTrader experimentTrader;

    @Inject
    private ConfigurationTrader configurationTrader;

    private ExperimentSorter subject;

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
    void setUp() {
        subject = new ExperimentSorter(dataFileHub, experimentTrader, configurationTrader);
    }

    @Test
    void testReverseSortExperimentsPerSize() {
        Collection<String> experimentAccessionsPerSizeDescending = subject.reverseSortAllExperimentsPerSize().values();
        Iterator<String> iterator = experimentAccessionsPerSizeDescending.iterator();

        String firstExperiment = iterator.next();


        String lastExperiment = firstExperiment;
        while (iterator.hasNext()) {
            lastExperiment = iterator.next();
        }

        Long firstSize = -1L;
        Long lastSize = -1L;

        for (Map.Entry<Long, Collection<String>> e: subject.reverseSortAllExperimentsPerSize().asMap().entrySet()) {
            if (e.getValue().contains(lastExperiment)) {
                lastSize = e.getKey();
            }
            if (e.getValue().contains(firstExperiment)) {
                firstSize = e.getKey();
            }
        }
        assertTrue(firstSize > 0 && lastSize > 0 && firstSize >= lastSize);
    }

    // TODO https://www.pivotaltracker.com/story/show/101118548
    @Disabled
    public void reverseSortExperimentsPerSizeContainsAllExperiments() {
        Collection<String> experimentAccessionsPerSizeDescending = subject.reverseSortAllExperimentsPerSize().values();
        assertThat(experimentAccessionsPerSizeDescending.size(), Matchers.greaterThan(50));
    }
}
