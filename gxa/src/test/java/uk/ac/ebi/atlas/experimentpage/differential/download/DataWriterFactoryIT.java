package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

import java.io.PrintWriter;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class DataWriterFactoryIT {

    private final String accession = "E-GEOD-54705";
    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    DataWriterFactory subject;

    @Inject
    DataFileHub dataFileHub;

    @Test
    public void testGetRnaSeqAnalyticsDataWriter() throws Exception {
        DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);
        assumeThat(dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).analytics.exists(), is(true));

        PrintWriter w = Mockito.mock(PrintWriter.class);

        long count = subject.getRnaSeqAnalyticsDataWriter(experiment, w).write();

        assertThat((int) count, greaterThan(0));
    }

    @Test
    public void testGetRnaSeqRawDataWriter() throws Exception {
        DifferentialExperiment experiment = (DifferentialExperiment) experimentTrader.getPublicExperiment(accession);
        assumeThat(dataFileHub.getDifferentialExperimentFiles(experiment.getAccession()).rawCounts.exists(), is(true));

        PrintWriter w = Mockito.mock(PrintWriter.class);

        long count = subject.getRnaSeqRawDataWriter(experiment, w).write();

        assertThat((int) count, greaterThan(0));

    }
}