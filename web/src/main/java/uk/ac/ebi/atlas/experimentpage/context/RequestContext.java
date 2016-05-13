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

package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;
import uk.ac.ebi.atlas.web.GeneQuery;

import java.util.Collections;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


public abstract class RequestContext<T, K extends ExperimentPageRequestPreferences> {
    private K requestPreferences;
    private Set<T> selectedQueryFactors;
    private String filteredBySpecies;
    private Set<T> allQueryFactors;
    private String queryDescription;

    public String getQueryDescription(){
        return queryDescription;
    }

    public GeneQuery getGeneQuery() {
        return getRequestPreferences().getGeneQuery();
    }

    public Integer getHeatmapMatrixSize() {
        return getRequestPreferences().getHeatmapMatrixSize();
    }

    public Set<T> getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    // the species for the current slice. Note: this is the mapped Ensembl species, ie: not the SDRF sample organism,
    // but the mapped Ensembl species for the sample (usually this is the same however)
    public String getFilteredBySpecies() {
        return filteredBySpecies;
    }

    public double getCutoff() {
        return getRequestPreferences().getCutoff();
    }

    public boolean isSpecific() {
        return getRequestPreferences().isSpecific();
    }

    public boolean isExactMatch() {
        return getRequestPreferences().isExactMatch();
    }

    public Set<T> getAllQueryFactors() {
        return allQueryFactors;
    }

    void setAllQueryFactors(Set<T> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
    }

    void setSelectedQueryFactors(Set<T> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    void setFilteredBySpecies(String filteredBySpecies) {
        this.filteredBySpecies = filteredBySpecies;
    }

    void setQueryDescription(String queryDescription){
        this.queryDescription = queryDescription;
    }

    protected void setRequestPreferences(K requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    protected K getRequestPreferences() {
        return requestPreferences;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add("requestPreferences", getRequestPreferences())
                .add("filteredBySpecies", filteredBySpecies)
                .toString();
    }

}
