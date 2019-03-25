package uk.ac.ebi.atlas.home;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import uk.ac.ebi.atlas.controllers.JsonExceptionHandlingController;
import uk.ac.ebi.atlas.model.card.CardModelAdapter;
import uk.ac.ebi.atlas.model.card.CardModelFactory;
import uk.ac.ebi.atlas.utils.GsonProvider;

import java.util.Comparator;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;

public abstract class SpeciesSummaryController extends JsonExceptionHandlingController {
    private final static Comparator<String> KINGDOM_COMPARATOR =
            arbitraryStringComparator("animals", "plants", "fungi", "protists");
    private final SpeciesSummaryService speciesSummaryService;
    private final CardModelFactory cardModelFactory;

    public SpeciesSummaryController(SpeciesSummaryDao speciesSummaryDao,
                                    CardModelFactory cardModelFactory) {
        this.speciesSummaryService = new SpeciesSummaryService(speciesSummaryDao);
        this.cardModelFactory = cardModelFactory;
    }

    public String getPopularExperimentsGroupedByKingdom(int limit) {
        // I want Java 10 and val!
        ImmutableList<ImmutableMap<String, Object>> kingdom2SerialisedCards =
                ImmutableSortedMap
                        .copyOf(speciesSummaryService.getSpeciesSummaryGroupedByKingdom(limit), KINGDOM_COMPARATOR)
                        .entrySet().stream()
                        .map(entry -> ImmutableMap.of(
                                "kingdom", entry.getKey(),
                                "cards", CardModelAdapter.serialize(entry.getValue().stream()
                                        .map(cardModelFactory::create)
                                        .collect(toImmutableList()))))
                        .collect(toImmutableList());

        return GsonProvider.GSON.toJson(ImmutableMap.of("speciesSummary", kingdom2SerialisedCards));
    }

    private static Comparator<String> arbitraryStringComparator(String... stringsInOrder) {
        // The list and the comparator are both reversed to get the argument strings first, and then the other elements
        Comparator<String> arbitraryComparator =
                comparing(s -> ImmutableList.copyOf(stringsInOrder).reverse().indexOf(s));
        return arbitraryComparator.reversed().thenComparing(naturalOrder());
    }
}
