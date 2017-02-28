package uk.ac.ebi.atlas.experimentpage.context;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.AssayGroupFactor;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@Scope("request")
public class BaselineRequestContext extends RequestContext<AssayGroup, BaselineRequestPreferences> implements
        BaselineProfileStreamOptions {

    public BaselineRequestContext() {
    }


    @Override
    public Double getThresholdForPremium() {
        return getRequestPreferences().getThresholdForPremium();
    }

    @Override
    public Double getFractionForPremium() {
        return getRequestPreferences().getFractionForPremium();
    }


    public String getQueryFactorType() {
        return getRequestPreferences().getQueryFactorType();
    }

    public static BaselineRequestContext createFor(BaselineExperiment experiment, BaselineRequestPreferences preferences){
        return new BaselineRequestContextBuilder()
                .forExperiment(experiment)
                .withPreferences(preferences)
                .build();
    }

    public static BaselineRequestContext createWithCustomGeneQueryDescription(BaselineExperiment experiment, BaselineRequestPreferences  preferences, String description){
        return new BaselineRequestContextBuilder()
                .forExperiment(experiment)
                .withPreferences(preferences)
                .withCustomQueryDescription(description)
                .build();
    }

    @Deprecated
    public List<Factor> getFilterFactorsInTheSameOrderAsTheExperimentHeader(){
        List<Factor> result = new ArrayList<>();
        return result;
    }

    @Deprecated
    public List<AssayGroupFactor> getOrderedAssayGroupFactors(){
        return new ArrayList<>();
    }

}