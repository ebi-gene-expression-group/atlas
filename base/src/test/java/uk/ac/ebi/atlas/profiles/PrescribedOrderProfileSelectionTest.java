package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PrescribedOrderProfileSelectionTest {

    @Test
    public void testSelect() {

        test("g2 g4 g5", "g1 g2 g3 g4 g5" , "g2 g4 g5");
        test(" ", "g1 g2 g3 g4 g5" , " ");
        test("g2 g1", "g1 g2 g3 g4 g5" , "g2 g1");
        test("g2 g1", "g1 g1 g2 g3 g4 g5" , "g2 g1 g1");
        test("g2 g1", "g1 g2 g3 g4 g5 g1" , "g2 g1 g1");
    }

    @Test
    public void testSizeRestriction(){
        test("g2 g4 g5", "g1 g2 g3 g4 g5" , " ",0);
        test("g2 g4 g5", "g1 g2 g3 g4 g5" , "g2 g4",2);

        test("g2 g4 g5", "g1 g2 g2 g3 g4 g5" , "g2 g2 g4",3);
    }

    @Test
    public void useProfileIdNotTheName(){
        OldBaselineProfile p = new OldBaselineProfile("AC197246.3_FG003", "RPL32");

        SelectProfiles<OldBaselineProfile, BaselineProfilesList> subject = new PrescribedOrderProfileSelection<>
                (ImmutableList.of("AC197246.3_FG003"), new BaselineProfilesListBuilder());


        Iterable<OldBaselineProfile> result = subject.select(ImmutableList.of(p), 1);

        assertThat(result.iterator().next(), is(p));

    }


    private void test(String[] order, String[] underlying, String[] expected, int maxRows){
        SelectProfiles<OldBaselineProfile, BaselineProfilesList> subject = new PrescribedOrderProfileSelection<>
                (Arrays.asList(order), new BaselineProfilesListBuilder());


        Iterable<OldBaselineProfile> result = subject.select(ps(underlying), maxRows);

        assertThat(result, is(ps(expected)));
    }

    private void test(String order, String underlying, String expected, int maxRows){
        test(order.split(" "), underlying.split(" "), expected.split(" "), maxRows);
    }

    private void test(String order, String underlying, String expected){
        test(order.split(" "), underlying.split(" "), expected.split(" "), 100);
    }

    private void test(String[] order, String[] underlying, String[] expected){
        test(order, underlying, expected, 100);
    }

    private Iterable<OldBaselineProfile> ps(String ... ns){
        List<OldBaselineProfile> r = new ArrayList<>();
        for(String n: ns){
            r.add(p(n));
        }
        return r;
    }

    private OldBaselineProfile p(String n){
        return new OldBaselineProfile(n,n);
    }

}