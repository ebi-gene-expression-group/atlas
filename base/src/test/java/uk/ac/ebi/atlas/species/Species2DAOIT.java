package uk.ac.ebi.atlas.species;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class Species2DAOIT {

    @Inject
    private Species2DAO subject;

    @Test
    public void fetchSpecies() throws Exception {

        assertThat(subject.fetchSpecies("Homo sapiens").canonicalName(), is("homo sapiens"));
    }

}