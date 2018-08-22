package uk.ac.ebi.atlas.solr.bioentities.query;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

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
    public void addGeneIdsShouldAddTermsThatAreDifferent() {
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G6"));
        assertThat(subject.getQueryTerms(), containsInAnyOrder("A QUERY TERM", "ANOTHER TERM"));
    }

    @Test
    public void addGeneIdsShouldNotAddSameTerm() {
        subject.addGeneIds(A_QUERY_TERM, Sets.newHashSet("G6"));
    }

    @Test
    public void addGeneIdsShouldNotAddEmptySets() {
        subject.addGeneIds(anotherQueryTerm, new HashSet<String>());
        assertThat(subject.getIds(anotherQueryTerm), is(empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addGeneIdsShouldNotAllowForBlankTerms() {
        subject.addGeneIds("", Sets.newHashSet("G1", "G2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addGeneIdsShouldNotAllowForNullTerms() {
        subject.addGeneIds(null, Sets.newHashSet("G1", "G2"));
    }

    @Test
    public void allGeneIdsShouldContainTheUnionOfGeneIdsFromAllGeneSets() {
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G1", "G4"));
        assertThat(subject.getAllGeneIds(), containsInAnyOrder("G1", "G2", "G4"));
        assertThat(subject.getAllGeneIds().size(), is(3));
    }

    @Test
    public void testIsEmpty() {
        subject = new GeneQueryResponse();
        assertThat(subject.isEmpty(), is(true));
    }

    @Test
    public void testIsNotEmpty() {
        assertThat(subject.isEmpty(), is(false));
    }

    @Test
    public void testGetQueryTerms() {
        assertThat(subject.getQueryTerms(), containsInAnyOrder(A_QUERY_TERM));
        subject.addGeneIds(anotherQueryTerm, Sets.newHashSet("G1", "G4"));
        assertThat(subject.getQueryTerms(), containsInAnyOrder(A_QUERY_TERM, ANOTHER_TERM));
    }

    @Test
    public void containsEntryShouldReturnTrue() {
        assertThat(subject.containsEntry(A_QUERY_TERM, "G1"), is(true));
        assertThat(subject.containsEntry(A_QUERY_TERM, "G2"), is(true));
    }

    @Test
    public void containsEntryShouldReturnFalse() {
        assertThat(subject.containsEntry(A_QUERY_TERM, "G3"), is(false));
        assertThat(subject.containsEntry(ANOTHER_TERM, "G1"), is(false));
        assertThat(subject.containsEntry(ANOTHER_TERM, "G3"), is(false));
    }

}
