package uk.ac.ebi.atlas.search.suggester;

import org.apache.solr.client.solrj.response.Suggestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import uk.ac.ebi.atlas.species.SpeciesFactory;

import java.util.stream.Stream;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SuggesterServiceTest {
    @Mock
    private SuggesterDao suggesterDaoMock;

    @Mock
    private SpeciesFactory speciesFactoryMock;

    private SuggesterService subject;

    @BeforeEach
    void setUp() {
        when(suggesterDaoMock.fetchBioentityProperties(anyString(), anyInt(), eq(true), any()))
                .thenReturn(Stream.of(
                        new Suggestion(randomAlphanumeric(10), 10, randomAlphabetic(10)),
                        new Suggestion(randomAlphanumeric(10), 20, randomAlphabetic(10)),
                        new Suggestion(randomAlphanumeric(10), 10, randomAlphabetic(10))));

        when(suggesterDaoMock.fetchBioentityProperties(anyString(), anyInt(), eq(false), any()))
                .thenReturn(Stream.of(
                        new Suggestion(randomAlphanumeric(10), 10, randomAlphabetic(10)),
                        new Suggestion(randomAlphanumeric(10), 20, randomAlphabetic(10)),
                        new Suggestion(randomAlphanumeric(10), 10, randomAlphabetic(10))));

        when(suggesterDaoMock.fetchBioentityIdentifiers(anyString(), anyInt(), any()))
                .thenReturn(Stream.of(
                        new Suggestion(randomAlphanumeric(10), 10, randomAlphabetic(10)),
                        new Suggestion(randomAlphanumeric(10), 20, randomAlphabetic(10)),
                        new Suggestion(randomAlphanumeric(10), 10, randomAlphabetic(10))));

        subject = new SuggesterService(suggesterDaoMock, speciesFactoryMock);
    }

    @Test
    void mapsSuggestionsToMaps() {
        assertThat(subject.fetchPropertiesWithoutHighlighting(randomAlphanumeric(3), ""))
                .allMatch(mappedSuggestion ->
                        mappedSuggestion.containsKey("term") && mappedSuggestion.containsKey("category"));

        assertThat(subject.fetchPropertiesWithHighlighting(randomAlphanumeric(3), ""))
                .allMatch(mappedSuggestion ->
                        mappedSuggestion.containsKey("term") && mappedSuggestion.containsKey("category"));

        assertThat(subject.fetchIdentifiers(randomAlphanumeric(3), ""))
                .allMatch(mappedSuggestion ->
                        mappedSuggestion.containsKey("term") && mappedSuggestion.containsKey("category"));
    }

    @Test
    void suggestionsWithoutHighlightingAreSentToTheRightSuggester() {
        subject.fetchPropertiesWithoutHighlighting(randomAlphanumeric(3), "");
        verify(suggesterDaoMock).fetchBioentityProperties(anyString(), anyInt(), eq(false), eq(null));
    }

    @Test
    void suggestionsWithHighlightingAreSentToTheRightSuggester() {
        subject.fetchPropertiesWithHighlighting(randomAlphanumeric(3), "");
        verify(suggesterDaoMock).fetchBioentityProperties(anyString(), anyInt(), eq(true), eq(null));
    }

    @Test
    void suggestionsForIdentifiersAreSentToTheRightSuggester() {
        subject.fetchIdentifiers(randomAlphanumeric(3), "");
        verify(suggesterDaoMock).fetchBioentityIdentifiers(anyString(), anyInt(), eq(null));
    }
}
