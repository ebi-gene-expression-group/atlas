package uk.ac.ebi.atlas.model;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.geneannotation.GeneNamesProvider;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfileTest {

    @Mock
    private GeneNamesProvider geneNamesProviderMock;

    private static String GENE_ID = "geneId_1";
    private static String GENE_NAME = "geneName_1";

    private Expression expression_1 = new Expression(new ExperimentRun("RUN_ACCESSION_1").addOrganismPartFactorValue("nose"), 2.2D);
    private Expression expression_2 = new Expression(new ExperimentRun("RUN_ACCESSION_2").addOrganismPartFactorValue("trunk"), 3D);
    private Expression expression_3 = new Expression(new ExperimentRun("RUN_ACCESSION_3").addOrganismPartFactorValue("head"), 3.001D);

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
    public void isExpressedAtMostOnTest(){
        assertThat(subject.isExpressedAtMostOn(Sets.newHashSet("nose", "trunk")), is(false));

        assertThat(subject.isExpressedAtMostOn(Sets.newHashSet("nose", "head", "trunk")), is(true));

        assertThat(subject.isExpressedAtMostOn(Sets.newHashSet("nose", "head", "trunk", "knee")), is(true));
    }

    @Test
    public void isExpressedAtMostOnShouldNotBeCaseInsensitiveTest(){
        assertThat(subject.isExpressedAtMostOn(Sets.newHashSet("noSe", "Head", "trUnK")), is(false));
    }

}

