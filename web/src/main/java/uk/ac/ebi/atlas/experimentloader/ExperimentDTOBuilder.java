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
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.arrayexpress2.magetab.datamodel.MAGETABInvestigation;
import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.commons.magetab.MageTabLimpopoUtils;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.MalformedURLException;
import java.util.Set;

@Named
@Scope("prototype")
public class ExperimentDTOBuilder {
    private static final Logger LOGGER = Logger.getLogger(ExperimentDTOBuilder.class);

    private MageTabLimpopoUtils mageTabLimpopoUtils;
    private String experimentAccession;
    private ExperimentType experimentType;
    private boolean isPrivate;

    private Set<String> species;

    @Inject
    public ExperimentDTOBuilder(MageTabLimpopoUtils mageTabLimpopoUtils){

        this.mageTabLimpopoUtils = mageTabLimpopoUtils;
    }

    public ExperimentDTOBuilder forExperimentAccession(String experimentAccession){
        this.experimentAccession = experimentAccession;
        return this;
    }

    public ExperimentDTOBuilder withExperimentType(ExperimentType experimentType){
        this.experimentType = experimentType;
        return this;
    }

    public ExperimentDTOBuilder withPrivate(boolean isPrivate){
        this.isPrivate = isPrivate;
        return this;
    }

    public ExperimentDTOBuilder withSpecies(Set<String> species) {
        this.species = species;
        return this;
    }

    public ExperimentDTO build(){

        try {

            LOGGER.debug(String.format("parsing %s: begin",experimentAccession));

            MAGETABInvestigation magetabInvestigation = mageTabLimpopoUtils.parseInvestigation(experimentAccession);
            String title = mageTabLimpopoUtils.extractInvestigationTitle(magetabInvestigation);
            Set<String> pubmedIds = Sets.newHashSet(mageTabLimpopoUtils.extractPubMedIdsFromIDF(magetabInvestigation));

            LOGGER.debug(String.format("parsing %s: done",experimentAccession));

            return new ExperimentDTO(experimentAccession, experimentType, species, pubmedIds, title, isPrivate);

        } catch (ParseException | MalformedURLException  e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e.getMessage());
        }

    }
}
