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
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commands.impl.FilterParameters;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.cache.baseline.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;
import uk.ac.ebi.atlas.web.RequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

@Named
@Scope("prototype")
public class RequestContextBuilder implements Serializable {

    private FilterParameters filterParameters;

    private BaselineExperiment experiment;

    private FilterFactorsConverter filterFactorsConverter;

    private BaselineExperimentsCache experimentsCache;

    private RequestPreferences preferences;

    @Inject
    public RequestContextBuilder(FilterParameters filterParameters, FilterFactorsConverter filterFactorsConverter, BaselineExperimentsCache experimentsCache) {
        this.experimentsCache = experimentsCache;
        this.filterParameters = filterParameters;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    public RequestContextBuilder forExperiment(String experimentAccession) {
        this.experiment = experimentsCache.getExperiment(experimentAccession);
        return this;
    }

    public RequestContextBuilder withPreferences(RequestPreferences preferences) {
        this.preferences = preferences;
        return this;
    }

    Set<String> getQueryFactorValues() {
        if (CollectionUtils.isNotEmpty(preferences.getQueryFactorValues())) {
            return preferences.getQueryFactorValues();
        } else {
            return Collections.EMPTY_SET;
        }
    }

    public RequestContext build() {
        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        filterParameters.setRequestPreferences(preferences);

        Set<Factor> selectedFilterFactors = filterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());

        filterParameters.setSelectedFilterFactors(selectedFilterFactors);

        String filteredBySpecie = getFilteredBySpecie(selectedFilterFactors);
        filterParameters.setFilteredBySpecies(filteredBySpecie);

        Set<Factor> queryFactors = new HashSet<Factor>();
        for (String queryFactorValues : getQueryFactorValues()) {
            queryFactors.add(new Factor(filterParameters.getQueryFactorType(), queryFactorValues));
        }
        filterParameters.setSelectedQueryFactors(queryFactors);

        SortedSet<Factor> allQueryFactors = experiment.getExperimentalFactors().getFilteredFactors(selectedFilterFactors);
        filterParameters.setAllQueryFactors(allQueryFactors);

        return filterParameters;
    }

    String getFilteredBySpecie(Set<Factor> selectedFilterFactors) {
        String filteredBySpecie = null;
        for (Factor selectedFilterFactor : selectedFilterFactors) {
            if (selectedFilterFactor.getType().equalsIgnoreCase("organism")) {
                filteredBySpecie = selectedFilterFactor.getValue().toLowerCase();
            }
        }
        if (filteredBySpecie == null) {
            filteredBySpecie = experiment.getFirstSpecies().toLowerCase();
        }
        return filteredBySpecie;
    }

}