package uk.ac.ebi.atlas.search.suggester;

import org.apache.solr.client.solrj.response.Suggestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.testutils.SpeciesUtils;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class SuggesterDaoIT {
    @Inject
    private SpeciesUtils speciesUtils;

    @Inject
    private SuggesterDao subject;

    @Test
    void suggestionsCanBeFetched() {
        assertThat(subject.fetchBioentityProperties("asp", 10, false))
                .isNotEmpty();

        assertThat(subject.fetchBioentityIdentifiers("asp", 10))
                .isNotEmpty();
    }

    @Test
    void duplicatesAreRemoved() {
        assertThat(subject.fetchBioentityProperties("asp", 100, false).count())
                .isLessThan(100);
    }

    @Test
    void highlightsInBoldMatchedRegion() {
        assertThat(subject.fetchBioentityProperties("asp", 10, true))
                .isNotEmpty()
                .allMatch(suggestion -> suggestion.getTerm().matches(".*<b>(?i)(asp)</b>.*"));
    }

    @Test
    void filtersOnSpecies() {
        long numberOfUnfilteredSuggestions =
                subject.fetchBioentityProperties("aspm", 10, false).count();
        long numberOfFilteredSuggestions =
                subject.fetchBioentityProperties("aspm", 10, false, speciesUtils.getHuman()).count();
        assertThat(numberOfFilteredSuggestions)
                .isGreaterThan(0)
                .isLessThan(numberOfUnfilteredSuggestions);
    }

    @Test
    void filtersOnMultipleSpecies() {
        long numberOfUnfilteredSuggestions =
                subject.fetchBioentityProperties("aspm", 10, false).count();
        long numberOfHumanFilteredSuggestions =
                subject.fetchBioentityProperties("aspm", 10, false, speciesUtils.getHuman()).count();
        long numberOfHumanAndMouseFilteredSuggestions =
                subject.fetchBioentityProperties(
                        "aspm", 10, false, speciesUtils.getHuman(), speciesUtils.getMouse()).count();

        assertThat(numberOfHumanAndMouseFilteredSuggestions)
                .isGreaterThan(0)
                .isGreaterThan(numberOfHumanFilteredSuggestions)
                .isLessThan(numberOfUnfilteredSuggestions);
    }

    @Test
    void atLeastTwoCharactersRequired() {
        assertThat(subject.fetchBioentityProperties("a", 10, false))
                .isEmpty();
        assertThat(subject.fetchBioentityProperties("a", 10, false, speciesUtils.getHuman()))
                .isEmpty();

        assertThat(subject.fetchBioentityProperties("ar", 10, false))
                .isNotEmpty();
        assertThat(subject.fetchBioentityProperties("ar", 10, false, speciesUtils.getHuman()))
                .isNotEmpty();
    }

    @Test
    void closestMatchIsFirst() {
        String twoCharSymbol = "AR";

        // Suggestion::equals is established only by term and payload, weight isn’t considered
        assertThat(subject.fetchBioentityProperties(twoCharSymbol.toLowerCase(), 10, false))
                .isNotEmpty()
                .startsWith(new Suggestion(twoCharSymbol, 0, "symbol"));
        assertThat(subject.fetchBioentityProperties("ar", 10, false, speciesUtils.getHuman()))
                .isNotEmpty()
                .startsWith(new Suggestion(twoCharSymbol, 0, "symbol"));
    }
}
