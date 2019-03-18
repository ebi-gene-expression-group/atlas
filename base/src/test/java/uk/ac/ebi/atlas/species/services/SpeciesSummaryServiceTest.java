package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.home.SpeciesSummaryDao;
import uk.ac.ebi.atlas.home.SpeciesSummaryService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SpeciesSummaryServiceTest {
    private static final ImmutableMap<String, ImmutableList<String>> KINGDOM_TO_SPECIES =
            ImmutableMap.of(
                    "animals",
                    ImmutableList.of(
                            "Homo sapiens", "Caenorhabditis elegans", "Bos taurus", "Mus musculus",
                            "Drosophila melanogaster", "Xenopus tropicalis"),
                    "plants",
                    ImmutableList.of(
                            "Zea mays", "Vitis vinifera", "Arabidopsis thaliana", "Glycine max", "Hordeum vulgare",
                            "Oryza sativa"),
                    "fungi",
                    ImmutableList.of(
                            "Aspergillus fumigatus", "Saccharomyces cerevisiae", "Schizosaccharomyces pombe"));

    @Mock
    private SpeciesSummaryDao speciesSummaryDaoMock;

    private SpeciesSummaryService subject;

    @Before
    public void setUp() throws Exception {
        subject = new SpeciesSummaryService(speciesSummaryDaoMock);
    }

    @Test
    void stub() {
        // TODO
        assertThat(true).isTrue();
    }
}
