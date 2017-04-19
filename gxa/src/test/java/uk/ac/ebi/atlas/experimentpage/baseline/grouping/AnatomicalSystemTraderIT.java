package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:dbContext.xml"})
public class AnatomicalSystemTraderIT {

    @Inject
    AnatomicalSystemTrader subject;

    @Test
    public void testGetAnatomicalSystems() {
        assertThat(subject.getAnatomicalSystemsIncluding("UBERON_0000006"),
                Matchers.contains(AnatomicalSystem.create
                ("UBERON_0000949","endocrine system" )));

    }

    /*
    original distribution (count / number of overlapping systems)
    7777 1
    1610 3
    1560 2
    260 4
    99 6
    98 5
    12 7
    2 8
     */
    @Test
    public void someIdsAreInMultipleTissues() {
        assertThat(subject.getAnatomicalSystemsIncluding("UBERON_0022292").size(), Matchers.greaterThan(4));
    }

    @Test
    public void nonexistentIdsAreInNoTissues() {
        assertThat(subject.getAnatomicalSystemsIncluding("UBERON_1234567"), Matchers.empty());
    }
}