package uk.ac.ebi.atlas.markergenes;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomEnsemblGeneId;
import static uk.ac.ebi.atlas.testutils.RandomDataTestUtils.generateRandomGeneSymbol;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HighchartsHeatmapAdapterTest {

    @Mock
    private MarkerGenesDao markerGenesDaoMock;

    private HighchartsHeatmapAdapter subject;

    @BeforeEach
    void setUp() {
        subject = new HighchartsHeatmapAdapter(markerGenesDaoMock);
    }

    @Test
    void markerGeneWithoutSymbolHasGeneIDAsName() {
        String gene1 = generateRandomEnsemblGeneId();
        String gene2 = generateRandomEnsemblGeneId();
        String gene3 = generateRandomEnsemblGeneId();

        String geneSymbol1 = generateRandomGeneSymbol();
        String geneSymbol2 = generateRandomGeneSymbol();

        List<String> randomGeneIds = Arrays.asList(gene1, gene2, gene3);

        when(markerGenesDaoMock.getSymbolsForGeneIds(randomGeneIds)).thenReturn(ImmutableMap.of(
                gene1, geneSymbol1,
                gene2, geneSymbol2
        ));

        List<MarkerGene> markerGenes = Arrays.asList(
                MarkerGene.create(gene1, 1, 1, 0.004, 1, 199, 185),
                MarkerGene.create(gene2, 1, 3, 0.0006, 2, 12, 1.11),
                MarkerGene.create(gene3, 1, 5, 0.001, 6, 1000, 10000));

        List<Map<String, Object>> result = subject.getMarkerGeneHeatmapData(markerGenes);
        assertThat(result).hasSize(3);

        assertThat(result).element(0).extracting("name").containsOnly(geneSymbol1);
        assertThat(result).element(1).extracting("name").containsOnly(geneSymbol2);
        assertThat(result).element(2).extracting("name").containsOnly(gene3);
    }
}
