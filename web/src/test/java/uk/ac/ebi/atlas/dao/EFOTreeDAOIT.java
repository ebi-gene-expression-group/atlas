package uk.ac.ebi.atlas.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class EFOTreeDAOIT {

    @Inject
    private EFOTreeDAO subject;

    private static final String BTO_0002690 = "BTO_0002690";
    private static final String GO_0023014 = "GO_0023014";

    private static final String BRAIN = "brain";
    private static final String UBERON_0000955 = "UBERON_0000955";

    private static final String EFO_0000311 = "EFO_0000311"; // cancer
    private static final String EFO_0001639 = "EFO_0001639"; // cancer cell line
    private static final String EFO_0005127 = "EFO_0005127"; // cancer biomarker measurement
    private static final String EFO_0000312 = "EFO_0000312"; // cancer site
    private static final String EFO_0000196 = "EFO_0000196"; // metastatic prostate cancer


    @Test
    public void allParents() {
        assertThat(subject.getAllParents(BTO_0002690).size(), is(2));
    }

    @Test
    public void onlyIsARelationsAreIncluded() {
        assertThat(subject.getAllParents(GO_0023014).size(), is(15));
    }

    @Test
    public void parentNodesAreUnique() {
        Set<String> efoNodeIds = new HashSet();
        efoNodeIds.add(GO_0023014);
        efoNodeIds.add(BTO_0002690);

        assertThat(subject.getAllParents(efoNodeIds).size(), is(16));
    }

    @Test
    public void nonExistentIdsHaveNoParents() {
        assertThat(subject.getAllParents("Blah").isEmpty(), is(true));
    }

    @Test
    public void getIdsFromTerm() {
        System.out.println(subject.getIdsFromTerm("cancer").size());
        assertThat(subject.getIdsFromTerm("cancer").size(), is(34));
    }
}