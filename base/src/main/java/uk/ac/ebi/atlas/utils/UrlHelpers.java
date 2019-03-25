package uk.ac.ebi.atlas.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.model.experiment.Experiment;

import java.util.Optional;

public class UrlHelpers {
    public static String getExperimentsFilteredBySpeciesUrl(String species) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .buildAndExpand(species)
                .encode()
                .toUriString();
    }

    public static String getExperimentUrl(Experiment experiment) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{accession}")
                .buildAndExpand(experiment.getAccession())
                .encode()
                .toUriString();
    }

    public static String getExperimentsFilteredBySpeciesAndExperimentType(String species, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .query("experimentType={type}")
                .buildAndExpand(species, type)
                .encode()
                .toUriString();
    }

    public static String getImageUrl(String imageFileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resources/images/experiment-list-latest/{imageFileName}.png")
                .buildAndExpand(imageFileName)
                .toUriString();
    }

    public static String getCustomUrl(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path)
                .build()
                .toUriString();
    }

    public static Optional<String> getExperimentSetUrl(String keyword) {
        return Optional.of(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/experiments")
                        .query("experimentSet={keyword}")
                        .buildAndExpand(keyword)
                        .toUriString());
    }

    public static Optional<String> getExperimentUrl(String accession) {
        return Optional.of(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/experiments/{accession}")
                        .buildAndExpand(accession)
                        .toUriString());
    }
}
