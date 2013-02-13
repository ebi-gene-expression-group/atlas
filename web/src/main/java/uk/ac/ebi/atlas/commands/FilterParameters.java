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

package uk.ac.ebi.atlas.commands;

import com.google.common.base.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Named;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

//ToDo: this class now smells, GeneProfileInputStreamFilter now only requires selectedQueryFactors from this class and that is available also from other beans.
//ToDo: (NK)  getGeneQuery is used as well
public class FilterParameters {

    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private String geneQuery;

    private Set<Factor> selectedFilterFactors;

    private Set<Factor> selectedQueryFactors;

    private String queryFactorType;

    private FilterParameters(Builder builder) {
        geneQuery = builder.geneQuery;
        selectedFilterFactors = builder.selectedFilterFactors;
        selectedQueryFactors = builder.queryFactors;
        queryFactorType = builder.queryFactorType;

    }

    public String getGeneQuery() {
        return geneQuery;
    }

    public Set<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }

    public Set<Factor> getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    public String getQueryFactorType() {
        return queryFactorType;
    }

    public String formattedQueryFactorType() {
        return formatForDisplay(queryFactorType);
    }

    protected String formatForDisplay(String queryFactorType) {
        return StringUtils.capitalize(queryFactorType.replaceAll("_", " ").toLowerCase());
    }

    public String getFilteredBySpecies() {
        for (Factor selectedFilterFactor : selectedFilterFactors) {
            if (selectedFilterFactor.getType().equalsIgnoreCase("organism")) {
                return selectedFilterFactor.getValue();
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("geneQuery", geneQuery)
                .add("selectedFilterFactors", selectedFilterFactors)
                .add("queryFactorType", queryFactorType)
                .toString();
    }

    @Named("filterParametersBuilder")
    @Scope("request")
    public static class Builder {


        private String queryFactorType;

        private String geneQuery;
        private Set<Factor> selectedFilterFactors = new HashSet<>();
        private Set<Factor> queryFactors = new HashSet<>();
        private Set<String> queryFactorValues = Collections.EMPTY_SET;
        private Set<String> serializedFilterFactors = Collections.EMPTY_SET;

        Experiment experiment;

        public Builder() {
        }

        public Builder forExperiment(Experiment experiment) {
            this.experiment = experiment;
            return this;
        }

        public Builder withQueryFactorType(String queryFactorType) {
            this.queryFactorType = queryFactorType;
            return this;
        }

        public Builder withFilterFactors(Set<String> serializedFilterFactors) {
            if (CollectionUtils.isNotEmpty(serializedFilterFactors)) {
                this.serializedFilterFactors = serializedFilterFactors;
            }
            return this;
        }

        protected Factor buildFromSerializedFilterFactors(String serializedFilterFactors) {
            String[] split = serializedFilterFactors.split(FACTOR_VALUE_SEPARATOR);
            if (split.length == 2) {
                ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();
                String name = experimentalFactors.getFactorName(split[0]);
                return new Factor(split[0], name, split[1]);
            }
            throw new IllegalArgumentException("serialized Factor string should be colon separated between type and value.");
        }

        public Builder withGeneQuery(String geneQuery) {
            this.geneQuery = geneQuery;
            return this;
        }

        public Builder withQueryFactorValues(Set<String> queryFactorValuesString) {
            if (CollectionUtils.isNotEmpty(queryFactorValuesString)) {
                this.queryFactorValues = queryFactorValuesString;
            }
            return this;
        }

        public FilterParameters build() {
            checkState(experiment != null, "Please invoke forExperiment before build");

            if (StringUtils.isBlank(queryFactorType)) {
                queryFactorType = experiment.getDefaultQueryFactorType();
            }
            if (CollectionUtils.isEmpty(serializedFilterFactors)) {
                selectedFilterFactors = experiment.getDefaultFilterFactors();
            } else {
                for (String serializedFilterFactor : serializedFilterFactors) {
                    this.selectedFilterFactors.add(buildFromSerializedFilterFactors(serializedFilterFactor));
                }
            }
            for (String queryFactorValues : this.queryFactorValues) {
                this.queryFactors.add(new Factor(queryFactorType, queryFactorValues));
            }
            return new FilterParameters(this);
        }
    }
}
