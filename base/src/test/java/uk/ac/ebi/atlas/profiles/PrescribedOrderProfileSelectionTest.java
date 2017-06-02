package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.ac.ebi.atlas.commons.streams.ObjectInputStreamTest.convert;

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
        BaselineProfile p = new BaselineProfile("AC197246.3_FG003", "RPL32");

        SelectProfiles<BaselineProfile, BaselineProfilesList> subject = new PrescribedOrderProfileSelection<>
                (ImmutableList.of("AC197246.3_FG003"), new BaselineProfilesListBuilder());




        Iterable<BaselineProfile> result = subject.select(convert(ImmutableList.of(p)), 1);

        assertThat(result.iterator().next(), is(p));

    }


    private void test(String[] order, String[] underlying, String[] expected, int maxRows){
        SelectProfiles<BaselineProfile, BaselineProfilesList> subject = new PrescribedOrderProfileSelection<>
                (Arrays.asList(order), new BaselineProfilesListBuilder());


        List<BaselineProfile> result = subject.select(ps(underlying), maxRows);

        assertThat(result, is((List) ImmutableList.copyOf(new IterableObjectInputStream<>(ps(expected)))));
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

    private ObjectInputStream<BaselineProfile> ps(String ... ns){
        List<BaselineProfile> r = new ArrayList<>();
        for(String n: ns) {
            r.add(p(n));
        }
        return convert(r);
    }

    private BaselineProfile p(String n){
        return new BaselineProfile(n,n);
    }

}