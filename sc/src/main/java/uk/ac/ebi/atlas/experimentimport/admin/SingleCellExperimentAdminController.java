package uk.ac.ebi.atlas.experimentimport.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrudFactory;
import uk.ac.ebi.atlas.experimentimport.ScxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.SingleCellExperimentChecker;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.resource.DataFileHub;
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
                                               ExperimentTrader experimentTrader,
                                               ExperimentAttributesService experimentAttributesService) {
        super(
                new ExperimentOps(
                        new ExperimentOpLogWriter(dataFileHub),
                        new SingleCellOpsExecutionService(
                                experimentCrudFactory.create(experimentDao, experimentChecker),
                                experimentTrader,
                                experimentAttributesService)));
    }
}
