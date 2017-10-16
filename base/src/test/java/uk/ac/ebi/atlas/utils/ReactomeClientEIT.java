package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ReactomeClientEIT {

    @Inject
    private ReactomeClient subject;

    @Test
    public void parseReactomeId() throws Exception {
        assertThat(subject.fetchPathwayNameFailSafe("R-HSA-15869"), is(Optional.of("Metabolism of nucleotides")));
        assertThat(subject.fetchPathwayNameFailSafe("R-HSA-73887"), is(Optional.of("Death Receptor Signalling")));
    }

    @Test
    public void unmatchedIdReturnsNull() throws Exception {
        assertThat(subject.fetchPathwayNameFailSafe("foobar"), is(Optional.empty()));
    }
}
