package uk.ac.ebi.atlas.experimentimport.idf;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.atlas.model.Publication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IdfParserOutputTest {

    private IdfParserOutput subject;

    private static final List<Publication> publicationsWithNulls = Arrays.asList(
            new Publication(null, null, "Title 1"),
            new Publication("123", "0/0.1", "Title 2"),
            new Publication("456", "1.1/abc", "Title 3"));

    private static final List<Publication> publicationsWithoutNulls = Arrays.asList(
            new Publication("111", "1/1.aaa", "Title 1"),
            new Publication("123", "0/0.1", "Title 2"),
            new Publication("456", "1.1/abc", "Title 3"));

    @Test
    void testGetters() {
        subject = new IdfParserOutput(
                "Another experiment title",
                "Another experiment description",
                publicationsWithoutNulls,
                3,
                Arrays.asList("field1", "field2"));

        assertThat(subject.getTitle()).isEqualTo("Another experiment title");
        assertThat(subject.getExperimentDescription()).isEqualTo("Another experiment description");
        assertThat(subject.getExpectedClusters()).isEqualTo(3);
        assertThat(subject.getMetadataFieldsOfInterest()).containsExactly("field1", "field2");
        assertThat(subject.getPublications()).containsExactlyElementsOf(publicationsWithoutNulls);
        assertThat(subject.getPubmedIds()).containsOnly("111", "123", "456");
        assertThat(subject.getDois()).containsOnly("1/1.aaa", "0/0.1", "1.1/abc");
    }

    @Test
    void testDoisAndPubmedIdsWithNulls() {
        subject = new IdfParserOutput(
                "Experiment title",
                "Experiment description",
                publicationsWithNulls,
                0,
                Collections.singletonList("field1"));

        assertThat(subject.getPubmedIds()).containsOnly("123", "456");
        assertThat(subject.getDois()).containsOnly("0/0.1", "1.1/abc");
    }
}
