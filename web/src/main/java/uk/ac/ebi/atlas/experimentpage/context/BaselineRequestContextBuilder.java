package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Optional;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkState;

public class BaselineRequestContextBuilder {

    private BaselineExperiment experiment;

    private BaselineRequestPreferences preferences;

    private Optional<String> queryDescription = Optional.absent();

    public BaselineRequestContextBuilder() {
    }

    public BaselineRequestContextBuilder forExperiment(BaselineExperiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public BaselineRequestContextBuilder withPreferences(BaselineRequestPreferences preferences) {
        this.preferences = preferences;
        return this;
    }

    public BaselineRequestContextBuilder withCustomQueryDescription(String description) {
        this.queryDescription = Optional.of(description);
        return this;
    }

    Set<String> getQueryFactorValues() {
        return preferences.getQueryFactorValues();
    }

    public BaselineRequestContext build() {
        BaselineRequestContext requestContext = new BaselineRequestContext();

        checkState(experiment != null, "Please invoke forExperiment before build");

        requestContext.setExperiment(experiment);

        requestContext.setRequestPreferences(preferences);

        requestContext.setQueryDescription(queryDescription.or(preferences.getGeneQuery().asSolr1DNF()));



        SortedSet<Factor> selectedFilterFactors = FilterFactorsConverter.deserialize(preferences.getSerializedFilterFactors());
        requestContext.setSelectedFilterFactors(selectedFilterFactors);

        String filteredBySpecies = getFilteredBySpecies(selectedFilterFactors);
        requestContext.setFilteredBySpecies(filteredBySpecies);

        Set<Factor> queryFactors = new HashSet<>();
        for (String queryFactorValues : getQueryFactorValues()) {
            queryFactors.add(new Factor(requestContext.getQueryFactorType(), queryFactorValues));
        }
        requestContext.setSelectedQueryFactors(queryFactors);

        ExperimentalFactors experimentalFactors = experiment.getExperimentalFactors();

        SortedSet<Factor> allQueryFactors = experimentalFactors.getComplementFactors(selectedFilterFactors);
        checkState(!allQueryFactors.isEmpty(), "Cannot determine query factors. Check selected filter factors are correct: " + selectedFilterFactors);
        requestContext.setAllQueryFactors(allQueryFactors);

        return requestContext;
    }

    private String getFilteredBySpecies(Set<Factor> selectedFilterFactors) {
        String filteredBySpecies = null;
        for (Factor selectedFilterFactor : selectedFilterFactors) {
            if (selectedFilterFactor.getType().equalsIgnoreCase("organism")) {
                filteredBySpecies = selectedFilterFactor.getValue().toLowerCase();
            }
        }
        if (filteredBySpecies == null) {
            filteredBySpecies = experiment.getSpecies().toLowerCase();
        }
        Map<String, String> speciesMapping = experiment.getSpeciesToEnsemblMapping();
        if (speciesMapping.containsKey(filteredBySpecies)) {
            filteredBySpecies = speciesMapping.get(filteredBySpecies);
        }
        return filteredBySpecies;
    }

}
