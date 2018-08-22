package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PublicationTest {
    private Publication subject;

    @Before
    public void setUp() {
        subject = new Publication();
    }

    @Test
    public void getPublicationDetails() {
        subject.setAuthors("Patil VS");
        subject.setDoi("10.1126/sciimmunol.aan8664");
        subject.setPubmedId("29352091");
        subject.setTitle(
                "Precursors of human CD4+ cytotoxic T lymphocytes identified by single-cell transcriptome analysis.");
        subject.setPublicationYear("2018");

        assertThat(subject.getDoi(), is(not(emptyString())));
        assertThat(subject.getPubmedId(), is(not(emptyString())));
        assertThat(subject.getTitle(), is(not(emptyString())));
        assertThat(subject.getAuthors(), is(not(emptyString())));
        assertThat(subject.getPublicationYear(), is(not(emptyString())));
    }
    @Test
    public void getAuthorStringWithOneAuthor() {
        subject.setAuthors("Patil VS");

        assertThat(subject.getAuthors(), is("Patil VS"));
        assertThat(subject.getFullAuthorList(), is("Patil VS"));
    }

    @Test
    public void getAuthorStringWithEmptyAuthor() {
        subject.setAuthors("");

        assertThat(subject.getAuthors(), is(""));
        assertThat(subject.getFullAuthorList(), is(""));
    }

    @Test
    public void getAuthorStringWithMultipleAuthors() {
        subject = new Publication();

        subject.setAuthors(
                "Patil VS, Madrigal A, Schmiedel BJ, Clarke J, O'Rourke P, de Silva AD, Harris E, Peters B, " +
                        "Seumois G, Weiskopf D, Sette A, Vijayanand P.");

        assertThat(subject.getAuthors(), is("Patil VS, Madrigal A, Schmiedel BJ, Clarke J, O'Rourke P et al."));
        assertThat(subject.getFullAuthorList(), is("Patil VS, Madrigal A, Schmiedel BJ, Clarke J, O'Rourke P, " +
                "de Silva AD, Harris E, Peters B, Seumois G, Weiskopf D, Sette A, Vijayanand P."));
    }
}
