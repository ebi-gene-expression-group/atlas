package uk.ac.ebi.atlas.search.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
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


    @Test
    public void findsExpressionByGeneIdsInReact71() throws Exception {

        ImmutableList<String> react71geneIds = ImmutableList.of("ENSG00000196652", "ENSG00000082258", "ENSG00000047315", "ENSG00000077312", "ENSG00000198939", "ENSG00000178665", "ENSG00000161547");

        List<RnaSeqBslnExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(react71geneIds);
        assertThat(results, hasSize(124));

        RnaSeqBslnExpression adipose = RnaSeqBslnExpression.create("E-MTAB-513", "g15", 7D);
        RnaSeqBslnExpression adrenalGland = RnaSeqBslnExpression.create("E-MTAB-513", "g16", 10D);
        RnaSeqBslnExpression brain = RnaSeqBslnExpression.create("E-MTAB-513", "g7", 6D);
        RnaSeqBslnExpression breast = RnaSeqBslnExpression.create("E-MTAB-513", "g6", 6D);
        RnaSeqBslnExpression colon = RnaSeqBslnExpression.create("E-MTAB-513", "g12", 8D);
        RnaSeqBslnExpression heart = RnaSeqBslnExpression.create("E-MTAB-513", "g3", 6D);
        RnaSeqBslnExpression kidney = RnaSeqBslnExpression.create("E-MTAB-513", "g11", 4D);
        RnaSeqBslnExpression leukocyte = RnaSeqBslnExpression.create("E-MTAB-513", "g2", 11D);
        RnaSeqBslnExpression liver = RnaSeqBslnExpression.create("E-MTAB-513", "g14", 4D);
        RnaSeqBslnExpression lung = RnaSeqBslnExpression.create("E-MTAB-513", "g9", 6D);
        RnaSeqBslnExpression lymphNode = RnaSeqBslnExpression.create("E-MTAB-513", "g5", 9D);
        RnaSeqBslnExpression ovary = RnaSeqBslnExpression.create("E-MTAB-513", "g4", 9D);
        RnaSeqBslnExpression prostate = RnaSeqBslnExpression.create("E-MTAB-513", "g8", 17D);
        RnaSeqBslnExpression skeletalMuscle = RnaSeqBslnExpression.create("E-MTAB-513", "g1", 11D);
        RnaSeqBslnExpression testis = RnaSeqBslnExpression.create("E-MTAB-513", "g13", 11D);
        RnaSeqBslnExpression thyroid = RnaSeqBslnExpression.create("E-MTAB-513", "g10", 8D);

        assertThat(results, hasItems(adipose, adrenalGland, brain, breast, colon, heart, kidney, leukocyte, liver, lung, lymphNode, ovary, prostate, skeletalMuscle, testis, thyroid));
    }

}
