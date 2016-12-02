package uk.ac.ebi.atlas.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/applicationContext.xml", "/solrContext.xml", "/oracleContext.xml"})
public class DBSolrStatusControllerIT {

    @Inject
    private DBSolrStatusController subject;

    @Test
    public void dbAndSolrStatus() throws Exception {
        ModelAndView mav = subject.dbAndSolrStatus();
        assertThat((String) mav.getModel().get("DB"), is("UP"));
        assertThat((String) mav.getModel().get("Solr"), is("UP"));
    }
}