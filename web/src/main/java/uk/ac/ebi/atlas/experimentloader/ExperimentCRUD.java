package uk.ac.ebi.atlas.experimentloader;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentloader.analytics.AnalyticsDao;
import uk.ac.ebi.atlas.experimentloader.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentloader.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.UUID;

@Named
public class ExperimentCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUD.class);

    private ExperimentChecker experimentChecker;
    private ExperimentMetadataCRUD experimentMetadataCRUD;
    private AnalyticsLoaderFactory analyticsLoaderFactory;
    private AnalyticsDao analyticsDao;

    // requires no-arg constructor for @Transactional proxying, hence setter injection
    // of dependencies
    public ExperimentCRUD() {
    }

    @Inject
    public void setExperimentChecker(ExperimentChecker experimentChecker) {
        this.experimentChecker = experimentChecker;
    }

    @Inject
    public void setExperimentMetadataCRUD(ExperimentMetadataCRUD experimentMetadataCRUD) {
        this.experimentMetadataCRUD = experimentMetadataCRUD;
    }

    @Inject
    public void setAnalyticsLoaderFactory(AnalyticsLoaderFactory analyticsLoaderFactory) {
        this.analyticsLoaderFactory = analyticsLoaderFactory;
    }

    @Inject
    public void setAnalyticsDao(AnalyticsDao analyticsDao) {
        this.analyticsDao = analyticsDao;
    }

    @Transactional
    public UUID loadExperiment(String experimentAccession, boolean isPrivate) throws IOException {

        ExperimentConfiguration configuration = loadExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, configuration.getExperimentType());

        if (experimentAccessionExists(experimentAccession)) {
            deleteExperiment(experimentAccession);
        }

        AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(configuration.getExperimentType());

        //TODO: remove
        if (analyticsLoader != null) {
            analyticsLoader.loadAnalytics(experimentAccession);
        }

        return experimentMetadataCRUD.loadExperiment(experimentAccession, configuration, isPrivate);
    }

    private boolean experimentAccessionExists(String experimentAccession) {
        try {
            experimentMetadataCRUD.findExperiment(experimentAccession);
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    @Transactional
    public void deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);

        AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(experimentDTO.getExperimentType());

        //TODO: remove
        if (analyticsLoader != null) {
            analyticsLoader.deleteAnalytics(experimentAccession);
        }

        experimentMetadataCRUD.deleteExperiment(experimentDTO);
    }

    private ExperimentConfiguration loadExperimentConfiguration(String experimentAccession) {
        experimentChecker.checkConfigurationFilePermissions(experimentAccession);
        return experimentMetadataCRUD.loadExperimentConfiguration(experimentAccession);
    }

    public void deleteInactiveExpressions() {
        analyticsDao.deleteInactiveExpressions();
    }
}
