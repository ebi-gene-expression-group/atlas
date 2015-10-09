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
        if (experiment.isMultiOrganismExperiment()) {
            return buildMultipleEnsemblSpeciesGroupedByAssayGroupId(experiment);
        } else {
            return buildSingleEnsemblSpeciesGroupedByAssayGroupId(experiment);
        }
    }

    private static ImmutableMap<String, String> buildSingleEnsemblSpeciesGroupedByAssayGroupId(BaselineExperiment experiment) {
        Set<String> assayGroupIds = experiment.getAssayGroups().getAssayGroupIds();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String ensemblSpecies = Species.convertToEnsemblSpecies(experiment.getOrganismToEnsemblSpeciesMapping(), experiment.getFirstOrganism());

        for (String assayGroupId : assayGroupIds)  {
            builder.put(assayGroupId, ensemblSpecies);
        }

        return builder.build();
    }

    private static ImmutableMap<String, String> buildMultipleEnsemblSpeciesGroupedByAssayGroupId(BaselineExperiment experiment) {
        ImmutableMap<String, Factor> organismPartByGroupId = experiment.getExperimentalFactors().getFactorGroupedByAssayGroupId("ORGANISM");

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        for (Map.Entry<String, Factor> entry : organismPartByGroupId.entrySet())  {
            String ensemblSpecies = Species.convertToEnsemblSpecies(experiment.getOrganismToEnsemblSpeciesMapping(), entry.getValue().getValue());
            builder.put(entry.getKey(), ensemblSpecies);
        }

        return builder.build();
    }

    public static ImmutableMap<String, String> buildEnsemblSpeciesGroupedByContrastId(DifferentialExperiment experiment) {
        if (experiment.isMultiOrganismExperiment()) {
            return buildMultipleEnsemblSpeciesGroupedByContrastId(experiment);
        } else {
            return buildSingleEnsemblSpeciesGroupedByContrastId(experiment);
        }
    }

    private static ImmutableMap<String, String> buildSingleEnsemblSpeciesGroupedByContrastId(DifferentialExperiment experiment) {
        Set<String> contrastIds = experiment.getContrastIds();

        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();

        String ensemblSpecies = Species.convertToEnsemblSpecies(experiment.getFirstOrganism());

        for (String contrastId : contrastIds)  {
            builder.put(contrastId, ensemblSpecies);
        }

        return builder.build();
    }

    private static ImmutableMap<String, String> buildMultipleEnsemblSpeciesGroupedByContrastId(DifferentialExperiment experiment) {
        throw new UnsupportedOperationException();
    }

}
