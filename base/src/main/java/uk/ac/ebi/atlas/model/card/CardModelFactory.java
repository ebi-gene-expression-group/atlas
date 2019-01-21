package uk.ac.ebi.atlas.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.home.PopularSpeciesInfo;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.species.Species;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CardModelFactory {
    private final UrlHelpers urlHelpers;

    public CardModelFactory(UrlHelpers urlHelpers) {
        this.urlHelpers = urlHelpers;
    }

    private CardModel createCardModel(PopularSpeciesInfo popularSpeciesInfo, CardIconType type, List content){
        return CardModel.create(
                type,
                popularSpeciesInfo.species(),
                Pair.of(StringUtils.capitalize(popularSpeciesInfo.species()),
                        Optional.of(urlHelpers.getExperimentsFilteredBySpeciesUrl(popularSpeciesInfo.species()))),
                content);
    }

    public CardModel create(PopularSpeciesInfo popularSpeciesInfo) {
        return createCardModel(popularSpeciesInfo, CardIconType.SPECIES, Collections.singletonList(
                Pair.of(popularSpeciesInfo.totalExperiments() + " experiments",
                        Optional.empty())));
    }


    public CardModel createAtlasHomePageSpeciesCard(PopularSpeciesInfo popularSpeciesInfo) {
        List<Pair<String, Optional<String>>> content = new ArrayList<>();
        content.add(Pair.of(popularSpeciesInfo.totalExperiments() + " experiments",
                Optional.empty()));
        content.add(Pair.of(popularSpeciesInfo.baselineExperiments() + " ",
                Optional.of(urlHelpers.getExperimentsFilteredBySpeciesAndExperimentType(popularSpeciesInfo.species(), "baseline"))));
        content.add(Pair.of(popularSpeciesInfo.differentialExperiments() + " ",
                Optional.of(urlHelpers.getExperimentsFilteredBySpeciesAndExperimentType(popularSpeciesInfo.species(), "differential"))));

        return createCardModel(popularSpeciesInfo,  CardIconType.ATLASSPECIES, content);
    }

    public CardModel createLandingPageSpeciesCard(Collection<Experiment> experiments) {
        Species species = experiments.iterator().next().getSpecies();

        return CardModel.create(
                CardIconType.SPECIES,
                species.getReferenceName(),
                Pair.of(StringUtils.capitalize(species.getName() + " experiments"),
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
                Pair.of(description, Optional.of(descriptionUrl)),
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
