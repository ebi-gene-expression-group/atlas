package uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.atlas.configuration.TestConfig;
import uk.ac.ebi.atlas.experimentimport.analyticsindex.conditions.EfoLookupService;

import javax.inject.Inject;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class EfoLookupServiceIT {
    private static final String BTO_0002690 = "BTO_0002690";
    private static final String GO_0023014 = "GO_0023014";

    private static final int BTO_0002690_PARENTS = 2;
    private static final int GO_0023014_PARENTS = 15;
    private static final int COMMON_PARENTS = 1;

    @Inject
    private EfoLookupService subject;

    @Test
    public void allParents() {
        assertThat(subject.getAllParents(ImmutableSet.of(BTO_0002690)).size(), is(BTO_0002690_PARENTS));
    }

    @Test
    public void onlyIsARelationsAreIncluded() {
        assertThat(subject.getAllParents(ImmutableSet.of(GO_0023014)).size(), is(GO_0023014_PARENTS));
    }

    @Test
    public void parentNodesAreUnique() {
        Set<String> efoNodeIds = ImmutableSet.of(GO_0023014, BTO_0002690);
        assertThat(
                subject.getAllParents(efoNodeIds).size(),
                is(BTO_0002690_PARENTS + GO_0023014_PARENTS - COMMON_PARENTS));
    }

    @Test
    public void nonExistentIdsHaveNoParents() {
        assertThat(subject.getAllParents(ImmutableSet.of("Blah")).isEmpty(), is(true));
    }
}
