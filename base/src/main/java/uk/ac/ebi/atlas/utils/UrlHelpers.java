package uk.ac.ebi.atlas.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.model.experiment.Experiment;

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
        return getExperimentUrl(experiment.getAccession());
    }

    public static String getExperimentUrl(String accession) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/experiments/{accession}")
                        .buildAndExpand(accession)
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

    public static String getExperimentsSummaryImageUrl(String imageFileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resources/images/experiments-summary/{imageFileName}.png")
                .buildAndExpand(imageFileName)
                .toUriString();
    }

    public static String getCustomUrl(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path)
                .build()
                .toUriString();
    }

    public static String getExperimentSetUrl(String keyword) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/experiments")
                        .query("experimentSet={keyword}")
                        .buildAndExpand(keyword)
                        .toUriString();
    }
}
