package uk.ac.ebi.atlas.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.home.PopularSpeciesInfo;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardModelFactory {
    public static CardModel create(PopularSpeciesInfo popularSpeciesInfo) {
        return CardModel.create(
                CardIconType.SPECIES,
                popularSpeciesInfo.species(),
                Pair.of(StringUtils.capitalize(popularSpeciesInfo.species()),
                        Optional.of(getExperimentsFilteredBySpeciesUrl(popularSpeciesInfo.species()))),
                Collections.singletonList(
                        Pair.of(popularSpeciesInfo.totalExperiments() + " experiments",
                                Optional.empty())));
    }

    public static CardModel createLandingPageSpeciesCard(Collection<Experiment> experiments) {
        Species species = experiments.iterator().next().getSpecies();

        return CardModel.create(
                CardIconType.SPECIES,
                species.getReferenceName(),
                Pair.of(StringUtils.capitalize(species.getName() + " experiments"),
                        Optional.of(getExperimentsFilteredBySpeciesUrl(species.getReferenceName()))),
                experimentsToContent(experiments));

    }

    public static CardModel createLandingPageImageCard(Collection<Experiment> experiments,
                                                       String imageUrl,
                                                       String description,
                                                       String descriptionUrl) {
        return CardModel.create(
                CardIconType.IMAGE,
                imageUrl,
                Pair.of(description, Optional.of(descriptionUrl)),
                experimentsToContent(experiments));

    }

    private static String getExperimentsFilteredBySpeciesUrl(String species) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .buildAndExpand(species)
                .encode()
                .toUriString();
    }

    private static List<Pair<String, Optional<String>>> experimentsToContent(Collection<Experiment> experiments) {
        return experiments
                .stream()
                .map(CardModelFactory::experimentToDescriptionAndUrl)
                .collect(Collectors.toList());
    }

    private static Pair<String, Optional<String>> experimentToDescriptionAndUrl(Experiment experiment) {
        return Pair.of(
                experiment.getDescription(),
                Optional.of(getExperimentUrl(experiment)));
    }

    private static String getExperimentUrl(Experiment experiment) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{accession}")
                .buildAndExpand(experiment.getAccession())
                .encode()
                .toUriString();
    }

}
