package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-applicationContext.xml", "/test-solrContext.xml", "/test-oracleContext.xml"})
public class ReactomeClientEIT {

    @Inject
    ReactomeClient subject;

    @Test
    public void parseReactomeId() {
        assertThat(subject.fetchPathwayNameFailSafe("REACT_1698"), is("Metabolism of nucleotides"));
        assertThat(subject.fetchPathwayNameFailSafe("REACT_1619"), is("Death Receptor Signalling"));
    }
}
