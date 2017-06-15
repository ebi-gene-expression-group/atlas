package uk.ac.ebi.atlas.experimentpage.differential.download;

import com.google.common.base.Function;
import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AnalyticsDataHeaderBuilder implements Function<String[], String[]> {
    private final DifferentialExperiment experiment;
    private final int fixedColumnNumber;

    private AnalyticsDataHeaderBuilder(DifferentialExperiment experiment, int fixedColumnNumber) {
        checkNotNull(experiment, "Experiment should be not null!");
        this.experiment = experiment;
        this.fixedColumnNumber = fixedColumnNumber;
    }

    public static AnalyticsDataHeaderBuilder microarray(MicroarrayExperiment experiment) {
        return new AnalyticsDataHeaderBuilder(experiment, 3);
    }

    public static AnalyticsDataHeaderBuilder rnaSeq(DifferentialExperiment experiment) {
        return new AnalyticsDataHeaderBuilder(experiment, 2);
    }

    @Override
    public String[] apply(String[] header) {

        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(header).subList(0, fixedColumnNumber));

        for (int i = fixedColumnNumber; i < header.length; i++) {
            result.add(replaceContrastIdWithName(header[i]));
        }

        return result.toArray(new String[result.size()]);
    }

    protected String replaceContrastIdWithName(String columnHeader) {
        String contrastId = StringUtils.substringBefore(columnHeader, ".");
        String displayName = experiment.getDataColumnDescriptor(contrastId).getDisplayName();
        return StringUtils.replace(columnHeader, contrastId, displayName);
    }

}
