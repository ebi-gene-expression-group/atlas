//package uk.ac.ebi.atlas.model;
//
//import com.google.common.collect.Sets;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.*;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//
//public class GeneProfileTest {
//
//    private GeneProfile subject;
//
//    private Expression expression1 = new Expression(new ExperimentRun("RUN_ACCESSION_1"), 2.2D);
//    private Expression expression2 = new Expression(new ExperimentRun("RUN_ACCESSION_2", Sets.newHashSet(new FactorValue("organism part","trunk"))), 3D);
//
//    @Before
//    public void setUp() throws Exception {
//        subject = GeneProfile.forGeneId("EMBL-1")
//                    .addExpression(expression1)
//                    .addExpression(expression2)
//                    .create();
//    }
//
//    @Test
//    public void testGetGeneSpecificity() throws Exception {
//        assertThat(subject.getSpecificity(), is(2));
//    }
//
//    @Test
//    public void iteratorReturnsExpressionsAndThenNull() throws Exception {
//        //given
//        Iterator<GeneProfile> profileIterator = subject.iterator();
//
//        //then
//        assertThat(profileIterator.next().getLevel(), isOneOf(2.2D, 3D));
//        assertThat(profileIterator.next().getLevel(), isOneOf(2.2D, 3D));
//        //and
//        assertThat(profileIterator.hasNext(), is(false));
//    }
//
//    @Test
//    public void builderAddExpressionTest() {
//        //given
//        GeneProfile.Builder builder = GeneProfile.forGeneId("ENS1");
//        builder.withCutoff(3D);
//
//        Expression expressionSmaller = new Expression(new ExperimentRun("RUN_ACCESSION_1"), 2.2D);
//        builder.addExpression(expressionSmaller);
//
//        Expression expressionEquals = new Expression(new ExperimentRun("RUN_ACCESSION_2"), 3D);
//        builder.addExpression(expressionEquals);
//
//        Expression expressionBigger = new Expression(new ExperimentRun("RUN_ACCESSION_3"), 4D);
//        builder.addExpression(expressionBigger);
//
//        //when
//        Iterator<GeneExpression> profileIterator = builder.create().iterator();
//
//        //then
//        assertThat(profileIterator.next().getLevel(), is(4D));
//        //and
//        assertThat(profileIterator.hasNext(), is(false));
//
//    }
//
//    @Test
//    public void filterByOrganismPartsShouldRemoveExpressionsWithUnwontedFactorValues(){
//        Set<String> organismParts = Sets.newHashSet("beack", "trunk");
//
//        Iterable<GeneExpression> geneExpressions = subject.filterByOrganismParts(organismParts);
//
//        for(GeneExpression expression: geneExpressions){
//            assertThat(expression.getOrganismPart(), is(not("beack")));
//            assertThat(expression.getOrganismPart(), is("trunk"));
//        }
//    }
//
//
//    @Test
//    public void filterByOrganismPartsShouldKeepAllExpressionsWhenNoOrganismPartIsSpecified(){
//        Set<String> organismParts = Sets.newHashSet();
//
//        Iterable<GeneExpression> geneExpressions = subject.filterByOrganismParts(organismParts);
//
//        List<GeneExpression> filteredGeneExpressions = new ArrayList<GeneExpression>();
//
//        for(GeneExpression geneExpression: geneExpressions){
//            filteredGeneExpressions.add(geneExpression);
//        }
//
//        assertThat(filteredGeneExpressions.size(), is(2));
//
//    }
//
//}
//
