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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
        subject = new SuggesterService(suggesterDaoMock, speciesFactoryMock);
    }

    @Test
    void mapsSuggestionObjects() {
        when(suggesterDaoMock.fetchBioentityProperties(anyString(), anyInt(), anyBoolean(), any()))
                .thenReturn(Stream.of(
                        new Suggestion("term1", 10, "category1"),
                        new Suggestion("term2", 20, "category2"),
                        new Suggestion("term3", 10, "category3")));

        assertThat(subject.fetchGroupedIdSuggestions("foobar", ""))
                .allMatch(mappedSuggestion ->
                        mappedSuggestion.containsKey("term") && mappedSuggestion.containsKey("category"));
    }
}