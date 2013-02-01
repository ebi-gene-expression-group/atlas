package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileTest {

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    private static String GENE_ID = "geneId_1";
    private static String GENE_NAME = "geneName_1";

    private Factor factor1 = new Factor("ORGANISM_PART", "org", "nose");
    private Factor factor2 = new Factor("ORGANISM_PART", "org", "trunk");
    private Factor factor3 = new Factor("ORGANISM_PART", "org", "head");

    private Expression expression_1 = new Expression(2.2D, Sets.newHashSet(factor1));
    private Expression expression_2 = new Expression(3D, Sets.newHashSet(factor2));
    private Expression expression_3 = new Expression(3.001D, Sets.newHashSet(factor3));

    private GeneProfile subject;

    @Before
    public void setUp() throws Exception {
        when(geneNamesProviderMock.getGeneName(GENE_ID)).thenReturn(GENE_NAME);


        GeneProfile.Builder geneProfileBuilder = new GeneProfile.Builder().forGeneId(GENE_ID);

        GeneExpressionPrecondition geneExpressionPreconditionMock = mock(GeneExpressionPrecondition.class);
        when(geneExpressionPreconditionMock.apply(any(Expression.class))).thenReturn(true);
        when(geneExpressionPreconditionMock.getQueryFactorType()).thenReturn("ORGANISM_PART");

        geneProfileBuilder.setGeneExpressionPrecondition(geneExpressionPreconditionMock);

        subject = geneProfileBuilder
                .addExpression(expression_1)
                .addExpression(expression_2)
                .addExpression(expression_3)
                .create();

        subject.setGeneNamesProvider(geneNamesProviderMock);
    }

    @Test
    public void testGetGeneSpecificity() throws Exception {
        assertThat(subject.getSpecificity(), is(3));
    }

    @Test
    public void getAllFactorValuesTest() {
        assertThat(subject.getAllFactors(), hasItems(factor1, factor2, factor3));
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
                new Factor("ORGANISM_PART", "org", "panz")));
        assertThat(averageExpressionLevel, is(1.733666666666667D));
    }

    @Test
    public void weightedExpressionLevelOn() {
        double averageExpressionLevel = (double) subject.getWeightedExpressionLevelOn(Sets.newHashSet(factor1,
                factor3),
                Sets.newHashSet(factor1, factor3, factor2));
        assertThat(averageExpressionLevel, is(-0.39949999999999974D));

        averageExpressionLevel = (double) subject.getWeightedExpressionLevelOn(Sets.newHashSet(factor1),
                Sets.newHashSet(factor1, factor3, factor2));
        assertThat(averageExpressionLevel, is(-0.8004999999999995D));
    }


}

