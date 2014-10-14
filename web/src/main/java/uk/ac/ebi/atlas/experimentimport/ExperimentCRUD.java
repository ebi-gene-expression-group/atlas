package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsDao;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.web.controllers.ResourceNotFoundException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.UUID;

@Named
public class ExperimentCRUD {

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
    public UUID importExperiment(String experimentAccession, boolean isPrivate) throws IOException {

        ExperimentConfiguration configuration = loadExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, configuration.getExperimentType());

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(configuration.getExperimentType());
        analyticsLoader.loadAnalytics(experimentAccession);

        return experimentMetadataCRUD.importExperiment(experimentAccession, configuration, isPrivate, accessKey);
    }

    private Optional<String> fetchExperimentAccessKey(String experimentAccession) {
        try {
            ExperimentDTO experiment = experimentMetadataCRUD.findExperiment(experimentAccession);
            return Optional.of(experiment.getAccessKey());
        } catch (ResourceNotFoundException e) {
            return Optional.absent();
        }
    }

    // returns access key, so it can be reused during a reload
    @Transactional
    public String deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);

        AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(experimentDTO.getExperimentType());
        analyticsLoader.deleteAnalytics(experimentAccession);

        experimentMetadataCRUD.deleteExperiment(experimentDTO);

        return experimentDTO.getAccessKey();
    }

    private ExperimentConfiguration loadExperimentConfiguration(String experimentAccession) {
        experimentChecker.checkConfigurationFilePermissions(experimentAccession);
        return experimentMetadataCRUD.loadExperimentConfiguration(experimentAccession);
    }

    public void deleteInactiveAnalytics() {
        analyticsDao.deleteInactiveAnalytics();
    }
}
