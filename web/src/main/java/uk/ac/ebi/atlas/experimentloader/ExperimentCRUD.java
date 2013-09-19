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
import uk.ac.ebi.atlas.geneannotation.ArrayDesignDao;
import uk.ac.ebi.atlas.geneannotation.arraydesign.ArrayDesignType;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingLoader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.solr.admin.index.conditions.IndexCommandTrader;
import uk.ac.ebi.atlas.solr.admin.index.conditions.IndexOperation;

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

    private ArrayDesignDao arrayDesignDao;
    private ConfigurationTrader configurationTrader;
    private DesignElementMappingLoader designElementLoader;
    private ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder;
    private ExperimentDAO experimentDAO;
    private ExperimentTrader experimentTrader;

    private IndexCommandTrader indexCommandTrader;

    @Inject
    public ExperimentCRUD(ArrayDesignDao arrayDesignDao,
                          ConfigurationTrader configurationTrader,
                          DesignElementMappingLoader designElementLoader,
                          ExperimentDAO experimentDAO,
                          ExperimentDesignFileWriterBuilder experimentDesignFileWriterBuilder,
                          ExperimentTrader experimentTrader,
                          IndexCommandTrader indexCommandTrader) {

        this.arrayDesignDao = arrayDesignDao;
        this.configurationTrader = configurationTrader;
        this.designElementLoader = designElementLoader;
        this.experimentDAO = experimentDAO;
        this.experimentDesignFileWriterBuilder = experimentDesignFileWriterBuilder;
        this.experimentTrader = experimentTrader;
        this.indexCommandTrader = indexCommandTrader;
    }

    public UUID importExperiment(String accession, ExperimentType experimentType, boolean isPrivate) throws IOException {
        checkNotNull(accession);
        checkNotNull(experimentType);

        if (experimentDAO.isImported(accession)) {
            throw new IllegalStateException("Experiment with experimentAccession " + accession + " has been already imported.");
        }

        generateExperimentDesignFile(accession, experimentType);

        switch (experimentType) {
            case MICROARRAY:
            case TWOCOLOUR:
                loadArrayDesign(accession, ArrayDesignType.MICRO_ARRAY);
                break;
            case MICRORNA:
                loadArrayDesign(accession, ArrayDesignType.MICRO_RNA);
                break;
        }

        UUID uuid = experimentDAO.addExperiment(accession, experimentType, isPrivate);

        //experiment can be indexed only after it's been added to the DB, since fetching experiment
        //from cache gets this experiment from the DB first
        if (!isPrivate) {
            indexCommandTrader.getIndexCommand(accession, IndexOperation.ADD).execute();
        }

        return uuid;

    }

    void generateExperimentDesignFile(String accession, ExperimentType experimentType) throws IOException {

        ExperimentDesignFileWriter experimentDesignFileWriter =
                experimentDesignFileWriterBuilder.forExperimentAccession(accession)
                        .withExperimentType(experimentType)
                        .build();

        experimentDesignFileWriter.write(accession);
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

        if (!experiment.isPrivate()) {
            indexCommandTrader.getIndexCommand(experimentAccession, IndexOperation.REMOVE).execute();
        }

        experimentTrader.removeExperimentFromCache(experiment.getExperimentAccession(), experiment.getExperimentType());

        experimentDAO.deleteExperiment(experimentAccession);

    }

    public List<ExperimentDTO> findAllExperiments() {
        return experimentDAO.findAllExperiments();
    }

    public void updateExperiment(String experimentAccession, boolean isPrivate) {
        experimentDAO.updateExperiment(experimentAccession, isPrivate);

        if (!isPrivate) {
            indexCommandTrader.getIndexCommand(experimentAccession, IndexOperation.UPDATE).execute();

        }
    }

    public int updateAllExperimentDesigns() {
        List<ExperimentDTO> experiments = experimentDAO.findAllExperiments();
        for (ExperimentDTO experiment : experiments) {
            updateExperimentDesign(experiment);
        }
        return experiments.size();
    }

    void updateExperimentDesign(ExperimentDTO experiment) {
        String accession = experiment.getExperimentAccession();
        ExperimentType type = experiment.getExperimentType();
        try {
            if (!experiment.isPrivate()) {
                indexCommandTrader.getIndexCommand(accession, IndexOperation.UPDATE).execute();
            }
            experimentTrader.removeExperimentFromCache(accession, type);
            generateExperimentDesignFile(accession, type);
            LOGGER.info("updated design for experiment " + accession);

        } catch (IOException e) {
            throw new IllegalStateException("<updateExperimentDesign> error generateExperimentDesignFile : ", e);
        }
    }

    public List<ExperimentDTO> findExperiments(Set<String> experimentAccessions) {
        return experimentDAO.findExperiments(experimentAccessions, true);
    }
}