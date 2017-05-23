package uk.ac.ebi.atlas.controllers.rest.singlecellloader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.atlas.experimentimport.analytics.singlecell.SingleCellBaselineLoader;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by barrera on 12/05/2017.
 *
 */
@Controller
public class DummySingleCellLoaderFromTSVController {

    private SingleCellBaselineLoader singleCellBaselineLoader;

    @Inject
    public DummySingleCellLoaderFromTSVController(SingleCellBaselineLoader singleCellBaselineLoader) {
        this.singleCellBaselineLoader = singleCellBaselineLoader;
    }

    @RequestMapping(value = "/experiment/loadingSCDummy", method = RequestMethod.GET)
    public void testLoadingSC() throws IOException {

        singleCellBaselineLoader.loadAnalytics("dummy_singlecell");

    }

}
