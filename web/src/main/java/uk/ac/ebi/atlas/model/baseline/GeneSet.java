
package uk.ac.ebi.atlas.model.baseline;

import java.util.HashSet;
import java.util.Set;

//Not Spring managed because actually requires initialization parameters
//and Spring doesn't support assisted constructor injection
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
