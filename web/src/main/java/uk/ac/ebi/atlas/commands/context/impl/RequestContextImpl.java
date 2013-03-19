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

package uk.ac.ebi.atlas.commands.context.impl;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.commands.context.RequestContext;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.web.RequestPreferences;

import java.util.Set;


public class RequestContextImpl implements RequestContext {
    protected RequestPreferences requestPreferences;
    private ExperimentType experimentType;
    private Set selectedQueryFactors;
    private String filteredBySpecies;

    public String getGeneQuery() {
        return requestPreferences.getGeneQuery();
    }

    public Integer getHeatmapMatrixSize() {
        return requestPreferences.getHeatmapMatrixSize();
    }

    //ToDo: interface uses generics!!!
    public Set getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    public String getFilteredBySpecies() {
        return filteredBySpecies;
    }

    public double getCutoff() {
        return requestPreferences.getCutoff();
    }

    public boolean isSpecific() {
        return requestPreferences.isSpecific();
    }

    public void setSelectedQueryFactors(Set selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    public void setFilteredBySpecies(String filteredBySpecies) {
        this.filteredBySpecies = filteredBySpecies;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(ExperimentType experimentType) {
        this.experimentType = experimentType;
    }

    public void setRequestPreferences(RequestPreferences requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("requestPreferences", requestPreferences)
                .add("filteredBySpecies", filteredBySpecies)
                .toString();
    }


}
