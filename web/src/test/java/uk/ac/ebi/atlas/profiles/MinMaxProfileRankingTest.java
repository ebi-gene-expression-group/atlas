package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.BaselineProfileComparator;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class MinMaxProfileRankingTest {



    public BaselineProfilesList selectAverageExpressionsOnly(final double cutoff, Iterable<BaselineProfile> profiles,
                                                            int maxSize) {

        class VisibleBaselineProfileComparator extends BaselineProfileComparator{
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
        when(result.getAverageExpressionLevelOn(Mockito.anySet())).thenReturn(averageExpression);
        return result;
    }

    @Test
    public void testComparingByAverageExpression(){
        BaselineProfilesList l = new BaselineProfilesList();

        List<Double> expressions = new ArrayList<>();

        Random rng = new Random();
        double min = 1000* rng.nextDouble();
        double max = min + 1000* rng.nextDouble();

        int n = rng.nextInt(500);

        for(int i = 0; i< n; i++){
            double v = min + rng.nextDouble()*(max - min);
            l.add(mockProfile(v));
            expressions.add(v);
        }

        double cutoff = min + rng.nextDouble()*(max - min);

        int maxSize = rng.nextInt(n);

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
    
}