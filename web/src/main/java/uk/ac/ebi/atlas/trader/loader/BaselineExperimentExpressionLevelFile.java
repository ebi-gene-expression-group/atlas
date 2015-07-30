package uk.ac.ebi.atlas.trader.loader;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class BaselineExperimentExpressionLevelFile {

    private static final int HEADER_LINE_INDEX = 0;
    private static final int ASSAY_GROUP_HEADER_START_INDEX = 2;

    private final FileTsvReaderBuilder fileTsvReaderBuilder;

    @Inject
    public BaselineExperimentExpressionLevelFile(FileTsvReaderBuilder fileTsvReaderBuilder,
                                                 @Value("#{configuration['experiment.magetab.path.template']}")
                                                 String experimentDataFilePathTemplate) {
        this.fileTsvReaderBuilder = fileTsvReaderBuilder.forTsvFilePathTemplate(experimentDataFilePathTemplate);
    }

    public String[] readOrderedAssayGroupIds(String experimentAccession) {
        TsvReader experimentDataTsvReader = fileTsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        String[] experimentRunHeaders = experimentDataTsvReader.readLine(HEADER_LINE_INDEX);

        return ArrayUtils.subarray(experimentRunHeaders, ASSAY_GROUP_HEADER_START_INDEX, experimentRunHeaders.length);
    }
}