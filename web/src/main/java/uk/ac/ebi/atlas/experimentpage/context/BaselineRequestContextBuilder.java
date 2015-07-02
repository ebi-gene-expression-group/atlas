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

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class BaselineRequestContextBuilder {

    private BaselineRequestContext requestContext;

    private BaselineExperiment experiment;

    private FilterFactorsConverter filterFactorsConverter;

    private BaselineRequestPreferences preferences;

    // NB: although we are building a BaselineRequest object here, we get an empty one injected from
    // the spring container (and populate it) because we want it to come from the request scope.
    // This allows other request scoped beans (eg: BaselineProfilePreconditionBackedBuilder)
    // to inject the same instance that we are populating here
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
        return preferences.getQueryFactorValues();
    }

    public BaselineRequestContext build() {
        checkState(experiment != null, "Please invoke forExperiment before build");

        requestContext.setExperiment(experiment);

        requestContext.setRequestPreferences(preferences);

        Set<Factor> selectedFilterFactors = filterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());
        requestContext.setSelectedFilterFactors(selectedFilterFactors);

        String filteredBySpecie = getFilteredBySpecie(selectedFilterFactors);
        requestContext.setFilteredBySpecies(filteredBySpecie);

        Set<Factor> queryFactors = new HashSet<>();
        for (String queryFactorValues : getQueryFactorValues()) {
            queryFactors.add(new Factor(requestContext.getQueryFactorType(), queryFactorValues));
        }
        requestContext.setSelectedQueryFactors(queryFactors);

        if(experiment.getExperimentalFactors().getAllFactorsOrderedByXML() != null &&
                !experiment.getExperimentalFactors().getAllFactorsOrderedByXML().isEmpty()) {
            Set<Factor> allQueryFactors = experiment.getExperimentalFactors().getComplementFactorsByXML(selectedFilterFactors);
            checkState(!allQueryFactors.isEmpty(), "Cannot determine query factors. Check selected filter factors are correct: " + selectedFilterFactors);
            requestContext.setAllQueryFactors(allQueryFactors);
        } else {
            SortedSet<Factor> allQueryFactors = experiment.getExperimentalFactors().getComplementFactors(selectedFilterFactors);
            checkState(!allQueryFactors.isEmpty(), "Cannot determine query factors. Check selected filter factors are correct: " + selectedFilterFactors);
            requestContext.setAllQueryFactors(allQueryFactors);
        }

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
            filteredBySpecie = experiment.getFirstOrganism().toLowerCase();
        }
        Map<String, String> speciesMapping = experiment.getOrganismToEnsemblSpeciesMapping();
        if (speciesMapping.containsKey(filteredBySpecie)) {
            filteredBySpecie = speciesMapping.get(filteredBySpecie);
        }
        return filteredBySpecie;
    }

}