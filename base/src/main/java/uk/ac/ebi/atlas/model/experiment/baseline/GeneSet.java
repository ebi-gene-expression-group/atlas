package uk.ac.ebi.atlas.model.experiment.baseline;

import java.util.HashSet;
import java.util.Set;

public class GeneSet{

    private Set<OldBaselineProfile> baselineProfiles = new HashSet<>();

    private String id;

    public GeneSet(String id){
        this.id = id;
    }

    public GeneSet addBaselineProfile(OldBaselineProfile baselineProfile){
        baselineProfiles.add(baselineProfile);
        return this;
    }

    public OldBaselineProfile getAverageProfile(){
        OldBaselineProfile summaryProfile = new OldBaselineProfile(null, id);

        for(OldBaselineProfile baselineProfile: baselineProfiles){
            summaryProfile.sumProfile(baselineProfile);
        }

        return summaryProfile.foldProfile(baselineProfiles.size());
    }
}
