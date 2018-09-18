package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileTest {
    private static final String GENE_ID = "geneId_1";
    private static final String GENE_NAME = "geneName_1";

    private AssayGroup g1 = AssayGroupFactory.create("g1", "run11", "run12", "run13");
    private AssayGroup g2 = AssayGroupFactory.create("g2", "run21");
    private AssayGroup g3 = AssayGroupFactory.create("g3", "run31", "run32");
    private AssayGroup g4 = AssayGroupFactory.create("g4", "g41");

    private BaselineExpression expression1 = new BaselineExpression(2.2);
    private BaselineExpression expression2 = new BaselineExpression(3D);
    private BaselineExpression expression3 = new BaselineExpression(3.001);

    private BaselineProfile subject;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineProfile(GENE_ID, GENE_NAME);
        subject.add(g1, expression1);
        subject.add(g2, expression2);
        subject.add(g3, expression3);
    }

    @Test
    public void getSpecificityOnAssayGroups() {
        assertThat(subject.getSpecificity(ImmutableList.of(g1)), is(1L));
        assertThat(subject.getSpecificity(ImmutableList.of()), is(0L));
        assertThat(subject.getSpecificity(ImmutableList.of(g4)), is(0L));
    }

    @Test
    public void testGetGeneSpecificity() {
        assertThat(subject.getSpecificity(), is(3L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenQueryFactorsIsEmpty() {
        subject.getAverageExpressionLevelOn(new HashSet<>());
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
    public void isExpressedAnywhereIsLikeSpecificityGreaterThanZeroButPossiblyFaster() {

        List<AssayGroup> assayGroups = new ArrayList<>();
        while (assayGroups.isEmpty()) {
            if (RandomUtils.nextBoolean()) {
                assayGroups.add(g1);
            }
            if (RandomUtils.nextBoolean()) {
                assayGroups.add(g2);
            }
            if (RandomUtils.nextBoolean()) {
                assayGroups.add(g3);
            }
            if (RandomUtils.nextBoolean()) {
                assayGroups.add(g4);
            }
        }
        Collections.shuffle(assayGroups);

        assertThat(
                assayGroups.toString(),
                subject.isExpressedAnywhereOn(assayGroups),
                is(subject.getSpecificity(assayGroups) > 0)
        );
    }
}
