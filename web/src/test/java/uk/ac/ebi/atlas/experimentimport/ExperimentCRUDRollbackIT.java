package uk.ac.ebi.atlas.experimentimport;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.MageTabParserFactory;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml", "classpath:oracleUcpContext.xml"})
public class ExperimentCRUDRollbackIT {

    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUDRollbackIT.class);

    public static final String NEW_EXPERIMENT_ACCESSION = "TEST-BASELINE";

    @Inject
    private ExperimentCRUD subject;

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Inject
    ExperimentDAO experimentDAO;
    @Inject
    ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder;
    @Inject
    ExperimentTrader experimentTrader;
    @Inject
    ExperimentDTOBuilder experimentDTOBuilder;
    @Inject
    MageTabParserFactory mageTabParserFactory;
    @Inject
    ExperimentMetadataCRUD experimentMetadataCRUD;
    @Inject
    EFOParentsLookupService efoParentsLookupService;

    @Mock
    ConditionsIndexTrader conditionsIndexTrader;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(conditionsIndexTrader.getIndex(any(Experiment.class))).thenThrow(new IllegalStateException("die!"));
        ExperimentMetadataCRUD experimentMetadataCRUDmock = new ExperimentMetadataCRUD(experimentDAO,
                experimentDesignFileWriterBuilder, experimentTrader, experimentDTOBuilder,
                mageTabParserFactory, conditionsIndexTrader, efoParentsLookupService);
        subject.setExperimentMetadataCRUD(experimentMetadataCRUDmock);
    }

    @After
    public void cleanup() {
        // needed otherwise other tests run by maven/bamboo will die!
        subject.setExperimentMetadataCRUD(experimentMetadataCRUD);
    }

    @Test
    public void rollbackDatabaseChangesOnSolrFailure() throws IOException {
        assertThat("experiment already exists in db", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions already exist in db", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));

        String exceptionMessage = null;

        try {
            subject.importExperiment(NEW_EXPERIMENT_ACCESSION, false);
        } catch (IllegalStateException e) {
            exceptionMessage = e.getMessage();
        }

        LOGGER.info("importExperiment complete");

        assertThat("no exception thrown", exceptionMessage, is("die!"));

        assertThat("experiment not rolled back", experimentCount(NEW_EXPERIMENT_ACCESSION), is(0));
        assertThat("baseline expressions not rolled back", baselineExpressionsCount(NEW_EXPERIMENT_ACCESSION), is(0));
    }

    private int experimentCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from EXPERIMENT WHERE accession = ?", Integer.class, accession);
    }

    private int baselineExpressionsCount(String accession) {
        return jdbcTemplate.queryForObject("select COUNT(*) from RNASEQ_BSLN_EXPRESSIONS WHERE EXPERIMENT = ? AND ISACTIVE = 'T'", Integer.class, accession);
    }

}
