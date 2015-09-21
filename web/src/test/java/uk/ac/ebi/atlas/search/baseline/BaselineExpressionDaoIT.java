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

public class BaselineExpressionDaoIT {

    @Inject
    private BaselineExpressionDao subject;

    @Test
    public void findsExpressionByGeneId() throws Exception {
        List<BaselineExperimentExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(ImmutableList.of("ENSMUSG00000093014"));
        assertThat(results, hasSize(1));

    }

    @Test
    public void doesNotFindNonExistentGenes() throws Exception {
        List<BaselineExperimentExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(ImmutableList.of("DOES_NOT_EXIST"));
        assertThat(results, is(empty()));
    }


    @Test
    public void findsExpressionByGeneIdsInReact71() throws Exception {

        ImmutableList<String> react71geneIds = ImmutableList.of("ENSG00000196652", "ENSG00000082258", "ENSG00000047315", "ENSG00000077312", "ENSG00000198939", "ENSG00000178665", "ENSG00000161547");

        List<BaselineExperimentExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(react71geneIds);
        assertThat(results, hasSize(49));

        BaselineExperimentExpression adipose = BaselineExperimentExpression.create("E-MTAB-513", "g15", 7D);
        BaselineExperimentExpression adrenalGland = BaselineExperimentExpression.create("E-MTAB-513", "g16", 10D);
        BaselineExperimentExpression brain = BaselineExperimentExpression.create("E-MTAB-513", "g7", 6D);
        BaselineExperimentExpression breast = BaselineExperimentExpression.create("E-MTAB-513", "g6", 6D);
        BaselineExperimentExpression colon = BaselineExperimentExpression.create("E-MTAB-513", "g12", 8D);
        BaselineExperimentExpression heart = BaselineExperimentExpression.create("E-MTAB-513", "g3", 6D);
        BaselineExperimentExpression kidney = BaselineExperimentExpression.create("E-MTAB-513", "g11", 4D);
        BaselineExperimentExpression leukocyte = BaselineExperimentExpression.create("E-MTAB-513", "g2", 11D);
        BaselineExperimentExpression liver = BaselineExperimentExpression.create("E-MTAB-513", "g14", 4D);
        BaselineExperimentExpression lung = BaselineExperimentExpression.create("E-MTAB-513", "g9", 6D);
        BaselineExperimentExpression lymphNode = BaselineExperimentExpression.create("E-MTAB-513", "g5", 9D);
        BaselineExperimentExpression ovary = BaselineExperimentExpression.create("E-MTAB-513", "g4", 9D);
        BaselineExperimentExpression prostate = BaselineExperimentExpression.create("E-MTAB-513", "g8", 17D);
        BaselineExperimentExpression skeletalMuscle = BaselineExperimentExpression.create("E-MTAB-513", "g1", 11D);
        BaselineExperimentExpression testis = BaselineExperimentExpression.create("E-MTAB-513", "g13", 11D);
        BaselineExperimentExpression thyroid = BaselineExperimentExpression.create("E-MTAB-513", "g10", 8D);

        assertThat(results, hasItems(adipose, adrenalGland, brain, breast, colon, heart, kidney, leukocyte, liver, lung, lymphNode, ovary, prostate, skeletalMuscle, testis, thyroid));
    }

}
