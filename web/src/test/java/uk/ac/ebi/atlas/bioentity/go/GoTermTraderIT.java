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
public class GoTermTraderIT {

    @Inject
    GoTermTrader subject;

    @Test
    public void firstTerm() {
        assertThat(subject.getTermName("GO:0000001"), is("mitochondrion inheritance"));
    }

    @Test
    public void lastTerm() {
        assertThat(subject.getTermName("GO:2001317"), is("kojic acid biosynthetic process"));
    }

    @Test
    public void topLevelTermsDoNotHaveUnderscore() {
        assertThat(subject.getTermName("GO:0003674"), is("molecular function"));
    }

    @Test
    public void repeatedTermHasMinimumDepth() {
        assertThat(subject.getDepth("GO:0000022"), is(6));
    }
}