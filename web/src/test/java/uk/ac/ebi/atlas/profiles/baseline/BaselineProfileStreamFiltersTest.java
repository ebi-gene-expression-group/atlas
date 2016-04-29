package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesEMTab513React71;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.IterableObjectInputStream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

        Iterable<BaselineProfile> profiles = subject.filterBySpecificQueryFactors(new IterableObjectInputStream<>
                (eMTab513react71InputStream),
                options
                .getSelectedQueryFactors(), options
                .getAllQueryFactors());

        Set<String> genesOut = new HashSet<>();
        for(BaselineProfile profile: profiles){
            genesOut.add(profile.getName());
        }
        assertEquals(Sets.newHashSet("SRSF2", "SNRPA"), genesOut);
    }
}