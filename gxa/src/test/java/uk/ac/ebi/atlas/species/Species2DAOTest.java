package uk.ac.ebi.atlas.species;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import javax.inject.Named;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Named
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class Species2DAOTest {

    @Inject
    private Species2DAO subject;

    @Test
    public void fetchSpecies() throws Exception {

        assertThat(subject.fetchSpecies("Homo sapiens").canonicalName(), is("homo sapiens"));
    }




}