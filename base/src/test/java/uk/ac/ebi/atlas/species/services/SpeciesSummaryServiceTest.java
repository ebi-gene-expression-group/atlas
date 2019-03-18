package uk.ac.ebi.atlas.species.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.ac.ebi.atlas.home.SpeciesSummaryDao;
import uk.ac.ebi.atlas.home.SpeciesSummaryService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SpeciesSummaryServiceTest {
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

    @BeforeEach
    void setUp() {
        subject = new SpeciesSummaryService(speciesSummaryDaoMock);
    }

    @Test
    void stub() {
        // TODO
        assertThat(true).isTrue();
    }
}
