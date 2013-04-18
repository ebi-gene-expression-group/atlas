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

package uk.ac.ebi.atlas.commands.context;

import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named
@Scope("prototype")
public class BaselineRequestContextBuilder {

    private BaselineRequestContext requestContext;

    private BaselineExperiment experiment;

    private FilterFactorsConverter filterFactorsConverter;

    private BaselineRequestPreferences preferences;

    @Inject
    public BaselineRequestContextBuilder(BaselineRequestContext requestContext, FilterFactorsConverter filterFactorsConverter) {
        this.requestContext = requestContext;
        this.filterFactorsConverter = filterFactorsConverter;
    }

    public BaselineRequestContextBuilder forExperiment(BaselineExperiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public BaselineRequestContextBuilder withPreferences(BaselineRequestPreferences preferences) {
        this.preferences = preferences;
        return this;
    }

    Set<String> getQueryFactorValues() {
        if (CollectionUtils.isNotEmpty(preferences.getQueryFactorValues())) {
            return preferences.getQueryFactorValues();
        }
        return Collections.EMPTY_SET;
    }

    public BaselineRequestContext build() {
        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        requestContext.setRequestPreferences(preferences);

        Set<Factor> selectedFilterFactors = filterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());
        requestContext.setSelectedFilterFactors(selectedFilterFactors);

        String filteredBySpecie = getFilteredBySpecie(selectedFilterFactors);
        requestContext.setFilteredBySpecies(filteredBySpecie);

        Set<Factor> queryFactors = new HashSet<Factor>();
        for (String queryFactorValues : getQueryFactorValues()) {
            queryFactors.add(new Factor(requestContext.getQueryFactorType(), queryFactorValues));
        }
        requestContext.setSelectedQueryFactors(queryFactors);

        SortedSet<Factor> allQueryFactors = experiment.getExperimentalFactors().getFilteredFactors(selectedFilterFactors);
        requestContext.setAllQueryFactors(allQueryFactors);

        return requestContext;
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
        Map<String, String> speciesMapping = experiment.getSpeciesMapping();
        if (speciesMapping.containsKey(filteredBySpecie)) {
            filteredBySpecie = speciesMapping.get(filteredBySpecie);
        }
        return filteredBySpecie;
    }

}