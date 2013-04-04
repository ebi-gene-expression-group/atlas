package uk.ac.ebi.atlas.model.differential;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.rnaseq.RnaSeqProfile;

import javax.inject.Named;

@Named
@Scope("prototype")
public class RnaSeqProfilePrecondition extends DifferentialProfilePrecondition<RnaSeqProfile> {

    @Override
    protected boolean applyExtraConditions(RnaSeqProfile profile) {
        return true;
    }
}
