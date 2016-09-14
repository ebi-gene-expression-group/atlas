package uk.ac.ebi.atlas.experimentimport.efo;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentimport.efo.EFOLookupService;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class EFOLookupServiceIT {

    private static final String BTO_0002690 = "BTO_0002690";
    private static final String GO_0023014 = "GO_0023014";

    @Inject
    private EFOLookupService subject;


    @Test
    public void allParents() {
        assertThat(subject.getAllParents(ImmutableSet.of(BTO_0002690)).size(), is(2));
    }

    @Test
    public void onlyIsARelationsAreIncluded() {
        assertThat(subject.getAllParents(ImmutableSet.of(GO_0023014)).size(), is(15));
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
        assertThat(subject.getAllParents(ImmutableSet.of("Blah")).isEmpty(), is(true));
    }

}