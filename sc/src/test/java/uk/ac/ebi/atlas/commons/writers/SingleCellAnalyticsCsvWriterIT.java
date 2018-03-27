package uk.ac.ebi.atlas.commons.writers;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.SingleCellAnalyticsStreamer;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.DataFileHub.SingleCellExperimentFiles;

import javax.inject.Inject;

import java.io.FileWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class SingleCellAnalyticsCsvWriterIT {

    @Inject
    DataFileHub dataFileHub;

    SingleCellAnalyticsCsvWriter subject;

    @Test
    public void test() {

    }

    @Ignore
    public void write() throws Exception {
        SingleCellExperimentFiles scFiles = dataFileHub.getSingleCellExperimentFiles("E-MTAB-5061");

        SingleCellAnalyticsStreamer scaStreamer =
                new SingleCellAnalyticsStreamer(scFiles.tpmsMatrix, scFiles.geneIdsTsv, scFiles.cellIdsTsv);

        subject = new SingleCellAnalyticsCsvWriter(new FileWriter("/tmp/blah.csv"));
        subject.write(scaStreamer);
    }

}