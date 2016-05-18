
package uk.ac.ebi.atlas.solr.query;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneQueryResponseTest {

    private static final String A_QUERY_TERM = "A QUERY TERM";
    private static final String ANOTHER_TERM = "ANOTHER TERM";

    private GeneQueryResponse subject;
    private final String anotherQueryTerm = ANOTHER_TERM;

    @Before
    public void setUp() throws Exception {
        subject = new GeneQueryResponse();

        subject.addGeneIds(A_QUERY_TERM, Sets.newHashSet("G1", "G2"));
    }

    @Test
    public void addGeneIdsShouldAddTermsThatAreDifferent() throws Exception {
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G6"));
        assertThat(subject.getQueryTerms(), containsInAnyOrder("A QUERY TERM", "ANOTHER TERM"));
    }

    @Test
    public void addGeneIdsShouldNotAddSameTerm() throws Exception {
        subject.addGeneIds(A_QUERY_TERM, Sets.newHashSet("G6"));
    }

    @Test
    public void addGeneIdsShouldNotAddEmptySets() throws Exception {
        subject.addGeneIds(anotherQueryTerm, new HashSet<String>());
        assertThat(subject.getIds(anotherQueryTerm), is(empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addGeneIdsShouldNotAllowForBlankTerms() throws Exception {
        subject.addGeneIds("", Sets.newHashSet("G1", "G2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addGeneIdsShouldNotAllowForNullTerms() throws Exception {
        subject.addGeneIds(null, Sets.newHashSet("G1", "G2"));
    }

    @Test
    public void allGeneIdsShouldContainTheUnionOfGeneIdsFromAllGeneSets() throws Exception {
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G1","G4"));
        assertThat(subject.getAllGeneIds(), containsInAnyOrder("G1", "G2", "G4"));
        assertThat(subject.getAllGeneIds().size(), is(3));
    }

    @Test
    public void testIsEmpty() throws Exception {
        subject = new GeneQueryResponse();
        assertThat(subject.isEmpty(), is(true));
    }

    @Test
    public void testIsNotEmpty() throws Exception {
        assertThat(subject.isEmpty(), is(false));
    }

    @Test
    public void testGetQueryTerms() throws Exception {
        assertThat(subject.getQueryTerms(), containsInAnyOrder(A_QUERY_TERM));
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G1","G4"));
        assertThat(subject.getQueryTerms(), containsInAnyOrder(A_QUERY_TERM, ANOTHER_TERM));
    }

    @Test
    public void containsEntryShouldReturnTrue() throws Exception {
        assertThat(subject.containsEntry(A_QUERY_TERM, "G1"), is(true));
        assertThat(subject.containsEntry(A_QUERY_TERM, "G2"), is(true));
    }

    @Test
    public void containsEntryShouldReturnFalse() throws Exception {
        assertThat(subject.containsEntry(A_QUERY_TERM, "G3"), is(false));
        assertThat(subject.containsEntry(ANOTHER_TERM, "G1"), is(false));
        assertThat(subject.containsEntry(ANOTHER_TERM, "G3"), is(false));
    }

    @Test
    public void relatedTermsShouldDoReverseMapping() throws Exception {
        assertThat(subject.getRelatedQueryTerms("G2"), contains(A_QUERY_TERM));
        assertThat(subject.getRelatedQueryTerms("G3"), is(empty()));
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G1","G4"));
        assertThat(subject.getRelatedQueryTerms("G1"), containsInAnyOrder(A_QUERY_TERM, ANOTHER_TERM));
        assertThat(subject.getRelatedQueryTerms("G2"), contains(A_QUERY_TERM));
        assertThat(subject.getRelatedQueryTerms("G3"), is(empty()));
        assertThat(subject.getRelatedQueryTerms("G4"), contains(ANOTHER_TERM));
    }
}
