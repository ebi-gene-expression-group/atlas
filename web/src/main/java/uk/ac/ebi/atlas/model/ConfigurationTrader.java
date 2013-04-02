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

package uk.ac.ebi.atlas.model;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.differential.DifferentialExperimentConfiguration;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;

import javax.inject.Named;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.MessageFormat;

@Named
public class ConfigurationTrader {

    private static final Logger LOGGER = Logger.getLogger(ConfigurationTrader.class);

    @Value("#{configuration['experiment.factors.path.template']}")
    private String baselineConfigurationPathTemplate;

    @Value("#{configuration['diff.experiment.configuration.path.template']}")
    private String differentialConfigurationPathTemplate;

    public BaselineExperimentConfiguration getFactorsConfiguration(String experimentAccession) {
        XMLConfiguration xmlConfiguration = getXmlConfiguration(baselineConfigurationPathTemplate, experimentAccession);
        return new BaselineExperimentConfiguration(xmlConfiguration);
    }

    public DifferentialExperimentConfiguration getDifferentialExperimentConfiguration(String experimentAccession) {
        return getDifferentialExperimentConfiguration(experimentAccession, false);
    }

    private DifferentialExperimentConfiguration getDifferentialExperimentConfiguration(String experimentAccession, boolean isMicroarray) {

        XMLConfiguration xmlConfiguration = getXmlConfiguration(differentialConfigurationPathTemplate, experimentAccession);
        xmlConfiguration.setExpressionEngine(new XPathExpressionEngine());
        if (isMicroarray){
            return new MicroarrayExperimentConfiguration(xmlConfiguration);
        }
        return new DifferentialExperimentConfiguration(xmlConfiguration);
    }

    public MicroarrayExperimentConfiguration getMicroarrayExperimentConfiguration(String experimentAccession) {
        return (MicroarrayExperimentConfiguration) getDifferentialExperimentConfiguration(experimentAccession, true);
    }

    private XMLConfiguration getXmlConfiguration(String pathTemplate, String experimentAccession) {
        String path = MessageFormat.format(pathTemplate, experimentAccession);
        Path fileSystemPath = FileSystems.getDefault().getPath(path);
        try {
            File configFile = fileSystemPath.toFile();
            return new XMLConfiguration(configFile);
        } catch (ConfigurationException cex) {
            LOGGER.error(cex.getMessage(), cex);
            throw new IllegalStateException("Cannot read configuration from path " + fileSystemPath.toString(), cex);
        }
    }

}