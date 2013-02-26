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

package uk.ac.ebi.atlas.commands;

import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.impl.FilterParameters;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.ExperimentalFactors;
import uk.ac.ebi.atlas.model.Factor;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Named
@Scope("session")
public class SessionContextBuilder implements Serializable {
    public static final String FACTOR_VALUE_SEPARATOR = ":";

    private FilterParameters filterParameters;

    private Set<String> queryFactorValues = Collections.EMPTY_SET;
    private Set<String> serializedFilterFactors = Collections.EMPTY_SET;

    private Experiment experiment;

    @Inject
    public SessionContextBuilder(FilterParameters filterParameters) {
        this.filterParameters = filterParameters;
    }

    public SessionContextBuilder forExperiment(Experiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public SessionContextBuilder withQueryFactorType(String queryFactorType) {
        if (StringUtils.isBlank(queryFactorType)) {
            filterParameters.setQueryFactorType(experiment.getDefaultQueryFactorType());
        } else {
            filterParameters.setQueryFactorType(queryFactorType);
        }
        return this;
    }

    public SessionContextBuilder withFilterFactors(Set<String> serializedFilterFactors) {
        if (CollectionUtils.isNotEmpty(serializedFilterFactors)) {
            this.serializedFilterFactors = serializedFilterFactors;
        } else {
            this.serializedFilterFactors = Collections.EMPTY_SET;
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

    public SessionContextBuilder withGeneQuery(String geneQuery) {
        filterParameters.setGeneQuery(geneQuery);
        return this;
    }

    public SessionContextBuilder withQueryFactorValues(Set<String> queryFactorValuesString) {
        if (CollectionUtils.isNotEmpty(queryFactorValuesString)) {
            this.queryFactorValues = queryFactorValuesString;
        } else {
            this.queryFactorValues = Collections.EMPTY_SET;
        }
        return this;
    }

    public FilterParameters build() {
        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        if (CollectionUtils.isEmpty(serializedFilterFactors)) {
            filterParameters.setSelectedFilterFactors(experiment.getDefaultFilterFactors());
        } else {
            Set<Factor> selectedFilterFactors = new HashSet<Factor>();
            for (String serializedFilterFactor : serializedFilterFactors) {
                selectedFilterFactors.add(this.buildFromSerializedFilterFactors(serializedFilterFactor));
            }
            filterParameters.setSelectedFilterFactors(selectedFilterFactors);
        }

        String filteredBySpecie = null;
        for (Factor selectedFilterFactor : filterParameters.getSelectedFilterFactors()) {
            if (selectedFilterFactor.getType().equalsIgnoreCase("organism")) {
                filteredBySpecie = selectedFilterFactor.getValue().toLowerCase();
            }
        }
        if (filteredBySpecie == null) {
            filteredBySpecie = experiment.getFirstSpecies().toLowerCase();
        }
        filterParameters.setFilteredBySpecie(filteredBySpecie);

        Set<Factor> queryFactors = new HashSet<Factor>();
        for (String queryFactorValues : this.queryFactorValues) {
            queryFactors.add(new Factor(filterParameters.getQueryFactorType(), queryFactorValues));
        }
        filterParameters.setSelectedQueryFactors(queryFactors);

        return filterParameters;
    }
}