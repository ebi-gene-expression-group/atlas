package uk.ac.ebi.atlas.experimentpage.context;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class RnaSeqRequestContextBuilder
        extends DifferentialRequestContextBuilder
                <DifferentialExperiment, RnaSeqRequestContext, DifferentialRequestPreferences> {

    @Inject
    public RnaSeqRequestContextBuilder(RnaSeqRequestContext requestContext) {
        super(requestContext);
    }

}