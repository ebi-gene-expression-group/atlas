package uk.ac.ebi.atlas.util;

import com.google.common.base.Stopwatch;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.experimentimport.analytics.SingleCellBaselineAnalyticsLoader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Controller
public class SingleCellDBLoaderController {

    private SingleCellBaselineAnalyticsLoader singleCellBaselineLoader;

    @Inject
    public SingleCellDBLoaderController(SingleCellBaselineAnalyticsLoader singleCellBaselineLoader) {
        this.singleCellBaselineLoader = singleCellBaselineLoader;
    }

    @RequestMapping(value = "/experiment/loadingSCDummy", method = RequestMethod.GET)
    public void testLoadingSC() throws IOException {

        Stopwatch timer = Stopwatch.createStarted();

        singleCellBaselineLoader.loadAnalytics("dummy_singlecell");

        timer.stop();
        System.out.println("Time loading the file to the DB => " + timer.elapsed(TimeUnit.MILLISECONDS) + " milliseconds");
    }
}
