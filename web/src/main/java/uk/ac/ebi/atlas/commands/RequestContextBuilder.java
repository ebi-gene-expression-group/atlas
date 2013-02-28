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
import uk.ac.ebi.atlas.model.Factor;
import uk.ac.ebi.atlas.model.caches.ExperimentsCache;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Named
@Scope("request")
public class RequestContextBuilder implements Serializable {

    private FilterParameters filterParameters;

    private Set<String> queryFactorValues = Collections.EMPTY_SET;
    private String serializedFilterFactors;

    private String experimentAccession;

    private Experiment experiment;

    private FilterFactorsConverter filterFactorsConverter;
    private ExperimentsCache experimentsCache;

    @Inject
    public RequestContextBuilder(FilterParameters filterParameters, FilterFactorsConverter filterFactorsConverter, ExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
        this.filterParameters = filterParameters;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    public RequestContextBuilder forExperiment(String experimentAccession) {
        this.experimentAccession = experimentAccession;
        this.experiment = experimentsCache.getExperiment(experimentAccession);
        return this;
    }

    public RequestContextBuilder withQueryFactorType(String queryFactorType) {
        if (StringUtils.isBlank(queryFactorType)) {
            filterParameters.setQueryFactorType(experiment.getDefaultQueryFactorType());
        } else {
            filterParameters.setQueryFactorType(queryFactorType);
        }
        return this;
    }

    public RequestContextBuilder withSerializedFilterFactors(String serializedFilterFactors) {
        this.serializedFilterFactors = serializedFilterFactors;
        return this;
    }

    public RequestContextBuilder withGeneQuery(String geneQuery) {
        filterParameters.setGeneQuery(geneQuery);
        return this;
    }

    public RequestContextBuilder withQueryFactorValues(Set<String> queryFactorValuesString) {
        if (CollectionUtils.isNotEmpty(queryFactorValuesString)) {
            this.queryFactorValues = queryFactorValuesString;
        } else {
            this.queryFactorValues = Collections.EMPTY_SET;
        }
        return this;
    }

    public FilterParameters build() {
        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        if (StringUtils.isBlank(serializedFilterFactors)) {
            filterParameters.setSelectedFilterFactors(experiment.getDefaultFilterFactors());
        } else {

            Set<Factor> selectedFilterFactors = filterFactorsConverter.deserialize(serializedFilterFactors);

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