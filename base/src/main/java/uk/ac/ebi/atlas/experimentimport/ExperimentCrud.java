package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/*
 * Responsible for:
 * - Database:
 *     - gxa: the experiments table, and the deprecated expression values tables)
 *     - scxa: tables scxa_experiments, scxa_analytics; TODO scxa_marker_gene, scxa_tsne_plot_*
 * - design files on disk
 */

public class ExperimentCrud {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentCrud.class);

    private final ExperimentDao experimentDao;
    private final ExperimentChecker experimentChecker;
    private final AnalyticsLoaderFactory analyticsLoaderFactory;
    private final ExperimentDesignFileWriterService experimentDesignFileWriterService;
    private final CondensedSdrfParser condensedSdrfParser;
    private final ConfigurationTrader configurationTrader;

    public ExperimentCrud(ExperimentDao experimentDao,
                          ExperimentChecker experimentChecker,
                          AnalyticsLoaderFactory analyticsLoaderFactory,
                          CondensedSdrfParser condensedSdrfParser,
                          ExperimentDesignFileWriterService experimentDesignFileWriterService,
                          ConfigurationTrader configurationTrader) {

        this.experimentDao = experimentDao;
        this.experimentChecker = experimentChecker;
        this.analyticsLoaderFactory = analyticsLoaderFactory;
        this.condensedSdrfParser = condensedSdrfParser;
        this.experimentDesignFileWriterService = experimentDesignFileWriterService;
        this.configurationTrader = configurationTrader;
    }

    public UUID importExperiment(String experimentAccession, boolean isPrivate) throws IOException {
        checkNotNull(experimentAccession);

        Pair<ExperimentConfiguration, CondensedSdrfParserOutput> files = loadAndValidateFiles(experimentAccession);
        ExperimentConfiguration experimentConfiguration = files.getLeft();
        CondensedSdrfParserOutput condensedSdrfParserOutput = files.getRight();

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        ExperimentDTO experimentDTO = ExperimentDTO.create(
                condensedSdrfParserOutput,
                condensedSdrfParserOutput
                        .getExperimentDesign()
                        .getSpeciesForAssays(
                                experimentConfiguration.getAssayGroups().stream()
                                        .flatMap(assayGroup -> assayGroup.assaysAnalyzedForThisDataColumn().stream())
                                        .collect(Collectors.toSet())), isPrivate);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        // We only insert in the DB differential and single cell experiments expressions
        analyticsLoaderFactory.getLoader(experimentConfiguration.getExperimentType())
                .loadAnalytics(experimentAccession);

        UUID accessKeyUuid = accessKey.map(UUID::fromString).orElseGet(UUID::randomUUID);
        experimentDao.addExperiment(experimentDTO, accessKeyUuid);
        updateWithNewExperimentDesign(condensedSdrfParserOutput.getExperimentDesign(), experimentDTO);

        return accessKeyUuid;
    }

    private Optional<String> fetchExperimentAccessKey(String experimentAccession) {
        try {
            ExperimentDTO experiment = findExperiment(experimentAccession);
            return Optional.of(experiment.getAccessKey());
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }

    public void deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = findExperiment(experimentAccession);
        checkNotNull(experimentDTO, MessageFormat.format("Experiment not found: {0}", experimentAccession));

        AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(experimentDTO.getExperimentType());
        analyticsLoader.deleteAnalytics(experimentAccession);

        experimentDao.deleteExperiment(experimentDTO.getExperimentAccession());
    }

    public ExperimentDTO findExperiment(String experimentAccession) {
        return experimentDao.getExperimentAsAdmin(experimentAccession);
    }

    public List<ExperimentDTO> findAllExperiments() {
        return experimentDao.getAllExperimentsAsAdmin();
    }

    public void makeExperimentPrivate(String experimentAccession) throws IOException {
        setExperimentPrivacyStatus(experimentAccession, true);
    }

    public void makeExperimentPublic(String experimentAccession) throws IOException {
        setExperimentPrivacyStatus(experimentAccession, false);
    }

    private void setExperimentPrivacyStatus(String experimentAccession, boolean newPrivacyStatus) {
        ExperimentDesign newDesign = loadAndValidateFiles(experimentAccession).getRight().getExperimentDesign();
        experimentDao.setExperimentPrivacyStatus(experimentAccession, newPrivacyStatus);
        ExperimentDTO experimentDTO = experimentDao.getExperimentAsAdmin(experimentAccession);
        Preconditions.checkState(newPrivacyStatus == experimentDTO.isPrivate(), "Failed to change experiment status in the db! (?)");

        updateWithNewExperimentDesign(newDesign, experimentDTO);
    }

    public void updateExperimentDesign(String experimentAccession) {
        updateWithNewExperimentDesign(
                loadAndValidateFiles(experimentAccession).getRight().getExperimentDesign(),
                experimentDao.getExperimentAsAdmin(experimentAccession)
        );
    }

    private void updateWithNewExperimentDesign(ExperimentDesign newDesign, ExperimentDTO experimentDTO){
        updateWithNewExperimentDesign(experimentDTO.getExperimentAccession(), experimentDTO.getExperimentType(), newDesign);

    }

    private void updateWithNewExperimentDesign(String accession, ExperimentType type,
                                               ExperimentDesign experimentDesign) {
        try {
            experimentDesignFileWriterService.writeExperimentDesignFile(accession, type, experimentDesign);
            LOGGER.info("updated design for experiment {}", accession);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Pair<ExperimentConfiguration, CondensedSdrfParserOutput> loadAndValidateFiles(String experimentAccession) {
        ExperimentConfiguration experimentConfiguration =
                configurationTrader.getExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, experimentConfiguration.getExperimentType());

        CondensedSdrfParserOutput condensedSdrfParserOutput =
                condensedSdrfParser.parse(experimentAccession, experimentConfiguration.getExperimentType());

        new ExperimentFilesCrossValidator(experimentConfiguration, condensedSdrfParserOutput.getExperimentDesign())
                .validate();

        return Pair.of(experimentConfiguration, condensedSdrfParserOutput);
    }

    public void checkFiles(String experimentAccession) throws RuntimeException {
        loadAndValidateFiles(experimentAccession);
    }
}
