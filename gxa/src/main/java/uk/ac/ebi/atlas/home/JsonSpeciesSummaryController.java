package uk.ac.ebi.atlas.home;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.atlas.model.card.CardModelFactory;

@RestController
public class JsonSpeciesSummaryController extends SpeciesSummaryController {
    public JsonSpeciesSummaryController(GxaSpeciesSummaryDao speciesSummaryDao,
                                        CardModelFactory cardModelFactory) {
        super(speciesSummaryDao, cardModelFactory);
    }

    @GetMapping(value = "/json/species-summary",
                produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getSpeciesSummaryGroupedByKingdom(@RequestParam(defaultValue = "6") int limit) {
        return super.getPopularExperimentsGroupedByKingdom(limit);
    }
}
