
package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Scope("request")
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

    public static BaselineRequestContext createFor(BaselineExperiment experiment, BaselineRequestPreferences preferences){
        return new BaselineRequestContextBuilder()
                .forExperiment(experiment)
                .withPreferences(preferences)
                .build();
    }

    public static BaselineRequestContext createWithCustomGeneQueryDescription(BaselineExperiment experiment,
                                                               BaselineRequestPreferences  preferences, String
                                                                                      description){
        return new BaselineRequestContextBuilder()
                .forExperiment(experiment)
                .withPreferences(preferences)
                .withCustomQueryDescription(description)
                .build();
    }

    public List<Factor> getFilterFactorsInTheSameOrderAsTheExperimentHeader(){
        List<Factor> result = new ArrayList<>();
        for(AssayGroupFactor assayGroupFactor: getOrderedAssayGroupFactors()){
            result.add(assayGroupFactor.getFactor());
        }
        return result;
    }

    public List<AssayGroupFactor> getOrderedAssayGroupFactors(){
        return experiment.getExperimentalFactors().getComplementAssayGroupFactors(selectedFilterFactors);
    }

}