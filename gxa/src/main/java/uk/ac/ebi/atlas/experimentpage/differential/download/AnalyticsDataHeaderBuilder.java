
package uk.ac.ebi.atlas.experimentpage.differential.download;

import org.apache.commons.lang.StringUtils;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

class AnalyticsDataHeaderBuilder {
    private final DifferentialExperiment experiment;

    public AnalyticsDataHeaderBuilder(DifferentialExperiment experiment){
        checkNotNull(experiment, "Experiment should be not null!");
        this.experiment = experiment;
    }

    public String[] buildHeader(String[] header) {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < getFixedColumnNumber(); i++) {
            result.add(header[i]);
        }

        for (int i = getFixedColumnNumber(); i < header.length; i++) {
            result.add(replaceContrastIdWithName(header[i]));
        }

        return result.toArray(new String[result.size()]);
    }

    protected String replaceContrastIdWithName(String columnHeader) {
        String contrastId = StringUtils.substringBefore(columnHeader, ".");
        String displayName = experiment.getContrast(contrastId).getDisplayName();
        return StringUtils.replace(columnHeader, contrastId, displayName);
    }

    protected int getFixedColumnNumber() {
        return 2;
    }
}
