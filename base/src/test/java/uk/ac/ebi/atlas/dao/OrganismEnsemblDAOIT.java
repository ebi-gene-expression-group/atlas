package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/embeddedSolrServerContext.xml", "/oracleContext.xml"})
public class OrganismEnsemblDAOIT {

    @Inject
    JdbcTemplate jdbcTemplate;

    @Test
    public void getOrganismEnsemblNamesMap() throws Exception {
        Map<String, String> organismEnsemblNames = new OrganismKingdomDAO(jdbcTemplate).getOrganismKingdomMap();

        assertThat(organismEnsemblNames.size(), is(49));

        assertThat(organismEnsemblNames.get("homo sapiens"), is("animals"));
        assertThat(organismEnsemblNames.get("mus musculus"), is("animals"));
        assertThat(organismEnsemblNames.get("arabidopsis thaliana"), is("plants"));

    }

}
