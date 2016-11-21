package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexingService;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExperimentCrud {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentCrud.class);
    private final ExperimentDesignFileWriterService experimentDesignFileWriterService;
    private final CondensedSdrfParser condensedSdrfParser;
    private ExperimentDAO experimentDAO;
    private final ConditionsIndexingService conditionsIndexingService;
    private AnalyticsIndexerManager analyticsIndexerManager;
    /*
    TODO analyticsIndexerManager is funny because he only deletes things here, and imports them elsewhere.
    How to share the two classes:
    take out
    conditionsIndexingService and analyticsIndexerManager?
     */
    private final ExperimentChecker experimentChecker;
    private final AnalyticsLoaderFactory analyticsLoaderFactory;
    private final ConfigurationTrader configurationTrader;

    public ExperimentCrud(CondensedSdrfParser condensedSdrfParser,
                          ExperimentDesignFileWriterService experimentDesignFileWriterService,
                          ConditionsIndexingService conditionsIndexingService,
                          ExperimentDAO experimentDAO,
                          AnalyticsIndexerManager analyticsIndexerManager,
                          ExperimentChecker experimentChecker,
                          AnalyticsLoaderFactory analyticsLoaderFactory,
                          ConfigurationTrader configurationTrader) {
        this.condensedSdrfParser = condensedSdrfParser;
        this.experimentDesignFileWriterService = experimentDesignFileWriterService;
        this.conditionsIndexingService = conditionsIndexingService;
        this.experimentDAO = experimentDAO;
        this.analyticsIndexerManager = analyticsIndexerManager;
        this.experimentChecker = experimentChecker;
        this.analyticsLoaderFactory = analyticsLoaderFactory;
        this.configurationTrader = configurationTrader;
    }

    public UUID importExperiment(String experimentAccession, boolean isPrivate) throws IOException {
        checkNotNull(experimentAccession);
        ExperimentConfiguration experimentConfiguration = loadExperimentConfiguration(experimentAccession);
        checkNotNull(experimentConfiguration);

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        analyticsLoaderFactory.getLoader(experimentConfiguration.getExperimentType()).loadAnalytics(experimentAccession);


        ExperimentType experimentType = experimentConfiguration.getExperimentType();
        CondensedSdrfParserOutput condensedSdrfParserOutput = condensedSdrfParser.parse(experimentAccession, experimentType);
        ExperimentDTO experimentDTO = ExperimentDTO.createNew(
                condensedSdrfParserOutput,
                condensedSdrfParserOutput.getExperimentDesign().getSpeciesForAssays(experimentConfiguration.getAssayAccessions()), isPrivate);

        updateExperimentDesign(experimentDTO);

        return experimentDAO.addExperiment(experimentDTO, accessKey);
    }

    private Optional<String> fetchExperimentAccessKey(String experimentAccession) {
        try {
            ExperimentDTO experiment = findExperiment(experimentAccession);
            return Optional.of(experiment.getAccessKey());
        } catch (ResourceNotFoundException e) {
            return Optional.absent();
        }
    }

    public String deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = findExperiment(experimentAccession);

        AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(experimentDTO.getExperimentType());
        analyticsLoader.deleteAnalytics(experimentAccession);

        checkNotNull(experimentDTO);


        if (!experimentDTO.isPrivate()) {
            conditionsIndexingService.removeConditions(experimentDTO.getExperimentAccession(), experimentDTO.getExperimentType());
            analyticsIndexerManager.deleteFromAnalyticsIndex(experimentDTO.getExperimentAccession());
        }

        experimentDAO.deleteExperiment(experimentDTO.getExperimentAccession());

        return experimentDTO.getAccessKey();
    }

    public ExperimentDTO findExperiment(String experimentAccession) {
        return experimentDAO.findExperiment(experimentAccession, true);
    }

    public List<ExperimentDTO> findAllExperiments() {
        return experimentDAO.findAllExperiments();
    }

    public void makeExperimentPrivate(String experimentAccession) throws IOException {
        experimentDAO.updateExperiment(experimentAccession, true);
        analyticsIndexerManager.deleteFromAnalyticsIndex(experimentAccession);
        updateExperimentDesign(experimentAccession);
    }

    public void makeExperimentPublic(String experimentAccession) throws IOException {
        experimentDAO.updateExperiment(experimentAccession, false);
        updateExperimentDesign(experimentAccession);
    }

    public void updateExperimentDesign(String experimentAccession) {
        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);
        updateExperimentDesign(experimentDTO);
    }

    void updateExperimentDesign(ExperimentDTO experimentDTO) {
        updateExperimentDesign(experimentDTO.getExperimentAccession(), experimentDTO.getExperimentType(),
                experimentDTO.isPrivate(),
                condensedSdrfParser.parse(experimentDTO.getExperimentAccession(),
                experimentDTO.getExperimentType())
                .getExperimentDesign());
    }

    void updateExperimentDesign(String accession, ExperimentType type, boolean isPrivate, ExperimentDesign experimentDesign) {
        try {
            experimentDesignFileWriterService.writeExperimentDesignFile(accession, type, experimentDesign);
            LOGGER.info("updated design for experiment {}", accession);
            if (!isPrivate) {
                conditionsIndexingService.indexConditions(accession, type, experimentDesign);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private ExperimentConfiguration loadExperimentConfiguration(String experimentAccession) {
        experimentChecker.checkConfigurationFile(experimentAccession);
        ExperimentConfiguration configuration = configurationTrader.getExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, configuration.getExperimentType());
        return configuration;
    }
}
