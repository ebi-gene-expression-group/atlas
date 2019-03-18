package uk.ac.ebi.atlas.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.home.SpeciesSummary;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CardModelFactory {
    private final UrlHelpers urlHelpers;

    public CardModelFactory(UrlHelpers urlHelpers) {
        this.urlHelpers = urlHelpers;
    }

    public CardModel create(SpeciesSummary speciesSummary) {
        List<Pair<String, Optional<String>>> content = new ArrayList<>();
        content.add(
                Pair.of(speciesSummary.getTotalExperiments() + " experiment" +
                                (speciesSummary.getTotalExperiments() > 1 ? "s" : ""),
                        Optional.empty()));

        if (speciesSummary.getBaselineExperiments() > 0) {
            content.add(
                    Pair.of("Baseline: " + speciesSummary.getBaselineExperiments(),
                            Optional.of(
                                    urlHelpers.getExperimentsFilteredBySpeciesAndExperimentType(
                                            speciesSummary.getSpecies(),
                                            "baseline"))));
        }
        if (speciesSummary.getDifferentialExperiments() > 0) {
            content.add(
                    Pair.of("Differential: " + speciesSummary.getDifferentialExperiments(),
                            Optional.of(
                                    urlHelpers.getExperimentsFilteredBySpeciesAndExperimentType(
                                            speciesSummary.getSpecies(),
                                            "differential"))));
        }

        return CardModel.create(
                CardIconType.SPECIES,
                speciesSummary.getSpecies(),
                Pair.of(
                        Optional.of(StringUtils.capitalize(speciesSummary.getSpecies())),
                        Optional.of(urlHelpers.getExperimentsFilteredBySpeciesUrl(speciesSummary.getSpecies()))),
                content);
    }

    public CardModel createLandingPageSpeciesCard(Collection<Experiment> experiments) {
        Species species = experiments.iterator().next().getSpecies();

        return CardModel.create(
                CardIconType.SPECIES,
                species.getReferenceName(),
                Pair.of(
                        Optional.of(StringUtils.capitalize(species.getName() + " experiments")),
                        Optional.of(urlHelpers.getExperimentsFilteredBySpeciesUrl(species.getReferenceName()))),
                experimentsToContent(experiments));

    }

    public CardModel createLandingPageImageCard(Collection<Experiment> experiments,
                                                String imageUrl,
                                                String description,
                                                String descriptionUrl) {
        return CardModel.create(
                CardIconType.IMAGE,
                imageUrl,
                Pair.of(Optional.of(description), Optional.of(descriptionUrl)),
                experimentsToContent(experiments));
    }

    private List<Pair<String, Optional<String>>> experimentsToContent(Collection<Experiment> experiments) {
        return experiments
                .stream()
                .map(this::experimentToDescriptionAndUrl)
                .collect(Collectors.toList());
    }

    private Pair<String, Optional<String>> experimentToDescriptionAndUrl(Experiment experiment) {
        return Pair.of(
                experiment.getDescription(),
                Optional.of(urlHelpers.getExperimentUrl(experiment)));
    }

}
