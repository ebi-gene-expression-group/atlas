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
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignWriter;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignWriterBuilder;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.transcript.TranscriptProfileDAO;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("prototype")
public class ExperimentCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUD.class);

    private TranscriptProfilesLoader transcriptProfileLoader;
    private ArrayDesignDao arrayDesignDao;
    private ConfigurationTrader configurationTrader;
    private DesignElementMappingLoader designElementLoader;
    private ExperimentDesignWriterBuilder experimentDesignWriterBuilder;
    private ExperimentDAO experimentDAO;
    private TranscriptProfileDAO transcriptProfileDAO;
    private ExperimentTrader experimentTrader;

    @Inject
    public ExperimentCRUD(TranscriptProfilesLoader transcriptProfileLoader,
                          ArrayDesignDao arrayDesignDao,
                          ConfigurationTrader configurationTrader,
                          DesignElementMappingLoader designElementLoader,
                          ExperimentDAO experimentDAO,
                          TranscriptProfileDAO transcriptProfileDAO,
                          ExperimentDesignWriterBuilder experimentDesignWriterBuilder, ExperimentTrader experimentTrader) {
        this.transcriptProfileLoader = transcriptProfileLoader;
        this.arrayDesignDao = arrayDesignDao;
        this.transcriptProfileDAO = transcriptProfileDAO;
        this.configurationTrader = configurationTrader;
        this.designElementLoader = designElementLoader;
        this.experimentDAO = experimentDAO;
        this.experimentDesignWriterBuilder = experimentDesignWriterBuilder;
        this.experimentTrader = experimentTrader;
    }

    public UUID importExperiment(String accession, ExperimentType experimentType, boolean isPrivate) throws IOException {
        checkNotNull(accession);
        checkNotNull(experimentType);

        if (experimentDAO.isImported(accession)) {
            throw new IllegalStateException("Experiment with experimentAccession " + accession + " has been already imported.");
        }

        generateExperimentDesignFile(accession, experimentType);

        switch (experimentType) {
            case BASELINE:
                loadTranscripts(accession);
                break;
            case MICROARRAY:
            case TWOCOLOUR:
                loadArrayDesign(accession, ArrayDesignType.MICRO_ARRAY);
                break;
            case MICRORNA:
                loadArrayDesign(accession, ArrayDesignType.MICRO_RNA);
                break;
        }

        return experimentDAO.addExperiment(accession, experimentType, isPrivate);

    }

    void generateExperimentDesignFile(String accession, ExperimentType experimentType) throws IOException {

        ExperimentDesignWriter experimentDesignWriter =
                experimentDesignWriterBuilder.forExperimentAccession(accession)
                        .withExperimentType(experimentType)
                        .build();

        experimentDesignWriter.write(accession);
    }

    void loadTranscripts(String accession) {
        try {
            transcriptProfileLoader.load(accession);
        } catch (IOException e) {
            LOGGER.error("<loadTranscripts> error reading from file: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());
        }
    }

    void loadArrayDesign(String accession, ArrayDesignType arrayDesignType) {

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(accession);

        for (String arrayDesign : microarrayExperimentConfiguration.getArrayDesignNames()) {
            if (!arrayDesignDao.isArrayDesignPresent(arrayDesign)) {
                designElementLoader.loadMappings(arrayDesign, arrayDesignType);
            }
        }

    }

    public void deleteExperiment(String experimentAccession) {
        checkNotNull(experimentAccession);

        ExperimentDTO experiment = experimentDAO.findExperiment(experimentAccession, true);
        experimentTrader.removeExperimentFromCache(experiment.getExperimentAccession(), experiment.getExperimentType());

        experimentDAO.deleteExperiment(experimentAccession);

        transcriptProfileDAO.deleteTranscriptProfilesForExperiment(experimentAccession);

    }


    public List<ExperimentDTO> findAllExperiments() {
        return experimentDAO.findAllExperiments();
    }

    public void updateExperiment(String experimentAccession, boolean isPrivate) {
        experimentDAO.updateExperiment(experimentAccession, isPrivate);
    }

    public int updateAllExperimentDesigns() {
        List<ExperimentDTO> experiments = experimentDAO.findAllExperiments();
        for (ExperimentDTO experiment : experiments) {
            updateExperimentDesign(experiment);
        }
        return experiments.size();
    }

    void updateExperimentDesign(ExperimentDTO experiment) {
        try {
            experimentTrader.removeExperimentFromCache(experiment.getExperimentAccession(), experiment.getExperimentType());
            generateExperimentDesignFile(experiment.getExperimentAccession(), experiment.getExperimentType());
            LOGGER.info("updated design for experiment " + experiment.getExperimentAccession());
        } catch (IOException e) {
            throw new IllegalStateException("<updateExperimentDesign> error generateExperimentDesignFile : ", e);
        }
    }

    public List<ExperimentDTO> findExperiments(Set<String> experimentAccessions) {
        return experimentDAO.findExperiments(experimentAccessions, true);
    }
}