package uk.ac.ebi.atlas.trader.cache.loader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProteomicsBaselineExperimentExpressionLevelFileTest {

    ProteomicsBaselineExperimentExpressionLevelFile subject;

    @Mock
    private FileTsvReaderBuilder fileTsvReaderBuilder;

    @Before
    public void setup() {
        when(fileTsvReaderBuilder.forTsvFilePathTemplate(anyString())).thenReturn(fileTsvReaderBuilder);
        subject = new ProteomicsBaselineExperimentExpressionLevelFile(fileTsvReaderBuilder, "");
    }

    @Test
    public void extractAssayGroupIds() {
        String[] tsvFileHeader = "GeneID\tg1.SpectralCount\tg2.SpectralCount\tg3.SpectralCount\tg4.SpectralCount\tg5.SpectralCount\tg6.SpectralCount\tg7.SpectralCount\tg8.SpectralCount\tg9.SpectralCount\tg10.SpectralCount\tg11.SpectralCount\tg12.SpectralCount\tg13.SpectralCount\tg14.SpectralCount\tg15.SpectralCount\tg16.SpectralCount\tg17.SpectralCount\tg18.SpectralCount\tg19.SpectralCount\tg20.SpectralCount\tg21.SpectralCount\tg22.SpectralCount\tg23.SpectralCount\tg24.SpectralCount\tg25.SpectralCount\tg26.SpectralCount\tg27.SpectralCount\tg28.SpectralCount\tg29.SpectralCount\tg30.SpectralCount\tg1.WithInSampleAbundance\tg2.WithInSampleAbundance\tg3.WithInSampleAbundance\tg4.WithInSampleAbundance\tg5.WithInSampleAbundance\tg6.WithInSampleAbundance\tg7.WithInSampleAbundance\tg8.WithInSampleAbundance\tg9.WithInSampleAbundance\tg10.WithInSampleAbundance\tg11.WithInSampleAbundance\tg12.WithInSampleAbundance\tg13.WithInSampleAbundance\tg14.WithInSampleAbundance\tg15.WithInSampleAbundance\tg16.WithInSampleAbundance\tg17.WithInSampleAbundance\tg18.WithInSampleAbundance\tg19.WithInSampleAbundance\tg20.WithInSampleAbundance\tg21.WithInSampleAbundance\tg22.WithInSampleAbundance\tg23.WithInSampleAbundance\tg24.WithInSampleAbundance\tg25.WithInSampleAbundance\tg26.WithInSampleAbundance\tg27.WithInSampleAbundance\tg28.WithInSampleAbundance\tg29.WithInSampleAbundance\tg30.WithInSampleAbundance".split("\t");
        assertThat(subject.extractAssayGroupIds(tsvFileHeader), is(new String[]{"g1", "g2", "g3", "g4", "g5", "g6", "g7", "g8", "g9", "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21", "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30"}));
    }
}
