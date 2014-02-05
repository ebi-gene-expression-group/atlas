package uk.ac.ebi.atlas.trader.loader;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.commons.readers.TsvReaderBuilder;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class BaselineExperimentExpressionLevelFile {

    private static final int HEADER_LINE_INDEX = 0;

    private final TsvReaderBuilder tsvReaderBuilder;

    @Inject
    public BaselineExperimentExpressionLevelFile(TsvReaderBuilder tsvReaderBuilder,
                                                 @Value("#{configuration['experiment.magetab.path.template']}")
                                                 String experimentDataFilePathTemplate) {
        this.tsvReaderBuilder = tsvReaderBuilder.forTsvFilePathTemplate(experimentDataFilePathTemplate);
    }

    public String[] readOrderedAssayGroupIds(String experimentAccession) {
        TsvReader experimentDataTsvReader = tsvReaderBuilder.withExperimentAccession(experimentAccession).build();

        String[] experimentRunHeaders = experimentDataTsvReader.readLine(HEADER_LINE_INDEX);

        return ArrayUtils.subarray(experimentRunHeaders, BaselineExperimentsCacheLoader.ASSAY_GROUP_HEADER_START_INDEX, experimentRunHeaders.length);
    }
}