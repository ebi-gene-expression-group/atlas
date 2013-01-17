package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import java.util.HashSet;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileTest {

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    private static String GENE_ID = "geneId_1";
    private static String GENE_NAME = "geneName_1";

    private FactorValue factorValue1 = new FactorValue("ORGANISM_PART", "org", "nose");
    private FactorValue factorValue2 = new FactorValue("ORGANISM_PART", "org", "trunk");
    private FactorValue factorValue3 = new FactorValue("ORGANISM_PART", "org", "head");

    private Expression expression_1 = new Expression(factorValue1, 2.2D, Sets.newHashSet(factorValue1));
    private Expression expression_2 = new Expression(factorValue2, 3D, Sets.newHashSet(factorValue2));
    private Expression expression_3 = new Expression(factorValue3, 3.001D, Sets.newHashSet(factorValue3));

    private GeneProfile subject;

    @Before
    public void setUp() throws Exception {
        when(geneNamesProviderMock.getGeneName(GENE_ID)).thenReturn(GENE_NAME);

        subject = new GeneProfileBuilderConcreteFactory()
                .with(GENE_ID, 0)
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
    public void iteratorReturnsExpressionsOrderedByOrganismPartNameAndThenNull() throws Exception {
        //given
        Iterator<Expression> profileIterator = subject.iterator();

        //then
        assertThat(profileIterator.next().getLevel(), is(3.001D));
        assertThat(profileIterator.next().getLevel(), is(2.2D));
        assertThat(profileIterator.next().getLevel(), is(3D));
        //and
        assertThat(profileIterator.hasNext(), is(false));
    }

    @Test
    public void builderAddExpressionTest() {
        //given
        GeneProfile.Builder builder = new GeneProfileBuilderConcreteFactory()
                .with(GENE_ID, 3D);

        builder.addExpression(expression_1);

        builder.addExpression(expression_2);

        builder.addExpression(expression_3);

        //when
        Iterator<Expression> profileIterator = builder.create().iterator();

        //then
        assertThat(profileIterator.next().getLevel(), is(3.001D));
        //and
        assertThat(profileIterator.hasNext(), is(false));

    }


    @Test
    public void getAllFactorValuesTest() {
        assertThat(subject.getAllFactorValues(), hasItems(factorValue1, factorValue2, factorValue3));
    }

    @Test
    public void averageExpressionLevelOnNullCollection() {
        assertThat(subject.getAverageExpressionLevelOn(null), is(0D));
    }

    @Test
    public void averageExpressionLevelOnEmptyCollection() {
        assertThat(subject.getAverageExpressionLevelOn(new HashSet<String>()), is(0D));
    }

    @Test
    public void averageExpressionLevel() {
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet("nose", "head"));
        assertThat(averageExpressionLevel, is(2.6005000000000003D));

        averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet("nose", "head", "panz"));
        assertThat(averageExpressionLevel, is(1.733666666666667D));
    }

     @Test
    public void weightedExpressionLevelOn() {
        double averageExpressionLevel = (double) subject.getWeightedExpressionLevelOn(Sets.newHashSet("nose", "head"),
                Sets.newHashSet("nose", "head", "trunk"));
        assertThat(averageExpressionLevel, is(-0.39949999999999974D));

        averageExpressionLevel = (double) subject.getWeightedExpressionLevelOn(Sets.newHashSet("nose"), Sets.newHashSet("nose", "head", "trunk"));
        assertThat(averageExpressionLevel, is(-0.8004999999999995D));
    }




}

