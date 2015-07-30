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

package uk.ac.ebi.atlas.trader.loader;

import com.google.common.cache.CacheLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

public abstract class ExperimentsCacheLoader<T extends Experiment> extends CacheLoader<String, T> {
    private static final Logger LOGGER = Logger.getLogger(ExperimentsCacheLoader.class);

    private String extraInfoPathTemplate;

    private ArrayExpressClient arrayExpressClient;

    private ExperimentDesignParser experimentDesignParser;

    private ExperimentDAO experimentDAO;

    protected ExperimentsCacheLoader() {
    }

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    public void setExtraInfoPathTemplate(String extraInfoPathTemplate) {
        this.extraInfoPathTemplate = extraInfoPathTemplate;
    }

    @Inject
    public void setExperimentDAO(ExperimentDAO experimentDAO) {
        this.experimentDAO = experimentDAO;
    }

    @Inject
    public void setArrayExpressClient(ArrayExpressClient arrayExpressClient) {
        this.arrayExpressClient = arrayExpressClient;
    }

    @Inject
    public void setExperimentDesignParser(ExperimentDesignParser experimentDesignParser) {
        this.experimentDesignParser = experimentDesignParser;
    }

    @Override
    public T load(String experimentAccession) throws IOException {

        LOGGER.info("loading experiment with accession: " + experimentAccession);

        boolean hasExtraInfoFile = extraInfoFileExists(experimentAccession);

        ExperimentDesign experimentDesign = experimentDesignParser.parse(experimentAccession);

        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);

        String experimentDescription = fetchExperimentNameFromArrayExpress(experimentAccession, experimentDTO);

        return load(experimentDTO, experimentDescription, hasExtraInfoFile, experimentDesign);

    }

    private boolean extraInfoFileExists(String experimentAccession) {
        String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);
        return Files.exists(Paths.get(extraInfoFileLocation));
    }

    protected abstract T load(ExperimentDTO experimentDTO, String experimentDescription,
                              boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException;

    final String fetchExperimentNameFromArrayExpress(String experimentAccession, ExperimentDTO experimentDTO) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return experimentDTO.getTitle();
        }
    }


}
