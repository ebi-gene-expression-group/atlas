package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.SortedSet;


@Named
public class BaselineRequestContext extends RequestContext<Factor, BaselineRequestPreferences> implements BaselineProfileStreamOptions {

    private BaselineExperiment experiment;

    private SortedSet<Factor> selectedFilterFactors;

    public BaselineRequestContext() {
    }

    public SortedSet<Factor> getSelectedFilterFactors() {
        return selectedFilterFactors;
    }


    @Override
    public String getExperimentAccession() {
        return experiment.getAccession();
    }

    public String getQueryFactorType() {
        return getRequestPreferences().getQueryFactorType();
    }

    void setSelectedFilterFactors(SortedSet<Factor> selectedFilterFactors) {
        this.selectedFilterFactors = selectedFilterFactors;
    }

    void setExperiment(BaselineExperiment experiment) {
        this.experiment = experiment;
    }

    public BaselineExperiment getExperiment() {
        return experiment;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .addValue(super.toString())
                .add("selectedFilterFactors", selectedFilterFactors)
                .add("experiment", experiment).toString();
    }

}