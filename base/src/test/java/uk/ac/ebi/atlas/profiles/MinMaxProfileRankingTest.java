package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.commons.streams.ObjectInputStreamTest.convert;

public class MinMaxProfileRankingTest {

    private Random rng = new Random();

    private class VisibleBaselineProfileComparator extends BaselineProfileComparator {

        protected VisibleBaselineProfileComparator(boolean isSpecific,
                                                   Collection<AssayGroup> selectedQueryFactors,
                                                   Collection<AssayGroup> allQueryFactors) {
            super(isSpecific, selectedQueryFactors, allQueryFactors);
        }
        
        @Override
        public int compareOnAverageExpressionLevel(BaselineProfile firstBaselineProfile,
                                                   BaselineProfile otherBaselineProfile,
                                                   Collection<AssayGroup> assayGroups) {
        return super.compareOnAverageExpressionLevel(firstBaselineProfile, otherBaselineProfile, assayGroups);
        }
    }

    private BaselineProfilesList selectAverageExpressionsOnly(final Iterable<BaselineProfile> profiles, int maxSize) {
        final boolean isSpecific = true;
        final List<AssayGroup> selectedQueryFactors = ImmutableList.of();
        final List<AssayGroup> allQueryFactors = ImmutableList.of();

        final VisibleBaselineProfileComparator c =
                new VisibleBaselineProfileComparator(isSpecific,selectedQueryFactors,allQueryFactors);

        Comparator<BaselineProfile> comparatorThatTestsAverageExpressionsOnly =
                (o1, o2) -> c.compareOnAverageExpressionLevel(o1,o2,allQueryFactors);

        MinMaxProfileRanking<BaselineProfile, BaselineProfilesList> subject =
                new MinMaxProfileRanking<>(
                        comparatorThatTestsAverageExpressionsOnly, new BaselineProfilesListBuilder());

        return subject.select(convert(profiles), maxSize);
    }

    private BaselineProfile mockProfile(double averageExpression){
        BaselineProfile result = Mockito.mock(BaselineProfile.class);
        when(result.getAverageExpressionLevelOn(anyList())).thenReturn(averageExpression);
        return result;
    }

    private double randDouble(double min, double max) {
        return min + rng.nextDouble() * (max - min);
    }

    @Test
    public void testComparingByAverageExpression(){
        BaselineProfilesList l = new BaselineProfilesList();

        List<Double> expressions = new ArrayList<>();

        double min = 1000 * rng.nextDouble();
        double max = min + 1000 * rng.nextDouble();

        int n = rng.nextInt(500);

        for(int i = 0 ; i < n ; i++) {
            double v = randDouble(min,  max);
            l.add(mockProfile(v));
            expressions.add(v);
        }

        int maxSize = rng.nextInt(n) + 1;

        BaselineProfilesList result = selectAverageExpressionsOnly(l, maxSize);
        List<Double> resultExpressions = new ArrayList<>();
        for(BaselineProfile p : result){
            resultExpressions.add(p.getAverageExpressionLevelOn(ImmutableList.of()));
        }

        //sort expressions independently
        List<Double> sortedExpressions =
                new ArrayList<>(new TreeSet<>(expressions).descendingSet()).subList(0, resultExpressions.size());

        assertEquals(sortedExpressions,resultExpressions);
    }

    private BaselineProfile randomProfile(double min, double max, String id, List<AssayGroup> allFactors,
                                          boolean includeAll) {

        BaselineProfile result = new BaselineProfile(id, id);
        for (AssayGroup factorName: allFactors) {
            if (rng.nextBoolean() || includeAll) {
                result.add(factorName, new BaselineExpression(randDouble(min, max)));
            }
        }
        if(result.isEmpty()) {
            return randomProfile(min, max, id, allFactors, includeAll);
        }
        return result;
    }

    @Test
    public void nonSpecificSearchLetsTheHighestExpressedFactorWin(){
        BaselineProfilesList l = new BaselineProfilesList();

        double min = 1000 * rng.nextDouble();
        double max = min + 1000 * rng.nextDouble();

        List<AssayGroup> allFactors =
                ImmutableList.of(
                        new AssayGroup("a", "run_1"), new AssayGroup("b", "run_2"), new AssayGroup("c", "run_3"));

        int j = rng.nextInt(10);
        for(int i = 0 ; i < j ; i++) {
            l.add(randomProfile(min, max, "profile_" + i, allFactors, false));
        }
        l.add(randomProfile(max + 10, max + 11, "profileWeWant", allFactors, true)); // expressed on all factors,
                                                                                     // very highly
        for(int i = 0 ; i < 10 - j ; i++) {
            l.add(randomProfile(min, max, "profile_" + i, allFactors, false));
        }

        BaselineProfilesList result =
                new MinMaxProfileRanking<>(
                        new VisibleBaselineProfileComparator(false, allFactors, allFactors),
                        new BaselineProfilesListBuilder())
                        .select(convert(l),5);

        assertEquals("profileWeWant", result.iterator().next().getName());
    }

    @Test
    public void specificSearchLetsTheHighestExpressedFactorWin(){
        BaselineProfilesList l = new BaselineProfilesList();

        double min = 1000 * rng.nextDouble();
        double max = min + 1000 * rng.nextDouble();

        List<AssayGroup> allFactors =
                ImmutableList.of(
                        new AssayGroup("a", "run_1"), new AssayGroup("b", "run_2"), new AssayGroup("c", "run_3"));

        int j = rng.nextInt(1000);
        for(int i = 0 ; i < j ; i++) {
            l.add(randomProfile(min, max, "profile_" + i, allFactors, false));
        }

        l.add(randomProfile(
                max + 1000, max + 11, "profileWeWant", ImmutableList.of(new AssayGroup("a", "run_1")), true));

        for(int i = 0 ; i < 10 - j ; i++) {
            l.add(randomProfile(min, max, "profile_" + i, allFactors, false));
        }

        BaselineProfilesList result =
                new MinMaxProfileRanking<>(
                        new VisibleBaselineProfileComparator(true, allFactors, allFactors),
                        new BaselineProfilesListBuilder())
                        .select(convert(l), 50);

        if (!"profileWeWant".equals(result.iterator().next().getName())) {
            fail();
        }
    }

}