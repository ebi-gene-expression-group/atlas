package uk.ac.ebi.atlas.experimentpage.baseline.grouping;

import com.google.common.collect.ImmutableList;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.OntologyTerm;

import javax.inject.Inject;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "/dispatcher-servlet.xml"})
public class OrganismPartGroupingServiceIT {

    @Inject
    OrganismPartGroupingService subject;

    @Test
    public void testGetAnatomicalSystems() {
        assertThat(subject.getAnatomicalSystemsGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000006"))).keySet(),
                Matchers.contains(ColumnGroup.create
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
        assertThat(        subject.getAnatomicalSystemsGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0022292"))).size(), Matchers.greaterThan(4));
    }

    @Test
    public void nonexistentIdsAreInNoTissues() {
        assertThat(subject.getAnatomicalSystemsGrouping(ImmutableList.of(OntologyTerm.create("UBERON_1234567"))).entrySet(), empty());
    }

    @Test
    public void gotYourNose(){
        assertThat(subject.getAnatomicalSystemsGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000004","nose"))).entrySet(), not(empty()));
        assertThat(subject.getOrgansGrouping(ImmutableList.of(OntologyTerm.create("UBERON_0000004","nose"))).entrySet(), not(empty()));
    }
}