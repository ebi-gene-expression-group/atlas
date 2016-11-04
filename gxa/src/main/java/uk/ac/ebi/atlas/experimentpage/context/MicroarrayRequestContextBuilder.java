
package uk.ac.ebi.atlas.experimentpage.context;

import uk.ac.ebi.atlas.web.MicroarrayRequestPreferences;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayRequestContextBuilder extends DifferentialRequestContextBuilder<MicroarrayRequestContext, MicroarrayRequestPreferences> {

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