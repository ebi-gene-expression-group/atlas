package uk.ac.ebi.atlas.bioentity.interpro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml", "classpath:oracleUcpContext.xml"})
public class InterProTermTraderIT {

    @Inject
    InterProTermTrader subject;

    @Test
    public void firstTerm() {
        assertThat(subject.getTerm("IPR000001"), is("Kringle (domain)"));
    }

    @Test
    public void lastTerm() {
        assertThat(subject.getTerm("IPR029511"), is("Dehydrogenase/reductase SDR member 4-like (family)"));
    }

}