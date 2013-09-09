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

package uk.ac.ebi.atlas.model.cache;

import com.google.common.cache.CacheLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.experimentloader.ExperimentDAO;
import uk.ac.ebi.atlas.experimentloader.ExperimentDTO;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

public abstract class ExperimentsCacheLoader<T extends Experiment> extends CacheLoader<String, T> {
    private static final Logger LOGGER = Logger.getLogger(ExperimentsCacheLoader.class);

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private ArrayExpressClient arrayExpressClient;

    private ExperimentDesignParser experimentDesignParser;

    private ExperimentDAO experimentDAO;

    protected ExperimentsCacheLoader() {
    }

    @Inject
    public void setMageTabLimpopoUtils(MageTabLimpopoUtils mageTabLimpopoUtils) {
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

    @Inject
    public void setMageTabLimpopoUtils(ExperimentDAO experimentDAO) {
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
    public T load(String experimentAccession) throws ParseException, IOException {

        LOGGER.info("loading experiment with accession: " + experimentAccession);

        String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);

        boolean hasExtraInfoFile = Files.exists(Paths.get(extraInfoFileLocation));

        ExperimentDesign experimentDesign = experimentDesignParser.parse(experimentAccession);

        ExperimentDTO experimentDTO = experimentDAO.findExperiment(experimentAccession, true);

        MAGETABInvestigation investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);

        String experimentDescription = fetchExperimentDescription(experimentAccession, investigation);

        Set<String> species = extractSpecies(investigation);

        List<String> pubMedIds = extractPubMedIds(investigation);

        return load(experimentDTO, experimentDescription, species, pubMedIds, hasExtraInfoFile, experimentDesign);

    }

    protected abstract T load(ExperimentDTO experimentDTO, String experimentDescription, Set<String> species,
                              List<String> pubMedIds, boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws ParseException, IOException;

    final String fetchExperimentDescription(String experimentAccession, MAGETABInvestigation investigation) {
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return extractInvestigationTitle(investigation);
        }
    }

    final Set<String> extractSpecies(MAGETABInvestigation investigation) {
        return mageTabLimpopoUtils.extractSpeciesFromSDRF(investigation);
    }

    final List<String> extractPubMedIds(MAGETABInvestigation investigation) {
        return mageTabLimpopoUtils.extractPubMedIdsFromIDF(investigation);
    }

    final String extractInvestigationTitle(MAGETABInvestigation investigation) {
        return mageTabLimpopoUtils.extractInvestigationTitle(investigation);
    }


}
