package uk.ac.ebi.atlas.model.card;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.home.PopularSpeciesInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

    public static CardModel createLandingPageCard(Map<String, Object> landingPageCardInfo) {
        String experimentAccession = String.valueOf(landingPageCardInfo.get("experimentAccession"));
        Map<String, String> imageSrc = new HashMap<>();
        imageSrc.put("E-EHCA", "https://goo.gl/images/xf7STU");
        imageSrc.put("E-GEOD", "https://www.ebi.ac.uk/gxa/sc/resources/images/home/hero_cell.png");
        imageSrc.put("E-ENAD", "https://www.ebi.ac.uk/gxa/sc/resources/images/home/hero_cell.png");
        imageSrc.put("E-MTAB", "https://www.ebi.ac.uk/gxa/sc/resources/images/home/hero_cell.png");
        return CardModel.create(
                CardIconType.IMAGE,
                imageSrc.get(experimentAccession.substring(0,6)),
                experimentAccession,
                Collections.singletonList(
                        Pair.of(String.valueOf(landingPageCardInfo.get("experimentDescription")),
                                Optional.of(getExperimentPageUrlByAccession(experimentAccession))))
        );
    }

    private static String getExperimentPageUrlByAccession(String accession) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments/{accession}")
                .buildAndExpand(accession)
                .encode()
                .toUriString();
    }
}
