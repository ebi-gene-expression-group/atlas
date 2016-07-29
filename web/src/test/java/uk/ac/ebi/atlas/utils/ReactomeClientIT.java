package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by barrera on 28/07/2014.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class ReactomeClientIT {

    @Inject
    ReactomeClient subject;

    @Test
    public void parseReactomeId() {
        assertThat(subject.fetchPathwayNameFailSafe("REACT_1698"), is("Metabolism of nucleotides"));
        assertThat(subject.fetchPathwayNameFailSafe("REACT_1619"), is("Death Receptor Signalling"));
    }
}
