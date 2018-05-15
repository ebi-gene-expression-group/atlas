package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.SendingContext.RunTime;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.model.Publication;

import javax.inject.Inject;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EuropePmcClientIT {

    @Inject
    private EuropePmcClient subject;

    @Test
    public void publicationForValidDoi() {
        Optional<Publication> result = subject.getPublicationByIdentifier("10.1126/sciimmunol.aan8664");

        assertThat(result.isPresent()).isTrue();

        assertThat(result.orElseThrow(RuntimeException::new))
                .extracting("doi", "authors", "title")
                .isNotEmpty();

        assertThat(result.orElseThrow(RuntimeException::new).getDoi()).isEqualToIgnoringCase("10.1126/sciimmunol.aan8664");
    }

    @Test
    public void publicationForValidPubmedId() {
        Optional<Publication> result = subject.getPublicationByIdentifier("29352091");

        assertThat(result.isPresent()).isTrue();

        assertThat(result.orElseThrow(RuntimeException::new))
                .extracting("pubmedId", "authors", "title")
                .isNotEmpty();

        assertThat(result.orElseThrow(RuntimeException::new).getPubmedId()).isEqualToIgnoringCase("29352091");
    }

    @Test
    public void noResultForEmptyIdentifier() {
        Optional<Publication> result = subject.getPublicationByIdentifier("");

        assertThat(result.isPresent()).isFalse();
    }

}
