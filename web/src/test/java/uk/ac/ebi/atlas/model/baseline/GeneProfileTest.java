//ToDo: this test is too complex. It's not really unit test on GeneProfile because it tests all the chain of builder , preconditions , etc....

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;
import uk.ac.ebi.atlas.model.baseline.Expression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneProfile;
import uk.ac.ebi.atlas.model.impl.FactorSet;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileTest {

    private static final String QUERY_FACTOR_TYPE = "ORGANISM_PART";
    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    private static String GENE_ID = "geneId_1";
    private static String GENE_NAME = "geneName_1";

    private Factor factor1 = new Factor(QUERY_FACTOR_TYPE, "nose");
    private Factor factor2 = new Factor(QUERY_FACTOR_TYPE, "trunk");
    private Factor factor3 = new Factor(QUERY_FACTOR_TYPE, "head");

    private Expression expression_1 = new Expression(2.2D, new FactorSet().add(factor1));
    private Expression expression_2 = new Expression(3D, new FactorSet().add(factor2));
    private Expression expression_3 = new Expression(3.001D, new FactorSet().add(factor3));

    private GeneProfile subject;

    private double cutoff  = 0.5;
    //private GeneProfile.Builder geneProfileBuilder;

    @Before
    public void setUp() throws Exception {
        when(geneNamesProviderMock.getGeneName(GENE_ID)).thenReturn(GENE_NAME);

        subject = new GeneProfile();

        subject.add(expression_1,QUERY_FACTOR_TYPE).add(expression_2,QUERY_FACTOR_TYPE).add(expression_3,QUERY_FACTOR_TYPE);

        //geneProfileBuilder = new GeneProfile.Builder().forGeneId(GENE_ID);

        //GeneExpressionPrecondition geneExpressionPreconditionMock = mock(GeneExpressionPrecondition.class);
        //when(geneExpressionPreconditionMock.apply(any(Expression.class))).thenReturn(true);

        //geneProfileBuilder.setGeneExpressionPrecondition(geneExpressionPreconditionMock);
/*
        subject = geneProfileBuilder
                .addExpression(expression_1)
                .addExpression(expression_2)
                .addExpression(expression_3)
                .create();
*/
        subject.setGeneNamesProvider(geneNamesProviderMock);
    }

    @Test
    public void testGetGeneSpecificity() throws Exception {
        assertThat(subject.getSpecificity(), is(3));
    }

    @Test
    public void getAllFactorValuesTest() {
        assertThat(subject.getAllFactors(), containsInAnyOrder(factor1, factor2, factor3));
    }

    @Test
    public void averageExpressionLevelOnNullCollection() {
        assertThat(subject.getAverageExpressionLevelOn(null), is(0D));
    }

    @Test
    public void averageExpressionLevelOnEmptyCollection() {
        assertThat(subject.getAverageExpressionLevelOn(new HashSet<Factor>()), is(0D));
    }

    @Test
    public void averageExpressionLevel() {
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(factor1, factor3));
        assertThat(averageExpressionLevel, is(2.6005000000000003D));

        averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(factor1, factor3,
                new Factor(QUERY_FACTOR_TYPE, "leg")));
        assertThat(averageExpressionLevel, is(1.733666666666667D));
    }

}