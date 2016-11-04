package uk.ac.ebi.atlas.baseline;

import java.util.HashSet;
import java.util.Set;

public class GeneSet{

    private Set<BaselineProfile> baselineProfiles = new HashSet<>();

    private String id;

    public GeneSet(String id){
        this.id = id;
    }

    public GeneSet addBaselineProfile(BaselineProfile baselineProfile){
        baselineProfiles.add(baselineProfile);
        return this;
    }

    public BaselineProfile getAverageProfile(){
        BaselineProfile summaryProfile = new BaselineProfile(null, id);

        for(BaselineProfile baselineProfile: baselineProfiles){
            summaryProfile.sumProfile(baselineProfile);
        }

        return summaryProfile.foldProfile(baselineProfiles.size());
    }
}
