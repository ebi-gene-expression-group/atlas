package uk.ac.ebi.atlas.commons.writers;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.SingleCellAnalyticsStream;
import uk.ac.ebi.atlas.resource.DataFileHub;

import javax.inject.Inject;

import java.io.FileWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SingleCellAnalyticsCsvWriterIT {

    @Inject
    DataFileHub dataFileHub;

    SingleCellAnalyticsCsvWriter subject;

    @Ignore
    public void write() throws Exception {
        SingleCellAnalyticsStream scaStream =
                new SingleCellAnalyticsStream(dataFileHub.getSingleCellExperimentFiles("E-MTAB-5061").dataFiles());

        subject = new SingleCellAnalyticsCsvWriter(new FileWriter("/tmp/blah.csv"));
        subject.write(scaStream);
    }

}