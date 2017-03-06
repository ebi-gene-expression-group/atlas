package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileTest {

    private static final String GENE_ID = "geneId_1";
    private static final String GENE_NAME = "geneName_1";

    private AssayGroup g1 = new AssayGroup("g1", "run11","run12","run13");
    private AssayGroup g2 = new AssayGroup("g2", "run21");
    private AssayGroup g3 = new AssayGroup("g3", "run31","run32");
    private AssayGroup g4 = new AssayGroup("g4", "g41");

    private BaselineExpression expression_1 = new BaselineExpression(2.2D, "g1");
    private BaselineExpression expression_2 = new BaselineExpression(3D, "g2");
    private BaselineExpression expression_3 = new BaselineExpression(3.001D, "g3");

    private BaselineProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineProfile(GENE_ID, GENE_NAME);
        subject.add(g1, expression_1);
        subject.add(g2, expression_2);
        subject.add(g3, expression_3);
    }

    @Test
    public void testGetGeneSpecificity() throws Exception {
        assertThat(subject.getSpecificity(), is(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenQueryFactorsIsEmpty() {
        subject.getAverageExpressionLevelOn(new HashSet<AssayGroup>());
    }

    @Test
    public void averageExpressionLevelOnEmptyCollection() {
        //given
        Set<AssayGroup> queryFactors = ImmutableSet.of(mock(AssayGroup.class));
        //when
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(queryFactors);
        //then
        assertThat(averageExpressionLevel, is(0D));
    }

    @Test
    public void averageExpressionLevel() {
        double averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(g1, g3));
        assertThat(averageExpressionLevel, is(2.6005000000000003D));

        averageExpressionLevel = subject.getAverageExpressionLevelOn(Sets.newHashSet(g1, g3, g4));
        assertThat(averageExpressionLevel, is(1.733666666666667D));
    }

    @Test
    public void testSumProfile(){
        subject.sumProfile(buildOtherProfile());
        assertThat(subject.getId(), is(subject.getId()));
        assertThat(subject.getExpressionLevel(g1), is(2.2D + 1D));
        assertThat(subject.getExpressionLevel(g2), is(3D + 2D));
        assertThat(subject.getExpressionLevel(g3), is(3.001D + 3D));
        assertThat(subject.getExpressionLevel(g4), is(300D));
    }

    @Test
    public void sumProfileShouldPreserveLevelsThatAreNotExpressedInOtherProfile(){
        BaselineProfile otherProfile = new BaselineProfile("other profile", "other name");
        otherProfile.add(g2, expression_2);

        subject.sumProfile(otherProfile);
        assertThat(subject.getId(), is(GENE_ID));
        assertThat(subject.getExpressionLevel(g1), is(subject.getExpressionLevel(g1)));
        assertThat(subject.getExpressionLevel(g2), is(6D));
        assertThat(subject.getExpressionLevel(g3), is(subject.getExpressionLevel(g3)));
        assertThat(subject.getExpressionLevel(g4), is(nullValue()));
    }

    @Test
    public void testFold(){
        BaselineProfile sumProfile = subject.foldProfile(3);
        assertThat(sumProfile.getId(), is(subject.getId()));
        assertThat(sumProfile.getExpressionLevel(g1), is(0.7D));
        assertThat(sumProfile.getExpressionLevel(g2), is(1.0D));
        assertThat(sumProfile.getExpressionLevel(g3), is(1.0D));
        assertThat(sumProfile.getExpressionLevel(g4), is(nullValue()));
    }


    BaselineProfile buildOtherProfile(){


        BaselineProfile baselineProfile = new BaselineProfile("OTHER_ID", "OTHER_NAME");

        baselineProfile.add(g1,new BaselineExpression(1D, "g1"));
        baselineProfile.add(g2, new BaselineExpression(2D, "g2"));
        baselineProfile.add(g3, new BaselineExpression(3D, "g3"));
        baselineProfile.add(g4, new BaselineExpression(300D, "g4"));

        return baselineProfile;
    }

}