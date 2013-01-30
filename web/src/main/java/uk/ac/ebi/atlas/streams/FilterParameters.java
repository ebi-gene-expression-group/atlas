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

package uk.ac.ebi.atlas.streams;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneindex.IndexClient;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.FactorValue;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

// should have been declared final, but cannot mock final classes
public class FilterParameters {

    private Set<String> geneIDs;

    private Set<FactorValue> filterFactorValues;

    private Double cutoff;

    private boolean hasGenesForQuery;

    private Set<FactorValue> queryFactorValues;

    private String queryFactorType;

    private FilterParameters(Builder builder) {
        geneIDs = builder.geneIDs;
        filterFactorValues = builder.filterFactorValues;
        cutoff = builder.cutoff;
        hasGenesForQuery = builder.hasGenesForQuery;
        queryFactorValues = builder.queryFactorValues;
        queryFactorType = builder.queryFactorType;

    }

    public boolean hasGenesForQuery() {
        return hasGenesForQuery;
    }

    public Set<String> getGeneIDs() {
        return geneIDs;
    }

    public Set<FactorValue> getFilterFactorValues() {
        return filterFactorValues;
    }

    public Double getCutoff() {
        return cutoff;
    }


    public Set<FactorValue> getQueryFactorValues() {
        return queryFactorValues;
    }

    public String getQueryFactorType() {
        return queryFactorType;
    }

    @Override
    public String toString() {
        return "FilterParameters{" +
                "geneIDs=" + geneIDs +
                ", filterFactorValues=" + filterFactorValues +
                ", cutoff=" + cutoff +
                ", hasGenesForQuery=" + hasGenesForQuery +
                ", queryFactorValues=" + queryFactorValues +
                ", queryFactorType='" + queryFactorType + '\'' +
                '}';
    }

    @Named("filterParametersBuilder")
    @Scope("request")
    public static class Builder {
        //required
        private String experimentAccession;
        private Double cutoff;

        //optional
        private String queryFactorType;

        private Set<String> geneIDs;
        private Set<FactorValue> filterFactorValues;
        private String geneQuery;
        private Set<FactorValue> queryFactorValues;
        private boolean hasGenesForQuery;


        private ExperimentsCache experimentsCache;
        private IndexClient indexClient;

        @Inject
        public Builder(ExperimentsCache experimentsCache, IndexClient indexClient) {
            this.experimentsCache = experimentsCache;
            this.indexClient = indexClient;
        }

        public Builder forExperimentAccession(String experimentAccession) {
            this.experimentAccession = experimentAccession;
            return this;
        }

        public Builder withCutoff(Double cutoff) {
            this.cutoff = cutoff;
            return this;
        }

        public Builder withQueryFactorType(String queryFactorType) {
            this.queryFactorType = queryFactorType;
            return this;
        }

        public Builder withFilterFactorValues(Set<String> filterFactorValues) {
            checkState(experimentAccession != null, "Please invoke forExperimentAccession before any other method.");
            Experiment experiment = experimentsCache.getExperiment(experimentAccession);

            this.filterFactorValues = new HashSet<>();

            for (String filterFactorValueString : filterFactorValues) {
                FactorValue factorValue = buildFactorValueFromFilterFactorValueString(experiment, filterFactorValueString);
                this.filterFactorValues.add(factorValue);
            }

            return this;
        }

        private FactorValue buildFactorValueFromFilterFactorValueString(Experiment experiment, String filterFactorValueString) {
            String[] split = filterFactorValueString.split(FactorValue.FACTOR_VALUE_SEPARATOR);
            if (split.length == 2) {
                String name = experiment.getFactorName(split[0], split[1]);
                return new FactorValue(split[0], name, split[1]);
            }
            throw new IllegalArgumentException("FactorValue string should be colon separated between type and value.");
        }

        public Builder withGeneQuery(String geneQuery) {
            this.hasGenesForQuery = !StringUtils.isEmpty(geneQuery);

            this.geneIDs = new HashSet<>();
            //init geneIds
            Experiment experiment = experimentsCache.getExperiment(experimentAccession);
            this.geneIDs.addAll(indexClient.findGeneIds(geneQuery, experiment.getSpecie()));
            return this;

        }

        public Builder withQueryFactorValues(Set<String> queryFactorValues) {
            //init queryFactorValues
            this.queryFactorValues = new HashSet<>();
            //ToDo: check if these assertions can be removed...
            if (queryFactorValues != null) {
                if (StringUtils.isEmpty(queryFactorType)) {
                    Experiment experiment = experimentsCache.getExperiment(experimentAccession);
                    queryFactorType = experiment.getDefaultQueryFactorType();
                }
                for (String queryFactorValue : queryFactorValues) {
                    this.queryFactorValues.add(new FactorValue(queryFactorType, queryFactorType, queryFactorValue));
                }
            }
            return this;
        }

        public FilterParameters build() {

            return new FilterParameters(this);
        }
    }
}
