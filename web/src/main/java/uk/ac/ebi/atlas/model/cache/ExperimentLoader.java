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
import uk.ac.ebi.atlas.expdesign.ExpDesignParser;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.utils.ArrayExpressClient;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

//import uk.ac.ebi.atlas.expdesign.ExpDesignParser;
//import uk.ac.ebi.atlas.expdesign.ExpDesignMageTabParser;

public abstract class ExperimentLoader<T extends Experiment> extends CacheLoader<String, T> {
    private static final Logger LOGGER = Logger.getLogger(ExperimentLoader.class);

    @Value("#{configuration['experiment.extra-info-image.path.template']}")
    private String extraInfoPathTemplate;

    private MageTabLimpopoUtils mageTabLimpopoUtils;

    private ArrayExpressClient arrayExpressClient;

    private ExpDesignParser expDesignParser;

    protected ExperimentLoader() {
    }

    @Inject
    public void setMageTabLimpopoUtils(MageTabLimpopoUtils mageTabLimpopoUtils) {
        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

    @Inject
    public void setArrayExpressClient(ArrayExpressClient arrayExpressClient) {
        this.arrayExpressClient = arrayExpressClient;
    }

    @Inject
    public void setExpDesignParser(ExpDesignParser expDesignParser) {
        this.expDesignParser = expDesignParser;
    }

    @Override
    public T load(String experimentAccession) throws ParseException, IOException {

        String extraInfoFileLocation = MessageFormat.format(extraInfoPathTemplate, experimentAccession);

        boolean hasExtraInfoFile = new File(extraInfoFileLocation).exists();

        ExperimentDesign experimentDesign = expDesignParser.parse(experimentAccession);

        return load(experimentAccession, fetchExperimentDescription(experimentAccession), hasExtraInfoFile, experimentDesign);

    }

    protected abstract T load(String experimentAccession, String experimentDescription, boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws ParseException, IOException;

    private String fetchExperimentDescription(String experimentAccession) {
        // TODO: move this information to database and only call once during experiment load
        try {
            return arrayExpressClient.fetchExperimentName(experimentAccession);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "Error connecting to ArrayExpress!";
        }
    }

    protected Set<String> extractSpecies(String experimentAccession) throws MalformedURLException, ParseException {
        // TODO: move this information to database and only call once during experiment load
        MAGETABInvestigation investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
        return mageTabLimpopoUtils.extractSpeciesFromSDRF(investigation);
    }

    protected List<String> extractPubMedIds(String experimentAccession) throws MalformedURLException, ParseException {
        // TODO: move this information to database and only call once during experiment load
        MAGETABInvestigation investigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
        return mageTabLimpopoUtils.extractPubMedIdsFromIDF(investigation);
    }

}
