package uk.ac.ebi.atlas.hcalandingpage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.experiments.ExperimentSearchService;
import uk.ac.ebi.atlas.model.card.CardModel;
import uk.ac.ebi.atlas.model.card.CardModelAdapter;
import uk.ac.ebi.atlas.model.card.CardModelFactory;
import uk.ac.ebi.atlas.utils.GsonProvider;

@RestController
public class JsonHcaLandingPageController extends JsonExceptionHandlingController {
    protected static final String HCA_ACCESSION_PATTERN = "EHCA";

    private final ExperimentSearchService experimentSearchService;
    private final CardModelFactory cardModelFactory;

    public JsonHcaLandingPageController(ExperimentSearchService experimentSearchService,
                                        CardModelFactory cardModelFactory) {
        this.experimentSearchService = experimentSearchService;
        this.cardModelFactory = cardModelFactory;
    }

    @GetMapping(value = {"/json/hca"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String foo() {
        CardModel hcaCard =
                cardModelFactory.createLandingPageImageCard(
                        experimentSearchService.searchPublicExperimentsByAccession(HCA_ACCESSION_PATTERN),
                        getHcaCellLogoUrl(),
                        "Human Cell Atlas experiments",
                        getHcaExperimentsUrl());

        CardModel humanCard =
                cardModelFactory.createLandingPageSpeciesCard(
                        experimentSearchService.searchPublicExperimentsBySpecies("Homo sapiens"));

        return GsonProvider.GSON.toJson(
                ImmutableMap.of("cards", CardModelAdapter.serialize(ImmutableList.of(hcaCard, humanCard))));
    }

    // TODO The two methods below are subject to be generalised when we add featured experiments in SCEA’s home page

    private String getHcaCellLogoUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/resources/images/logos/hca_cell_logo.png")
                .toUriString();
    }

    private String getHcaExperimentsUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/experiments")
                .query("experimentSet={accessionPattern}")
                .buildAndExpand(HCA_ACCESSION_PATTERN)
                .encode()
                .toUriString();
    }
}

