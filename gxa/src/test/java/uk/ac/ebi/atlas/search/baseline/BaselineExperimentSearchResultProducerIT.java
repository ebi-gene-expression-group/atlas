package uk.ac.ebi.atlas.search.baseline;

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
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BaselineExperimentSearchResultProducerIT {
    @Inject
    private DataSource dataSource;

    @Inject
    private ExperimentTrader experimentTrader;

    private BaselineExperimentSearchResultProducer subject;

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
        subject = new BaselineExperimentSearchResultProducer(experimentTrader);
    }

    @Test
    void weHaveTwoColumnsCorrespondingToAdultAndFetus() {
        BaselineExperiment eProt1 = (BaselineExperiment) experimentTrader.getPublicExperiment("E-PROT-1");

        Map<String, Map<String, Double>> expressionsPerColumnPerExperiment = new HashMap<>();

        Map<String, Double> fakeResults = new HashMap<>();
        for (AssayGroup assayGroup: eProt1.getDataColumnDescriptors()) {
            fakeResults.put(assayGroup.getId(), Math.random() * 10000);
        }
        expressionsPerColumnPerExperiment.put(eProt1.getAccession(), fakeResults);

        BaselineExperimentProfilesList result =
                subject.buildProfilesForExperiments(expressionsPerColumnPerExperiment, "ORGANISM_PART");
        assertThat(result.getFactorsAcrossExperiments().size(), lessThan(eProt1.getDataColumnDescriptors().size()));
        assertThat(result.size(), is(2));
    }
}
