package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.ac.ebi.atlas.commons.streams.ObjectInputStreamTest.convert;

public class MinMaxProfileRankingTest {

    private Random rng = new Random();

    private class VisibleBaselineProfileComparator extends BaselineProfileComparator {

        protected VisibleBaselineProfileComparator(boolean isSpecific,
                                                   Collection<AssayGroup> selectedQueryFactors,
                                                   Collection<AssayGroup> allQueryFactors) {
            super(isSpecific, selectedQueryFactors, allQueryFactors);
        }
    }


    private double randDouble(double min, double max) {
        return min + rng.nextDouble() * (max - min);
    }


    private BaselineProfile randomProfile(double min, double max, String id, List<AssayGroup> allFactors,
                                          boolean includeAll) {

        BaselineProfile result = new BaselineProfile(id, id);
        for (AssayGroup factorName: allFactors) {
            if (includeAll || rng.nextBoolean() ) {
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

    List<AssayGroup> allFactors =
            ImmutableList.of(
                    new AssayGroup("a", "run_1"), new AssayGroup("b", "run_2"), new AssayGroup("c", "run_3"));

    @Test
    public void specificSearchLetsTheHighestExpressedFactorWin(){
        BaselineProfilesList l = new BaselineProfilesList();

        double min = 1000 * rng.nextDouble();
        double max = min + 1000 * rng.nextDouble();

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

    @Test
    public void sizeZeroMeansDoNotLimit(){
        assertThat(new MinMaxProfileRanking<>(new VisibleBaselineProfileComparator(true, allFactors, allFactors), new BaselineProfilesListBuilder()).select(convert(ImmutableList.of(new BaselineProfile("", "")))
        , 0).isEmpty(), is(false));
    }

}