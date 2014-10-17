package uk.ac.ebi.atlas.trader.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

import static uk.ac.ebi.atlas.utils.StringArrayUtil.contains;
import static uk.ac.ebi.atlas.utils.StringArrayUtil.substringBefore;

@Named
@Scope("prototype")
public class ProteomicsBaselineExperimentExpressionLevelFile {

    private static final int HEADER_LINE_INDEX = 0;
    public static final String WITH_IN_SAMPLE_ABUNDANCE = "WithInSampleAbundance";

    private final TsvReaderBuilder tsvReaderBuilder;

    @Inject
    public ProteomicsBaselineExperimentExpressionLevelFile(TsvReaderBuilder tsvReaderBuilder,
                                                           @Value("#{configuration['experiment.magetab.path.template']}")
                                                           String experimentDataFilePathTemplate) {
        this.tsvReaderBuilder = tsvReaderBuilder.forTsvFilePathTemplate(experimentDataFilePathTemplate);
    }

    public String[] readOrderedAssayGroupIds(String experimentAccession) {
        TsvReader experimentDataTsvReader = tsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        String[] tsvFileHeader = experimentDataTsvReader.readLine(HEADER_LINE_INDEX);

        return extractAssayGroupIds(tsvFileHeader);

    }

    String[] extractAssayGroupIds(String[] tsvFileHeader) {
        String[] filtered = contains(tsvFileHeader, WITH_IN_SAMPLE_ABUNDANCE);
        return substringBefore(filtered, ".");
    }

}
