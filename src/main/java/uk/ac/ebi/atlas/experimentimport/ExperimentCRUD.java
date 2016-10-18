package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.expressiondataserializer.ExpressionSerializerService;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
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
    private ConfigurationTrader configurationTrader;
    private ExpressionSerializerService expressionSerializerService;

    // requires no-arg constructor for @Transactional proxying, hence setter injection of dependencies
    public ExperimentCRUD() {
    }

    @Inject
    public void setConfigurationTrader(ConfigurationTrader configurationTrader) {
        this.configurationTrader = configurationTrader;
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
    public void setExpressionSerializerService(ExpressionSerializerService expressionSerializerService) {
        this.expressionSerializerService = expressionSerializerService;
    }

    @Transactional
    public UUID importExperiment(String experimentAccession, boolean isPrivate) throws IOException {

        ExperimentConfiguration configuration = loadExperimentConfiguration(experimentAccession);

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        analyticsLoaderFactory.getLoader(configuration.getExperimentType()).loadAnalytics(experimentAccession);

        // TODO If baseline experiment serialize on import here https://www.pivotaltracker.com/story/show/96273286
        // if (configuration.getExperimentType() == ExperimentType.RNASEQ_MRNA_BASELINE || ExperimentType.{a serializable experiment type}...) {
        //     kryoSerializeExpressionData(experimentAccession, experimentType);
        // }

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

        expressionSerializerService.removeKryoFile(experimentAccession);
        return experimentDTO.getAccessKey();
    }

    private ExperimentConfiguration loadExperimentConfiguration(String experimentAccession) {
        experimentChecker.checkConfigurationFile(experimentAccession);
        ExperimentConfiguration configuration = configurationTrader.getExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, configuration.getExperimentType());
        return configuration;
    }

    public String serializeExpressionData(String experimentAccession) {
        return expressionSerializerService.kryoSerializeExpressionData(experimentAccession);
    }
}
