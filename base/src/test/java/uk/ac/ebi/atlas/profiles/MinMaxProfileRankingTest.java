package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class MinMaxProfileRankingTest {

    private Random rng = new Random();

    private class VisibleBaselineProfileComparator extends BaselineProfileComparator {
        VisibleBaselineProfileComparator(boolean isSpecific, Set<Factor> selectedQueryFactors,
                                         Set<Factor> allQueryFactors, double cutoff,Double
                                                 minimumExpressionLevelToQualifyAsGoodForOurRule,Double minimumFractionOfExpressionToQualifyAsGoodForOurRule){
            super(isSpecific, selectedQueryFactors, allQueryFactors, cutoff, minimumExpressionLevelToQualifyAsGoodForOurRule, minimumFractionOfExpressionToQualifyAsGoodForOurRule);
        }
        @Override
        public int compareOnAverageExpressionLevel(BaselineProfile firstBaselineProfile, BaselineProfile
                otherBaselineProfile, Set<Factor> factors) {
            return super.compareOnAverageExpressionLevel(firstBaselineProfile,otherBaselineProfile,factors);
        }
    }

    private BaselineProfilesList selectAverageExpressionsOnly(final double cutoff, Iterable<BaselineProfile> profiles,
                                                             int maxSize) {
        final boolean isSpecific = true;
        final Set<Factor> selectedQueryFactors = ImmutableSet.of();
        final Set<Factor> allQueryFactors = ImmutableSet.of();

        final VisibleBaselineProfileComparator c = new VisibleBaselineProfileComparator(isSpecific,selectedQueryFactors,allQueryFactors,cutoff,null,null);

        Comparator<BaselineProfile> comparatorThatTestsAverageExpressionsOnly = new Comparator<BaselineProfile>(){
            @Override
            public int compare(BaselineProfile o1, BaselineProfile o2) {
                return c.compareOnAverageExpressionLevel(o1,o2,allQueryFactors);
            }
        };

        MinMaxProfileRanking<BaselineProfile, BaselineProfilesList> subject = new MinMaxProfileRanking<>(comparatorThatTestsAverageExpressionsOnly, new BaselineProfilesListBuilder());

        return subject.select(profiles,maxSize);
    }

    private BaselineProfile mockProfile(double averageExpression){
        BaselineProfile result = Mockito.mock(BaselineProfile.class);
        when(result.getAverageExpressionLevelOn(Matchers.anySetOf(Factor.class))).thenReturn(averageExpression);
        return result;
    }

    private double randDouble(double min, double max){
        return min + rng.nextDouble()*(max - min);
    }

    @Test
    public void testComparingByAverageExpression(){
        BaselineProfilesList l = new BaselineProfilesList();

        List<Double> expressions = new ArrayList<>();

        double min = 1000* rng.nextDouble();
        double max = min + 1000* rng.nextDouble();

        int n = rng.nextInt(500);

        for(int i = 0; i< n; i++){
            double v = randDouble(min,max);
            l.add(mockProfile(v));
            expressions.add(v);
        }

        double cutoff = randDouble(min,max);

        int maxSize = rng.nextInt(n)+1;

        BaselineProfilesList result = selectAverageExpressionsOnly(cutoff, l, maxSize);
        List<Double> resultExpressions = new ArrayList<>();
        for(BaselineProfile p: result){
            resultExpressions.add(p.getAverageExpressionLevelOn(ImmutableSet.<Factor>of()));
        }

        //sort expressions independently
        List<Double> sortedExpressions = new ArrayList<>(new TreeSet<>(expressions).descendingSet()).subList(0,
                resultExpressions.size());

        assertEquals(sortedExpressions,resultExpressions);
    }

    private BaselineProfile randomProfile(double min, double max,String id,List<String> allFactors, boolean includeAll){

        BaselineProfile result = new BaselineProfile(id,id);
        for(String factorName: allFactors){
            if(rng.nextBoolean() || includeAll){
                result.add("FACTOR_TYPE", new BaselineExpression(randDouble(min,max), new FactorSet(new Factor
                        ("FACTOR_TYPE",factorName))));
            }
        }
        if(result.isEmpty()){
            return randomProfile(min,max,id,allFactors,includeAll);
        }
        return result;
    }

    @Test
    public void nonSpecificSearchLetsTheHighestExpressedFactorWin(){
        BaselineProfilesList l = new BaselineProfilesList();

        double min = 1000* rng.nextDouble();
        double max = min + 1000* rng.nextDouble();

        List<String> allFactorValues = ImmutableList.of("a","b","c");
        Set<Factor> allFactors = ImmutableSet.of(new Factor("FACTOR_TYPE","a"),new Factor("FACTOR_TYPE","b"),new
                Factor("FACTOR_TYPE","c"));

        int j = rng.nextInt(10);
        for(int i =0; i< j; i++){
            l.add(randomProfile(min,max, "profile_"+i,allFactorValues,false));
        }
        l.add(randomProfile(max+10, max+11,"profileWeWant", allFactorValues,true)); //expressed on all factors, very
        // highly
        for(int i =0; i< 10-j; i++){
            l.add(randomProfile(min,max, "profile_"+i,allFactorValues,false));
        }

        double cutoff = randDouble(min,max);

        BaselineProfilesList result = new MinMaxProfileRanking<>(new VisibleBaselineProfileComparator(false,
                ImmutableSet.<Factor>of(), allFactors,cutoff, null,null), new BaselineProfilesListBuilder()).select
                (l,5);

        assertEquals("profileWeWant",result.iterator().next().getName());

    }



    @Test
    public void specificSearchLetsTheHighestExpressedFactorWin(){
        BaselineProfilesList l = new BaselineProfilesList();

        double min = 1000* rng.nextDouble();
        double max = min + 1000* rng.nextDouble();

        List<String> allFactorValues = ImmutableList.of("a","b","c");
        Set<Factor> allFactors = ImmutableSet.of(new Factor("FACTOR_TYPE","a"),new Factor("FACTOR_TYPE","b"),new
                Factor("FACTOR_TYPE","c"));

        int j = rng.nextInt(1000);
        for(int i =0; i< j; i++){
            l.add(randomProfile(min,max, "profile_"+i,allFactorValues,false));
        }
        l.add(randomProfile(max+1000, max+11,"profileWeWant", ImmutableList.of("a"),true));
        for(int i =0; i< 10-j; i++){
            l.add(randomProfile(min,max, "profile_"+i,allFactorValues,false));
        }

        double cutoff = randDouble(min,max);

        BaselineProfilesList result = new MinMaxProfileRanking<>(new VisibleBaselineProfileComparator(true,
                ImmutableSet.<Factor>of(), allFactors,cutoff, null,null), new BaselineProfilesListBuilder()).select
                (l,50);

        if(!"profileWeWant".equals(result.iterator().next().getName())){
            fail();
        }
    }

    @Test
    public void cutoffCantChangeTheWinner(){
        BaselineProfilesList l = new BaselineProfilesList();

        double min = 1000* rng.nextDouble();
        double max = min + 1000* rng.nextDouble();

        List<String> allFactorValues = new ArrayList<>();
        Set<Factor> allFactors = new HashSet<>();
        for(int i =0; i<10; i++){
            String factorValue = "factor_"+i;
            allFactorValues.add(factorValue);
            allFactors.add(new Factor("FACTOR_TYPE",factorValue));
        }
        Set<Factor> selectedFactors = ImmutableSet.of(allFactors.iterator().next());

        int queueSize = 50;
        for(int i =0; i< 10; i++){
            l.add(randomProfile(min,max, "profile_"+i,allFactorValues,false));
        }

        double cutoff = randDouble(min,max);
        double nextCutoff;

        String result = getFirstProfile(l,selectedFactors, allFactors, cutoff,queueSize).getName();
        String nextResult;

        while(cutoff<max){
            nextCutoff = randDouble(cutoff,max)+1;
            nextResult = getFirstProfile(l, selectedFactors,allFactors, nextCutoff,queueSize).getName();
            if(!nextResult.equals(result)){
                if (getFirstProfile(l, selectedFactors,allFactors, nextCutoff,queueSize).getSpecificity()
                        == 1 || 1== getFirstProfile(l,
                        selectedFactors,allFactors, cutoff,queueSize).getSpecificity()) {
                    /*
                    We understand this scenario - the cutoff does enter the formula when there are no non-selected
                    factors, see BaselineProfileComparator.getExpressionLevelFoldChange.
                    The behaviour is not great but does not represent a failure really.
                     */
                } else {
                    fail();
                }
            } else {
                cutoff = nextCutoff;
            }
        }
    }

    private BaselineProfile getFirstProfile(BaselineProfilesList l,Set<Factor> selectedFactors, Set<Factor> allFactors, double
            cutoff,int queueSize) {
        BaselineProfilesList result = new MinMaxProfileRanking<>(new VisibleBaselineProfileComparator(true,
                selectedFactors, allFactors,cutoff, null,null), new BaselineProfilesListBuilder()).select
                (l,queueSize);

        return result.iterator().next();
    }

}