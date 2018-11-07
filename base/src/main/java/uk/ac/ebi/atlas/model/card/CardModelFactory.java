package uk.ac.ebi.atlas.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.home.PopularSpeciesInfo;

import java.util.Collections;
import java.util.Optional;

public class CardModelFactory {

    public static CardModel create(PopularSpeciesInfo popularSpeciesInfo) {

        return CardModel.create(
                CardIconType.SPECIES,
                popularSpeciesInfo.species(),
                StringUtils.capitalize(popularSpeciesInfo.species()),
                Collections.singletonList(
                        Pair.of(popularSpeciesInfo.totalExperiments() + " experiments",
                                Optional.of(getExperimentsFilteredBySpeciesUrl(popularSpeciesInfo.species()))))
                );
    }

    private static String getExperimentsFilteredBySpeciesUrl(String species) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("species={species}")
                .buildAndExpand(species)
                .encode()
                .toUriString();
    }
}
