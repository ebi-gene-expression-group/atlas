package uk.ac.ebi.atlas.model.differential;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.geneannotation.arraydesign.DesignElementMappingProvider;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;

import javax.inject.Inject;
import javax.inject.Named;

@Named
@Scope("prototype")
public class MicroarrayProfilePrecondition extends DifferentialProfilePrecondition<MicroarrayProfile> {

    private DesignElementMappingProvider designElementMappingProvider;

    private String arrayDesignAcc;

    @Inject
    public void setDesignElementMappingProvider(DesignElementMappingProvider designElementMappingProvider) {
        this.designElementMappingProvider = designElementMappingProvider;
    }

    @Override
    protected boolean applyExtraConditions(MicroarrayProfile profile) {
        return !StringUtils.isBlank(designElementMappingProvider.getEnsGeneId(arrayDesignAcc, profile.getGeneId()));
    }


    public void setArrayDesignAcc(String arrayDesignAcc) {
        this.arrayDesignAcc = arrayDesignAcc;
    }
}
