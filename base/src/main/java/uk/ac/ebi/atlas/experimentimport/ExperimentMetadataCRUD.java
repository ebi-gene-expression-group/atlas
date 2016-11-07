package uk.ac.ebi.atlas.experimentimport;

import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileService;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSetMultimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

// Experiment metadata is sourced from SDRF/IDF files, and stored in experiment design files, the database, and the Solr conditions Index
@Named
@Scope("prototype")
public class ExperimentMetadataCRUD {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentMetadataCRUD.class);

    private ExperimentDAO experimentDAO;
    private ExperimentDesignFileService experimentDesignFileService;
    private ExperimentTrader experimentTrader;
    private final CondensedSdrfParser condensedSdrfParser;
    private ConditionsIndexTrader conditionsIndexTrader;
    private EFOLookupService efoParentsLookupService;
    private AnalyticsIndexerManager analyticsIndexerManager;

    //TODO: refactor this class - it has too many collaborators
    @Inject
    public ExperimentMetadataCRUD(ExperimentDAO experimentDAO,
                                  ExperimentTrader experimentTrader,
                                  CondensedSdrfParser condensedSdrfParser,
                                  EFOLookupService efoParentsLookupService) {
        this.experimentDAO = experimentDAO;
        this.experimentTrader = experimentTrader;
        this.condensedSdrfParser = condensedSdrfParser;
        this.efoParentsLookupService = efoParentsLookupService;
    }

    // The purpose of having AnalyticsIndexerManager in a setter is to allow mocking. By having the mock do nothing,
    // tests that delete experiments or set them private have no effect on the Analytics index.
    @Inject
    public void setAnalyticsIndexerManager(AnalyticsIndexerManager analyticsIndexerManager) {
        this.analyticsIndexerManager = analyticsIndexerManager;
    }

    @Inject
    public void setConditionsIndexTrader(ConditionsIndexTrader conditionsIndexTrader) {
        this.conditionsIndexTrader = conditionsIndexTrader;
    }

    @Inject
    public void setExperimentDesignFileService(ExperimentDesignFileService experimentDesignFileService) {
        this.experimentDesignFileService = experimentDesignFileService;
    }

    public UUID importExperiment(String accession, ExperimentConfiguration experimentConfiguration, boolean isPrivate, Optional<String> accessKey) throws IOException {
        checkNotNull(accession);
        checkNotNull(experimentConfiguration);

        ExperimentType experimentType = experimentConfiguration.getExperimentType();
        CondensedSdrfParserOutput condensedSdrfParserOutput = condensedSdrfParser.parse(accession, experimentType);

        ExperimentDesign experimentDesign = condensedSdrfParserOutput.getExperimentDesign();
        writeExperimentDesignFile(accession, experimentType, experimentDesign);


        UUID uuid = experimentDAO.addExperiment
                (ExperimentDTO.createNew(
                        condensedSdrfParserOutput,
                        experimentDesign.getSpeciesForAssays(experimentConfiguration.getAssayAccessions()), isPrivate)
                        , accessKey);

        //experiment can be indexed only after it's been added to the DB, since fetching experiment
        //from cache gets this experiment from the DB first
        //TODO: change this so it uses experimentConfiguration, experimentDesign, and accession rather than experiment
        if (!isPrivate) {
            addPublicExperimentToConditionsIndex(accession, experimentDesign);
        }

        return uuid;
    }

    private void addPublicExperimentToConditionsIndex(String accession, ExperimentDesign experimentDesign) {
        Experiment experiment = experimentTrader.getPublicExperiment(accession);
        ImmutableSetMultimap<String, String> termIdsByAssayAccession = experimentDesign.getAllOntologyTermIdsByAssayAccession();
        conditionsIndexTrader.getIndex(experiment.getType()).addConditions(experiment, efoParentsLookupService.expandOntologyTerms(termIdsByAssayAccession));
    }

    private void updatePublicExperimentInConditionsIndex(String accession, ExperimentDesign experimentDesign) {
        Experiment experiment = experimentTrader.getPublicExperiment(accession);
        ImmutableSetMultimap<String, String> termIdsByAssayAccession = experimentDesign.getAllOntologyTermIdsByAssayAccession();
        conditionsIndexTrader.getIndex(experiment.getType()).updateConditions(experiment, efoParentsLookupService.expandOntologyTerms(termIdsByAssayAccession));
    }

    void writeExperimentDesignFile(String accession, ExperimentType experimentType, ExperimentDesign experimentDesign) throws IOException {
        experimentDesignFileService.write(accession,experimentType,experimentDesign);
    }

    public void deleteExperiment(ExperimentDTO experimentDTO) {
        checkNotNull(experimentDTO);

        String experimentAccession = experimentDTO.getExperimentAccession();

        if (!experimentDTO.isPrivate()) {
            conditionsIndexTrader.getIndex(experimentDTO.getExperimentType()).removeConditions(experimentAccession);
            analyticsIndexerManager.deleteFromAnalyticsIndex(experimentAccession);
        }

        experimentTrader.removeExperimentFromCache(experimentDTO.getExperimentAccession());

        experimentDAO.deleteExperiment(experimentAccession);

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
        String accession = experimentDTO.getExperimentAccession();
        ExperimentType type = experimentDTO.getExperimentType();

        try {
            experimentTrader.removeExperimentFromCache(accession);

            ExperimentDesign newDesign = condensedSdrfParser.parse(accession, type).getExperimentDesign();
            writeExperimentDesignFile(accession, type, newDesign);

            LOGGER.info("updated design for experiment {}", accession);

            if (!experimentDTO.isPrivate()) {
                updatePublicExperimentInConditionsIndex(accession,newDesign);
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}