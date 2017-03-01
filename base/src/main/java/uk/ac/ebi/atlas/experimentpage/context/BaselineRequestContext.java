package uk.ac.ebi.atlas.experimentpage.context;

import com.google.common.collect.ImmutableSet;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.Set;

@Named
@Scope("request")
public class BaselineRequestContext extends RequestContext<AssayGroup,BaselineExperiment, BaselineRequestPreferences>
        implements BaselineProfileStreamOptions {


    public BaselineRequestContext(BaselineRequestPreferences requestPreferences, BaselineExperiment experiment) {
        super(requestPreferences, experiment);
    }

    @Override
    public Double getThresholdForPremium() {
        return requestPreferences.getThresholdForPremium();
    }

    @Override
    public Double getFractionForPremium() {
        return requestPreferences.getFractionForPremium();
    }


    public String getQueryFactorType() {
        return requestPreferences.getQueryFactorType();
    }

    public Set<OntologyTerm> ontologyTermsForColumn(AssayGroup assayGroup){
        ImmutableSet.Builder<OntologyTerm> b = ImmutableSet.builder();
        for(Factor factor : experiment.getExperimentalFactors().getFactorGroup(assayGroup.getId())){
            b.addAll(factor.getValueOntologyTerms());
        }

        return b.build();
    }

}