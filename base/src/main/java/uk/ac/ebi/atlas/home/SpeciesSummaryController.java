package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.model.card.CardModelAdapter;
import uk.ac.ebi.atlas.model.card.CardModelFactory;
import uk.ac.ebi.atlas.utils.GsonProvider;

import static com.google.common.collect.ImmutableList.toImmutableList;

public abstract class SpeciesSummaryController extends JsonExceptionHandlingController {
    private final SpeciesSummaryService speciesSummaryService;
    private final CardModelFactory cardModelFactory;

    public SpeciesSummaryController(SpeciesSummaryDao popularSpeciesDao,
                                    CardModelFactory cardModelFactory) {
        this.speciesSummaryService = new SpeciesSummaryService(popularSpeciesDao);
        this.cardModelFactory = cardModelFactory;
    }

    public String getPopularExperimentsGroupedByKingdom(int limit) {
        // I want Java 10 and val!
        ImmutableList<ImmutableMap<String, Object>> foo =
                speciesSummaryService.getSpeciesSummaryGroupedByKingdom(limit).entrySet().stream()
                        .map(entry -> ImmutableMap.of(
                                "kingdom", entry.getKey(),
                                "cards", CardModelAdapter.serialize(entry.getValue().stream()
                                        .map(cardModelFactory::create)
                                        .collect(toImmutableList()))))
                        .collect(toImmutableList());

        return GsonProvider.GSON.toJson(ImmutableMap.of("speciesSummary", foo));
    }
}
