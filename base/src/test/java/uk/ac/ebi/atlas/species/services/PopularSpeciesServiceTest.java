package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopularSpeciesServiceTest {

    static final ImmutableMap<String, ImmutableList<String>> KINGDOM_TO_SPECIES = ImmutableMap.of(
            "animals", ImmutableList.of("Homo sapiens", "Caenorhabditis elegans", "Bos taurus", "Mus musculus", "Drosophila melanogaster", "Xenopus tropicalis"),
            "plants", ImmutableList.of("Zea mays", "Vitis vinifera", "Arabidopsis thaliana", "Glycine max", "Hordeum vulgare", "Oryza sativa"),
            "fungi", ImmutableList.of("Aspergillus fumigatus", "Saccharomyces cerevisiae", "Schizosaccharomyces pombe")
    );

    @Mock
    PopularSpeciesDao popularSpeciesDaoMock;

    @InjectMocks
    PopularSpeciesService subject;

    @Before
    public void setUp() throws Exception {
        ImmutableList.Builder<PopularSpeciesInfo> popularSpeciesBuilder = ImmutableList.builder();
        for (Map.Entry<String, ImmutableList<String>> speciesEntry : KINGDOM_TO_SPECIES.entrySet()) {
            for (String speciesName : speciesEntry.getValue()) {
                int baselineExperiments = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                int differentialExperiments = ThreadLocalRandom.current().nextInt(0, 100 + 1);
                popularSpeciesBuilder.add(PopularSpeciesInfo.create(speciesName, speciesEntry.getKey(), baselineExperiments, differentialExperiments));
            }
        }

        when(popularSpeciesDaoMock.popularSpecies()).thenReturn(popularSpeciesBuilder.build());
    }

    public void assertThatListIsSortedInDescendingOrder(List<PopularSpeciesInfo> list) {
        Iterator<PopularSpeciesInfo> iterator = list.iterator();
        while(iterator.hasNext()) {
            PopularSpeciesInfo thisPopularSpecies = iterator.next();
            PopularSpeciesInfo nextPopularSpecies = iterator.hasNext() ? iterator.next() : null;

            if (nextPopularSpecies != null) {
                assertThat(thisPopularSpecies.totalExperiments(),  is(greaterThanOrEqualTo(nextPopularSpecies.totalExperiments())));
            }
        }
    }

    public int getKingdomToSpeciesTotalSize() {
        int kingdomToSpeciesTotalSize = 0;
        for (List<String> kingdomSpeciesName : KINGDOM_TO_SPECIES.values()) {
            kingdomToSpeciesTotalSize += kingdomSpeciesName.size();
        }
        return kingdomToSpeciesTotalSize;
    }

    @Test
    public void getPopularSpecies() throws Exception {
        List<PopularSpeciesInfo> result = subject.getPopularSpecies();

        assertThat(result, hasSize(getKingdomToSpeciesTotalSize()));
        assertThatListIsSortedInDescendingOrder(result);
    }

    @Test
    public void getPopularSpeciesWithHowMany() throws Exception {
        int howMany = getKingdomToSpeciesTotalSize();
        List<PopularSpeciesInfo> result = subject.getPopularSpecies(howMany);

        assertThat(result, hasSize(howMany));
        assertThatListIsSortedInDescendingOrder(result);

    }

    @Test
    public void getPopularSpeciesWithHowManyByKingdom() throws Exception {
        for (Map.Entry<String, ImmutableList<String>> kingdomToSpeciesEntry : KINGDOM_TO_SPECIES.entrySet()) {
            String kingdom = kingdomToSpeciesEntry.getKey();
            int howMany = ThreadLocalRandom.current().nextInt(0, kingdomToSpeciesEntry.getValue().size() + 1);
            List<PopularSpeciesInfo> result = subject.getPopularSpecies(kingdom, howMany);

            assertThat(result, hasSize(howMany));
            assertThatListIsSortedInDescendingOrder(result);
            for (PopularSpeciesInfo popularSpeciesInfo : result) {
                assertThat(popularSpeciesInfo.kingdom(), is(kingdomToSpeciesEntry.getKey()));
            }
        }
    }

    @Test
    public void askMoreThanThereAre() throws Exception {
        assertThat(true, is(false));
    }
}