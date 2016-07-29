package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.SpeciesUtils;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.Set;

/*
TODO this shows that the concepts of an assay group and contrast are parallel, and could have a common name/marker
interface/common getter in Experiment.
I'm not sure how to name them now.
 */
public final class SpeciesGrouper {

    private SpeciesGrouper() {}

    public static ImmutableMap<String, String> buildEnsemblSpeciesGroupedByAssayGroupId(BaselineExperiment experiment) {
        Set<String> assayGroupIds = experiment.getAssayGroups().getAssayGroupIds();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String ensemblSpecies = experiment.getSpecies().mappedName;

        for (String assayGroupId : assayGroupIds)  {
            builder.put(assayGroupId, ensemblSpecies);
        }

        return builder.build();
    }

    public static ImmutableMap<String, String> buildEnsemblSpeciesGroupedByContrastId(DifferentialExperiment experiment) {
        Set<String> contrastIds = experiment.getContrastIds();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String ensemblSpecies = experiment.getSpecies().mappedName;

        for (String contrastId : contrastIds)  {
            builder.put(contrastId, ensemblSpecies);
        }

        return builder.build();
    }


}
