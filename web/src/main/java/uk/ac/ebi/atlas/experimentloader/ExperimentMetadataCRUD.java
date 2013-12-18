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

package uk.ac.ebi.atlas.experimentloader;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignFileWriter;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignFileWriterBuilder;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.impl.MageTabParser;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.impl.MageTabParserFactory;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.impl.MageTabParserOutput;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.solr.admin.index.conditions.ConditionsIndexTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("prototype")
public class ExperimentMetadataCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentMetadataCRUD.class);
    private final MageTabParserFactory mageTabParserFactory;
    private final ConditionsIndexTrader conditionsIndexTrader;
    private ConfigurationTrader configurationTrader;
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder;
    private ExperimentDAO experimentDAO;
    private ExperimentTrader experimentTrader;

    private ExperimentDTOBuilder experimentDTOBuilder;

    //TODO: refactor this class - it has too many collaborators
    @Inject
    public ExperimentMetadataCRUD(ConfigurationTrader configurationTrader,
                                  ExperimentDAO experimentDAO,
                                  ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder,
                                  ExperimentTrader experimentTrader,
                                  ExperimentDTOBuilder experimentDTOBuilder,
                                  MageTabParserFactory mageTabParserFactory,
                                  ConditionsIndexTrader conditionsIndexTrader) {

        this.configurationTrader = configurationTrader;
        this.experimentDAO = experimentDAO;
        this.experimentDesignFileWriterBuilder = experimentDesignFileWriterBuilder;
        this.experimentTrader = experimentTrader;
        this.experimentDTOBuilder = experimentDTOBuilder;
        this.mageTabParserFactory = mageTabParserFactory;
        this.conditionsIndexTrader = conditionsIndexTrader;
    }

    public ExperimentConfiguration loadExperimentConfiguration(String accession) {
        return configurationTrader.getExperimentConfiguration(accession);
    }

    public UUID loadExperiment(String accession, ExperimentConfiguration experimentConfiguration, boolean isPrivate) throws IOException {
        checkNotNull(accession);
        checkNotNull(experimentConfiguration);

        ExperimentType experimentType = experimentConfiguration.getExperimentType();

        MageTabParserOutput mageTabParserOutput = readMageTab(accession, experimentType);

        ExperimentDesign experimentDesign = mageTabParserOutput.getExperimentDesign();

        generateExperimentDesignFile(accession, experimentType, experimentDesign);

        Set<String> assayAccessions = configurationTrader.getExperimentConfiguration(accession).getAssayAccessions();

        Set<String> species = experimentDesign.getSpeciesForAssays(assayAccessions);

        //TODO: inefficient - this re-reads the magetab, but we've already done this above
        ExperimentDTO experimentDTO = experimentDTOBuilder.forExperimentAccession(accession)
                .withExperimentType(experimentType).withPrivate(isPrivate).withSpecies(species).build();

        UUID uuid = experimentDAO.addExperiment(experimentDTO);

        //experiment can be indexed only after it's been added to the DB, since fetching experiment
        //from cache gets this experiment from the DB first
        if (!isPrivate) {
            Experiment experiment = experimentTrader.getPublicExperiment(accession);
            conditionsIndexTrader.getIndex(experiment).addConditions(experiment, mageTabParserOutput.getOntologyTerms());
        }

        return uuid;

    }

    MageTabParserOutput readMageTab(String accession, ExperimentType experimentType) throws IOException {
        MageTabParser mageTabParser = mageTabParserFactory.create(experimentType);
        return mageTabParser.parse(accession);
    }

    void generateExperimentDesignFile(String accession, ExperimentType experimentType, ExperimentDesign experimentDesign) throws IOException {
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
            //TODO: shouldn't have to get the experiment here but conditionsIndexTrader needs Experiment obj
            Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
            conditionsIndexTrader.getIndex(experiment).removeConditions(experimentAccession);
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
        }
    }

    public int updateAllExperimentDesigns() {
        List<ExperimentDTO> experiments = experimentDAO.findAllExperiments();
        for (ExperimentDTO experiment : experiments) {
            updateExperimentDesign(experiment);
        }
        return experiments.size();
    }

    public int updateAllExperiments() throws IOException {
        List<ExperimentDTO> experiments = experimentDAO.findAllExperiments();
        for (ExperimentDTO experiment : experiments) {
            String accession = experiment.getExperimentAccession();
            deleteExperiment(experiment);
            loadExperiment(accession, loadExperimentConfiguration(accession), experiment.isPrivate());
        }
        return experiments.size();
    }


    void updateExperimentDesign(ExperimentDTO experimentDTO) {
        String accession = experimentDTO.getExperimentAccession();
        ExperimentType type = experimentDTO.getExperimentType();
        try {
            experimentTrader.removeExperimentFromCache(accession, type);

            MageTabParserOutput mageTabParserOutput = readMageTab(accession, type);
            ExperimentDesign experimentDesign = mageTabParserOutput.getExperimentDesign();

            generateExperimentDesignFile(accession, type, experimentDesign);

            LOGGER.info("updated design for experiment " + accession);

            if (!experimentDTO.isPrivate()) {
                Experiment experiment = experimentTrader.getPublicExperiment(accession);
                conditionsIndexTrader.getIndex(experiment).updateConditions(experiment, mageTabParserOutput.getOntologyTerms());
            }

        } catch (IOException e) {
            throw new IllegalStateException("<updateExperimentDesign> error generateExperimentDesignFile : ", e);
        }
    }

    public List<ExperimentDTO> findExperiments(Set<String> experimentAccessions) {
        return experimentDAO.findExperiments(experimentAccessions, true);
    }
}