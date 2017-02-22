package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExpression;
import uk.ac.ebi.atlas.model.experiment.baseline.OldBaselineProfile;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.impl.FactorSet;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileStreamFiltersTest {

    @Mock
    BaselineProfileStreamOptions options;

    BaselineProfilesEMTab513React71 eMTab513react71InputStream = new BaselineProfilesEMTab513React71(0.5);

    BaselineProfileStreamFilters subject = new BaselineProfileStreamFilters();

    @Before
    public void setUp(){
        when(options.getAllQueryFactors()).thenReturn(eMTab513react71InputStream.getOrderedFactorGroups().extractFactors());
    }

    @Test
    public void testFilterBySpecificQueryFactors() throws Exception {
        when(options.getSelectedQueryFactors()).thenReturn(Collections.singleton(new Factor("ORGANISM_PART",
                "prostate")));

        Iterable<OldBaselineProfile> profiles = subject.filterBySpecificQueryFactors(new IterableObjectInputStream<>
                (eMTab513react71InputStream),
                options
                .getSelectedQueryFactors(), options
                .getAllQueryFactors());

        Set<String> genesOut = new HashSet<>();
        for(OldBaselineProfile profile: profiles){
            genesOut.add(profile.getName());
        }
        assertEquals(Sets.newHashSet("SRSF2", "SNRPA"), genesOut);
    }

    @Test
    public void averageProfilesWithMultipleFactorsIntoGeneSets() throws
            Exception {
        ImmutableSetMultimap<String,String> genesByGeneSet =
                ImmutableSetMultimap
                        .<String,String>builder()
                        .put("gene_set","gene_1")
                        .put("gene_set","gene_2")
                        .build();
        OldBaselineProfile p1 = new OldBaselineProfile("gene_1","gene_1");
        p1.add("ORGANISM_PART",new BaselineExpression(
                10, new FactorSet()
                .add(new Factor("ORGANISM_PART","seed"))
                ));
        OldBaselineProfile p2 = new OldBaselineProfile("gene_2","gene_2");
        p2.add("ORGANISM_PART",new BaselineExpression(
                20, new FactorSet()
                .add(new Factor("ORGANISM_PART","seed"))
                ));

        List<OldBaselineProfile> result = ImmutableList.<OldBaselineProfile>builder().addAll(subject.averageIntoGeneSets(ImmutableList.of(p1,
                p2),genesByGeneSet)).build();

        assertThat(result.size(),is(1));
        assertThat(result.get(0).getName(), is("gene_set"));
        assertThat(result.get(0).getMaxExpressionLevel(),
                is (new Double((10+20)/2)));
        assertThat(result.get(0).getMinExpressionLevel(),
                is (new Double((10+20)/2)));
    }

    /*
    This represents quite a fragile case that happened when we change the condensed sdrf file to include another
     factor, but do not change factors.xml to account for it.
     */
    @Test(expected=IllegalArgumentException.class)
    public void averageProfilesWithMultipleFactorsIntoGeneSetsWillNotWorkIfTheFactorGroupsDoNotAlign() throws
            Exception {
        ImmutableSetMultimap<String,String> genesByGeneSet =
                ImmutableSetMultimap
                        .<String,String>builder()
                        .put("gene_set","gene_1")
                        .put("gene_set","gene_2")
                        .build();
        OldBaselineProfile p1 = new OldBaselineProfile("gene_1","gene_1");
        p1.add("ORGANISM_PART",new BaselineExpression(
                Math.random(), new FactorSet()
                    .add(new Factor("ORGANISM_PART","seed"))
                    .add(new Factor("AGE","20"))));
        OldBaselineProfile p2 = new OldBaselineProfile("gene_2","gene_2");
        p2.add("ORGANISM_PART",new BaselineExpression(
                Math.random(), new FactorSet()
                .add(new Factor("ORGANISM_PART","seed"))
                .add(new Factor("AGE","25"))));

        subject.averageIntoGeneSets(ImmutableList.of(p1, p2),genesByGeneSet);
    }
}