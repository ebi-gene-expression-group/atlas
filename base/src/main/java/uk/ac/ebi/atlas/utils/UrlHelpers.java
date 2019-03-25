package uk.ac.ebi.atlas.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.model.experiment.Experiment;

public interface UrlHelpers {
    default String getExperimentsFilteredBySpeciesUrl(String species) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .buildAndExpand(species)
                .encode()
                .toUriString();
    }

    default String getExperimentUrl(Experiment experiment) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{accession}")
                .buildAndExpand(experiment.getAccession())
                .encode()
                .toUriString();
    }

    default String getExperimentsFilteredBySpeciesAndExperimentType(String species, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .query("experimentType={type}")
                .buildAndExpand(species, type)
                .encode()
                .toUriString();
    }
}
