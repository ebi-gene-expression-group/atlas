package uk.ac.ebi.atlas.model.card;

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
}
