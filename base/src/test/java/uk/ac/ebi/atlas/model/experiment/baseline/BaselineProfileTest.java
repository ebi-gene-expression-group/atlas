
//ToDo: this test is too complex. It's not really unit test on GeneProfile because it tests all the chain of builder , preconditions , etc....

package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileTest {

    private static final String QUERY_FACTOR_TYPE = "ORGANISM_PART";

    private static final String GENE_ID = "geneId_1";
    private static final String GENE_NAME = "geneName_1";

    private Factor factor1 = new Factor(QUERY_FACTOR_TYPE, "nose");
    private Factor factor2 = new Factor(QUERY_FACTOR_TYPE, "trunk");
    private Factor factor3 = new Factor(QUERY_FACTOR_TYPE, "head");
    private Factor factor4 = new Factor(QUERY_FACTOR_TYPE, "billabong");

    private BaselineExpression expression_1 = new BaselineExpression(2.2D, new FactorSet().add(factor1));
    private BaselineExpression expression_2 = new BaselineExpression(3D, new FactorSet().add(factor2));
    private BaselineExpression expression_3 = new BaselineExpression(3.001D, new FactorSet().add(factor3));

    private OldBaselineProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new OldBaselineProfile(GENE_ID, GENE_NAME);
        subject.add(QUERY_FACTOR_TYPE, expression_1).add(QUERY_FACTOR_TYPE, expression_2).add(QUERY_FACTOR_TYPE, expression_3);
    }

    @Test
    public void testGetGeneSpecificity() throws Exception {
        assertThat(subject.getSpecificity(), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenQueryFactorsIsEmpty() {
        subject.getAverageExpressionLevelOn(new HashSet<Factor>());
    }

    @Test
    public void averageExpressionLevelOnEmptyCollection() {
        //given
        Set<Factor> queryFactors = Sets.newHashSet(mock(Factor.class));
        //when
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(queryFactors);
        //then
        assertThat(averageExpressionLevel, is(0D));
    }

    @Test
    public void averageExpressionLevel() {
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(factor1, factor3));
        assertThat(averageExpressionLevel, is(2.6005000000000003D));

        averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(factor1, factor3,
                new Factor(QUERY_FACTOR_TYPE, "leg")));
        assertThat(averageExpressionLevel, is(1.733666666666667D));
    }

    @Test
    public void testSumProfile(){
        subject.sumProfile(buildOtherProfile());
        assertThat(subject.getId(), is(subject.getId()));
        assertThat(subject.getExpressionLevel(factor1), is(2.2D + 1D));
        assertThat(subject.getExpressionLevel(factor2), is(3D + 2D));
        assertThat(subject.getExpressionLevel(factor3), is(3.001D + 3D));
        assertThat(subject.getExpressionLevel(factor4), is(300D));
    }

    @Test
    public void sumProfileShouldPreserveLevelsThatAreNotExpressedInOtherProfile(){
        OldBaselineProfile otherProfile = new OldBaselineProfile("other profile", "other name").add(QUERY_FACTOR_TYPE, expression_2);

        subject.sumProfile(otherProfile);
        assertThat(subject.getId(), is(GENE_ID));
        assertThat(subject.getExpressionLevel(factor1), is(subject.getExpressionLevel(factor1)));
        assertThat(subject.getExpressionLevel(factor2), is(6D));
        assertThat(subject.getExpressionLevel(factor3), is(subject.getExpressionLevel(factor3)));
        assertThat(subject.getExpressionLevel(factor4), is(nullValue()));
    }

    @Test
    public void nonTissueExpressionsDoNotAffectMinOrMax() {
        assertThat(subject.getMinExpressionLevel(), is(2.2));
        assertThat(subject.getMaxExpressionLevel(), is(3.001));

        subject.add(QUERY_FACTOR_TYPE, new BaselineExpression("NT", new FactorSet().add(factor1)));

        assertThat(subject.getMinExpressionLevel(), is(2.2));
        assertThat(subject.getMaxExpressionLevel(), is(3.001));
    }

    @Test
    public void testFold(){
        OldBaselineProfile sumProfile = subject.foldProfile(3);
        assertThat(sumProfile.getId(), is(subject.getId()));
        assertThat(sumProfile.getExpressionLevel(factor1), is(0.7D));
        assertThat(sumProfile.getExpressionLevel(factor2), is(1.0D));
        assertThat(sumProfile.getExpressionLevel(factor3), is(1.0D));
        assertThat(sumProfile.getExpressionLevel(factor4), is(nullValue()));
    }


    OldBaselineProfile buildOtherProfile(){
        BaselineExpression expression_1 = new BaselineExpression(1D, new FactorSet().add(factor1));
        BaselineExpression expression_2 = new BaselineExpression(2D, new FactorSet().add(factor2));
        BaselineExpression expression_3 = new BaselineExpression(3D, new FactorSet().add(factor3));
        BaselineExpression expression_4 = new BaselineExpression(300D, new FactorSet().add(factor4));

        OldBaselineProfile baselineProfile = new OldBaselineProfile("OTHER_ID", "OTHER_NAME");

        return baselineProfile.add(QUERY_FACTOR_TYPE, expression_1)
                              .add(QUERY_FACTOR_TYPE, expression_2)
                              .add(QUERY_FACTOR_TYPE, expression_3)
                              .add(QUERY_FACTOR_TYPE, expression_4);
    }

}