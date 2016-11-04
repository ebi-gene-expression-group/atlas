package uk.ac.ebi.atlas.trader.loader;

import uk.ac.ebi.atlas.utils.StringArrayUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class ProteomicsBaselineExperimentExpressionLevelFile implements BaselineExperimentExpressionLevelFile {

    private static final int HEADER_LINE_INDEX = 0;
    public static final String WITH_IN_SAMPLE_ABUNDANCE = "WithInSampleAbundance";

    private final FileTsvReaderBuilder fileTsvReaderBuilder;

    @Inject
    public ProteomicsBaselineExperimentExpressionLevelFile(FileTsvReaderBuilder fileTsvReaderBuilder,
                                                           @Value("#{configuration['experiment.magetab.path.template']}")
                                                           String experimentDataFilePathTemplate) {
        this.fileTsvReaderBuilder = fileTsvReaderBuilder.forTsvFilePathTemplate(experimentDataFilePathTemplate);
    }

    public String[] readOrderedAssayGroupIds(String experimentAccession) {
        TsvReader experimentDataTsvReader = fileTsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        String[] tsvFileHeader = experimentDataTsvReader.readLine(HEADER_LINE_INDEX);

        return extractAssayGroupIds(tsvFileHeader);

    }

    static String[] extractAssayGroupIds(String[] tsvFileHeader) {
        String[] filtered = StringArrayUtil.filterBySubstring(tsvFileHeader, WITH_IN_SAMPLE_ABUNDANCE);
        return StringArrayUtil.substringBefore(filtered, ".");
    }

    public static int[] indicesOfAssayGroups(String[] tsvFileHeader) {
        return StringArrayUtil.indicesOf(tsvFileHeader, WITH_IN_SAMPLE_ABUNDANCE);
    }

}
