package uk.ac.ebi.atlas.experimentimport.analyticsindex.support;

import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import java.util.Map;
import java.util.Set;

public final class SpeciesGrouper {

    private SpeciesGrouper() {}

    public static ImmutableMap<String, String> buildEnsemblSpeciesGroupedByAssayGroupId(BaselineExperiment experiment) {
        Set<String> assayGroupIds = experiment.getAssayGroups().getAssayGroupIds();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String ensemblSpecies = Species.convertToEnsemblSpecies(experiment.getOrganismToEnsemblSpeciesMapping(), experiment.getFirstOrganism());

        for (String assayGroupId : assayGroupIds)  {
            builder.put(assayGroupId, ensemblSpecies);
        }

        return builder.build();
    }

    public static ImmutableMap<String, String> buildEnsemblSpeciesGroupedByContrastId(DifferentialExperiment experiment) {
        Set<String> contrastIds = experiment.getContrastIds();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String ensemblSpecies = Species.convertToEnsemblSpecies(experiment.getFirstOrganism());

        for (String contrastId : contrastIds)  {
            builder.put(contrastId, ensemblSpecies);
        }

        return builder.build();
    }


}
