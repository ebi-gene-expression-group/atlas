/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSetMultimap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.AnalyticsIndexerManager;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.magetab.*;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

// Experiment metadata is sourced from SDRF/IDF files, and stored in experiment design files, the database, and the Solr conditions Index
@Named
@Scope("prototype")
public class ExperimentMetadataCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentMetadataCRUD.class);

    private ExperimentDAO experimentDAO;
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder;
    private ExperimentTrader experimentTrader;
    private ExperimentDTOBuilder experimentDTOBuilder;
    private final CondensedSdrfParser condensedSdrfParser;
    private final MageTabParserFactory mageTabParserFactory;
    private final ConditionsIndexTrader conditionsIndexTrader;
    private EFOParentsLookupService efoParentsLookupService;
    private final AnalyticsIndexerManager analyticsIndexerManager;

    //TODO: refactor this class - it has too many collaborators
    @Inject
    public ExperimentMetadataCRUD(ExperimentDAO experimentDAO,
                                  ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder,
                                  ExperimentTrader experimentTrader,
                                  ExperimentDTOBuilder experimentDTOBuilder,
                                  CondensedSdrfParser condensedSdrfParser,
                                  MageTabParserFactory mageTabParserFactory,
                                  ConditionsIndexTrader conditionsIndexTrader,
                                  EFOParentsLookupService efoParentsLookupService,
                                  AnalyticsIndexerManager analyticsIndexerManager) {
        this.experimentDAO = experimentDAO;
        this.experimentDesignFileWriterBuilder = experimentDesignFileWriterBuilder;
        this.experimentTrader = experimentTrader;
        this.experimentDTOBuilder = experimentDTOBuilder;
        this.condensedSdrfParser = condensedSdrfParser;
        this.mageTabParserFactory = mageTabParserFactory;
        this.conditionsIndexTrader = conditionsIndexTrader;
        this.efoParentsLookupService = efoParentsLookupService;
        this.analyticsIndexerManager = analyticsIndexerManager;
    }

    public UUID importExperiment(String accession, ExperimentConfiguration experimentConfiguration, boolean isPrivate, Optional<String> accessKey) throws IOException {
        checkNotNull(accession);
        checkNotNull(experimentConfiguration);

        //TODO push accession and experimentType into MageTabParserOutput
        ExperimentType experimentType = experimentConfiguration.getExperimentType();
        MageTabParserOutput mageTabParserOutput = readMageTab(accession, experimentType);

        try {
            CondensedSdrfParserOutput condensedSdrfParserOutput = condensedSdrfParser.parse(accession);
            ExperimentDesign tempExperimentDesign = condensedSdrfParserOutput.getExperimentDesign();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExperimentDesign experimentDesign = mageTabParserOutput.getExperimentDesign();
        writeExperimentDesignFile(accession, experimentType, experimentDesign);

        Set<String> assayAccessions = experimentConfiguration.getAssayAccessions();
        Set<String> species = experimentDesign.getSpeciesForAssays(assayAccessions);

        ExperimentDTO experimentDTO = buildExperimentDTO(accession, experimentType, mageTabParserOutput, species, isPrivate);
        UUID uuid = experimentDAO.addExperiment(experimentDTO, accessKey);

        //experiment can be indexed only after it's been added to the DB, since fetching experiment
        //from cache gets this experiment from the DB first
        //TODO: change this so it uses experimentconfiguration, experiment design, and accession rather than experiment
        if (!isPrivate) {
            Experiment experiment = experimentTrader.getPublicExperiment(accession);
            ImmutableSetMultimap<String, String> termIdsByAssayAccession = experimentDesign.getAllOntologyTermIdsByAssayAccession();
            conditionsIndexTrader.getIndex(experiment).addConditions(experiment, expandOntologyTerms(termIdsByAssayAccession));
        }

        return uuid;
    }

    private ImmutableSetMultimap<String, String> expandOntologyTerms(ImmutableSetMultimap<String, String> termIdsByAssayAccession) {

        ImmutableSetMultimap.Builder<String, String> builder = ImmutableSetMultimap.builder();
        for (String assayAccession : termIdsByAssayAccession.keys()) {
            Set<String> expandedOntologyTerms = new HashSet<>();

            expandedOntologyTerms.addAll(efoParentsLookupService.getAllParents(termIdsByAssayAccession.get(assayAccession)));
            expandedOntologyTerms.addAll(termIdsByAssayAccession.get(assayAccession));

            builder.putAll(assayAccession, expandedOntologyTerms);
        }

        return builder.build();
    }

    private ExperimentDTO buildExperimentDTO(String accession, ExperimentType experimentType, MageTabParserOutput mageTabParserOutput, Set<String> species, boolean isPrivate) {
        return experimentDTOBuilder.forExperimentAccession(accession)
                    .withExperimentType(experimentType).withPrivate(isPrivate).withSpecies(species)
                    .withTitle(mageTabParserOutput.getTitle())
                    .withPubMedIds(mageTabParserOutput.getPubmedIds())
                    .build();
    }

    MageTabParserOutput readMageTab(String accession, ExperimentType experimentType) throws IOException {
        MageTabParser mageTabParser = mageTabParserFactory.create(experimentType);
        return mageTabParser.parse(accession);
    }

    void writeExperimentDesignFile(String accession, ExperimentType experimentType, ExperimentDesign experimentDesign) throws IOException {
        ExperimentDesignFileWriter experimentDesignFileWriter =
                experimentDesignFileWriterBuilder.forExperimentAccession(accession)
                        .withExperimentType(experimentType)
                        .build();

        experimentDesignFileWriter.write(experimentDesign);
    }

    public void deleteExperiment(ExperimentDTO experimentDTO) {
        checkNotNull(experimentDTO);

        String experimentAccession = experimentDTO.getExperimentAccession();

        if (!experimentDTO.isPrivate()) {
            conditionsIndexTrader.getIndex(experimentDTO.getExperimentType()).removeConditions(experimentAccession);
            analyticsIndexerManager.deleteFromAnalyticsIndex(experimentAccession);
        }

        experimentTrader.removeExperimentFromCache(experimentDTO.getExperimentAccession(), experimentDTO.getExperimentType());

        experimentDAO.deleteExperiment(experimentAccession);

    }

    public ExperimentDTO findExperiment(String experimentAccession) {
        return experimentDAO.findExperiment(experimentAccession, true);
    }

    public List<ExperimentDTO> findAllExperiments() {
        return experimentDAO.findAllExperiments();
    }

    public void updateExperiment(String experimentAccession, boolean isPrivate) throws IOException {
        experimentDAO.updateExperiment(experimentAccession, isPrivate);

        if (!isPrivate) {
            ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);
            updateExperimentDesign(experimentDTO);
            experimentTrader.removeExperimentFromCache(experimentAccession, experimentDTO.getExperimentType());
        } else {
            analyticsIndexerManager.deleteFromAnalyticsIndex(experimentAccession);
        }
    }

    public int updateAllExperimentDesigns() {
        List<ExperimentDTO> experiments = experimentDAO.findAllExperiments();
        for (ExperimentDTO experiment : experiments) {
            updateExperimentDesign(experiment);
        }
        return experiments.size();
    }

    public void updateExperimentDesign(String experimentAccession) {
        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);
        updateExperimentDesign(experimentDTO);
    }


    void updateExperimentDesign(ExperimentDTO experimentDTO) {
        String accession = experimentDTO.getExperimentAccession();
        ExperimentType type = experimentDTO.getExperimentType();
        try {
            experimentTrader.removeExperimentFromCache(accession, type);

            try {
                CondensedSdrfParserOutput condensedSdrfParserOutput = condensedSdrfParser.parse(accession);
                ExperimentDesign tempExperimentDesign = condensedSdrfParserOutput.getExperimentDesign();
            } catch (IOException e) {
                e.printStackTrace();
            }

            MageTabParserOutput mageTabParserOutput = readMageTab(accession, type);
            ExperimentDesign experimentDesign = mageTabParserOutput.getExperimentDesign();

            writeExperimentDesignFile(accession, type, experimentDesign);

            LOGGER.info("updated design for experiment " + accession);

            if (!experimentDTO.isPrivate()) {
                Experiment experiment = experimentTrader.getPublicExperiment(accession);
                ImmutableSetMultimap<String, String> termIdsByAssayAccession = experimentDesign.getAllOntologyTermIdsByAssayAccession();
                conditionsIndexTrader.getIndex(experiment).updateConditions(experiment, expandOntologyTerms(termIdsByAssayAccession));
            }

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<ExperimentDTO> findExperiments(Set<String> experimentAccessions) {
        return experimentDAO.findExperiments(experimentAccessions, true);
    }

}