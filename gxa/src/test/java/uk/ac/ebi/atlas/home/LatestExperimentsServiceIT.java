package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import uk.ac.ebi.atlas.utils.ExperimentInfo;

import javax.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:dispatcher-servlet.xml"})
public class LatestExperimentsServiceIT {

    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    @Inject
    ExperimentTrader experimentTrader;

    @Inject
    LatestExperimentsDao latestExperimentsDao;

    LatestExperimentsService subject;

    @Before
    public void setUp() throws Exception {
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