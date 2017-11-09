package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoader;
import uk.ac.ebi.atlas.experimentimport.analytics.AnalyticsLoaderFactory;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/*
 * Responsible for:
 * - the database (the experiments table, and the deprecated expression values tables)
 * - conditions index
 * - design files on disk
 */

public class ExperimentCrud {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentCrud.class);
    private final ExperimentDesignFileWriterService experimentDesignFileWriterService;
    private final CondensedSdrfParser condensedSdrfParser;
    private final ExperimentDAO experimentDAO;
    private final ExperimentChecker experimentChecker;
    private final AnalyticsLoaderFactory analyticsLoaderFactory;
    private final ConfigurationTrader configurationTrader;

    public ExperimentCrud(CondensedSdrfParser condensedSdrfParser,
                          ExperimentDesignFileWriterService experimentDesignFileWriterService,
                          ExperimentDAO experimentDAO,
                          ExperimentChecker experimentChecker,
                          AnalyticsLoaderFactory analyticsLoaderFactory,
                          ConfigurationTrader configurationTrader) {

        this.condensedSdrfParser = condensedSdrfParser;
        this.experimentDesignFileWriterService = experimentDesignFileWriterService;
        this.experimentDAO = experimentDAO;
        this.experimentChecker = experimentChecker;
        this.analyticsLoaderFactory = analyticsLoaderFactory;
        this.configurationTrader = configurationTrader;

    }

    public UUID importExperiment(String experimentAccession, boolean isPrivate) throws IOException {
        checkNotNull(experimentAccession);

        Pair<ExperimentConfiguration, CondensedSdrfParserOutput> files = loadAndValidateFiles(experimentAccession);
        ExperimentConfiguration experimentConfiguration = files.getLeft();
        CondensedSdrfParserOutput condensedSdrfParserOutput = files.getRight();

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        ExperimentDTO experimentDTO = ExperimentDTO.createNew(
                condensedSdrfParserOutput,
                condensedSdrfParserOutput
                        .getExperimentDesign()
                        .getSpeciesForAssays(FluentIterable.from(experimentConfiguration.getAssayGroups()).transformAndConcat(AssayGroup::assaysAnalyzedForThisDataColumn).toSet()), isPrivate);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        // We only insert in the DB differential experiments expressions
        if (experimentConfiguration.getExperimentType().isDifferential()) {
            analyticsLoaderFactory.getLoader(experimentConfiguration.getExperimentType()).loadAnalytics(experimentAccession);
        }

        UUID result = experimentDAO.addExperiment(experimentDTO, accessKey);

        updateWithNewExperimentDesign(condensedSdrfParserOutput.getExperimentDesign(), experimentDTO);

        return result;

    }

    private Optional<String> fetchExperimentAccessKey(String experimentAccession) {
        try {
            ExperimentDTO experiment = findExperiment(experimentAccession);
            return Optional.of(experiment.getAccessKey());
        } catch (ResourceNotFoundException e) {
            return Optional.absent();
        }
    }



    public void deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = findExperiment(experimentAccession);
        checkNotNull(experimentDTO, MessageFormat.format("Experiment not found: {0}", experimentAccession));

        // We only insert in the DB differential experiments expressions
        if (experimentDTO.getExperimentType().isDifferential()) {
            AnalyticsLoader analyticsLoader = analyticsLoaderFactory.getLoader(experimentDTO.getExperimentType());
            analyticsLoader.deleteAnalytics(experimentAccession);
        }

        experimentDAO.deleteExperiment(experimentDTO.getExperimentAccession());

    }

    public ExperimentDTO findExperiment(String experimentAccession) {
        return experimentDAO.findExperiment(experimentAccession, true);
    }

    public List<ExperimentDTO> findAllExperiments() {
        return experimentDAO.findAllExperiments();
    }

    public void makeExperimentPrivate(String experimentAccession) throws IOException {
        setExperimentPrivacyStatus(experimentAccession, true);
    }

    public void makeExperimentPublic(String experimentAccession) throws IOException {
        setExperimentPrivacyStatus(experimentAccession, false);
    }

    private void setExperimentPrivacyStatus(String experimentAccession, boolean newPrivacyStatus){
        ExperimentDesign newDesign = loadAndValidateFiles(experimentAccession).getRight().getExperimentDesign();
        experimentDAO.updateExperiment(experimentAccession, newPrivacyStatus);
        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, newPrivacyStatus);
        Preconditions.checkState(newPrivacyStatus == experimentDTO.isPrivate(), "Failed to change experiment status in the db! (?)");

        updateWithNewExperimentDesign(newDesign, experimentDTO);
    }

    public void updateExperimentDesign(String experimentAccession) {
        updateWithNewExperimentDesign(
                loadAndValidateFiles(experimentAccession).getRight().getExperimentDesign(),
                experimentDAO.findExperiment(experimentAccession, true)
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

    private Pair<ExperimentConfiguration, CondensedSdrfParserOutput> loadAndValidateFiles(String experimentAccession){
        ExperimentConfiguration experimentConfiguration = configurationTrader.getExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, experimentConfiguration.getExperimentType());
        CondensedSdrfParserOutput condensedSdrfParserOutput = condensedSdrfParser.parse(experimentAccession, experimentConfiguration.getExperimentType());
        new ExperimentFilesCrossValidator(experimentConfiguration, condensedSdrfParserOutput.getExperimentDesign()).validate();
        return Pair.of(experimentConfiguration, condensedSdrfParserOutput);
    }

    public void checkFiles(String experimentAccession) throws RuntimeException {
        loadAndValidateFiles(experimentAccession);
    }
}
