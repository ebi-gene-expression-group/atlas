package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineProfile;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IsGeneIdMatchTest {

    @Test
    public void GeneIdMatchesPredicate() {

        //given
        Predicate<Profile> acceptancePredicate = new IsGeneIdMatch(ImmutableSet.of("GENE1_ID", "GENE2_ID"));
        //then
        assertThat(acceptancePredicate.apply(new BaselineProfile("GENE1_ID", "GENE1")), is(true));
        //and
        assertThat(acceptancePredicate.apply(new BaselineProfile("different gene id", "different gene name")), is(false));
    }

}
