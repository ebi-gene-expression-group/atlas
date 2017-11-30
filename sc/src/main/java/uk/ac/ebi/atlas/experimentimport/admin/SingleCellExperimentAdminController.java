package uk.ac.ebi.atlas.experimentimport.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrud;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrudFactory;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.SingleCellExperimentChecker;
import uk.ac.ebi.atlas.experimentimport.analytics.ScxaAnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.markergenes.MarkerGeneDao;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
@Scope("request")
@RequestMapping("/admin/experiments")
public class SingleCellExperimentAdminController extends ExperimentAdminController {
    @Inject
    public SingleCellExperimentAdminController(DataFileHub dataFileHub,
                                               ExperimentCrudFactory experimentCrudFactory,
                                               ScxaExperimentDao experimentDao,
                                               SingleCellExperimentChecker experimentChecker,
                                               ScxaAnalyticsLoaderFactory analyticsLoaderFactory,
                                               ExperimentTrader experimentTrader,
                                               MarkerGeneDao markerGeneDao) {
        super(
                new ExperimentOps(
                        new ExperimentOpLogWriter(dataFileHub),
                        new SingleCellOpsExecutionService(
                                experimentCrudFactory.create(experimentDao, experimentChecker, analyticsLoaderFactory),
                                experimentTrader,
                                analyticsLoaderFactory,
                                markerGeneDao)));
    }
}
