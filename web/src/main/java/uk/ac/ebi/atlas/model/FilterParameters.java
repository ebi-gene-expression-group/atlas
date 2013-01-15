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

package uk.ac.ebi.atlas.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class FilterParameters {
    private Set<String> geneIDs;

    private Set<String> queryFactorValues;

    private Set<FactorValue> filterFactorValues;

    private Double cutoff;

    private String geneQuery;


    public FilterParameters(String geneQuery, Set<String> queryFactorValues, Set<String> filterFactorValues, Double cutoff) {

        this.geneQuery = geneQuery;
        this.queryFactorValues = queryFactorValues;
        this.cutoff = cutoff;
        this.filterFactorValues = new HashSet<>();

        //ToDo: verify that null check needed
        if (filterFactorValues != null) {
            for (String filter : filterFactorValues) {
                FactorValue factorValue = FactorValue.createFactorValue(filter);
                if (factorValue != null) {
                    this.filterFactorValues.add(factorValue);
                }
            }
        }
    }

    public boolean hasGenesForQuery() {
        return !StringUtils.isEmpty(geneQuery);
    }

    public Set<String> getQueryFactorValues() {
        return queryFactorValues;
    }

    public Set<FactorValue> getFilterFactorValues() {
        return filterFactorValues;
    }

    public Double getCutoff() {
        return cutoff;
    }

    public String getGeneQuery() {
        return geneQuery;
    }

    public void setGeneIDs(Set<String> geneIDs) {
        this.geneIDs = geneIDs;
    }

    public Set<String> getGeneIDs() {
        return geneIDs;
    }
}
