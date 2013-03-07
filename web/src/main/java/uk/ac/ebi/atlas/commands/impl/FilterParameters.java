/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.commands.impl;

import com.google.common.base.Objects;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.RequestContext;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Named;
import java.util.Set;
import java.util.SortedSet;

@Named
@Scope("request")
public class FilterParameters implements RequestContext {

    private Set<Factor> selectedFilterFactors;

    private Set<Factor> selectedQueryFactors;

    private String filteredBySpecies;

    private SortedSet<Factor> allQueryFactors;

    private RequestPreferences requestPreferences;

    public FilterParameters() {
    }

    @Override
    public String getGeneQuery() {
        return requestPreferences.getGeneQuery();
    }

    @Override
    public Set<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }

    @Override
    public Integer getHeatmapMatrixSize() {
        return requestPreferences.getHeatmapMatrixSize();
    }

    @Override
    public Set<Factor> getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    @Override
    public String getQueryFactorType() {
        return requestPreferences.getQueryFactorType();
    }

    @Override
    public String getFilteredBySpecies() {
        return filteredBySpecies;
    }

    @Override
    public double getCutoff() {
        return requestPreferences.getCutoff();
    }

    @Override
    public boolean isSpecific() {
        return requestPreferences.isSpecific();
    }

    @Override
    public SortedSet<Factor> getAllQueryFactors() {
        return allQueryFactors;
    }

    public void setRequestPreferences(RequestPreferences requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    public void setAllQueryFactors(SortedSet<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
    }

    public void setSelectedFilterFactors(Set<Factor> selectedFilterFactors) {
        this.selectedFilterFactors = selectedFilterFactors;
    }

    public void setSelectedQueryFactors(Set<Factor> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    public void setFilteredBySpecies(String filteredBySpecies) {
        this.filteredBySpecies = filteredBySpecies;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("requestPreferences", requestPreferences)
                .add("allQueryFactors", allQueryFactors)
                .add("selectedFilterFactors", selectedFilterFactors)
                .add("filteredBySpecies", filteredBySpecies)
                .toString();
    }

}