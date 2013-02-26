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

package uk.ac.ebi.atlas.commons.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.MessageFormat;

@Named
public class ExperimentFactorsConfiguration {

    private static final Logger LOGGER = Logger.getLogger(ExperimentFactorsConfiguration.class);

    private String pathTemplate;

    @Inject
    public ExperimentFactorsConfiguration(
            @Value("#{configuration['experiment.experiment-factors.path.template']}")
            String pathTemplate) {
        this.pathTemplate = pathTemplate;
    }

    public XMLConfiguration forExperiment(String experimentAccession) {
        Path fileSystemPath = FileSystems.getDefault().getPath(getPathString(experimentAccession));

        try {
            File configFile = fileSystemPath.toFile();
            XMLConfiguration config = new XMLConfiguration(configFile);
            return config;
        } catch (ConfigurationException cex) {
            LOGGER.error(cex.getMessage(), cex);
            throw new IllegalStateException("Cannot read configuration from path " + fileSystemPath.toString(), cex);
        }
    }

    private String getPathString(String experimentAccession) {
        return MessageFormat.format(pathTemplate, experimentAccession);
    }

}