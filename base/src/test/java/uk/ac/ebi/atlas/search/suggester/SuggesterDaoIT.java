package uk.ac.ebi.atlas.search.suggester;

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
    void mapsSuggestionsToMapOfTermAndCategory() {
        assertThat(subject.fetchBioentityProperties("asp", 10, false))
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
    void atLeastThreeCharactersRequired() {
        assertThat(subject.fetchBioentityProperties("as", 10, false))
                .isEmpty();
        assertThat(subject.fetchBioentityProperties("as", 10, false, speciesUtils.getHuman()))
                .isEmpty();
        assertThat(subject.fetchBioentityProperties("asp", 10, false))
                .isNotEmpty();
        assertThat(subject.fetchBioentityProperties("asp", 10, false, speciesUtils.getHuman()))
                .isNotEmpty();
    }
}
