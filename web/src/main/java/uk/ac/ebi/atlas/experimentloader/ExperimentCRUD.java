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
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignWriter;
import uk.ac.ebi.atlas.experimentloader.experimentdesign.ExperimentDesignWriterFactory;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.transcript.GeneProfileDao;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("prototype")
public class ExperimentCRUD {

    private static final Logger LOGGER = Logger.getLogger(ExperimentCRUD.class);

    private TranscriptProfilesLoader transcriptProfileLoader;
    private ArrayDesignDao arrayDesignDao;
    private ConfigurationTrader configurationTrader;
    private DesignElementMappingLoader designElementLoader;
    private ExperimentDesignWriterFactory experimentDesignWriterFactory;
    private ConfigurationDao configurationDao;
    private GeneProfileDao geneProfileDao;

    @Inject
    public ExperimentCRUD(TranscriptProfilesLoader transcriptProfileLoader,
                          ArrayDesignDao arrayDesignDao,
                          ConfigurationTrader configurationTrader,
                          DesignElementMappingLoader designElementLoader,
                          ConfigurationDao configurationDao,
                          GeneProfileDao geneProfileDao,
                          ExperimentDesignWriterFactory experimentDesignWriterFactory) {
        this.transcriptProfileLoader = transcriptProfileLoader;
        this.arrayDesignDao = arrayDesignDao;
        this.geneProfileDao = geneProfileDao;
        this.configurationTrader = configurationTrader;
        this.designElementLoader = designElementLoader;
        this.configurationDao = configurationDao;
        this.experimentDesignWriterFactory = experimentDesignWriterFactory;
    }

    public void importExperiment(String accession, ExperimentType experimentType) throws IOException{
        checkNotNull(accession);
        checkNotNull(experimentType);

        if (configurationDao.getExperimentConfiguration(accession) != null) {
            throw new IllegalStateException("Experiment with experimentAccession " + accession + " already exists.");
        }

        generateDesignFile(accession, experimentType);

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

        if (! configurationDao.addExperimentConfiguration(accession, experimentType)) {
            throw new IllegalStateException("Failure storing configuration for experiment " + accession);
        }

    }

    void generateDesignFile(String accession, ExperimentType experimentType) throws IOException{

        ExperimentDesignWriter experimentDesignWriter = experimentDesignWriterFactory.create(experimentType, accession);

        experimentDesignWriter.write(accession);
    }

    void loadTranscripts(String accession) {
        try {
            StopWatch stopWatch = new StopWatch(getClass().getSimpleName());

            stopWatch.start();
            int count = transcriptProfileLoader.load(accession);
            stopWatch.stop();

            LOGGER.info("<loadTranscripts> Inserted " + count + " transcript profiles and took " + stopWatch.getLastTaskInfo().getTimeSeconds() + "secs");
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

    public void deleteExperiment(String accession){
        checkNotNull(accession);

        boolean experimentFound = configurationDao.deleteExperimentConfiguration(accession);
        if (!experimentFound) {
            throw new IllegalArgumentException("Experiment " + accession + " was not found in the set of the imported experiments.");
        }
        int deletedTranscriptsCount = geneProfileDao.deleteTranscriptProfilesForExperiment(accession);
        LOGGER.info("<deleteExperiment> deleted transcripts count for experiment with accession " + accession + ": " + deletedTranscriptsCount);
    }


    public List<ExperimentConfiguration> findAllExperiments() {
        return configurationDao.findAllExperimentConfigurations();
    }
}