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
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})

public class BaselineExpressionDaoIT {

    @Inject
    private BaselineExpressionDao subject;

    @Test
    public void doesNotFindNonExistentGenes() throws Exception {
        List<BaselineExperimentExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(ImmutableList.of("DOES_NOT_EXIST"));
        assertThat(results, is(empty()));
    }


    @Test
    public void findsExpressionByGeneIdsInRHSA74160() throws Exception {

        ImmutableList<String> RHSA74160GeneIds = ImmutableList.of("ENSG00000196652", "ENSG00000082258", "ENSG00000047315", "ENSG00000077312", "ENSG00000198939", "ENSG00000178665", "ENSG00000161547");

        List<BaselineExperimentExpression> results = subject.fetchAverageExpressionByExperimentAssayGroup(RHSA74160GeneIds);
        assertThat(results.size(), greaterThan(100));
    }



}
