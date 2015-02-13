package uk.ac.ebi.atlas.bioentity.go;

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
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class PoTermTraderIT {

    @Inject
    PoTermTrader subject;

    @Test
    public void firstTerm() {
        assertThat(subject.getTermName("PO:0000001"), is("embryo proper"));
    }

    @Test
    public void lastTerm() {
        assertThat(subject.getTermName("PO:0030087"), is("non-vascular leaf initial cell"));
    }

    @Test
    public void termsWithoutDepthColumnHaveDepth1() {
        assertThat(subject.getDepth("PO:0030087"), is(1));
    }
}