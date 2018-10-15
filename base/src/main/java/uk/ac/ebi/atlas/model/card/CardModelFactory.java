package uk.ac.ebi.atlas.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import uk.ac.ebi.atlas.species.services.PopularSpeciesInfo;

import java.util.Collections;
import java.util.Optional;

public class CardModelFactory {

    public static CardModel create(PopularSpeciesInfo popularSpeciesInfo) {

        return CardModel.create(
                "species",
                popularSpeciesInfo.species(),
                StringUtils.capitalize(popularSpeciesInfo.species()),
                Collections.singletonList(
                        Pair.of(popularSpeciesInfo.totalExperiments() + " experiments", Optional.empty()))
        );
    }
}
