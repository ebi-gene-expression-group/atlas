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

package uk.ac.ebi.atlas.web.controllers.rest;

import org.apache.commons.lang.StringUtils;

public class ExperimentInfo {

    private String experimentType = StringUtils.EMPTY;

    private String experimentAccession = StringUtils.EMPTY;

    private String experimentDescription = StringUtils.EMPTY;

    private int numberOfAssays = 0;

    private int numberOfContrasts = 0;

    private String species = StringUtils.EMPTY;

    private String experimentalFactors = StringUtils.EMPTY;

    private String arrayDesigns = StringUtils.EMPTY;

    public String getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public void setExperimentAccession(String experimentAccession) {
        this.experimentAccession = experimentAccession;
    }

    public String getExperimentDescription() {
        return experimentDescription;
    }

    public void setExperimentDescription(String experimentDescription) {
        this.experimentDescription = experimentDescription;
    }

    public int getNumberOfAssays() {
        return numberOfAssays;
    }

    public void setNumberOfAssays(int numberOfAssays) {
        this.numberOfAssays = numberOfAssays;
    }

    public int getNumberOfContrasts() {
        return numberOfContrasts;
    }

    public void setNumberOfContrasts(int numberOfContrasts) {
        this.numberOfContrasts = numberOfContrasts;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getExperimentalFactors() {
        return experimentalFactors;
    }

    public void setExperimentalFactors(String experimentalFactors) {
        this.experimentalFactors = experimentalFactors;
    }

    public String getArrayDesigns() {
        return arrayDesigns;
    }

    public void setArrayDesigns(String arrayDesigns) {
        this.arrayDesigns = arrayDesigns;
    }

}