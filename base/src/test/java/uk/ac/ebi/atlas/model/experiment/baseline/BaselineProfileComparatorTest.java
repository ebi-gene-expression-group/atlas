package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileComparatorTest {

    BaselineProfileComparator subject;

    @Mock
    BaselineProfile geneWithSpecificity1;

    @Mock
    BaselineProfile geneWithSpecificity16;

    @Mock
    BaselineProfile geneWithSpecificity16AndSmallerExpressionLevel;

    AssayGroup g1 = new AssayGroup("g1", "run11");
    AssayGroup g2 = new AssayGroup("g2", "run21");
    AssayGroup g3 = new AssayGroup("g3", "run31");

    List<AssayGroup> selectedOrganismParts = ImmutableList.of(g1);
    List<AssayGroup> allOrganismParts = ImmutableList.of(g1, g2, g3);

    private BaselineProfile profileWithExpressions(Map<AssayGroup, Double> expressions){
        String geneId = RandomStringUtils.randomAlphabetic(10);
        BaselineProfile profile = new BaselineProfile(geneId,geneId);
        for(Map.Entry<AssayGroup, Double> e : expressions.entrySet()){
            profile.add(e.getKey(), new BaselineExpression(e.getValue()));
        }
        return profile;
    }


    @Test
    public void twoGenesExpressedInSingleAssayGroup(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 2d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d)))
                , lessThan(0)
        );
    }

    @Test
    public void geneNotInSelectedIdsIsNotFavoured(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d)))
                , lessThan(0)
        );
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d, g3, 1d)))
                , lessThan(0)
        );
    }

    @Test
    public void differentSelectIdsCompareByExpression(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1, g2), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 2d)),
                        profileWithExpressions(ImmutableMap.of(g2, 1d)))
                ,lessThan(0)
        );
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 2d, g3, 1d)),
                        profileWithExpressions(ImmutableMap.of(g2, 1d, g3, 1d)))
                ,lessThan(0)
        );
    }
    @Test
    public void beingExpressedOutsideSelectedSetIsDisfavoured(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d, g3, 1d)))
                ,lessThan(0)
        );

        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d, g3, 1d)))
                ,lessThan(0)
        );
    }

    @Test
    public void sizeOfExpressionOutsideSelectedSetMatters(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 2d)))
                ,lessThan(0)
        );
    }

    @Test
    public void sizeOfExpressionInsideSelectedSetMatters(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 2d, g2, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 1d, g2, 1d)))
                ,lessThan(0)
        );
    }

    @Test
    public void specificityInsideSelectedSetMatters(){
        subject = new BaselineProfileComparator(true, ImmutableList.of(g1, g2), ImmutableList.of(g1, g2, g3));
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d)),
                        profileWithExpressions(ImmutableMap.of(g1, 2d, g2, 2d)))
                ,lessThan(0)
        );
        assertThat(
                subject.compare(profileWithExpressions(ImmutableMap.of(g1, 1d, g3, 3d)),
                        profileWithExpressions(ImmutableMap.of(g1, 2d, g2, 2d, g3, 3d)))
                ,lessThan(0)
        );
    }


    @Before
    public void initGeneExpressions() {
        when(geneWithSpecificity1.getId()).thenReturn("Gene with specificity 1");
        when(geneWithSpecificity1.getSpecificity()).thenReturn(1L);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(5D);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(5D);

        when(geneWithSpecificity16.getId()).thenReturn("Gene with specificity 16");
        when(geneWithSpecificity16.getSpecificity()).thenReturn(16L);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(10D);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(2D);

        when(geneWithSpecificity16AndSmallerExpressionLevel.getId()).thenReturn("Gene with specificity 16 and smaller");
        when(geneWithSpecificity16AndSmallerExpressionLevel.getSpecificity()).thenReturn(16L);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(0D);
    }


    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        subject = new BaselineProfileComparator(true, allOrganismParts, allOrganismParts);
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);
        //then
        assertThat(comparison, is(greaterThan(0)));
    }

    @Test
    public void highSpecificityShouldPrecedeLowSpecificity() {
        subject = new BaselineProfileComparator(true, allOrganismParts, allOrganismParts);
        //when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);
        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void sameSpecificityShouldBeSortedByExpressionLevel() {
        subject = new BaselineProfileComparator(true, allOrganismParts, allOrganismParts);
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity16AndSmallerExpressionLevel);
        // then
        assertThat(comparison, is(lessThan(0)));
    }


    @Test
    public void higherAverageAcrossAllTissues() {
        subject = new BaselineProfileComparator(false, allOrganismParts, allOrganismParts);
        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);
        // then
        assertThat(comparison, is(greaterThan(0)));
    }

    @Test
    public void higherAverageAcrossSelectedTissues() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts);
        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);
        // then
        assertThat(comparison, is(lessThan(0)));
    }

}
