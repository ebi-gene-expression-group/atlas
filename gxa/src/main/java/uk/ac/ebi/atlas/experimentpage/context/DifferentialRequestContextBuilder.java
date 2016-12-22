package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import java.util.Set;
import java.util.SortedSet;

public class DifferentialRequestContextBuilder
        <E extends DifferentialExperiment,
         T extends DifferentialRequestContext<E>, K extends DifferentialRequestPreferences> {

    protected T requestContext;
    private K requestPreferences;
    private E experiment;

    protected DifferentialRequestContextBuilder(T requestContext) {
        this.requestContext = requestContext;
    }

    public DifferentialRequestContextBuilder<E, T, K> forExperiment(E experiment) {
        this.experiment = experiment;
        return this;
    }

    public DifferentialRequestContextBuilder<E, T, K> withPreferences(K requestPreferences) {
        this.requestPreferences = requestPreferences;
        return this;
    }

    public T build() {
        Preconditions.checkState(experiment != null, "Please invoke forExperiment before build");

        requestContext.setRequestPreferences(getRequestPreferences());
        requestContext.setQueryDescription(SearchDescription.get(getRequestPreferences().getGeneQuery()));
        requestContext.setSelectedQueryFactors(getSelectedQueryContrasts(experiment));
        requestContext.setFilteredBySpecies(experiment.getSpecies().getReferenceName());
        requestContext.setAllQueryFactors(experiment.getContrasts());
        requestContext.setExperiment(experiment);

        return requestContext;
    }

    private Set<Contrast> getSelectedQueryContrasts(DifferentialExperiment experiment) {
        if (CollectionUtils.isEmpty(getRequestPreferences().getQueryFactorValues())) {
            return Sets.newHashSet();
        }

        SortedSet<Contrast> selectedQueryContrasts = Sets.newTreeSet();
        for (String queryContrastId : getRequestPreferences().getQueryFactorValues()) {

            try {
                Contrast contrast = experiment.getContrast(queryContrastId);
                selectedQueryContrasts.add(contrast);
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException(e);
            }

        }
        return selectedQueryContrasts;
    }

    protected DifferentialRequestPreferences getRequestPreferences() {
        return requestPreferences;
    }
}
