package uk.ac.ebi.atlas.experimentpage.context;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayRequestContextBuilder
        extends DifferentialRequestContextBuilder
                <MicroarrayExperiment, MicroarrayRequestContext, MicroarrayRequestPreferences> {

    @Inject
    public MicroarrayRequestContextBuilder(MicroarrayRequestContext requestContext) {
        super(requestContext);
    }

    @Override
    public MicroarrayRequestContextBuilder withPreferences(MicroarrayRequestPreferences requestPreferences) {
        super.withPreferences(requestPreferences);
        requestContext.setArrayDesignAccession(requestPreferences.getArrayDesignAccession());
        return this;
    }

}