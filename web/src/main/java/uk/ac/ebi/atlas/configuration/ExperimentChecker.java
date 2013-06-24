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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.text.MessageFormat;
import java.util.Properties;

@Named
@Scope("prototype")
public class ExperimentChecker {

    private static final Logger LOGGER = Logger.getLogger(ExperimentChecker.class);

    private Properties configurationProperties;

    private ConfigurationDao configurationDao;

    @Inject
    public ExperimentChecker(@Named("configuration") Properties configurationProperties, ConfigurationDao configurationDao) {
        this.configurationProperties = configurationProperties;
        this.configurationDao = configurationDao;
    }

    public ExperimentType checkAccessionAndType(String accession, String type) {
        if (StringUtils.isEmpty(accession)) {
            LOGGER.error("<checkAccessionAndType> Experiment experimentAccession cannot be empty.");
            throw new IllegalStateException("Experiment experimentAccession cannot be empty.");
        }

        if (configurationDao.getExperimentConfiguration(accession) != null) {
            LOGGER.error("<checkAccessionAndType> Experiment with experimentAccession " + accession + " already exists.");
            throw new IllegalStateException("Experiment with experimentAccession " + accession + " already exists.");
        }

        ExperimentType experimentType;
        try {
            experimentType = ExperimentType.valueOf(type);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<checkAccessionAndType> Illegal ExperimentType specified: " + e.getMessage());
            throw new IllegalStateException("An unknown experiment type has been specified.");
        }

        return experimentType;
    }

    public void checkAllFilesPresent(String experimentAccession, ExperimentType experimentType) {

        // every experiment should have analysis methods file
        checkRequiredFileCanRead(experimentAccession, "experiment.analysis-method.path.template");

        switch (experimentType) {
            case BASELINE:
                checkBaseline(experimentAccession);
                break;
            case DIFFERENTIAL:
                checkDifferential(experimentAccession);
                break;
            case MICROARRAY:
                checkMicroarray(experimentAccession);
                break;
            case TWOCOLOUR:
                checkTwoColour(experimentAccession);
                break;
            default:
                LOGGER.error("<checkAllFilesPresent> The specified experiment type is not supported.");
                throw new IllegalStateException("The specified experiment type is not supported.");
        }
    }

    protected void checkBaseline(String experimentAccession) {
        checkRequiredFileCanRead(experimentAccession, "experiment.magetab.path.template");
        checkRequiredFileCanRead(experimentAccession, "experiment.transcripts.path.template");
        checkRequiredFileCanRead(experimentAccession, "experiment.factors.path.template");
    }

    protected void checkDifferential(String experimentAccession) {
        checkRequiredFileCanRead(experimentAccession, "diff.experiment.data.path.template");
        checkRequiredFileCanRead(experimentAccession, "diff.experiment.raw-counts.path.template");
        checkRequiredFileCanRead(experimentAccession, "diff.experiment.configuration.path.template");
    }

    protected void checkMicroarray(String experimentAccession) {
        checkRequiredFileCanRead(experimentAccession, "microarray.experiment.data.path.template");
        checkRequiredFileCanRead(experimentAccession, "microarray.normalized.data.path.template");
        checkRequiredFileCanRead(experimentAccession, "diff.experiment.configuration.path.template");
    }

    protected void checkTwoColour(String experimentAccession) {
        checkRequiredFileCanRead(experimentAccession, "microarray.experiment.data.path.template");
        checkRequiredFileCanRead(experimentAccession, "microarray.log-fold-changes.data.path.template");
        checkRequiredFileCanRead(experimentAccession, "diff.experiment.configuration.path.template");
    }

    protected void checkRequiredFileCanRead(String experimentAccession, String configurationPropertyKey) {
        String dataFileUrlTemplate = configurationProperties.getProperty(configurationPropertyKey);
        String dataFileURL = MessageFormat.format(dataFileUrlTemplate, experimentAccession);
        File dataFile = new File(dataFileURL);
        if (!dataFile.canRead()) {
            LOGGER.error("<checkRequiredFileCanRead> Required file can not be read: " + dataFile.getAbsolutePath());
            throw new IllegalStateException("Required file can not be read: " + dataFile.getAbsolutePath());
        }
    }

}