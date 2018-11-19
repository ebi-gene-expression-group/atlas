package uk.ac.ebi.atlas.model.card;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.model.experiment.Experiment;

// Consider moving this class to an interface with different implementations in EA and SCEA
@Component
class UrlHelpersImpl implements UrlHelpers {
    @Override
    public String getExperimentsFilteredBySpeciesUrl(String species) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .buildAndExpand(species)
                .encode()
                .toUriString();
    }

    @Override
    public String getExperimentUrl(Experiment experiment) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{accession}")
                .buildAndExpand(experiment.getAccession())
                .encode()
                .toUriString();
    }
}
