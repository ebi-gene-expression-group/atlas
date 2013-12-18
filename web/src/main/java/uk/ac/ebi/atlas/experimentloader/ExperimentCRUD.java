package uk.ac.ebi.atlas.experimentloader;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.UUID;

@Named
public class ExperimentCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUD.class);

    private ExperimentChecker experimentChecker;
    private ExperimentMetadataCRUD experimentMetadataCRUD;
    private ExperimentAnalyticsCRUD experimentAnalyticsCRUD;


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
    public void setExperimentAnalyticsCRUD(ExperimentAnalyticsCRUD experimentAnalyticsCRUD) {
        this.experimentAnalyticsCRUD = experimentAnalyticsCRUD;
    }

    @Transactional
    public UUID loadExperiment(String experimentAccession, boolean isPrivate) throws IOException {

        ExperimentConfiguration configuration = loadExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, configuration.getExperimentType());

        experimentAnalyticsCRUD.loadAnalytics(experimentAccession, configuration.getExperimentType());

        return experimentMetadataCRUD.loadExperiment(experimentAccession, configuration, isPrivate);
    }

    @Transactional
    public void deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = experimentMetadataCRUD.findExperiment(experimentAccession);
        experimentAnalyticsCRUD.deleteAnalytics(experimentAccession, experimentDTO.getExperimentType());
        experimentMetadataCRUD.deleteExperiment(experimentDTO);
    }

    private ExperimentConfiguration loadExperimentConfiguration(String experimentAccession) {
        experimentChecker.checkConfigurationFilePermissions(experimentAccession);
        return experimentMetadataCRUD.loadExperimentConfiguration(experimentAccession);
    }

}
