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
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

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

    private ConfigurationTrader configurationTrader;

    @Inject
    public ExperimentChecker(@Named("configuration") Properties configurationProperties,
                             ConfigurationDao configurationDao,
                             ConfigurationTrader configurationTrader) {
        this.configurationProperties = configurationProperties;
        this.configurationDao = configurationDao;
        this.configurationTrader = configurationTrader;
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
        checkRequiredFileCanRead("experiment.analysis-method.path.template", experimentAccession);

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
            case MICRORNA:
                checkMicroarray(experimentAccession);
                break;
            default:
                LOGGER.error("<checkAllFilesPresent> The specified experiment type is not supported.");
                throw new IllegalStateException("The specified experiment type is not supported.");
        }
    }

    protected void checkBaseline(String experimentAccession) {
        checkRequiredFileCanRead("experiment.magetab.path.template", experimentAccession);
        checkRequiredFileCanRead("experiment.transcripts.path.template", experimentAccession);
        checkRequiredFileCanRead("experiment.factors.path.template", experimentAccession);
    }

    protected void checkDifferential(String experimentAccession) {
        checkRequiredFileCanRead("diff.experiment.configuration.path.template", experimentAccession);
        checkRequiredFileCanRead("diff.experiment.data.path.template", experimentAccession);
        checkRequiredFileCanRead("diff.experiment.raw-counts.path.template", experimentAccession);
    }

    protected void checkMicroarray(String experimentAccession) {
        checkRequiredFileCanRead("diff.experiment.configuration.path.template", experimentAccession);
        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        for (String arrayDesign : microarrayExperimentConfiguration.getArrayDesignNames()) {
            checkRequiredFileCanRead("microarray.experiment.data.path.template", experimentAccession, arrayDesign);
            checkRequiredFileCanRead("microarray.normalized.data.path.template", experimentAccession, arrayDesign);
        }
    }

    protected void checkTwoColour(String experimentAccession) {
        checkRequiredFileCanRead("diff.experiment.configuration.path.template", experimentAccession);
        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        for (String arrayDesign : microarrayExperimentConfiguration.getArrayDesignNames()) {
            checkRequiredFileCanRead("microarray.experiment.data.path.template", experimentAccession, arrayDesign);
            checkRequiredFileCanRead("microarray.log-fold-changes.data.path.template", experimentAccession, arrayDesign);
        }
    }

    protected void checkRequiredFileCanRead(String configurationPropertyKey, String... templateContents) {
        String dataFileUrlTemplate = configurationProperties.getProperty(configurationPropertyKey);
        String dataFileURL = MessageFormat.format(dataFileUrlTemplate, templateContents);
        File dataFile = new File(dataFileURL);
        if (!dataFile.canRead()) {
            LOGGER.error("<checkRequiredFileCanRead> Required file can not be read: " + dataFile.getAbsolutePath());
            throw new IllegalStateException("Required file can not be read: " + dataFile.getAbsolutePath());
        }
    }

}