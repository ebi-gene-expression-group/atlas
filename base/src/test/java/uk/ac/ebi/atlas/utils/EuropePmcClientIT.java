package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.Publication;

import javax.inject.Inject;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EuropePmcClientIT {

    @Inject
    private EuropePmcClient subject;

    @Test
    public void publicationForValidDoi() {
        Optional<Publication> result = subject.getPublicationByDoi("10.1126/sciimmunol.aan8664");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getDoi(), is("10.1126/sciimmunol.aan8664"));
        assertThat(result.get().getAuthors(), not(isEmptyString()));
        assertThat(result.get().getTitle(), not(isEmptyString()));
    }

    @Test
    public void publicationForValidPubmedId() {
        Optional<Publication> result = subject.getPublicationByPubmedId("29352091");

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getPubmedId(), is("29352091"));
        assertThat(result.get().getAuthors(), not(isEmptyString()));
        assertThat(result.get().getTitle(), not(isEmptyString()));
    }

    @Test
    public void noResultForEmptyDoi() {
        Optional<Publication> result = subject.getPublicationByDoi("");

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void noResultForEmptyPubmedId() {
        Optional<Publication> result = subject.getPublicationByPubmedId("");

        assertThat(result.isPresent(), is(false));
    }

}
