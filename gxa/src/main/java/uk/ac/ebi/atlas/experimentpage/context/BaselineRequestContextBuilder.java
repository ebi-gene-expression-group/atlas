package uk.ac.ebi.atlas.experimentpage.context;

import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import java.util.*;

import static com.google.common.base.Preconditions.checkState;

public class BaselineRequestContextBuilder {

    private BaselineExperiment experiment;

    private BaselineRequestPreferences preferences;

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


    Set<String> getQueryFactorValues() {
        return preferences.getQueryFactorValues();
    }

    public BaselineRequestContext build() {
        BaselineRequestContext requestContext = new BaselineRequestContext();

        checkState(experiment != null, "Please invoke forExperiment before build");

        requestContext.setExperiment(experiment);
        requestContext.setRequestPreferences(preferences);

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

    @Deprecated
    private String getFilteredBySpecies(Set<Factor> selectedFilterFactors) {
        List<String> candidateAnswers = new ArrayList<>();
        for (Factor selectedFilterFactor : selectedFilterFactors) {
            if (selectedFilterFactor.getType().equalsIgnoreCase("organism")) {
                candidateAnswers.add(selectedFilterFactor.getValue().toLowerCase());
            }
        }
        candidateAnswers.add(experiment.getSpecies().getReferenceName());
        return candidateAnswers.get(0);
    }

}
