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
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("request")
public class FilterParameters implements RequestContext {

    private String geneQuery;

    private Set<Factor> selectedFilterFactors;

    private Set<Factor> selectedQueryFactors;

    private String queryFactorType;

    private String filteredBySpecie;

    private double cutoff;

    private boolean specific;

    private Set<Factor> allQueryFactors;

    public FilterParameters() {
    }

    @Override
    public String getGeneQuery() {
        return geneQuery;
    }

    @Override
    public Set<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }

    @Override
    public Set<Factor> getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    @Override
    public String getQueryFactorType() {
        return queryFactorType;
    }

    @Override
    public String getFilteredBySpecies() {
        return filteredBySpecie;
    }

    @Override
    public double getCutoff() {
        return cutoff;
    }

    @Override
    public boolean isSpecific() {
        return specific;
    }

    @Override
    public Set<Factor> getAllQueryFactors() {
        return allQueryFactors;
    }

    public void setAllQueryFactors(Set<Factor> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
    }

    public void setGeneQuery(String geneQuery) {
        this.geneQuery = geneQuery;
    }

    public void setSelectedFilterFactors(Set<Factor> selectedFilterFactors) {
        this.selectedFilterFactors = selectedFilterFactors;
    }

    public void setSelectedQueryFactors(Set<Factor> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    public void setQueryFactorType(String queryFactorType) {
        this.queryFactorType = queryFactorType;
    }

    public void setFilteredBySpecie(String filteredBySpecie) {
        this.filteredBySpecie = filteredBySpecie;
    }

    public void setCutoff(double cutoff) {
        this.cutoff = cutoff;
    }

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .add("selectedFilterFactors", selectedFilterFactors)
                .add("queryFactorType", queryFactorType)
                .toString();
    }
}