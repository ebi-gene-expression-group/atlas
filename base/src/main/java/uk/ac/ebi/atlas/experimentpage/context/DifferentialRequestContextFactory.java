package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.search.SearchDescription;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public abstract class DifferentialRequestContextFactory<E extends DifferentialExperiment, K extends
        DifferentialRequestPreferences, R extends
        DifferentialRequestContext<E, K>> {

   public abstract R create(E experiment, K preferences);

    protected R populateCommonThings(R requestContext, E experiment, K preferences){
        requestContext.setRequestPreferences(preferences);
        requestContext.setSelectedQueryFactors(getSelectedQueryContrasts(experiment, preferences));
        requestContext.setFilteredBySpecies(experiment.getSpecies().getReferenceName());
        requestContext.setAllQueryFactors(new HashSet<>(experiment.getDataColumnDescriptors()));
        requestContext.setExperiment(experiment);
        return requestContext;
    }

    private Set<Contrast> getSelectedQueryContrasts(DifferentialExperiment experiment, K requestPreferences) {
        if (CollectionUtils.isEmpty(requestPreferences.getQueryFactorValues())) {
            return Sets.newHashSet();
        }

        SortedSet<Contrast> selectedQueryContrasts = Sets.newTreeSet();
        for (String queryContrastId : requestPreferences.getQueryFactorValues()) {

            try {
                Contrast contrast = experiment.getDataColumnDescriptor(queryContrastId);
                selectedQueryContrasts.add(contrast);
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException(e);
            }

        }
        return selectedQueryContrasts;
    }

    public static class RnaSeq extends DifferentialRequestContextFactory<DifferentialExperiment,
            DifferentialRequestPreferences, RnaSeqRequestContext> {
        @Override
        public RnaSeqRequestContext create(DifferentialExperiment experiment, DifferentialRequestPreferences preferences) {
            return populateCommonThings(new RnaSeqRequestContext(), experiment, preferences);
        }
    }

    public static class Microarray extends DifferentialRequestContextFactory<MicroarrayExperiment,
            MicroarrayRequestPreferences, MicroarrayRequestContext> {
        @Override
        public MicroarrayRequestContext create(MicroarrayExperiment experiment, MicroarrayRequestPreferences preferences) {
            return populateCommonThings(new MicroarrayRequestContext(), experiment, preferences);
        }
    }

}
