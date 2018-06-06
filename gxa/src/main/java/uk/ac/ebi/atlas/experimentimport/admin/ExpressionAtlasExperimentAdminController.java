package uk.ac.ebi.atlas.experimentimport.admin;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.atlas.experimentimport.ExperimentCrudFactory;
import uk.ac.ebi.atlas.experimentimport.ExpressionAtlasExperimentChecker;
import uk.ac.ebi.atlas.experimentimport.GxaExperimentDao;
import uk.ac.ebi.atlas.experimentimport.analytics.GxaAnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.coexpression.BaselineCoexpressionProfileLoader;
import uk.ac.ebi.atlas.experimentpage.ExperimentAttributesService;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;

@Controller
@Scope("request")
@RequestMapping("/admin/experiments")
public class ExpressionAtlasExperimentAdminController extends ExperimentAdminController {
    @Inject
    public ExpressionAtlasExperimentAdminController(DataFileHub dataFileHub,
                                                    ExperimentCrudFactory experimentCrudFactory,
                                                    GxaExperimentDao experimentDao,
                                                    ExpressionAtlasExperimentChecker experimentChecker,
                                                    GxaAnalyticsLoaderFactory analyticsLoaderFactory,
                                                    BaselineCoexpressionProfileLoader baselineCoexpressionProfileLoader,
                                                    AnalyticsIndexerManager analyticsIndexerManager,
                                                    ExperimentTrader experimentTrader,
                                                    ExperimentAttributesService experimentAttributesService) {
        super(
                new ExperimentOps(
                        new ExperimentOpLogWriter(dataFileHub),
                        new ExpressionAtlasExperimentOpsExecutionService(
                                experimentCrudFactory.create(experimentDao, experimentChecker, analyticsLoaderFactory),
                                baselineCoexpressionProfileLoader,
                                analyticsIndexerManager,
                                experimentTrader,
                                experimentAttributesService)));
    }
}
