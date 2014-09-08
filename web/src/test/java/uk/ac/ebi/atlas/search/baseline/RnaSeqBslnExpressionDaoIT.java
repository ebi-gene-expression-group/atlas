package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})

public class RnaSeqBslnExpressionDaoIT {

    @Inject
    private RnaSeqBslnExpressionDao subject;

    @Test
    public void findsExpressionByGeneId() throws Exception {
        List<RnaSeqBslnExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(ImmutableList.of("ENSMUSG00000093014"));
        assertThat(results, hasSize(3));


        RnaSeqBslnExpression g3 = RnaSeqBslnExpression.create("E-MTAB-599", "g3", 54922);
        RnaSeqBslnExpression g5 = RnaSeqBslnExpression.create("E-MTAB-599", "g5", 48948);
        RnaSeqBslnExpression g6 = RnaSeqBslnExpression.create("E-MTAB-599", "g6", 387123);

        assertThat(results, containsInAnyOrder(g3, g5, g6));
    }

    @Test
    public void doesNotFindNonExistentGenes() throws Exception {
        List<RnaSeqBslnExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(ImmutableList.of("DOES_NOT_EXIST"));
        assertThat(results, is(empty()));
    }

}
