package uk.ac.ebi.atlas.profiles;

import com.google.common.base.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;

import java.util.Set;
import java.util.SortedSet;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.newTreeSet;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IsGeneIdMatchTest {

    private static final String GENE_2 = "Gene2";

    @Mock
    private ObjectInputStream<Profile> inputStreamMock;

    @Mock
    private OldBaselineProfile gene1ProfileMock;

    @Mock
    private OldBaselineProfile gene3ProfileMock;

    private Set<String> geneIDs = newHashSet("GENE1", "GENE2");

    private Factor factor1 = new Factor("ORG", "heart");
    private Factor factor2 = new Factor("ORG", "hair");
    private SortedSet<Factor> factors = newTreeSet(newHashSet(factor1, factor2));

    @Before
    public void initMocks() {
        when(gene1ProfileMock.getId()).thenReturn(GENE_2);
        when(gene1ProfileMock.isExpressedOnAnyOf(factors)).thenReturn(true);
        when(gene3ProfileMock.getId()).thenReturn("UNACCEPTABLE_GENE");
        when(gene3ProfileMock.isExpressedOnAnyOf(factors)).thenReturn(true);
    }

    @Test
    public void GeneIdMatchesPredicate() {
        //given
        Predicate<Profile> acceptancePredicate = new IsGeneIdMatch(geneIDs);
        //then
        assertThat(acceptancePredicate.apply(gene1ProfileMock), is(true));
        //and
        assertThat(acceptancePredicate.apply(gene3ProfileMock), is(false));
    }

}
