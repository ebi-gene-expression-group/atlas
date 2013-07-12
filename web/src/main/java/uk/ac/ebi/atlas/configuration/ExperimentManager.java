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

package uk.ac.ebi.atlas.configuration;

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.expdesign.ExpDesignTsvWriter;
import uk.ac.ebi.atlas.expdesign.ExperimentDesignWriter;
import uk.ac.ebi.atlas.expdesign.ExperimentDesignWriterFactory;
import uk.ac.ebi.atlas.expdesign.ExperimentDesignWritingException;
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.transcript.TranscriptProfilesLoader;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;

@Named
@Scope("prototype")
public class ExperimentManager {

    private static final Logger LOGGER = Logger.getLogger(ExperimentManager.class);

    private ExpDesignTsvWriter expDesignTsvWriter;
    private TranscriptProfilesLoader transcriptProfileLoader;
    private ArrayDesignDao arrayDesignDao;
    private ConfigurationTrader configurationTrader;
    private DesignElementMappingLoader designElementLoader;
    private ExperimentDesignWriterFactory experimentDesignWriterFactory;

    @Inject
    public ExperimentManager(ExpDesignTsvWriter expDesignTsvWriter,
                             TranscriptProfilesLoader transcriptProfileLoader,
                             ArrayDesignDao arrayDesignDao,
                             ConfigurationTrader configurationTrader,
                             DesignElementMappingLoader designElementLoader, ExperimentDesignWriterFactory experimentDesignWriterFactory) {
        this.expDesignTsvWriter = expDesignTsvWriter;
        this.transcriptProfileLoader = transcriptProfileLoader;
        this.arrayDesignDao = arrayDesignDao;
        this.configurationTrader = configurationTrader;
        this.designElementLoader = designElementLoader;
        this.experimentDesignWriterFactory = experimentDesignWriterFactory;
    }

    public void generateExpDesign(String accession, ExperimentType experimentType) {

        ExperimentDesignWriter experimentDesignWriter = experimentType.createExperimentDesignWriter(experimentDesignWriterFactory);

        try (CSVWriter csvWriter = expDesignTsvWriter.forExperimentAccession(accession)) {
            experimentDesignWriter.write(accession, csvWriter);
            csvWriter.flush();
            LOGGER.info("<generateExpDesign> written ExpDesign file: " + expDesignTsvWriter.getFileAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("<generateExpDesign> error writing to file: " + e.getMessage());
            throw new IllegalStateException(e.getMessage());

        } catch (ExperimentDesignWritingException e) {
            LOGGER.error("<generateExpDesign> error parsing SDRF: " + e.getMessage());
            File expDesignTsv = new File(expDesignTsvWriter.getFileAbsolutePath());
            String outcomeDelete = expDesignTsv.delete() ? " success" : " fail";
            LOGGER.error("<generateExpDesign> ExpDesign cleanup " + expDesignTsvWriter.getFileAbsolutePath() + outcomeDelete);
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void loadTranscripts(String accession) {
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

    public void loadArrayDesign(String accession, ArrayDesignType arrayDesignType) {

        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(accession);

        for (String arrayDesign : microarrayExperimentConfiguration.getArrayDesignNames()) {
            if (!arrayDesignDao.isArrayDesignPresent(arrayDesign)) {
                designElementLoader.loadMappings(arrayDesign, arrayDesignType);
            }
        }

    }

}