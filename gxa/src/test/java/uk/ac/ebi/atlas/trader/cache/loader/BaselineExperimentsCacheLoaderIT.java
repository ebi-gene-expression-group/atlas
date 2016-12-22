package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class BaselineExperimentsCacheLoaderIT {

    private String accession = "E-MTAB-513";

    @Inject
    private RnaSeqBaselineExperimentFactory rnaSeqBaselineExperimentFactory;

    @Mock
    private ExperimentDAO experimentDao;

    @Mock
    private ArrayExpressClient arrayExpressClient;

    @Inject
    private ExperimentDesignParser experimentDesignParser;

    private ExperimentsCacheLoader<BaselineExperiment> subject;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Set<String> pubMedIds = Collections.emptySet();
        ExperimentDTO experimentDTO = new ExperimentDTO(accession, ExperimentType.RNASEQ_MRNA_BASELINE,
                "Homo sapiens", pubMedIds, "title", new Date(),
                false, UUID.randomUUID().toString());
        when(experimentDao.findExperiment(accession, true)).thenReturn(experimentDTO);

        subject = new ExperimentsCacheLoader<>(arrayExpressClient,experimentDesignParser,experimentDao,
                rnaSeqBaselineExperimentFactory );
    }


    @Test
    public void correctSpeciesReadFromDatabase() throws Exception {
        //given
        BaselineExperiment experiment = subject.load(accession);
        //then
        String species = experiment.getSpecies().getName();
        assertThat(species, is("Homo sapiens"));
    }


    @Test
    public void experimentShouldOnlyContainRunsFromDataFile() throws IOException {
        BaselineExperiment experiment = subject.load(accession);

        assertThat(experiment.getExperimentRunAccessions(), hasItems(
            "ERR030872", "ERR030873", "ERR030874", "ERR030875",
            "ERR030876", "ERR030877", "ERR030878", "ERR030879",
            "ERR030880", "ERR030881", "ERR030882", "ERR030883",
            "ERR030884", "ERR030885", "ERR030886", "ERR030887"
        ));

    }

    @Test
    public void experimentShouldContainAssayGroups() throws IOException {
        BaselineExperiment experiment = subject.load(accession);

        assertThat(experiment.getAssayGroups().getAssayGroupIds(), hasSize(16));
    }

    @Test(expected = IllegalStateException.class)
    public void loadNonExistentExperimentThrowsIllegalStateException() throws IOException {
        subject.load("FOOBAR");
    }

}
