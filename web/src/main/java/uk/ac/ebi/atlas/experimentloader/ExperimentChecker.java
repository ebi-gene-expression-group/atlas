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

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentChecker {

    private Properties configurationProperties;

    private ConfigurationTrader configurationTrader;

    @Inject
    public ExperimentChecker(@Named("configuration") Properties configurationProperties,
                             ConfigurationTrader configurationTrader) {
        this.configurationProperties = configurationProperties;
        this.configurationTrader = configurationTrader;
    }

    public void checkAllFiles(String experimentAccession, ExperimentType experimentType) {

        // every experiment should have analysis methods file
        checkFilePermission("experiment.analysis-method.path.template", experimentAccession);

        switch (experimentType) {
            case BASELINE:
                checkBaselineFiles(experimentAccession);
                break;
            case DIFFERENTIAL:
                checkDifferentialFiles(experimentAccession);
                break;
            case MICROARRAY:
            case MICRORNA:
                checkMicroarrayFiles(experimentAccession);
                break;
            case TWOCOLOUR:
                checkTwoColourFiles(experimentAccession);
                break;
            default:
                throw new IllegalStateException("The specified experiment type is not supported.");
        }
    }


    void checkBaselineFiles(String experimentAccession) {
        Set<String> baselineExperimentPathTemplates =
                Sets.newHashSet("experiment.magetab.path.template", "experiment.transcripts.path.template", "experiment.factors.path.template");

        checkFilesPermissions(baselineExperimentPathTemplates, experimentAccession);
    }

    void checkDifferentialFiles(String experimentAccession) {
        Set<String> differentialExperimentPathTemplates =
                Sets.newHashSet("diff.experiment.configuration.path.template", "diff.experiment.data.path.template", "diff.experiment.raw-counts.path.template");

        checkFilesPermissions(differentialExperimentPathTemplates, experimentAccession);
    }

    void checkMicroarrayFiles(String experimentAccession) {
        checkFilePermission("diff.experiment.configuration.path.template", experimentAccession);
        MicroarrayExperimentConfiguration microarrayConfiguration =
                        configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        for (String arrayDesign : microarrayConfiguration.getArrayDesignNames()) {
            Set<String> arrayDesignDependentPathTemplates = Sets.newHashSet("microarray.experiment.data.path.template", "microarray.normalized.data.path.template");
            checkFilesPermissions(arrayDesignDependentPathTemplates, experimentAccession, arrayDesign);
        }
    }

    void checkTwoColourFiles(String experimentAccession) {
        checkFilePermission("diff.experiment.configuration.path.template", experimentAccession);
        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);

        Set<String> arrayDesignDependentPathTemplates = Sets.newHashSet("microarray.experiment.data.path.template", "microarray.log-fold-changes.data.path.template");
        for (String arrayDesign : microarrayExperimentConfiguration.getArrayDesignNames()) {
            checkFilesPermissions(arrayDesignDependentPathTemplates, experimentAccession, arrayDesign);
        }
    }

    void checkFilesPermissions(Set<String> pathTemplatePropertyKeys, String... pathArguments) {
        for (String pathTemplatePropertyKey : pathTemplatePropertyKeys){
            checkFilePermission(pathTemplatePropertyKey, pathArguments);
        }
    }

    void checkFilePermission(String pathTemplatePropertyKey, String... pathArguments) {
        String pathTemplate = configurationProperties.getProperty(pathTemplatePropertyKey);
        Path path = Paths.get(MessageFormat.format(pathTemplate, pathArguments));
        checkState(Files.isReadable(path), "Required file can not be read: " + path.toAbsolutePath().toString());
    }

}